package com.example.westf.homework1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText colorChanger;
    TextView colorText, hexColor, staticColor;
    Button clickMe, letsDraw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        colorChanger = findViewById(R.id.editText);
        colorText = findViewById(R.id.colorText);
        hexColor = findViewById(R.id.hexValue);
        staticColor = findViewById(R.id.staticColor);
        clickMe = findViewById(R.id.changeColor);
        letsDraw = findViewById(R.id.enableActivity2);
        main();
    }

    public void main() {

        letsDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                int redCode = random.nextInt(255);
                int blueCode = random.nextInt(255);
                int greenCode = random.nextInt(255);

                colorChanger.setTextColor(Color.rgb(redCode, greenCode, blueCode));
                staticColor.setTextColor(Color.rgb(redCode, greenCode, blueCode));
                colorText.setTextColor(Color.rgb(redCode, greenCode, blueCode));
                hexColor.setTextColor(Color.rgb(redCode, greenCode, blueCode));

                colorText.setText(redCode + "r, " + greenCode + "g, and " + blueCode + "b.");

                hexColor.setText(String.format("#%02x%02x%02x", redCode, greenCode, blueCode));
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
