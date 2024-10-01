package com.example.ejerciciosjhonbairop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor rotationSensor = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) : null;
        if (rotationSensor == null) {
            Toast.makeText(this, "Sensor de rotación no disponible", Toast.LENGTH_SHORT).show();
        }

        Button downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(v ->
                new DownloadImageTask().execute("https://www.artstation.com/artwork/XgNyAn.png")
        );
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try (InputStream in = new URL(urls[0]).openStream()) {
                return BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
                Toast.makeText(MainActivity.this, "Imagen descargada exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Error al descargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

