package net.trustly.trustlysdkdemoandroid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.trustly.android.sdk.views.TrustlyView
import net.trustly.trustlysdkdemoandroid.EstablishData
import net.trustly.trustlysdkdemoandroid.R
import net.trustly.trustlysdkdemoandroid.data.RetrofitClient
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val mCompositeDisposable: CompositeDisposable
        get() = CompositeDisposable()

    private fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.addAll(disposable)
    }

    private fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    private val establishDataValues = EstablishData.getEstablishDataValues().toMutableMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openBottomSheet = findViewById<AppCompatButton>(R.id.btnOpenBottomSheet)
        openBottomSheet.setOnClickListener {
            BottomSheetWidgetFragment({
                updatePaymentProviderId(it)
            }).show(supportFragmentManager, BottomSheetWidgetFragment.TAG)
        }

        if (EstablishData.DYNAMIC_REQUEST_SIGNATURE) postRequestSignature()
        else initWidget()
    }

    override fun onDestroy() {
        unsubscribe()
        super.onDestroy()
    }

    private fun postRequestSignature() {
        val retrofitClient = RetrofitClient.getClient()
        addDisposable(retrofitClient.postRequestSignature(establishDataValues)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate {
                initWidget()
            }
            .subscribe({
                val requestSignature = it.string()
                Log.i(TAG, "Dynamic requestSignature: $requestSignature")
                establishDataValues["requestSignature"] = requestSignature
            }, {
                Log.e(TAG, it.message, it)
            })
        )
    }

    private fun initWidget() {
        val payWithMyBankWidget = findViewById<TrustlyView>(R.id.trustlyWidget)
        payWithMyBankWidget.selectBankWidget(establishDataValues).onBankSelected { _, data ->
            updatePaymentProviderId(data)
        }
    }

    private fun updatePaymentProviderId(data: Map<String, String>) {
        establishDataValues[PAYMENT_PROVIDER_ID] = data[PAYMENT_PROVIDER_ID].toString()
        openLightbox()
    }

    private fun openLightbox() {
        val intent = Intent(this@MainActivity, LightBoxActivity::class.java)
        intent.putExtra(LightBoxActivity.ESTABLISH_DATA, establishDataValues as Serializable)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val PAYMENT_PROVIDER_ID = "paymentProviderId"
    }

}