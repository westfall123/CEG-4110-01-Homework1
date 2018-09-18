package com.example.westf.homework1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    int redProgress, greenProgress, blueProgress;
    TextView red, green, blue;
    Button clear, save, randomColor;
    SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    PaintView pv;
    ImageView colorSample;

    public static int REQUEST_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
    }

    public void init() {
        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);

        clear = findViewById(R.id.clear);
        save = findViewById(R.id.save);
        randomColor = findViewById(R.id.randomColor);
        redSeekBar = findViewById(R.id.redSeekBar);
        greenSeekBar = findViewById(R.id.greenSeekBar);
        blueSeekBar = findViewById(R.id.blueSeekBar);

        colorSample = findViewById(R.id.colorSample);

        pv = findViewById(R.id.paintView);

        setSampleColorBackground(0, 0, 0);

        requestSavePermission();

        main();
    }

    public void main() {

        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red.setText("" + progress);
                redProgress = progress;
                setSampleColorBackground(redProgress, greenProgress, blueProgress);
                pv.setPaintColor(redProgress, greenProgress, blueProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                green.setText("" + progress);
                greenProgress = progress;
                setSampleColorBackground(redProgress, greenProgress, blueProgress);
                pv.setPaintColor(redProgress, greenProgress, blueProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blue.setText("" + progress);
                blueProgress = progress;
                setSampleColorBackground(redProgress, greenProgress, blueProgress);
                pv.setPaintColor(redProgress, greenProgress, blueProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        randomColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pv.clear();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    /**
     * If the phone has not already granted the application permissoin to write to the external
     * storage then give the user a prompt.
     */
    public void requestSavePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }

    public void openActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setSampleColorBackground(int red, int green, int blue) {
        // set the paint color and the color of the sample
        pv.setPaintColor(red, green, blue);
        colorSample.setBackgroundColor(Color.rgb(redProgress, greenProgress, blueProgress));
    }

    public static Bitmap viewToBitmap(View view, int width, int height) {
        //
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    /**
     * returns the unique directory for the phones DCIM folder
     */
    private File getDirectory() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(dir, "homework");
    }

    /** This save method creates a unique filename and specifies an exact path to the phones
     *  photo application.  The specified path is then used in an output stream to write the
     *  created bitmap to the phones storage in .jpeg format.
     */
    public void save() {
        FileOutputStream os = null;
        File file = getDirectory();

        if (!file.exists() && !file.mkdirs()) {
            return;
        }

        // creates a unique name for the image using the time and date it was created
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String imageName = "IMG" + date + ".jpeg";
        String fileLocation = file.getAbsolutePath() + "/" + imageName;
        File newFile = new File(fileLocation);

        try {
            os = new FileOutputStream(fileLocation);
            Bitmap bitmap = viewToBitmap(pv, pv.getWidth(), pv.getHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
            Toast.makeText(this, "Art piece saved to gallery", Toast.LENGTH_SHORT).show();
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(newFile));
        sendBroadcast(intent);
    }
}
