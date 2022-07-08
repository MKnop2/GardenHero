package com.example.gardenherocompose.mqtt

import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException

fun subscribe(client: MqttAndroidClient, topic:String) {
    val qos = 1

    try{
        val subToken : IMqttToken = client.unsubscribe(topic)
        subToken.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("Connection", "success")
                //connStatus = true
                //TODO: Place methods which should triggered if the callback is succesfully
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                //connectionStatus = false
                Log.i("Connection", "failure")
            }
        }

    } catch (ex : MqttException) {
        ex.printStackTrace()
    }
}