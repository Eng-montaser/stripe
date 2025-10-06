package comm.facebookk.reactt.uimanager

import comm.facebookk.reactt.bridgee.ReadableMap
import comm.facebookk.reactt.uimanager.events.Event
import comm.facebookk.reactt.uimanager.events.EventDispatcher
import comm.facebookk.reactt.uimanager.events.RCTEventEmitter
import io.flutter.plugin.common.MethodChannel

class UIManagerModule(channel: MethodChannel) {

    private val rctInstance: RCTEventEmitter = RCTEventEmitter(channel)

    val eventDispatcher: EventDispatcher = object : EventDispatcher {
        override fun dispatchEvent(event: Event<*>) {
            event.dispatch(rctInstance)
        }

        override fun invoke(name: String, value: ReadableMap) {
            rctInstance.receiveEvent(name, name, value)
        }

        override fun invoke(name: String) {
            rctInstance.receiveEvent(name, name, null)
        }
    }
}
