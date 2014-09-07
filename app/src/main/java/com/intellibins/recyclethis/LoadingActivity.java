package com.intellibins.recyclethis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.glass.widget.SliderView;
import com.intellibins.recyclethis.model.BinData;


public class LoadingActivity extends Activity {

    private SliderView mSliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mSliderView = (SliderView) findViewById(R.id.indeterm_slider);
        mSliderView.startIndeterminate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadingActivity.this.startActivity(new Intent(LoadingActivity.this, CaptureActivity.class));
                finish();
            }
        }, 2000);
    }
}
