yellow.
=======

A powered glove with an Android API. This was a hackathon project at StirHack 2017 by [Agnieszka Wasikowska](https://github.com/aga11313) and [Cameron MacLeod](https://github.com/notexactlyawe). The project consists of a rubber glove with buttons attached whose presses can be registered on a phone and picked up by any app through Broadcasts.

Project Structure
-----------------

The subfolders in this repo with their descriptions are as follows. 

 - **Scroll**

Scroll is an example application that consumes our button press API to scroll up and down an activity screen.

- **bluetooth_android**

This directory contains the BLE code to connect to the micro:bit on the phone and the broadcast code. It is the main application a user needs to install.

- **microbit**

The micro:bit application. Exposes an IOPin service.

- **trumpet**

An example application that enables playing a trumpet with the glove.
