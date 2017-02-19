package com.hillnerds.yellow.scroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    ScrollView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add an Observer to the ObservableObject in this class
        ObservableObject.getInstance().addObserver(this);

        // Set the text from strings.xml
        TextView text = (TextView)findViewById(R.id.text);
        text.setText(R.string.much_text);

        sv = (ScrollView)findViewById(R.id.scroll);
    }

    /**
     * Scrolls all the way to the top of ScrollView
     */
    public void scrollUp(){
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(sv.FOCUS_UP);
            }
        });
    }

    /**
     * Scrolls all the way to the bottom of a ScrollView
     */
    public void scrollDown(){
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(sv.FOCUS_DOWN);
            }
        });
    }

    /**
     * Method called form the update method. Decides which scroll action should be taken
     * based on the broadcast message.
     * @param broadcast - the String broadcast code
     */
    public void scroll (String broadcast){
        //a switch statement controlling which scroll action should be called on which button press
        switch (broadcast){
            case ("com.hillnerds.yellow.FINGER0_DOWN"):
                scrollUp();
                break;
            case ("com.hillnerds.yellow.FINGER0_UP"):
                break;
            case ("com.hillnerds.yellow.FINGER1_DOWN"):
                scrollDown();
                break;
            case ("com.hillnerds.yellow.FINGER1_UP"):
                break;
            case ("com.hillnerds.yellow.FINGER2_DOWN"):
                break;
            case ("com.hillnerds.yellow.FINGER2_UP"):
                break;
            default:
                break;
        }

    }

    /**
     * Method called when the ObservableObject is changed (this class is its Observer)
     * @param o - the ObservableObject
     * @param arg - the data changed in the ObservableObject
     */
    @Override
    public void update(Observable o, Object arg) {
        scroll(((Intent) arg).getAction());
    }
}
