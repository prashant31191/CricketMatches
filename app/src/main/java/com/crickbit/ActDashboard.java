package com.crickbit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.utils.AppFlags;
import com.utils.PreferencesKeys;

/**
 * Created by prashant.patel on 9/4/2017.
 */

public class ActDashboard extends BaseActivity {
    Button btnLiveMatch;
    String strTitle = "Dashboard";

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup.inflate(this, R.layout.act_dashboard, llContainerSub);

        try {
            App.sharePrefrences.setPref(PreferencesKeys.strMenuSelectedId, "0");
            initialization();
            setClickEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialization() {
        rlMenu.setVisibility(View.VISIBLE);
        tvTitle.setText(strTitle);

        btnLiveMatch = (Button) findViewById(R.id.btnLiveMatch);


    }

    private void setClickEvent() {
        btnLiveMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActDashboard.this, ActLiveMatchList.class);
                intent.putExtra(AppFlags.tagFrom, "ActDashboard");
                startActivity(intent);
                animStartActivity();
            }
        });
    }
}
