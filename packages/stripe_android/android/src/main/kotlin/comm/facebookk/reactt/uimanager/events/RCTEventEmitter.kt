package com.facebook.react.uimanager.events

import comm.facebookk.reactt.bridgee.ReadableMap
import comm.facebookk.reactt.bridgee.WritableMap
import io.flutter.plugin.common.MethodChannel

class RCTEventEmitter(private val channel: MethodChannel) {

    fun receiveEvent(viewTag: Any, eventName: String, serializeEventData: ReadableMap?) {
        channel.invokeMethod(eventName, serializeEventData)
    }
}
