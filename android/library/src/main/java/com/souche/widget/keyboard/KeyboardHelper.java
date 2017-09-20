package com.souche.widget.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;

/**
 * @author Relish Wang
 * @since 2017/09/18
 */
public class KeyboardHelper {


    public static void bind(final EditText et, @KeyboardType int type) {
        KeyboardView keyboard = KeyboardFactory.create(et.getContext(), type);
        bind(et, keyboard);
    }

    public static void bind(EditText et, KeyboardView keyboard) {
        bind(et, keyboard, null);
    }

    /**
     * 为EditText绑定自定义键盘，并设置自定义键盘的特殊键的功能
     *
     * @param et       输入框(EditText)
     * @param keyboard 键盘(KeyboardView)
     * @param listener 键盘事件监听器
     */
    public static void bind(final EditText et, final KeyboardView keyboard, KeyboardView.OnKeyboardActionListener listener) {
        if (et == null) throw new NullPointerException("EditText should not be NULL!");
        if (keyboard == null) return; // 表示使用系统键盘
        keyboard.setOnKeyboardActionListener(listener != null ? listener : new OnKeyboardActionAdapter(et) {
            @Override
            public void close() {
                hideCustomInput(et);
            }
        });
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showCustomInput(et,keyboard);
                return true;
            }
        });
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                UiThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showCustomInput(et,keyboard);
                    }
                });
            }
        });
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showCustomInput(et,keyboard);
                } else {
                    hideCustomInput(et);
                }
            }
        });
    }

    private static class KeyboardFactory {
        static KeyboardView create(Context context, @KeyboardType int type) {
            KeyboardView keyboardView = new KeyboardView(context, null);
            Keyboard keyboard;
            switch (type) {
                case KeyboardType.ID_CARD:
                    keyboard = new Keyboard(context, R.xml.keyboard_id_card);
                    break;
                case KeyboardType.CAR_NUMBER:
                    keyboard = new Keyboard(context, R.xml.keyboard_car_number);
                    break;
                case KeyboardType.SYSTEM:// 系统键盘
                default:
                    return null;
            }
            keyboardView.setKeyboard(keyboard);
            keyboardView.setEnabled(true);
            keyboardView.setPreviewEnabled(false);
            return keyboardView;
        }
    }

    private static void showCustomInput(EditText et, KeyboardView keyboard) {
        hideSysInput(et);
        Object obj = et.getTag(R.id.keyboard);
        PopupWindow keyboardWindow;
        if (obj == null) {
            keyboardWindow = new PopupWindow(
                    keyboard,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            keyboardWindow.setAnimationStyle(R.style.BottomDialog);
            et.setTag(R.id.keyboard, keyboardWindow);
        } else {
            keyboardWindow = ((PopupWindow) obj);
        }
        Window window = getWindow(et);
        if (window == null) return;
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View decorView = window.getDecorView();
        if (decorView == null) return;
        keyboardWindow.showAtLocation(decorView, Gravity.BOTTOM, 0, 0);
        keyboardWindow.update();
    }

    private static void hideCustomInput(EditText et) {
        hideSysInput(et);
        et.clearFocus();
        Object tag = et.getTag(R.id.keyboard);
        if (tag == null) return;
        if (tag instanceof PopupWindow) {
            PopupWindow window = (PopupWindow) tag;
            if (window.isShowing()) {
                window.dismiss();
            }
        }
    }

    private static void hideSysInput(EditText et) {
        IBinder windowToken = et.getWindowToken();
        if (windowToken != null) {
            InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static Window getWindow(EditText et) {
        Context context = et.getContext();
        if (context instanceof ReactContext) {
            context = ((ReactContext) context).getBaseContext();
        } else if (context instanceof Activity) {
            //do nothing.
        }
        return ((Activity) context).getWindow();
    }
}
