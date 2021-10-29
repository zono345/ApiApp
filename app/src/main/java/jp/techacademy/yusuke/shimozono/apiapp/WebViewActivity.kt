package jp.techacademy.yusuke.shimozono.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.activity_web_view.favoriteImageView

class WebViewActivity : AppCompatActivity() {
    var favState:Boolean = false // 表示しているクーポン画面の店のお気に入り登録状態を表す。true→登録済み、false→未登録

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // getSerializableExtraでオブジェクト型(Shop型 = Serializable)でデータを受け取る
        val shop = intent.getSerializableExtra(KEY_SHOP) as Shop

        // getSerializableExtraで受け取ったShop型データの中のurl情報を変数urlへ代入
        val url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc

        webView.loadUrl(url)
        imageViewRefresh()

        // ★マークをクリックした時、お気に入りに追加or削除をする。
        favoriteImageView.setOnClickListener {
            if (favState) {
                showConfirmDeleteFavoriteDialog(shop.id)
            } else {
                onAddFavorite(shop)
                imageViewRefresh()
            }
        }
    }

    // お気に入りに追加する
    private fun onAddFavorite(shop: Shop) {
        FavoriteShop.insert(FavoriteShop().apply {
            id = shop.id
            name = shop.name
            address = shop.address // 課題用追記。住所の表示
            imageUrl = shop.logoImage
            url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
        })
    }

    // 「削除していい？」のダイアログ表示
    private fun showConfirmDeleteFavoriteDialog(id: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_favorite_dialog_title)
            .setMessage(R.string.delete_favorite_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                deleteFavorite(id) // 「OK」ならお気に入りから削除
                imageViewRefresh() // お気に入り状態を確認して、★マーク画像に反映する
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .create()
            .show()
    }

    // お気に入りから削除する
    private fun deleteFavorite(id: String) {
        FavoriteShop.delete(id)
    }

    // クリックした店の情報をShop型オブジェクトで受け渡す
    companion object {
        private const val KEY_SHOP = "key_shop"
        fun start(activity: Activity, shop: Shop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_SHOP, shop))
        }
    }

    // お気に入り状態を判定して、★マークの画像を置換する
    private fun imageViewRefresh() {
        val shop = intent.getSerializableExtra(KEY_SHOP) as Shop
        favState = FavoriteShop.findBy(shop.id) != null
        if (favState) {
            favoriteImageView.setImageResource(R.drawable.ic_star)
        } else {
            favoriteImageView.setImageResource(R.drawable.ic_star_border)
        }
    }
}