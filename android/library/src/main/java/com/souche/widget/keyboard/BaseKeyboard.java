package com.souche.widget.keyboard;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;

import com.facebook.react.bridge.Callback;

/**
 * @author Relish Wang
 * @since 2017/09/18
 */
@Deprecated
public abstract class BaseKeyboard implements KeyboardView.OnKeyboardActionListener {

    protected KeyboardView mKeyboardView;
    protected Callback mCallback;

    public BaseKeyboard(KeyboardView keyboardView, Callback callback) {
        mKeyboardView = keyboardView;
        mCallback = callback;

        Keyboard keyboard = new Keyboard(keyboardView.getContext(), keyboardResId());
        mKeyboardView.setKeyboard(keyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setOnKeyboardActionListener(this);
    }

    /**
     * 生成对应的键盘
     */
    protected abstract int keyboardResId();

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onText(CharSequence charSequence) {

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
