Movies 2016 Demo
=================

This is an Android app written in Kotlin as a demo to show popular movies from 2016. The data source is tmdb.org.  

Running
-------

Clone this repository and open the project in Android Studio to run.

Design and Tools
----------------

This code uses Model-View-Presenter architecture. API work is done with Retrofit2 Gson for parsing. 
The data is displayed in a RecyclerView in the MainActivity. Item views inside the RecyclerView are in a ConstraintLayout.

Testing
-------

This app was tested on a Pixel 2 with Android 8.1.0 (API 27), a Samsung Galaxy S4 with Android 4.2.2 (API 17), and the Android emulators (API 27).

Internationalization and RTL support
----------------------------------------------

The app is ready to be internationalized and already supports RTL through layouts with start and end parameters (rather than left and right)  .
