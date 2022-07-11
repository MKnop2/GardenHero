package com.example.gardenherocompose.mqtt

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException

fun disconnect(client:MqttAndroidClient) {
    try {
        var disconToken:IMqttToken = client.disconnect()
        disconToken.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                TODO("Not yet implemented")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                TODO("Not yet implemented")
            }
        }
    } catch (e:MqttException) {
        e.printStackTrace()
    }
}