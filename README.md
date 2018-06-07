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
