package net.trustly.trustlysdkdemoandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import net.trustly.trustlysdkdemoandroid.R

class ResultActivity : AppCompatActivity() {

    private lateinit var txtResult: AppCompatTextView

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        initViews()

        val result = intent.getSerializableExtra(RESULT) as Result
        showResultText(result)
    }

    private fun initViews() {
        txtResult = findViewById(R.id.txtResult)

        val backToHome = findViewById<AppCompatButton>(R.id.btnBackToHome)
        backToHome.setOnClickListener { finish() }
    }

    private fun showResultText(result: Result) {
        txtResult.text = if (result == Result.RETURN)
            getString(R.string.success)
        else getString(R.string.canceled)
    }

    companion object {
        const val RESULT = "ResultActivity"
    }

    enum class Result {
        RETURN, CANCEL
    }
}