Getting started with C/C++ on the Micro:bit
===========================================

The resource that all this information is based off is this useful site here:

 **[Offline C/C++ Development With the Micro:bit](http://www.i-programmer.info/programming/hardware/9654-offline-cc-development-with-the-microbit-.html)**

Firstly you need to install [yotta](http://yottadocs.mbed.com/).

After you have installed that, run the following commands in this folder:

``` bash
yotta target bbc-microbit-classic-gcc
yotta install lancaster-university/microbit
yotta build
```

This should produce a hex file in `microbit/build/bbc-microbit-classic-gcc/source` called `yellowglove-combined.hex`. Copy this file to your connected Micro:bit. This will install the program and start it running.

[Micro:bit runtime docs](https://lancaster-university.github.io/microbit-docs)
