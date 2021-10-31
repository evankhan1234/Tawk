package com.tawk.framework.mvvm.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class CustomBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {

    private boolean isExpandedOrCollapsed;

    public CustomBottomSheetBehavior() {
        super();

        listenForSlideEvents();
    }

    public CustomBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        listenForSlideEvents();
    }

    void listenForSlideEvents() {
        setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                isExpandedOrCollapsed = slideOffset < 0.1f || slideOffset > 0.9f;
            }
        });
    }

}