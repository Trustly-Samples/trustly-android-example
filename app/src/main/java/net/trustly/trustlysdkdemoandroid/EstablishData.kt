package net.trustly.trustlysdkdemoandroid

object EstablishData {

    const val DYNAMIC_REQUEST_SIGNATURE = false

    fun getEstablishDataValues(): Map<String, String> {
        val establishDataValues: MutableMap<String, String> = HashMap()
        establishDataValues["accessId"] = "YOUR_ACCESS_ID"
//        establishDataValues["requestSignature"] = "YOUR_REQUEST_SIGNATURE"
        establishDataValues["merchantId"] = "YOUR_MERCHANT_ID"
        establishDataValues["description"] = "Android SDK Demo transaction"
        establishDataValues["currency"] = "USD"
        establishDataValues["merchantReference"] = "some_unique_transaction_identifier"
        establishDataValues["paymentType"] = "Retrieval"
        establishDataValues["customer.name"] = "John"
        establishDataValues["customer.address.country"] = "US"
        establishDataValues["metadata.urlScheme"] = "sdkdemo://"
        establishDataValues["env"] = "sandbox"
        return establishDataValues
    }

}