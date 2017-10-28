package com.goose.wateryou;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    ToggleButton waterButton = null;
    ToggleButton lampButton = null;
    ToggleButton autoButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waterButton = findViewById(R.id.WaterToggle);
        lampButton  = findViewById(R.id.LampToggle);
        autoButton  = findViewById(R.id.AutoToggle);


    }

    //when the "Water Valve" toggle is pressed
    public void onWater(View view) {
        new upload().execute();
    }

    //when the "Lamp" button is pressed
    public void onLamp(View view) {
        new upload().execute();
    }

    //when the "Auto" toggle is pressed
    public void onAuto(View view) {
        //if the toggle is set to "off" enable the manual toggles (ie. Water Valve & Lamp)
        if (autoButton.getText().equals(getString(R.string.off))) {
            waterButton.setEnabled(true);
            lampButton.setEnabled(true);
        }
        else {
            waterButton.setEnabled(false);
            lampButton.setEnabled(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private static class upload extends AsyncTask<Object, Object, Void>{
        @Override
        protected Void doInBackground(Object... objects) {
            HttpURLConnection urlConnection = null;
            String ServerUrl = "https://script.google.com/macros/s/AKfycbxtNl7nBbXJvDYXR3-MMG0_okpaXMvZeXveO9Bp1JEjOOzPJSQ/exec?pX0=3&pY0=1&pCode=codeW";
            try {
                URL url = new URL(ServerUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                Log.v("H", readStream(in));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                assert urlConnection != null;
                urlConnection.disconnect();
            }
            return null;
        }

        //convert the returned HTML text into a string
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
