package com.example.myapplication;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final float[] gravedad = new float[3];
    private final float[] AceleracionLineal = new float[3];
    private SensorManager SensorManager;
    private Sensor SensoAcelerometro;
    private TextView result;
    private TextView aceleracionX;
    private TextView aceleracionY;
    private TextView aceleracionZ;
    View vista_Principal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.SensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.SensoAcelerometro = SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.result = findViewById(R.id.result);
        this.vista_Principal = findViewById(R.id.back);
        this.aceleracionX = findViewById(R.id.aceleracionX);
        this.aceleracionY = findViewById(R.id.aceleracionY);
        this.aceleracionZ = findViewById(R.id.aceleracionZ);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorManager.registerListener(this, SensoAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.8f;

        gravedad[0] = alpha * gravedad[0] + (1 - alpha) * event.values[0];
        gravedad[1] = alpha * gravedad[1] + (1 - alpha) * event.values[1];
        gravedad[2] = alpha * gravedad[2] + (1 - alpha) * event.values[2];

        AceleracionLineal[0] = event.values[0] - gravedad[0];
        AceleracionLineal[1] = event.values[1] - gravedad[1];
        AceleracionLineal[2] = event.values[2] - gravedad[2];
        

        double aceleracionTotal = ((int) (Math.sqrt(Math.pow(AceleracionLineal[0], 2.0) + Math.pow(AceleracionLineal[1], 2.0) + Math.pow(AceleracionLineal[2], 2.0))* 1000)) / 1000;

        if(aceleracionTotal == 0){
            vista_Principal.setBackgroundColor(Color.YELLOW);
        }else{
            vista_Principal.setBackgroundColor(Color.WHITE);
        }


        aceleracionX.setText("X: " + AceleracionLineal[0]);
        aceleracionY.setText("Y: " +AceleracionLineal[1]);
        aceleracionZ.setText("Z: "+ AceleracionLineal[2]);
        result.setText(aceleracionTotal + "");
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}