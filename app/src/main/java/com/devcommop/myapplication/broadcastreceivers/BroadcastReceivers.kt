package com.devcommop.myapplication.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun AirplaneModeScreen() {
    val context = LocalContext.current
    var text by remember {
        mutableStateOf(value = "")
    }
    val broadcastReceiver = remember {
        object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val bundle = intent?.getBooleanExtra("state", false)?.let {
                    when (it) {
                        true -> text = "Airplane Mode is enabled"
                        else -> text = "Airplane Mode is disabled"
                    }
                }
            }

        }
    }
    DisposableEffect(key1 = true){
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).apply {
            context.registerReceiver(broadcastReceiver , this )
        }
        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }
    
    Text(
        text = text,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.headlineMedium
    )
}
