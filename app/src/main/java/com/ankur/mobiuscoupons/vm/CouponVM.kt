package com.ankur.mobiuscoupons.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankur.mobiuscoupons.model.Coupon
import com.ankur.mobiuscoupons.request.RetrofitApi
import com.ankur.mobiuscoupons.request.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

/**
 *
 * @author ankur
 *
 */
class CouponVM : ViewModel() {
    private val couponsData: MutableLiveData<ArrayList<Coupon>> = MutableLiveData()
    private var serviceAPi: RetrofitApi? = null

    init {
        serviceAPi = RetrofitClient().getRetrofitApi()
        requestCoupons()
    }

    /**
     * Call coup request
     */
    private fun requestCoupons() {
        val callService = serviceAPi?.getCoupon()

        callService?.enqueue(object : Callback<ArrayList<Coupon>> {
            override fun onFailure(call: Call<ArrayList<Coupon>>, t: Throwable) {
            }

            override fun onResponse(call: Call<ArrayList<Coupon>>, response: Response<ArrayList<Coupon>>) {

                val getCode = response.code()

                if (getCode == 200) {
                    val couponList = response.body()
                    couponsData.value=couponList
                }
            }
        })
    }

    fun getCouponsData(): LiveData<ArrayList<Coupon>> {
        return couponsData
    }
}
