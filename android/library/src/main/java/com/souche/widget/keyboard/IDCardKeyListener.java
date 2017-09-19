package com.souche.widget.keyboard;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.XmlRes;

import com.facebook.react.bridge.Callback;

/**
 * @author Relish Wang
 * @since 2017/09/18
 */
@Deprecated
public class IDCardKeyListener extends BaseKeyboard {


    public IDCardKeyListener(KeyboardView keyboardView, Callback callback) {
        super(keyboardView, callback);
    }

    @XmlRes
    @Override
    protected int keyboardResId() {
        return R.xml.keyboard_id_card;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode == Keyboard.KEYCODE_DELETE) { // 回退
            mCallback.invoke("delete");
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            mCallback.invoke("cancel");
        } else {
            mCallback.invoke(Character.toString((char) primaryCode));
        }
    }
}
