package com.everis.contacts.View;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.everis.contacts.R;

import androidx.appcompat.app.AppCompatActivity;
import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this).withFullScreen().withTargetActivity(ContactActivity.class)
                .withSplashTimeOut(3500)
                .withBackgroundColor(Color.parseColor("#3c73a3"))
                .withHeaderText("")
                .withFooterText(getString(R.string.developer))
                .withBeforeLogoText("")
                .withAfterLogoText(getString(R.string.app_name))
                .withLogo(R.mipmap.ic_launcher_round);

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
