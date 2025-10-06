package comm.facebookk.reactt.uimanager

import comm.facebookk.reactt.uimanager.events.EventDispatcher

class UIManagerHelper {
    companion object {
        fun getEventDispatcherForReactTag(context: ThemedReactContext, id: Int): EventDispatcher? {
            return context.getNativeModule(UIManagerModule::class.java).eventDispatcher
        }
    }

}
