package com.ss.commons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.platform.ToggleHandler;

/* renamed from: com.ss.core.commons._ToggleButton */
public class _ToggleButton {
    /* access modifiers changed from: private */
    public boolean active;
    /* access modifiers changed from: private */
    public String ctx;
    /* access modifiers changed from: private */
    public ToggleHandler handler;
    /* access modifiers changed from: private */

    /* renamed from: t1 */
    public Image t1;
    /* access modifiers changed from: private */

    /* renamed from: t2 */
    public Image t2;

    public _ToggleButton(Image image, Image image2, String str, ToggleHandler toggleHandler) {
        this.t1 = image;
        this.t2 = image2;
        this.handler = toggleHandler;
        this.ctx = str;
        evenclick(this.t1);
        evenclick(this.t2);
    }

    /* access modifiers changed from: 0000 */
    public void evenclick(Image image) {
        image.addListener(new ClickListener() {
            public void clicked(InputEvent inputEvent, float f, float f2) {
            _ToggleButton.this.active = !_ToggleButton.this.active;
            super.clicked(inputEvent, f, f2);
//            SoundEffect.Play(SoundEffect.click);
            if (_ToggleButton.this.active) {
                _ToggleButton.this.t2.setVisible(true);
                _ToggleButton.this.t1.setVisible(false);
                _ToggleButton.this.handler.activeHandler(_ToggleButton.this.ctx);
                return;
            }
            _ToggleButton.this.t2.setVisible(false);
            _ToggleButton.this.t1.setVisible(true);
            _ToggleButton.this.handler.deactiveHandler(_ToggleButton.this.ctx);

            }
        });
    }
}
