package jp.techacademy.yusuke.shimozono.apiapp

interface FragmentCallback {

    // Itemを押したときの処理：クーポン画面へShop型(id,name,urlなど一式)を引き渡すメソッド
    fun onClickItem(shop: Shop)

    // お気に入り追加時の処理
    fun onAddFavorite(shop: Shop)

    // お気に入り削除時の処理
    fun onDeleteFavorite(id: String)
}