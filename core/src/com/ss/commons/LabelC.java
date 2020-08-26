package com.ss.commons;


import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LabelC extends Label {


  public LabelC(CharSequence text, LabelStyle style) {
    super(text, style);
  }

  @Override
  public void setText(CharSequence newText) {
    super.setText(newText);
    setSize(getPrefWidth(), getPrefHeight());
  }
}
