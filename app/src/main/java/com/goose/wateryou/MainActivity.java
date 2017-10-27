package com.goose.wateryou;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

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

    }

    //when the "Lamp" button is pressed
    public void onLamp(View view) {

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
}
