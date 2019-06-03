package com.example.ocwiejaj20.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowInfoOnCarSlectedScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_on_car_slected_screen);
        final Intent i = new Intent(this, HomeScreenActivity.class);
        Button homeScreen = (Button) findViewById(R.id.BackToHomeScreen);
        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        final Intent j = new Intent(this, ChoosePreferencesForElectricCar.class);

        Button nextScreen = (Button) findViewById(R.id.nextButton);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(j);
            }
        });
    }
}
