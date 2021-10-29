package jp.techacademy.yusuke.shimozono.apiapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class ApiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        // 新しいバージョンのRealm(plugin:10.7.0以降？)を使う場合は、UiThreadでのRealm使用を許可する必要がある。コードは以下リンクからコピペ。
        // https://www.section.io/engineering-education/using-realm-database-in-android/
        val configuration = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)
    }
}