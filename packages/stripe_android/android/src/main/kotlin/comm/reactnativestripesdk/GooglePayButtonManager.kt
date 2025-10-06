package comm.reactnativestripesdk

import comm.facebookk.reactt.module.annotations.ReactModule
import comm.facebookk.reactt.uimanager.SimpleViewManager
import comm.facebookk.reactt.uimanager.ThemedReactContext
import comm.facebookk.reactt.uimanager.annotations.ReactProp
import comm.facebookk.reactt.viewmanagers.GooglePayButtonManagerDelegate
import comm.facebookk.reactt.viewmanagers.GooglePayButtonManagerInterface

@ReactModule(name = GooglePayButtonManager.REACT_CLASS)
class GooglePayButtonManager :
  SimpleViewManager<GooglePayButtonView>(),
  GooglePayButtonManagerInterface<GooglePayButtonView> {
  private val delegate = GooglePayButtonManagerDelegate(this)

  override fun getName() = REACT_CLASS

  override fun getDelegate() = delegate

  override fun createViewInstance(reactContext: ThemedReactContext): GooglePayButtonView = GooglePayButtonView(reactContext)

  override fun onAfterUpdateTransaction(view: GooglePayButtonView) {
    super.onAfterUpdateTransaction(view)

    view.initialize()
  }

  @ReactProp(name = "type")
  override fun setType(
    view: GooglePayButtonView,
    buttonType: Int,
  ) {
    view.setType(buttonType)
  }

  @ReactProp(name = "appearance")
  override fun setAppearance(
    view: GooglePayButtonView,
    appearance: Int,
  ) {
    view.setAppearance(appearance)
  }

  @ReactProp(name = "borderRadius")
  override fun setBorderRadius(
    view: GooglePayButtonView,
    borderRadius: Int,
  ) {
    view.setBorderRadius(borderRadius)
  }

  companion object {
    const val REACT_CLASS = "GooglePayButton"
  }
}
