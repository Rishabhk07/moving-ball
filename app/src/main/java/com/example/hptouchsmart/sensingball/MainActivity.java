package com.example.hptouchsmart.sensingball;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    ImageView imageView ;
    SensorManager sensorManager;
    public static final String TAG = "TAG";
    float  xAccel , xvel = 0.0f ;
    float yAccel , yvel = 0.0f  ;
    long prevtimeStamp = 0;
    int maxX;
    int maxY;
    Display display;
    Point size;
    int xPos;
    int yPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelMeter , SensorManager.SENSOR_DELAY_GAME);

        imageView = (ImageView) findViewById(R.id.ball);
        imageView.scrollTo(maxX/2,maxY/2);


        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        maxX = size.x;
        maxY = size.y;

        Log.d(TAG , "maxX " +maxX);
        Log.d(TAG , "maxY "+maxY);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//            prevtimeStamp = event.timestamp;
//            Log.d(TAG, "Oncreate Sensor : " + event.values[0]);
//            Log.d(TAG, "Oncreate Sensor : " + event.values[1]);
//            Log.d(TAG, "Oncreate Sensor : " + event.values[2]);

//        x = x + event.values[0];
//        y =y + event.values[1];
//
//        if((event.values[0] > 3 || event.values[0] < -3) && x <maxX && x > 0) {
//            x = x + event.values[0];
//            Log.d(TAG, "location X " + x);
//        }
//
//        if((event.values[1] > 3 || event.values[1] < -3) && y <maxY && y > 0) {
//            y = y + event.values[1];
//            Log.d(TAG, "location Y " + y);
//        }
//
//        x = Gravity2screen(event.values[0] , maxX);
//        y = Gravity2screen(event.values[1]  , maxY);
//
//
//
//        imageView.setX(x);
//        imageView.setY(y);



        if((event.values[0] > 0 || event.values[0] < -0) || (event.values[1] > 0 || event.values[1] < -0) ) {

//            xPos = Gravity2screen(event.values[0], maxX);
//            yPos = Gravity2screen(event.values[1], maxY);

            Log.d(TAG, "x : " + event.values[0]);
            Log.d(TAG, "y : " + event.values[1]);

            xPos  -= event.values[0];
            yPos += event.values[1];



            if(xPos > (maxX-100)){
                xPos = maxX-100;
            }else if(xPos < 1){
                xPos = 1;
            }

            if(yPos > (maxY-400)){
                yPos = maxY-400;
            }else if(yPos < 0 ){
                yPos = 0;
            }

            //imageView.scrollBy(xPos , yPos);

            imageView.setX(xPos);
            imageView.setY(yPos);

        }


    }

    private void updateBall() {

        float framTime = 0.6666f;

        xvel += xAccel*framTime;
        yvel += yAccel*framTime;

        float XS = (xvel/2)*framTime;
        float YS = (yvel/2)*framTime;

        xPos -= XS;
        yPos -=YS;


        if(xPos > maxX){
            xPos = maxX;
        }else if(xPos < 0){
            xPos = 0;
        }

        if(yPos > maxY){
            yPos = maxY;
        }else if(yPos < 0){
            yPos = 0;
        }

        imageView.setX(xPos);
        imageView.setY(yPos);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private int Gravity2screen(float gravity , int flag){
        return (int) (((gravity+10)/20)*flag);
    }
}
