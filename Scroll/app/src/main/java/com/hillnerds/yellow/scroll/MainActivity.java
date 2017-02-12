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
        ObservableObject.getInstance().addObserver(this);

        TextView text = (TextView)findViewById(R.id.text);
        text.setText(R.string.much_text);

        sv = (ScrollView)findViewById(R.id.scroll);
        scrollDown();
    }

    public void scrollUp(){
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(sv.FOCUS_UP);
            }
        });
    }

    public void scrollDown(){
        sv.post(new Runnable() {
            public void run() {
                sv.fullScroll(sv.FOCUS_DOWN);
            }
        });
    }

    public void scroll (String broadcast){

        Log.i("updateButtonList", MessageFormat.format("{0}", broadcast));
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

    @Override
    public void update(Observable o, Object arg) {
        scroll(((Intent) arg).getAction());
    }
}
