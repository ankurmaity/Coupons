package com.ankur.equipment.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ankur.mobiuscoupons.R
import com.ankur.mobiuscoupons.model.Coupon
import com.ankur.mobiuscoupons.model.Slab
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


/**
 *
 * @author ankur
 *
 */
class CouponAdapter(private var context: Context, private val couponList: ArrayList<Coupon>) :
    RecyclerView.Adapter<CouponAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_coupon, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return couponList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coupon = couponList[position]

        holder.codeTV!!.text = coupon.code.toString()
        holder.ribbonTV!!.text = coupon.ribbon_msg
        holder.validityTV!!.text =
            "Valid till " + SimpleDateFormat("d MMM, yyyy hh:mm a").format(coupon.valid_until)

        val htmlCode: String =
            "For every <font color=#FFEB3B>\u20B9" + coupon.wager_to_release_ratio_numerator + "</font> bet, <font color=#FFEB3B>\u20B9" + coupon.wager_to_release_ratio_denominator + "</font> will be released from the bonus amount"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.wagerMsgTV!!.text = Html.fromHtml(htmlCode, Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.wagerMsgTV!!.text = Html.fromHtml(htmlCode)
        }

        holder.bonusMsgTV!!.text =
            "Bonus expiry " + coupon.wager_bonus_expiry + " days from the issue"

        val minSlabList: MutableList<Double> = ArrayList()
        val maxWagerList: MutableList<Double> = ArrayList()
        val maxWagerPercentList: MutableList<Double> = ArrayList()
        val maxOtcList: MutableList<Double> = ArrayList()
        val maxOtcPercentList: MutableList<Double> = ArrayList()

        holder.detailBody!!.removeAllViews()

        for (slab in coupon.slabs!!) {
            minSlabList.add(slab.min)
            maxWagerList.add(slab.wagered_max)
            maxOtcList.add(slab.otc_max)
            maxWagerPercentList.add(slab.wagered_percent)
            maxOtcPercentList.add(slab.otc_percent)
            addSlabs(holder, slab)
        }

        val maxPercent: Int =
            (Collections.max(maxWagerPercentList) + Collections.max(maxOtcPercentList)).roundToInt()
        val maxValue: Int =
            ((Collections.max(maxWagerList) + Collections.max(maxOtcList)).roundToInt())

        holder.maxMsgTV!!.text =
            "Get " + maxPercent + "% upto \u20B9" + maxValue

        holder.minMsgTV!!.text = "Min. Deposit\n\u20B9" + Collections.min(minSlabList).roundToInt()


        if (coupon.isExpand) {
            holder.hideVisibleTV!!.text = "Hide Details"
            holder.arrowIV!!.setImageResource(R.drawable.ic_arrow_up)
            holder.detailLayout!!.visibility = View.VISIBLE
        } else {
            holder.hideVisibleTV!!.text = "Show Details"
            holder.arrowIV!!.setImageResource(R.drawable.ic_arrow_right)
            holder.detailLayout!!.visibility = View.GONE
        }
    }

    /**
     * Add slab views
     */
    private fun addSlabs(holder: ViewHolder, slab: Slab) {
        val v: View =
            LayoutInflater.from(holder.detailLayout!!.context)
                .inflate(R.layout.slab_row, holder.detailLayout, false)

        (v.findViewById(R.id.purchase_amount) as TextView).text =
            ">=" + slab.min.roundToInt() + " & <" + slab.max.roundToInt()

        (v.findViewById(R.id.bonus_percent) as TextView).text =
            slab.wagered_percent.roundToInt().toString() + "%"
        (v.findViewById(R.id.bonus_amount) as TextView).text =
            "(Max. \u20B9" + slab.wagered_max.roundToInt() + ")"

        (v.findViewById(R.id.instant_percent) as TextView).text =
            slab.otc_percent.roundToInt().toString() + "%"
        (v.findViewById(R.id.instant_cash) as TextView).text =
            "(Max. \u20B9" + slab.otc_max.roundToInt() + ")"

        holder.detailBody!!.addView(v)

    }

    /**
     * Update data for recycler view
     */
    fun addData(gossipNode: ArrayList<Coupon>) {
        couponList.clear()
        couponList.addAll(gossipNode)
        notifyDataSetChanged()
    }

    /**
     * View holder class
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var codeTV: TextView? = null
        var ribbonTV: TextView? = null
        var validityTV: TextView? = null
        var maxMsgTV: TextView? = null
        var minMsgTV: TextView? = null
        var wagerMsgTV: TextView? = null
        var bonusMsgTV: TextView? = null
        var arrowIV: ImageView? = null
        var bodyHide: RelativeLayout? = null
        var detailLayout: LinearLayout? = null
        var detailBody: LinearLayout? = null
        var hideVisibleTV: TextView? = null

        init {
            codeTV = view.findViewById(R.id.coupon_code)
            ribbonTV = view.findViewById(R.id.ribbon)
            validityTV = view.findViewById(R.id.validity_message)
            maxMsgTV = view.findViewById(R.id.max_message)
            minMsgTV = view.findViewById(R.id.min_msg)
            wagerMsgTV = view.findViewById(R.id.wager_msg)
            bonusMsgTV = view.findViewById(R.id.bonus_msg)
            detailLayout = view.findViewById(R.id.details_layout)
            detailBody = view.findViewById(R.id.detail_body)
            bodyHide = view.findViewById(R.id.body_hide)
            hideVisibleTV = view.findViewById(R.id.hide_visible_tv)
            arrowIV = view.findViewById(R.id.arrow)

            bodyHide?.setOnClickListener {
                couponList[adapterPosition].isExpand = !couponList[adapterPosition].isExpand
                notifyItemChanged(adapterPosition)
            }
        }
    }
}