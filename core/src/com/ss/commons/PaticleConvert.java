package com.ss.commons;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class PaticleConvert {
  public PaticleConvert(){
    edit_file_particle();
  }
  private void edit_file_particle() {
    if (! (Gdx.app.getType() == Application.ApplicationType.Desktop)) return;
    String path_Particle = "particle";
    FileHandle folder = Gdx.files.local(path_Particle);
    for (FileHandle file : folder.list()) check_file(file);
  }

  private void check_file(FileHandle file_) {
    FileHandle[] arr_file = file_.list();
    if (arr_file.length == 0) process_file(file_);
    else for (FileHandle file : arr_file) process_file(file);
  }

  private void process_file(FileHandle file) {
    if (file.list().length > 0) {
      check_file(file);
      return;
    }
    if (file.extension().equals("png")) return;
    String[] arr = file.readString().split("\n");
    String log = "";
    int i = 0;
    for (String s : arr) {
      i++;
      String[] arr_s = s.split(":");
      if (arr_s[0].equals("independent")) continue;
      log += s + (i == arr.length ? "" : "\n");
    }
    file.writeString(log, false);
  }
}
