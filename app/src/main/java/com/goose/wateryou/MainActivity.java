package com.goose.wateryou;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button waterButton = null;
    Button lampButton = null;
    Button autoButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waterButton = findViewById(R.id.WaterToggle);
        lampButton  = findViewById(R.id.LampToggle);
        autoButton  = findViewById(R.id.AutoToggle);

    }

    public void onWater(View view) {

    }

    public void onLamp(View view) {

    }

    public void onAuto(View view) {
        if (autoButton.getText().equals(getString(R.string.off))) {
            waterButton.setClickable(true);
            lampButton.setClickable(true);
        }
        else if (autoButton.getText().equals(getString(R.string.on))){
            waterButton.setClickable(false);
            lampButton.setClickable(false);
        }
    }
}
