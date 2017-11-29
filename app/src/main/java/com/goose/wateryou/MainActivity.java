package com.goose.wateryou;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class MainActivity extends Activity {
    private ToggleButton waterButton = null;
    private ToggleButton lampButton = null;
    private EditText timerText = null;

    private String Water = "0";
    private String Lamp = "0";
    private String Timer = "0";
    private boolean read = true;

    /**
     * Create the main activity.
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waterButton = findViewById(R.id.WaterToggle);
        lampButton  = findViewById(R.id.LampToggle);
        timerText = findViewById(R.id.TimerText);

        timerText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Timer = timerText.getText().toString();
                new upload().execute();
            }
        });

        new upload().execute();
    }

    //when the "Water Valve" toggle is pressed
    public void onWater(View view) {
        if (waterButton.isChecked())
            Water = "1";
        else
            Water = "0";

        new upload().execute();
    }

    //when the "Lamp" button is pressed
    public void onLamp(View view) {
        if (lampButton.isChecked())
            Lamp = "1";
        else
            Lamp = "0";

        new upload().execute();
    }



    //when the "Auto" toggle is pressed

    /*private void switchAuto(){
        //if the toggle is set to "off" enable the manual toggles (ie. Water Valve & Lamp)
        if (!autoButton.isChecked()) {
            waterButton.setEnabled(true);
            lampButton.setEnabled(true);
        }
        else {
            waterButton.setEnabled(false);
            lampButton.setEnabled(false);
        }
    }*/

    public void setWater(final String newVal){
        final Handler myHandler = new Handler(Looper.getMainLooper());
        final Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Water newVal", newVal);
                if (Objects.equals(newVal, "1")){
                    waterButton.setChecked(true);
                    Water = "1";
                }
                else if (Objects.equals(newVal, "0")){
                    waterButton.setChecked(false);
                    Water = "0";
                }
            }
        };
        myHandler.post(myRunnable);
    }

    public void setLamp(final String newVal){
        final Handler myHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Lamp newVal", newVal);

                if (Objects.equals(newVal, "1")){
                    lampButton.setChecked(true);
                    Lamp = "1";
                }
                else if (Objects.equals(newVal, "0")){
                    lampButton.setChecked(false);
                    Lamp = "0";
                }
            }
        };
        myHandler.post(myRunnable);

    }

    public void setTimer(final String newVal){
        final Handler myHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Timer newVal", newVal);
                Timer = newVal;
            }
        };
        myHandler.post(myRunnable);
    }

    private class upload extends AsyncTask<Object, Object, Void> {
        @Override
        protected Void doInBackground(Object... params) {
            //Build URL with parameters
            String ServerUrl = "https://script.google.com/macros/s/AKfycbwPE9mfnqfUhx8GCZrJ0J-AzaJAS2S08IFjy1R8NC93vvIXurk/exec";
            if (!read){
                //parameters when writing
                ServerUrl += ("?pWater=" + Water);
                ServerUrl += ("&pLamp=" + Lamp);
                ServerUrl += ("&pTimer=" + Timer);
            }

            Log.d("ServerURL", ServerUrl);

            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(ServerUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                String html = readStream(in);

                int indexOfStart = html.indexOf("!START!");
                int indexOfEnd = html.indexOf("!END!");

                if (read) {
                    setWater(html.substring(indexOfStart + 9, indexOfStart + 10));
                    setLamp(html.substring(indexOfStart + 13, indexOfStart + 14));
                    setTimer(html.substring(indexOfStart + 17, indexOfEnd));
                    read = false;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                assert urlConnection != null;
                urlConnection.disconnect();
            }
            return null;
        }

        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while (i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }
    }
}