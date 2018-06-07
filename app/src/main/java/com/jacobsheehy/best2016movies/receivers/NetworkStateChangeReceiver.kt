package com.jacobsheehy.best2016movies.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.jacobsheehy.best2016movies.presenters.MoviePresenter

/**
 * Receive Android Intents when the state of the network changes,
 * either to connected or disconnected.
 */
class NetworkStateChangeReceiver : BroadcastReceiver() {

    // Receive intents with information about network state
    override fun onReceive(context: Context, networkStateIntent: Intent) {
        if (networkStateChangeReceiverListener != null) {
            networkStateChangeReceiverListener!!.onNetworkConnectionChanged(MoviePresenter.checkConnection(context))
        }
    }
    // Contract interface for others to use
    interface NetworkStateChangeReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    // For setting a listener statically
    companion object {
        var networkStateChangeReceiverListener: NetworkStateChangeReceiverListener? = null
    }
}