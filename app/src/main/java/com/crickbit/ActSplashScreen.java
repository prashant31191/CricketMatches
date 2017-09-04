package com.crickbit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.utils.AppFlags;

/**
 * Created by prashant.patel on 9/4/2017.
 */

public class ActSplashScreen extends Activity
{
    ImageView ivAppIcon;
    TextView tvAppName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.act_splash);

        ivAppIcon = (ImageView) findViewById(R.id.ivAppIcon);
        tvAppName = (TextView) findViewById(R.id.tvAppName);

        RunAnimationText();
        RunAnimationImage();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActSplashScreen.this, ActDashboard.class);
                intent.putExtra(AppFlags.tagFrom, "ActSplashScreen");
                startActivity(intent);
            }
        }, 5000);


    }

    private void RunAnimationText()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.slide_in_down);
        a.reset();
        ivAppIcon.clearAnimation();
        ivAppIcon.startAnimation(a);
    }
    private void RunAnimationImage()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.slide_in_up);
        a.reset();

        tvAppName.clearAnimation();
        tvAppName.startAnimation(a);
    }
}
