package com.souche.widget.keyboard;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;

import com.facebook.react.bridge.Callback;

/**
 * @author Relish Wang
 * @since 2017/09/18
 */
public abstract class OnKeyboardActionAdapter implements KeyboardView.OnKeyboardActionListener {

    private Callback mCallback;
    private EasyCallback mEasyCallback;

    public OnKeyboardActionAdapter(Callback callback) {
        this.mCallback = callback;
    }

    public OnKeyboardActionAdapter(EasyCallback callback) {
        this.mEasyCallback = callback;
    }

    /**
     * （用于拓展）处理特殊的键盘按键响应
     *
     * @param primaryCode 主键值
     * @param keyCodes    所有键值
     * @param callback    RN回调
     */
    public void onKey(int primaryCode, int[] keyCodes, Callback callback) {
    }

    /**
     * 关闭键盘
     */
    public abstract void close();

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        onKey(primaryCode, keyCodes, mCallback);
        if (primaryCode == Keyboard.KEYCODE_DELETE) { // 回退(backspace)
            if (mCallback != null) mCallback.invoke("-1");
            if (mEasyCallback != null) mEasyCallback.onKeyEvent("-1");
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) { // 完成（complete）
            close();
            // mCallback.invoke("cancel");
            if (mCallback != null) mCallback.invoke("0");
            if (mEasyCallback != null) mEasyCallback.onKeyEvent("0");
        } else {
            String code = Character.toString((char) primaryCode);
            if (mCallback != null) {
                mCallback.invoke(code);
            }
            if (mEasyCallback != null) mEasyCallback.onKeyEvent(code);
        }
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
