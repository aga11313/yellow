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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        midi = new MidiDriver();
        //initialize the activeButton array to all false
        buttonList = new boolean[] {false, false, false};
        //initialize the instrument to trumpet
        Instrument trumpet = new Instrument("trumpet", 57, 64, 80);

        //add a startingSequence instrument change to the MIDI stream in channel 0
        StartingSequence addStart = new StartingSequence(192, trumpet, 0);

        //create a new thread playThread for the MIDI synthesis
        MidiParser mp = new MidiParser(addStart);
        playThread = new Thread(mp);
        playThread.start();

        //initialize the first note to the middle C
        previousNote = 60;

        //adds an Observer to the ObservableObject in this class
        ObservableObject.getInstance().addObserver(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (midi != null) {
            midi.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (midi != null) {
            midi.stop();
        }
    }

    /**
     * Method called whenever a braodcast is recieved.
     * @param broadcast - the broadcast message received
     */
    public void broadcastReceived(String broadcast){
        updateButtonList(broadcast);
        updateMidiSynthesis();
    }


    /**
     * Method called from within the broadcastReceived. Receive the broadcasts on button presses
     * and updates the list of currently pressed buttons.
     * @param broadcast - the broadcast message received
     */
    public void updateButtonList(String broadcast){
        Log.i("updateButtonList", MessageFormat.format("{0}", broadcast));

        //switch statement updating the activeButton List
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

    }

    /**
     * Called whenever a broadcast received and state of activeButton is changed.
     * Based on the combination of buttons pressed sends appropriate midiSequences to the MIDI
     * stream.
     */
    public void updateMidiSynthesis(){
        //TODO: make the button presses more realistic to how a real trumpet is played
        //TODO: introduce the possibility of more scales.

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

    /**
     * Sends a two byte array to the MIDI stream. Two byte sequences are Strating Seqences.
     * @param m - the code sequence. Indicates the purpose of this particular MIDI sequence
     * @param p - the instrument change code
     */
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
     * Method called for this Observer of the ObservableObject whenever it is changed.
     * @param o - the observable object
     * @param arg - the data with which the object was updated with
     */
    @Override
    public void update(Observable o, Object arg) {
        broadcastReceived(((Intent) arg).getAction());
    }

    /**
     * A class for MIDI synthesis happening in a separate thread.
     */
    public class MidiParser implements Runnable {
        private MidiSequence midiSequence;

        public MidiParser(MidiSequence midiSequence) {
            this.midiSequence = midiSequence;
        }

        /**
         * Method called when the Thread MidiParser is started.
         */
        public void run() {
            if (midiSequence instanceof StartingSequence) {
                sendMidi(midiSequence.startingCode, ((StartingSequence) midiSequence).instrument.instrumentMidiCode);
            } else {
                sendMidi(midiSequence.startingCode, ((Note) midiSequence).pitch, ((Note) midiSequence).velocity);

            }
        }
    }
}
