package com.jacobsheehy.best2016movies.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 * Receive Android Intents when the state of the network changes,
 * either to connected or disconnected.
 */
class NetworkStateChangeReceiver : BroadcastReceiver() {

    // Receive intents with information about network state
    override fun onReceive(context: Context, networkStateIntent: Intent) {
        if (networkStateChangeReceiverListener != null) {
            networkStateChangeReceiverListener!!.onNetworkConnectionChanged(isConnectedOrConnecting(context))
        }
    }

    // Check the connection status to determine if we're online
    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectionManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
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