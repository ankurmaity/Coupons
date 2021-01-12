package com.ankur.mobiuscoupons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ankur.equipment.adapter.CouponAdapter
import com.ankur.mobiuscoupons.model.Coupon
import com.ankur.mobiuscoupons.vm.CouponVM

class MainActivity : AppCompatActivity() {
    private lateinit var couponVM: CouponVM
    private lateinit var recyclerView: RecyclerView
    private lateinit var couponAdapter: CouponAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.title = getString(R.string.coupons)
        recyclerView = findViewById(R.id.coupon_rv)

        initializeAdapter()
    }

    override fun onResume() {
        super.onResume()

        couponVM = ViewModelProvider(this).get(CouponVM::class.java)
        couponVM.getCouponsData().observe(this, updateListObserver)    }
    /**
     * Observer for equipment list
     */
    private val updateListObserver: Observer<ArrayList<Coupon>?> =
        Observer { equipmentList -> couponAdapter.addData(equipmentList!!) }

    /**
     * Initialize adapter with empty list
     */
    private fun initializeAdapter() {
        couponAdapter = CouponAdapter(this, arrayListOf())
        recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = couponAdapter
    }

}