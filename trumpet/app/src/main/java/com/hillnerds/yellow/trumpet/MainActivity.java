package com.hillnerds.yellow.trumpet;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.billthefarmer.mididriver.MidiDriver;

import java.nio.channels.Channel;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    protected MidiDriver midi;

    private Thread playThread;

    public boolean[] buttonList;

    private boolean midiSynthesizingStop = false;

    int previousNote;

    private static Instrument[] instrumentList = new Instrument[] {
            new Instrument("piano", 1, 40, 60),
            new Instrument("guitar", 26, 45, 70),
            new Instrument("trombone", 58, 55, 70),
            new Instrument("trumpet", 57, 64, 80),
            new Instrument("violin", 41, 50, 65),
            new Instrument("saxophone", 66, 45, 60),
            new Instrument("flute", 74, 60, 75)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        midi = new MidiDriver();
        buttonList = new boolean[] {false, false, false};
        Instrument trumpet = instrumentList[3];

        StartingSequence addStart = new StartingSequence(192, trumpet, 0);

        MidiParser mp = new MidiParser(addStart);
        playThread = new Thread(mp);
        playThread.start();

        previousNote = 60;

        ObservableObject.getInstance().addObserver(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (midi != null) {
            midi.start();
        }

        //MidiParser mp = new MidiParser(sortedMidiSequenceArray);
        //playThread = new Thread(mp);
        //playThread.start();

    }

    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();

        if (midi != null) {
            midi.stop();
        }
    }

    public void updateButtonList(String broadcast){
        Log.i("updateButtonList", MessageFormat.format("{0}", broadcast));
        switch (broadcast){
            case ("com.hillnerds.yellow.FINGER0_DOWN"):
                buttonList[0] = true;
                break;
            case ("com.hillnerds.yellow.FINGER0_UP"):
                buttonList[0] = false;
                break;
            case ("com.hillnerds.yellow.FINGER1_DOWN"):
                buttonList[1] = true;
                break;
            case ("com.hillnerds.yellow.FINGER1_UP"):
                buttonList[1] = false;
                break;
            case ("com.hillnerds.yellow.FINGER2_DOWN"):
                buttonList[2] = true;
                break;
            case ("com.hillnerds.yellow.FINGER2_UP"):
                buttonList[2] = false;
                break;
            default:
                break;
        }

        Log.i("updateButtonList", MessageFormat.format("Array: {0}, {1}, {2}", buttonList[0], buttonList[1], buttonList[2]));

        if (buttonList[0] == true && buttonList[1] == true && buttonList[2] == true ){
            sendMidi(144, 60, 60);
            sendMidi(144, previousNote, 0);
            previousNote = 60;
        }
        else if (buttonList[0] == true && buttonList[1] == false && buttonList[2] == true){
            sendMidi(144, 62, 60);
            sendMidi(144, previousNote, 0);
            previousNote = 62;
        }
        else if (buttonList[0] == true  && buttonList[1] == true && buttonList[2] == false){
            sendMidi(144, 64, 60);
            sendMidi(144, previousNote, 0);
            previousNote = 64;
        }
        else if (buttonList[0] == true && buttonList[1] == false && buttonList[2] == false){
            //Note note = new Note(144, 66, 60, 0);
            //MidiParser mp = new MidiParser(note);
            //playThread   = new Thread(mp);
            //playThread.start();
            sendMidi(144, 66, 60);
            sendMidi(144, previousNote, 0);
            previousNote = 66;
        }
        else if (buttonList[0] == false && buttonList[1] == false && buttonList[2] == true){
            sendMidi(144, 68, 60);
            sendMidi(144, previousNote, 0);
            previousNote = 68;
        }
        else if (buttonList[0] == false && buttonList[1] == true && buttonList[2] == false){
            sendMidi(144, 70, 60);
            sendMidi(144, previousNote, 0);
            previousNote = 70;
        }
        else if (buttonList[0] == false && buttonList[1] == true && buttonList[2] == true){
            sendMidi(144, 72, 60);
            sendMidi(144, previousNote, 0);
            previousNote = 72;
        } else {
            sendMidi(144, previousNote, 0);
            previousNote = 60;
        }
    }

    protected void sendMidi(int m, int p) {
        byte msg[] = new byte[2];

        msg[0] = (byte) m;
        msg[1] = (byte) p;
        Log.i("sendMidi", MessageFormat.format("{0}, {1}", m, p));
        //Writes the two byte array to the MIDI stream.
        midi.write(msg);
    }

    /**
     * Sends a three byte array to the MIDI stream. Three byte sequences are Notes.
     * @param m - the code sequence. Indicates the purpose of this particular MIDI sequence
     * @param n - the pitch of the note
     * @param v - the velocity of the note
     */
    protected void sendMidi(int m, int n, int v) {
        byte msg[] = new byte[3];

        Log.i("sendMidi", MessageFormat.format("{0}, {1}, {2}", m, n, v));
        msg[0] = (byte) m;
        msg[1] = (byte) n;
        msg[2] = (byte) v;

        //Writes the three byte array to the MIDI stream
        midi.write(msg);
    }

    /**
     * A safe sleep function including a try catch statement to capture exceptions.
     * Created to avoid using try catch every time a Thread sleeps.
     * Catches Exceptions: InterruptedException, occurs when a different process wants to interrupt
     * this Thread
     * @param time - a sleep time in miliseconds.
     */
    public void safeSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateButtonList(((Intent) arg).getAction());
    }

    public class MidiParser implements Runnable {
        private MidiSequence midiSequence;

        public MidiParser(MidiSequence midiSequence) {
            this.midiSequence = midiSequence;
        }

        public void run() {{
                if (midiSequence instanceof StartingSequence) {
                    sendMidi(midiSequence.startingCode, ((StartingSequence) midiSequence).instrument.instrumentMidiCode);
                } else {
                    sendMidi(midiSequence.startingCode, ((Note) midiSequence).pitch, ((Note) midiSequence).velocity);

                }
            }
        }
    }
}
