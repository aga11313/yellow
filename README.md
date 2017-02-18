yellow.
=======

A powered glove with an Android API. This was a hackathon project at StirHack 2017 by [Agnieszka Wasikowska](https://github.com/aga11313) and [Cameron MacLeod](https://github.com/notexactlyawe). The project consists of a rubber glove with buttons attached at the fingertips. Button presses are registered by a microbit strapped to the glove. Later they are broadcasted by Bluetooth and picked up by the phone.

An android app will continously transmitt theese button presses which can be picked up by any app through Broadcasts.

Project Structure
-----------------

The subfolders in this repo with their descriptions are as follows. 


- **bluetooth_android**

This directory contains the BLE code to connect to the micro:bit on the phone and the broadcast code. It is the main application a user needs to install.

- **microbit**

The micro:bit application. Exposes an IOPin service.

- **trumpet**

An example application testing the functionalities of the power glove. Enables playing trumpet by recieving the transmitted button
presses. Uses a MIDI synthesizer to produce notes of different pitch depending on the current sequence of buttons pressed.

 - **Scroll**

An example application that consumes our button press API to scroll up and down an activity screen.
