package com.hillnerds.yellow.trumpet;

/**
 * Created by aga on 11/02/17.
 */

public class Player {

    //a hard coded list of all possible instruments
    private static Instrument[] instrumentList = new Instrument[] {
            new Instrument("piano", 1, 40, 60),
            new Instrument("guitar", 26, 45, 70),
            new Instrument("trombone", 58, 55, 70),
            new Instrument("trumpet", 57, 64, 80),
            new Instrument("violin", 41, 50, 65),
            new Instrument("saxophone", 66, 45, 60),
            new Instrument("flute", 74, 60, 75)
    };

    // (1100 0000) the first 4 bits of a starting sequence for an instrument change
    final static int INSTRUMENT_CHANGE_CODE = 192;

    //the amount of notes generated for each channel
    final static int CHANNEL_NOTE_AMOUNT = 16;

    // (1001 0000) the first 4 bits of a note on sequence
    final static int NOTE_ON_START_CODE = 144;

    public static Instrument generateInstrument(){
        int instrumentNumber = 3;
        return instrumentList[instrumentNumber];
    }

    public void generateSound (){

    }
}


