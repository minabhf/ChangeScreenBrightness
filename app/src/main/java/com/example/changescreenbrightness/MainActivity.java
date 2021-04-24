package com.example.changescreenbrightness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private SeekBar seekBar;
private int brightness;
private ContentResolver contentResolver;
private Window window;
TextView txtPerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar=findViewById(R.id.brightbar);
        txtPerc=findViewById(R.id.txtRercentage);
        contentResolver=getContentResolver();
        window=getWindow();
        seekBar.setMax(225);
        seekBar.setKeyProgressIncrement(1);

        try {
            brightness=Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);

        }catch (Exception e){
            Log.e("Error","cannot access system btightness.");
            e.printStackTrace();
        }
        seekBar.setProgress(brightness);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= 20){
                    brightness=20;
                }else {

                    brightness=progress;
                }

            }
            float perc =(brightness / (float) 225) * 100;
            txtPerc.setText((int)perc +"%")

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.System.putInt(contentResolver,Settings.System.SCREEN_BRIGHTNESS,brightness);
                WindowManager.LayoutParams layoutParams=window.getAttributes();
                layoutParams.screenBrightness=brightness / (float) 225;
                window.setAttributes(layoutParams);

            }
        });



    }
}