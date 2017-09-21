package com.souche.widget.keyboard;

import android.content.Context;
import android.graphics.Rect;
import android.view.inputmethod.InputMethodManager;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.views.textinput.ReactEditText;

/**
 * @author Relish Wang
 * @since 2017/09/21
 */
public class ReactEditTextCompat extends ReactEditText {
    private final InputMethodManager mInputMethodManager;

    public ReactEditTextCompat(Context context) {
        super(context);

        mInputMethodManager = (InputMethodManager)
                Assertions.assertNotNull(getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        boolean focus = super.requestFocus(direction, previouslyFocusedRect);
        hideSystemKeyboard();
        return focus;
    }

    private void hideSystemKeyboard() {
        mInputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
    }
}
