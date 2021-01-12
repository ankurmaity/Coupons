package com.ankur.mobiuscoupons.request

import com.ankur.mobiuscoupons.model.Coupon
import com.ankur.mobiuscoupons.model.CouponResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 *
 * @author ankur
 *
 */
interface RetrofitApi {
    @GET("/v3/4c663239-03af-49b5-bcb3-0b0c41565bd2")
    fun getCoupon() : Call<ArrayList<Coupon>>
}