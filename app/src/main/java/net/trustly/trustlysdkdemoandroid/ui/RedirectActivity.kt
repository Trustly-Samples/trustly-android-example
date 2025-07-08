package net.trustly.trustlysdkdemoandroid.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class RedirectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = if (intent.extras != null && intent.data!!.getQueryParameter("status") != null) {
            val transactionDetail = getTransactionDetailFromUri(intent.data!!)
            Intent(
                this,
                LightBoxActivity::class.java
            ).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
                .putExtra(LightBoxActivity.ESTABLISH_DATA, transactionDetail as Serializable)
        } else {
            Intent(this, LightBoxActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        }
        startActivity(intent)
        finish()
    }

    private fun getTransactionDetailFromUri(appLinkData: Uri): Map<String, String> {
        return mapOf(
            Pair("transactionId", appLinkData.getQueryParameter("transactionId")!!),
            Pair("transactionType", appLinkData.getQueryParameter("transactionType")!!),
            Pair("panel", appLinkData.getQueryParameter("panel")!!),
            Pair("payment.paymentType", appLinkData.getQueryParameter("payment.paymentType")!!),
            Pair("status", appLinkData.getQueryParameter("status")!!)
        )
    }

}