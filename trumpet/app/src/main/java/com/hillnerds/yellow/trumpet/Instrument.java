package com.hillnerds.yellow.trumpet;

/**
 * Created by aga on 11/02/17.
 */
public class Instrument {
    String instrument;
    int instrumentMidiCode;
    int rangeMin;
    int rangeMax;

    public Instrument(String instrument, int instrumentMidiCode, int rangeMin, int rangeMax){

        this.instrument = instrument;
        this.instrumentMidiCode = instrumentMidiCode;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;

    }

}
