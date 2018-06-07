package com.jacobsheehy.best2016movies.activities

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jacobsheehy.best2016movies.R
import com.jacobsheehy.best2016movies.adapters.MovieAdapter
import com.jacobsheehy.best2016movies.presenters.MoviePresenter
import com.jacobsheehy.best2016movies.receivers.NetworkStateChangeReceiver
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main and only Android activity (View) for this application. On start, prepare the UI
 * elements and load the data.
 */
class MainActivity : AppCompatActivity(), NetworkStateChangeReceiver.NetworkStateChangeReceiverListener {

    // Presenter
    private val moviePresenter = MoviePresenter()

    // Handle network state
    private var networkStateChangeReceiver = NetworkStateChangeReceiver()
    private var networkMessageSnackbar: Snackbar? = null
    private var isConnected = false

    // Application execution starts here.
    // Check for connections, register receivers,
    // setup the presenter, start loading data.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideSupportActionBar()
        checkNetwork()
        registerReceivers()
        initRecyclerView()
        loadMovies()
    }

    // We'll use our own title bar
    private fun hideSupportActionBar() {
        supportActionBar?.hide()
    }

    // Don't wait for the broadcast receiver callback,
    // we also want an immediate connection check here.
    private fun checkNetwork() {
        isConnected = MoviePresenter.checkConnection(applicationContext)
    }

    // use our Movie Presenter to load and display data
    private fun loadMovies() {
        moviePresenter.requestMovies(recyclerMovies, progressLoadingMovies)
    }

    // Initialize general properties about the RecyclerView, including
    // layout manager and data source (as well as listeners, adapters)
    private fun initRecyclerView() {
        recyclerMovies.setHasFixedSize(false)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerMovies.layoutManager = layoutManager
        recyclerMovies.adapter = MovieAdapter(moviePresenter.allMovies, moviePresenter, moviePresenter)
    }

    // Display a notice if we are missing network access, and load data
    // if we're just getting online
    private fun showNetworkStateMessage(online: Boolean) {
        if (!online) {
            networkMessageSnackbar = Snackbar.make(layoutMainContainer, getString(R.string.offline_message), Snackbar.LENGTH_LONG)
            networkMessageSnackbar?.duration = Snackbar.LENGTH_INDEFINITE
            networkMessageSnackbar?.show()
        } else {
            networkMessageSnackbar?.dismiss()
            loadMovies()
        }
    }

    // Start listening for changes to network state
    private fun registerReceivers() {
        registerReceiver(networkStateChangeReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    // handle dealing with network change
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkStateMessage(isConnected)
    }

    // Set our network state change listener
    override fun onResume() {
        super.onResume()
        NetworkStateChangeReceiver.networkStateChangeReceiverListener = this
    }

    // Unregister our network state receiver
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkStateChangeReceiver)
    }

}
