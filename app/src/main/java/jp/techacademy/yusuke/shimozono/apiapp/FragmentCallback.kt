package jp.techacademy.yusuke.shimozono.apiapp

interface FragmentCallback {
    // お気に入り追加時の処理
    fun onAddFavorite(shop: Shop)
    // お気に入り削除時の処理
    fun onDeleteFavorite(id: String)
}