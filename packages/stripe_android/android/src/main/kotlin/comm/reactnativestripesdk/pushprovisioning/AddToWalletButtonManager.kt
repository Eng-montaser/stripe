package comm.reactnativestripesdk.pushprovisioning

import android.content.Context
import com.bumptech.glide.Glide
import comm.facebookk.reactt.bridgee.Dynamic
import comm.facebookk.reactt.bridgee.ReadableMap
import comm.facebookk.reactt.module.annotations.ReactModule
import comm.facebookk.reactt.uimanager.SimpleViewManager
import comm.facebookk.reactt.uimanager.ThemedReactContext
import comm.facebookk.reactt.uimanager.annotations.ReactProp
import comm.facebookk.reactt.viewmanagers.AddToWalletButtonManagerDelegate
import comm.facebookk.reactt.viewmanagers.AddToWalletButtonManagerInterface
import comm.reactnativestripesdk.utils.asMapOrNull

@ReactModule(name = AddToWalletButtonManager.REACT_CLASS)
class AddToWalletButtonManager(
  applicationContext: Context,
) : SimpleViewManager<AddToWalletButtonView>(),
  AddToWalletButtonManagerInterface<AddToWalletButtonView> {
  private val delegate = AddToWalletButtonManagerDelegate(this)
  private val requestManager = Glide.with(applicationContext)

  override fun getName() = REACT_CLASS

  override fun getDelegate() = delegate

  override fun onDropViewInstance(view: AddToWalletButtonView) {
    view.onDropViewInstance()
    super.onDropViewInstance(view)
  }

  override fun onAfterUpdateTransaction(view: AddToWalletButtonView) {
    super.onAfterUpdateTransaction(view)
    view.onAfterUpdateTransaction()
  }

  override fun createViewInstance(reactContext: ThemedReactContext): AddToWalletButtonView =
    AddToWalletButtonView(reactContext, requestManager)

  override fun getExportedCustomDirectEventTypeConstants() =
    mutableMapOf(
      AddToWalletCompleteEvent.EVENT_NAME to
        mutableMapOf("registrationName" to "onCompleteAction"),
    )

  @ReactProp(name = "androidAssetSource")
  override fun setAndroidAssetSource(
    view: AddToWalletButtonView,
    source: ReadableMap?,
  ) {
    view.setSourceMap(source)
  }

  @ReactProp(name = "cardDetails")
  override fun setCardDetails(
    view: AddToWalletButtonView,
    cardDetails: Dynamic,
  ) {
    view.setCardDetails(cardDetails.asMapOrNull())
  }

  @ReactProp(name = "ephemeralKey")
  override fun setEphemeralKey(
    view: AddToWalletButtonView,
    ephemeralKey: Dynamic,
  ) {
    val map = ephemeralKey.asMap()
    if (map == null) return
    view.setEphemeralKey(map)
  }

  @ReactProp(name = "token")
  override fun setToken(
    view: AddToWalletButtonView,
    token: Dynamic,
  ) {
    val map = token.asMap()
    if (map == null) return
    view.setToken(map)
  }

  @ReactProp(name = "iOSButtonStyle")
  override fun setIOSButtonStyle(
    view: AddToWalletButtonView,
    value: String?,
  ) {
    // noop, iOS only.
  }

  @ReactProp(name = "testEnv")
  override fun setTestEnv(
    view: AddToWalletButtonView,
    value: Boolean,
  ) {
    // noop, iOS only.
  }

  companion object {
    const val REACT_CLASS = "AddToWalletButton"
  }
}
