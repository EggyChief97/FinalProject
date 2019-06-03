package com.example.ocwiejaj20.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        final Intent i = new Intent(this,CarSelectionActivity.class);
        Button carSelectionScreen  = (Button) findViewById(R.id.nextScreenButton);
        carSelectionScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onDestroy();
                startActivity(i);
            }
        });
        final Intent j = new Intent(this, GarageScreen.class);
        Button garage = (Button) findViewById(R.id.Garage);
        garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                startActivity(j);
            }
        });

    }
}
