package com.souche.widget.keyboard;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Relish Wang
 * @since 2017/09/20
 */

@IntDef({
        KeyboardType.SYSTEM,
        KeyboardType.ID_CARD,
        KeyboardType.CAR_NUMBER
})
@Retention(RetentionPolicy.SOURCE)
public @interface KeyboardType {
    int SYSTEM = 0;
    int ID_CARD = 1;
    int CAR_NUMBER = 2;
}