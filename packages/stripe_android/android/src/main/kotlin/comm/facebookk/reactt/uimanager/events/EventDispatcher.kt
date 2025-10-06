package comm.facebookk.reactt.uimanager.events

import comm.facebookk.reactt.bridgee.ReadableMap

interface EventDispatcher {
    fun dispatchEvent(event: Event<*>)
    fun invoke(name: String, value: ReadableMap)
    fun invoke(name: String)
}
