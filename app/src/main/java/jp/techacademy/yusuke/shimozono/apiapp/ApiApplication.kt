package jp.techacademy.yusuke.shimozono.apiapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class ApiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        // ここはメンタリングのアドバイスを元に追記した。以下リンクからコピペ
        // https://www.section.io/engineering-education/using-realm-database-in-android/
        val configuration = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)
    }
}