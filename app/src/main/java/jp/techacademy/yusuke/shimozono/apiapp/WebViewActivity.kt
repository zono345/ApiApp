package jp.techacademy.yusuke.shimozono.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var shop = intent.getSerializableExtra(KEY_SHOP) as? Shop

        if (shop == null) {
            Log.d("test99", "shopはnullだよ。intentで受け取れてないよ")
        } else {
            var url =
                if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
            webView.loadUrl(url)

//        btnTextRefresh()

        }



//
//
//        // 課題用：クリックした店のクーポン画面のURLを格納
//        var searchUrl:String = intent.getStringExtra("key_url").toString()
//
//        // TODO 後で消す。検証用
//        button.text = searchUrl
//
//        //クリックした店のお気に入り登録状況に応じて、初期状態のボタンのテキストを変更する
//        if (FavoriteShop.findByURL(searchUrl) == null) { // TODO 要修正
//            button.text = "お気に入りへ追加する"
//        } else {
//            button.text = "お気に入りから削除する"
//        }




    }


//    private fun btnTextRefresh() {
//        var shop = intent.getSerializableExtra(KEY_SHOP) as Shop
//        var favState:Boolean = false // 表示しているクーポン画面の店のお気に入り登録状態を表す。true→登録済み、false→未登録
//        favState = FavoriteShop.findBy(shop.id) != null
//        if (favState) button.text = "お気に入りから削除する" else button.text = "お気に入りへ追加する"
//    }


    companion object {
        private const val KEY_SHOP = "key_shop"
        fun start(activity: Activity, shop: Shop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_SHOP, "shop"))
        }
    }




    // TODO companion objectのテキスト記載の当初案
//    companion object {
//        private const val KEY_URL = "key_url"
//        fun start(activity: Activity, url: String) {
//            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, url))
//        }
//    }


    // TODO 変更案↓ urlで受け取る時のコード。id検索できないから没。
/*
    companion object {
        private const val KEY_URL = "key_url"
        private const val KEY_ID = "key_id"

        fun start(activity: Activity, url: String, id: String) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, url))
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_ID, id))
        }
    }
*/





}