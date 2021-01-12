package com.ankur.mobiuscoupons.model

import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author ankur
 *
 */
class Coupon {
    var isExpand: Boolean = true
    var id: String? = null
    var valid_from: Date? = null
    var valid_until: Date? = null
    var is_active = false
    var is_deleted = false
    var tags: Object? = null
    var created_at: Date? = null
    var last_updated_at: Date? = null
    var code: String? = null
    var bonus_image_front: String? = null
    var bonus_image_back: String? = null
    var user_redeem_limit = 0
    var user_limit = 0
    var tab_type: String? = null
    var ribbon_msg: String? = null
    var is_bonus_booster_enabled = false
    var wager_bonus_expiry = 0
    var wager_to_release_ratio_numerator = 0
    var wager_to_release_ratio_denominator = 0
    var slabs: List<Slab>? = null
    var user_segmentation_type: String? = null
    var eligibility_user_levels: List<Int>? = null
    var eligibility_user_segments: List<String>? = null
    var visibility_user_levels: List<Int>? = null
    var visibility_user_segments: List<String>? = null
    var days_since_last_purchase_min = 0
    var _cls: String? = null
    var campaign: String? = null
    var bonus_booster: Any? = null
}


class Slab {
    var name: String? = null
    var num = 0
    var min = 0.0
    var max = 0.0
    var wagered_percent = 0.0
    var wagered_max = 0.0
    var otc_percent = 0.0
    var otc_max = 0.0
}

class CouponResponse {
    var response: ArrayList<Coupon>? = null
}