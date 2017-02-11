package com.hillnerds.yellow.trumpet;

/**
 * Created by aga on 11/02/17.
 */
public class Note extends MidiSequence {
    int pitch;
    int velocity;

    public Note(int starting_code, int pitch, int velocity, int timestamp){

        super(starting_code, timestamp);
        this.pitch = pitch;
        this.velocity = velocity;

    }

}
