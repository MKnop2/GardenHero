package com.example.gardenherocompose.helper

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

//Tutorial MQTT
//https://www.hivemq.com/blog/mqtt-client-library-enyclopedia-paho-android-service/

fun MqttClientHelper(client:MqttAndroidClient ,context: Context) {
    try {
        val token = client.connect()
        token.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                // We are connected
                Log.d(TAG, "onSuccess")
            }

            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                // Something went wrong e.g. connection timeout or firewall problems
                Log.d(TAG, "onFailure")
            }
        }
    } catch (e: MqttException) {
        e.printStackTrace()
    }

    //Connect with MQTT 3.1
    val options = MqttConnectOptions()
    options.mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1

    //Connect with Last Will and Testament (LWT)
    val topic : String = "/greenLab/mobile/last/will"
    val payload = "some payload".toByteArray()
    options.setWill(topic, payload, 1, false)

    //Connect to broker (without credentials)
    val token: IMqttToken = client.connect(options)

    val subToken : IMqttToken = client.subscribe("/yo/", 1)
}