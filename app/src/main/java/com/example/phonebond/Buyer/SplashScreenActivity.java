package com.example.phonebond.Buyer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.example.phonebond.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        if(!isConnected()){
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_network)
                    .setTitle("Internet Connection Alert")
                    .setMessage("Please Check your Internet Connection")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
        }else {
            EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                    .withFullScreen()
                    .withTargetActivity(MainActivity.class)
                    .withSplashTimeOut(5000)
                    .withBackgroundColor(Color.parseColor("#d35400"))
                    .withFooterText("Designed by Tabu Technologies ");

            config.getFooterTextView().setTextColor(Color.WHITE);
            config.getFooterTextView().setAllCaps(true);

            View easySplashScreen = config.create();
            setContentView(easySplashScreen);

        }

    }

    private boolean isConnected(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();

    }
}
