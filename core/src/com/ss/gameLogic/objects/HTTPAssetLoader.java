package com.ss.gameLogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ss.gameLogic.config.Config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class HTTPAssetLoader {

  private ArrayList<LoadItem> loadItems;
  private int mileStone = 0;
  private int loadCount = 0;
  private Listener listener;
  private boolean errorFound = false;

  private static HTTPAssetLoader inst;
  public static HTTPAssetLoader inst() {
    if (inst == null)
      return new HTTPAssetLoader();
    return inst;
  }

  public void init(Listener listener, ArrayList<LoadItem> items) {
    if (items == null || items.size() == 0) {
      listener.error(new IllegalArgumentException("loadSingleTexture items null or have zero element"));
      errorFound = true;
      return;
    }
    this.loadItems = items;
    this.mileStone = loadItems.size();
    this.loadCount = 0;
    this.errorFound = false;
    this.listener = listener;
    loadAssets();
  }

  private void loadAssets() {
    if (errorFound)
      return;
    for (LoadItem item : loadItems) {
      loadSingleTexture(item);
    }
  }

  private void loadSingleTexture(LoadItem item) {
    final String path = "http/" + item.id + ".png";
    Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
    httpRequest.setUrl(item.textureURI);

    Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
      @Override
      public void handleHttpResponse (Net.HttpResponse httpResponse) {
        if (httpResponse.getStatus().getStatusCode() >= 200 && httpResponse.getStatus().getStatusCode() < 300) {

          final byte[] rawByte = httpResponse.getResult();
          try {

            final FileHandle file;
//            if (Gdx.files.isExternalStorageAvailable())
//              file = Gdx.files.external(path);
//            else
              file = Gdx.files.local(path);
            file.writeBytes(rawByte, false);

//            BufferedOutputStream bos = new BufferedOutputStream(file.write(false));
//            BufferedInputStream bis = new BufferedInputStream(is);
//            byte[] buffer = new byte[16384];
//            int len;
//
//            while ((len = bis.read(buffer)) > 0) {
//              bos.write(buffer, 0, len);
//            }
//
//            bos.flush();
//            bos.close();
//            bis.close();

            Gdx.app.postRunnable(() -> {
              if (file.exists() && !errorFound && loadCount < mileStone) {
                try {
                  Texture texture = new Texture(file);
                  texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                  item.itemTexture = new TextureRegion(texture);
                  loadCount++;
                  if (loadCount == mileStone) {
                    listener.finish(loadItems);
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            });
          }
          catch (Exception e) {
            errorFound = true;
            Gdx.app.postRunnable(() -> listener.error(e));
          }
        }
        else {
          errorFound = true;
          Gdx.app.postRunnable(() -> listener.error(new Exception("HTTP request failed, status code: " + httpResponse.getStatus().getStatusCode())));
        }
      }

      @Override
      public void failed (Throwable t) {
        errorFound = true;
        Config.checkConnet=false;
        Gdx.app.postRunnable(() -> listener.error(t));
      }

      @Override
      public void cancelled() {
        errorFound = true;
        Gdx.app.postRunnable(() -> listener.error(new Exception("HTTP request cancelled")));
      }
    });
  }

  public static class LoadItem {
    private int id;
    private String textureURI;
    private String displayName;
    private TextureRegion itemTexture;
    private String iosStoreURI;
    private String androidStoreURI;
    private String fiStoreURI;

    public static LoadItem newInst(int id, String url, String displayName, String androidStoreURI, String iosStoreURI, String fiStoreURI) {
      LoadItem item = new LoadItem();
      item.id = id;
      item.textureURI = url;
      item.displayName = displayName;
      item.androidStoreURI = androidStoreURI;
      item.iosStoreURI = iosStoreURI;
      item.fiStoreURI = fiStoreURI;
      return item;
    }

    public String getDisplayName() {
      return displayName;
    }

    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

    public String getTextureURI() {
      return textureURI;
    }

    public void setTextureURI(String textureURI) {
      this.textureURI = textureURI;
    }

    public TextureRegion getItemTexture() {
      return itemTexture;
    }

    public void setItemTexture(TextureRegion itemTexture) {
      this.itemTexture = itemTexture;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getAndroidStoreURI() {
      return androidStoreURI;
    }

    public String getFiStoreURI() {
      return fiStoreURI;
    }

    public String getIosStoreURI() {
      return iosStoreURI;
    }

    public void setAndroidStoreURI(String androidStoreURI) {
      this.androidStoreURI = androidStoreURI;
    }

    public void setFiStoreURI(String fiStoreURI) {
      this.fiStoreURI = fiStoreURI;
    }

    public void setIosStoreURI(String iosStoreURI) {
      this.iosStoreURI = iosStoreURI;
    }
  }

  public interface Listener {
    void finish(ArrayList<LoadItem> loadedItems);
    void error(Throwable e);
  }

}