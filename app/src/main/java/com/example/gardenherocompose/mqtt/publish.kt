package com.example.gardenherocompose.mqtt

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.UnsupportedEncodingException

fun publish(client:MqttAndroidClient, topic:String, payload:String){
    var encodedPayload = ByteArray(0)
    try {
        encodedPayload = payload.toByteArray(charset("UTF-8"))
        val message = MqttMessage(encodedPayload)
        client.publish(topic, message)
    } catch(ex:Exception) {
        when(ex) {
            is UnsupportedEncodingException,
            is MqttException  -> {
                ex.printStackTrace();
            }
        }
    }
}