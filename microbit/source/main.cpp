/*********************************************************************
 * FILENAME: main.cpp
 *
 * DESCRIPTION: The entry point of the powerglove application.
 *
 * AUTHOR: Cameron MacLeod
 ********************************************************************/

/*********************************************************************
 * Includes
 ********************************************************************/

#include "MicroBit.h"

/*********************************************************************
 * Private defines
 ********************************************************************/

// None

/*********************************************************************
 * Private function prototypes
 ********************************************************************/

// None

/*********************************************************************
 * Private data
 ********************************************************************/

MicroBit uBit;

/*********************************************************************
 * Public function implementations
 ********************************************************************/

// None

/*********************************************************************
 * Private function implementations
 ********************************************************************/

void onConnected(MicroBitEvent ev) {
    uBit.display.scroll("0R");
}

int main() {
    uBit.init();

    MicroBitPin p0(MICROBIT_ID_IO_P0, MICROBIT_PIN_P0, PIN_CAPABILITY_DIGITAL);
    MicroBitPin p1(MICROBIT_ID_IO_P1, MICROBIT_PIN_P1, PIN_CAPABILITY_DIGITAL);
    MicroBitPin p2(MICROBIT_ID_IO_P2, MICROBIT_PIN_P2, PIN_CAPABILITY_DIGITAL);

    // configure as inputs
    p0.eventOn(MICROBIT_PIN_EVENT_ON_EDGE);
    p1.eventOn(MICROBIT_PIN_EVENT_ON_EDGE);
    p2.eventOn(MICROBIT_PIN_EVENT_ON_EDGE);

    uBit.messageBus.listen(MICROBIT_ID_IO_P0, MICROBIT_PIN_EVT_RISE, onConnected, MESSAGE_BUS_LISTENER_IMMEDIATE);
    uBit.messageBus.listen(MICROBIT_ID_IO_P0, MICROBIT_PIN_EVT_FALL, onConnected, MESSAGE_BUS_LISTENER_IMMEDIATE);

    new MicroBitIOPinService(*uBit.ble, uBit.io);

    release_fiber();
}

/* END OF FILE */
