package com.eoinclancy.circularprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private int mInterval = 10; //1000*60*10
    CircularProgressBar circularProgressBar;
    TextView angle;
    TextView currBestVal;
    TextView highscoreVal;
    TextView numSquats;
    int countSquats = 1;
    private Random random = new Random();
    float min = 0;
    float max = 100;
    float f = 0;
    float previous = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularProgressBar = (CircularProgressBar)findViewById(R.id.circularPbar);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_horizontal_margin));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.default_background_stroke_width));
        int animationDuration = 2; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(2, animationDuration); // Default duration = 1500ms

        currBestVal = (TextView)findViewById(R.id.currentBestValue);
        highscoreVal = (TextView)findViewById(R.id.highscoreValue);
        angle = (TextView)findViewById(R.id.jointAngle);
        numSquats = (TextView)findViewById(R.id.NumSquats);
        numSquats.setText("Squats Completed: \t 0/100");
        mHandler = new Handler();                                               //Handler for implementing the timer
        mStatusChecker.run();                                                   //Start the timer


    }

    /* Timer used to periodically check on the GPS/Netowrk location services available */
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                //updateStatus(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);

                //circularProgressBar.setProgress(nextFloat(min,max));
                float value = iterate();
                if (previous < value){
                    highscoreVal.setText(""+value);
                    previous=value;
                }
                circularProgressBar.setProgress(value);
                angle.setText(value + "°");
                currBestVal.setText(value + "°");
            }

        }
    };

    public float nextFloat(float min, float max)
    {
        return min + random.nextFloat() * (max - min);
    }
    public float iterate() {
        if (f < 100) {
            f++;
            return f;
        } else {
            f = 0;
            numSquats.setText("Squats Completed: \t " + countSquats + "/100" );
            countSquats++;
            return 100f;
        }
    }
}

