package com.hillnerds.yellow.trumpet;

/**
 * Created by aga on 11/02/17.
 */
public class StartingSequence extends MidiSequence {
    Instrument instrument;

    public StartingSequence (int startingCode, Instrument instrument, int timestamp){
        super(startingCode, timestamp);
        this.instrument = instrument;
    }
}
