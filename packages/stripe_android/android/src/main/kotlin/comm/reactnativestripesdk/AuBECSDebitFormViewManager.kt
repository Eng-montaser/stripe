package comm.reactnativestripesdk

import comm.facebookk.reactt.bridgee.Dynamic
import comm.facebookk.reactt.module.annotations.ReactModule
import comm.facebookk.reactt.uimanager.SimpleViewManager
import comm.facebookk.reactt.uimanager.ThemedReactContext
import comm.facebookk.reactt.uimanager.annotations.ReactProp
import comm.facebookk.reactt.viewmanagers.AuBECSDebitFormManagerDelegate
import comm.facebookk.reactt.viewmanagers.AuBECSDebitFormManagerInterface
import comm.reactnativestripesdk.utils.asMapOrNull

@ReactModule(name = AuBECSDebitFormViewManager.REACT_CLASS)
class AuBECSDebitFormViewManager :
  SimpleViewManager<AuBECSDebitFormView>(),
  AuBECSDebitFormManagerInterface<AuBECSDebitFormView> {
  private val delegate = AuBECSDebitFormManagerDelegate(this)

  override fun getName() = REACT_CLASS

  override fun getDelegate() = delegate

  override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> =
    mutableMapOf(
      FormCompleteEvent.EVENT_NAME to
        mutableMapOf("registrationName" to "onCompleteAction"),
    )

  @ReactProp(name = "companyName")
  override fun setCompanyName(
    view: AuBECSDebitFormView,
    name: String?,
  ) {
    view.setCompanyName(name)
  }

  @ReactProp(name = "formStyle")
  override fun setFormStyle(
    view: AuBECSDebitFormView,
    style: Dynamic,
  ) {
    view.setFormStyle(style.asMapOrNull())
  }

  override fun createViewInstance(reactContext: ThemedReactContext): AuBECSDebitFormView = AuBECSDebitFormView(reactContext)

  companion object {
    const val REACT_CLASS = "AuBECSDebitForm"
  }
}
