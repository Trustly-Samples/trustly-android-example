package net.trustly.trustlysdkdemoandroid.data

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

fun interface DemoApi {

    @POST("/signature")
    fun postRequestSignature(@Body establishData: Map<String, String>): Observable<ResponseBody>

}