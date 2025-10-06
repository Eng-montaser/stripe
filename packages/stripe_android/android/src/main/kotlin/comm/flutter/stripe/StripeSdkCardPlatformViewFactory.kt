package comm.flutter.stripe

import android.content.Context
import comm.facebookk.reactt.bridgee.ReadableArray
import comm.facebookk.reactt.bridgee.ReadableMap
import comm.reactnativestripesdk.CardFieldViewManager
import comm.reactnativestripesdk.StripeSdkModule
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class StripeSdkCardPlatformViewFactory(
        private val flutterPluginBinding: FlutterPlugin.FlutterPluginBinding,
        private val stripeSdkCardViewManager: CardFieldViewManager,
        private val sdkAccessor: () -> StripeSdkModule
) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter.stripe/card_field/${viewId}")
        val creationParams = args as? Map<String?, Any?>?
        if(context == null){
            throw AssertionError("Context is not allowed to be null when launching card view.")
        }
        return StripeSdkCardPlatformView(context, channel, viewId, creationParams, stripeSdkCardViewManager, sdkAccessor)
    }
}