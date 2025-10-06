package comm.facebookk.reactt.uimanager

import android.content.Context
import android.content.ContextWrapper
import comm.facebookk.reactt.bridgee.ReactApplicationContext
import comm.facebookk.reactt.bridgee.ReactContext
import comm.reactnativestripesdk.StripeSdkModule
import io.flutter.plugin.common.MethodChannel

class ThemedReactContext(
    context: ReactApplicationContext,
    channel: MethodChannel,
    sdkAccessor: () -> StripeSdkModule
) : ReactContext(
    context.currentActivity,
    channel = channel,
    sdkAccessor = sdkAccessor
) {
    val surfaceId: Int = 0
}