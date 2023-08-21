package com.whatswebdots.whatswebscannerpy.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pesonal.adsdk.AppManage;
import com.whatswebdots.whatswebscannerpy.gbwhats.R;

public class gb_intro4 extends AppCompatActivity {
    CardView btn_next;
    FrameLayout native_container;

    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_intro4.this).showInterstitialAd(gb_intro4.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_intro4.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb_activity_intro4);
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_intro4.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_intro4.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(gb_intro4.this, gb_onActivity.class);
                startActivity(intent);
            }
        });
    }
}