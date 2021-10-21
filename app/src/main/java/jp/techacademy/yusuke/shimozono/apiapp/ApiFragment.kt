package jp.techacademy.yusuke.shimozono.apiapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_api.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class ApiFragment: Fragment() {
    private val apiAdapter by lazy { ApiAdapter(requireContext()) }
    private val handler = Handler(Looper.getMainLooper())

    private var fragmentCallback: FragmentCallback? = null //Fragment -> Activity　にFavoriteの変更を通知する

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCallback) {
            fragmentCallback = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fragment_api.xmlが反映されたViewを作成して、returnします
        return inflater.inflate(R.layout.fragment_api, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ここから初期化処理を行う
        //ApiAdapterのお気に入り追加、削除用のメソッドの追加を行う
        apiAdapter.apply {
            onClickAddFavorite = { //TODO バグ？
                //Adapterの処理をそのままActivityに通知する
                fragmentCallback?.onAddFavorite(it) //TODO バグ？
            }
            onClickDeleteFavorite = {
                //Adapterの処理をそのままActivityに通知する
                fragmentCallback?.onDeleteFavorite(it.id)
            }
        }
        //RecyclerViewの初期化
        recyclerView.apply {
            adapter = apiAdapter
            layoutManager = LinearLayoutManager(requireContext()) //一列ずつ表示
        }
        swipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
        updateData()
    }

    fun updateView() { //お気に入りが削除されたときの処理(Activityからコールされる)
        recyclerView.adapter?.notifyDataSetChanged() //RecyclerViewのAdapterに対して再描画のリクエストをする
    }

    private fun updateData() {
        val url = StringBuilder()
            .append(getString(R.string.base_url)) //https://webservice.recruit.co.jp/hotpepper/gourmet/v1/
            .append("?key=").append(getString(R.string.api_key)) //Apiを使うためのApiKey
            .append("&start=").append(1) //何件目からのデータを取得するか
            .append("&count=").append(COUNT) //1回で20件取得する
            .append("&keyword=").append(getString(R.string.api_keyword)) //お店のワード。ここでは例としてランチを検索
            .append("&format=json").toString() //ここで利用しているAPIは戻りの形をxmlかjsonか選択することができる。
                                                //Androidで扱う場合はxmlよりもjsonの方が扱いやすいのでjsonを選択
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) { //Error時の処理
                e.printStackTrace()
                handler.post {
                    updateRecyclerView(listOf())
                }
            }

            override fun onResponse(call: Call, response: Response) { //成功時の処理
                var list = listOf<Shop>()
                response.body?.string()?.also {
                    val apiResponse = Gson().fromJson(it, ApiResponse::class.java)
                    list = apiResponse.results.shop
                }
                handler.post {
                    updateRecyclerView(list)
                }
            }
        })
    }


    private fun updateRecyclerView(list: List<Shop>) {
        apiAdapter.refresh(list)
        swipeRefreshLayout.isRefreshing = false //SwipeRefreshLayoutのくるくるを消す
    }


    companion object {
        private const val COUNT = 20 //1回のAPIで取得する件数
    }
}