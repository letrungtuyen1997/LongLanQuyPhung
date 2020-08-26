package com.ss.core.exSprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GImageGrayscalable extends Image {
    static String vertexShader = "attribute vec4 a_position;\n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "\n" +
            "uniform mat4 u_projTrans;\n" +
            "\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "\n" +
            "void main() {\n" +
            "    v_color = a_color;\n" +
            "    v_texCoords = a_texCoord0;\n" +
            "    gl_Position = u_projTrans * a_position;\n" +
            "}";

    static final String FRAG = "#ifdef GL_ES\n" +
            "    precision mediump float;\n" +
            "#endif\n" +
            "\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform float u_grayness;\n" +
            "\n" +
            "void main() {\n" +
            "  vec4 c = v_color * texture2D(u_texture, v_texCoords);\n" +
            "  float grey = (c.r + c.g + c.b) / 3.0;\n" +
            "  vec3 blendedColor = mix(c.rgb, vec3(grey), u_grayness);\n" +
            "  gl_FragColor = vec4(blendedColor.rgb, c.a);\n" +
            "}";

    public static ShaderProgram grayscaleShader = new ShaderProgram(vertexShader,
            FRAG);

    public float getGrayness() {
        return grayness;
    }

    public void setGrayness(float grayness) {
        this.grayness = grayness;
    }

    private float grayness = 0f;
    public GImageGrayscalable(TextureAtlas.AtlasRegion region){
        super(region);
    }

    public void draw (Batch batch, float parentAlpha) {
        if(grayness==0f){
            super.draw(batch, parentAlpha);
        }
        else {
            batch.end();
            batch.setShader(grayscaleShader);
            batch.begin();
            grayscaleShader.setUniformf("u_grayness", grayness);
            super.draw(batch, parentAlpha);
            batch.end();
            batch.setShader(null);
            batch.begin();
        }
    }
}
