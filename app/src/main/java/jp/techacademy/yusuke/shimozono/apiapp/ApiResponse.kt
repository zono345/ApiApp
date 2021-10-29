package jp.techacademy.yusuke.shimozono.apiapp
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ApiResponse(
    @SerializedName("results")
    val results: Results
)

data class Results(
    @SerializedName("shop")
    var shop: List<Shop>
)

data class Shop(
    @SerializedName("coupon_urls")
    val couponUrls: CouponUrls,
    @SerializedName("id")
    val id: String,
    @SerializedName("logo_image")
    val logoImage: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address") // 住所の表示
    val address: String,
):Serializable // putExtraでオブジェクト型(Shop型)で受け渡す際にSerializable型にしておく必要がある

data class CouponUrls(
    @SerializedName("pc")
    val pc: String,
    @SerializedName("sp")
    val sp: String
):Serializable // putExtraでオブジェクト型(Shop型)で受け渡す際にSerializable型にしておく必要がある