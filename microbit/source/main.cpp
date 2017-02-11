#include "MicroBit.h"

MicroBit uBit;

void scroll(int pinNum) {
    uBit.display.scroll(pinNum);
}

int main() {
    uBit.init();

    MicroBitPin p0(MICROBIT_ID_IO_P0, MICROBIT_PIN_P0, PIN_CAPABILITY_DIGITAL);
    MicroBitPin p1(MICROBIT_ID_IO_P1, MICROBIT_PIN_P1, PIN_CAPABILITY_DIGITAL);
    MicroBitPin p2(MICROBIT_ID_IO_P2, MICROBIT_PIN_P2, PIN_CAPABILITY_DIGITAL);

    while(1) {
        int p0val = p0.getDigitalValue(PullDown);
        int p1val = p1.getDigitalValue(PullDown);
        int p2val = p2.getDigitalValue(PullDown);

        if (p0val == 1) {
            scroll(0);
        }

        if (p1val == 1) {
            scroll(1);
        }

        if (p2val == 1) {
            scroll(2);
        }

        uBit.sleep(10);
    }
}
