package com.hardwork.fg607.relaxfinger.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.hardwork.fg607.relaxfinger.R;
import com.hardwork.fg607.relaxfinger.service.FloatingBallService;
import com.hardwork.fg607.relaxfinger.utils.AppUtils;
import com.hardwork.fg607.relaxfinger.utils.Config;
import com.hardwork.fg607.relaxfinger.utils.FloatingBallUtils;

import net.grandcentrix.tray.TrayAppPreferences;

import java.net.URISyntaxException;

public class BlankActivity extends Activity {

    private Intent mIntent = null;
    private boolean mIsNew = true;
    private TrayAppPreferences mPreferences;

    public static BlankActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_blank);

        mPreferences = FloatingBallUtils.getMultiProcessPreferences();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setClickable(true);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeMenu();

            }
        });



    }

    private void closeMenu() {

        if(mIntent == null){

            mIntent = new Intent();
            mIntent.putExtra("what", Config.CLOSE_MENU);
            mIntent.setClass(BlankActivity.this, FloatingBallService.class);
        }

        startService(mIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getBooleanExtra("finish",false)){

            finish();

        }

     /*   else if(intent.getStringExtra("packageName")!=null){

            AppUtils.startApplication(this,intent.getStringExtra("packageName"));

        }else if(intent.getStringExtra("intentUri")!=null){


            try {
                AppUtils.startActivity(this,intent.getStringExtra("intentUri"));
            } catch (URISyntaxException e) {


            }
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!mIsNew&&!mPreferences.getBoolean("addBackground",false)){

            finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsNew = false;
    }

    @Override
    public void onBackPressed() {

        closeMenu();
    }
}
