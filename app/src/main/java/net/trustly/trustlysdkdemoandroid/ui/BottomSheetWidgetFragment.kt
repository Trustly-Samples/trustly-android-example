package net.trustly.trustlysdkdemoandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import net.trustly.android.sdk.views.TrustlyView
import net.trustly.trustlysdkdemoandroid.EstablishData
import net.trustly.trustlysdkdemoandroid.R

class BottomSheetWidgetFragment(val listener: (Map<String, String>) -> Unit) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)
        with(view) {
            val establishData = EstablishData.getEstablishDataValues()
            findViewById<TrustlyView>(R.id.trustly_view)
                .selectBankWidget(establishData)
                .onBankSelected { _, data ->
                    listener.invoke(data)
                    dismiss()
                }
        }
        return view
    }

    companion object {
        const val TAG = "BottomSheetWidgetFragment"
    }

}