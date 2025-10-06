package comm.reactnativestripesdk

import androidx.fragment.app.FragmentActivity
import comm.facebookk.reactt.bridgee.ReactApplicationContext
import comm.facebookk.reactt.bridgee.ReadableMap
import comm.facebookk.reactt.bridgee.WritableMap
import comm.reactnativestripesdk.utils.ErrorType
import comm.reactnativestripesdk.utils.GooglePayErrorType
import comm.reactnativestripesdk.utils.StripeFragment
import comm.reactnativestripesdk.utils.createError
import comm.reactnativestripesdk.utils.createMissingActivityError
import comm.reactnativestripesdk.utils.getBooleanOr
import comm.reactnativestripesdk.utils.getIntOrNull
import com.stripe.android.googlepaylauncher.GooglePayEnvironment
import com.stripe.android.googlepaylauncher.GooglePayLauncher

class GooglePayLauncherFragment : StripeFragment() {
  enum class Mode {
    ForSetup,
    ForPayment,
  }

  private lateinit var launcher: GooglePayLauncher
  private lateinit var clientSecret: String
  private lateinit var mode: Mode
  private lateinit var configuration: GooglePayLauncher.Config
  private lateinit var currencyCode: String
  private var amount: Int? = null
  private var label: String? = null
  private lateinit var callback: (result: GooglePayLauncher.Result?, error: WritableMap?) -> Unit

  override fun prepare() {
    launcher =
      GooglePayLauncher(
        fragment = this,
        config = configuration,
        readyCallback = ::onGooglePayReady,
        resultCallback = ::onGooglePayResult,
      )
  }

  fun presentGooglePaySheet(
    clientSecret: String,
    mode: Mode,
    googlePayParams: ReadableMap,
    context: ReactApplicationContext,
    callback: (GooglePayLauncher.Result?, error: WritableMap?) -> Unit,
  ) {
    this.clientSecret = clientSecret
    this.mode = mode
    this.callback = callback
    this.currencyCode = googlePayParams.getString("currencyCode") ?: "USD"
    this.amount = getIntOrNull(googlePayParams, "amount")
    this.label = googlePayParams.getString("label")
    this.configuration =
      GooglePayLauncher.Config(
        environment =
          if (googlePayParams.getBoolean("testEnv")) {
            GooglePayEnvironment.Test
          } else {
            GooglePayEnvironment.Production
          },
        merchantCountryCode = googlePayParams.getString("merchantCountryCode").orEmpty(),
        merchantName = googlePayParams.getString("merchantName").orEmpty(),
        isEmailRequired = googlePayParams.getBooleanOr("isEmailRequired", false),
        billingAddressConfig =
          buildBillingAddressParameters(googlePayParams.getMap("billingAddressConfig")),
        existingPaymentMethodRequired =
          googlePayParams.getBooleanOr("existingPaymentMethodRequired", false),
        allowCreditCards = googlePayParams.getBooleanOr("allowCreditCards", true),
      )

    (context.currentActivity as? FragmentActivity)?.let {
      attemptToCleanupPreviousFragment(it)
      commitFragmentAndStartFlow(it)
    }
      ?: run {
        callback(null, createMissingActivityError())
        return
      }
  }

  private fun attemptToCleanupPreviousFragment(currentActivity: FragmentActivity) {
    currentActivity.supportFragmentManager
      .beginTransaction()
      .remove(this)
      .commitAllowingStateLoss()
  }

  private fun commitFragmentAndStartFlow(currentActivity: FragmentActivity) {
    try {
      currentActivity.supportFragmentManager
        .beginTransaction()
        .add(this, TAG)
        .commit()
    } catch (error: IllegalStateException) {
      callback(null, createError(ErrorType.Failed.toString(), error.message))
    }
  }

  private fun onGooglePayReady(isReady: Boolean) {
    if (isReady) {
      when (mode) {
        Mode.ForSetup -> {
          launcher.presentForSetupIntent(clientSecret, currencyCode, amount?.toLong(), label)
        }
        Mode.ForPayment -> {
          launcher.presentForPaymentIntent(clientSecret, label)
        }
      }
    } else {
      callback(
        null,
        createError(
          GooglePayErrorType.Failed.toString(),
          "Google Pay is not available on this device. You can use isPlatformPaySupported to preemptively check for Google Pay support.",
        ),
      )
    }
  }

  private fun onGooglePayResult(result: GooglePayLauncher.Result) {
    callback(result, null)
  }

  companion object {
    const val TAG = "google_pay_launcher_fragment"

    private fun buildBillingAddressParameters(params: ReadableMap?): GooglePayLauncher.BillingAddressConfig {
      val isRequired = params?.getBooleanOr("isRequired", false)
      val isPhoneNumberRequired = params?.getBooleanOr("isPhoneNumberRequired", false)
      val format =
        when (params?.getString("format").orEmpty()) {
          "FULL" -> GooglePayLauncher.BillingAddressConfig.Format.Full
          "MIN" -> GooglePayLauncher.BillingAddressConfig.Format.Min
          else -> GooglePayLauncher.BillingAddressConfig.Format.Min
        }

      return GooglePayLauncher.BillingAddressConfig(
        isRequired = isRequired ?: false,
        format = format,
        isPhoneNumberRequired = isPhoneNumberRequired ?: false,
      )
    }
  }
}
