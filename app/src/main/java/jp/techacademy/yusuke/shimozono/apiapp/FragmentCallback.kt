package jp.techacademy.yusuke.shimozono.apiapp

interface FragmentCallback {

// 元のコード
//    // Itemを押したときの処理
//    fun onClickItem(url: String)

    // TODO Itemを押したときの処理：クーポン画面へshop情報(id,name,urlなど一式)を引き渡すメソッド
    fun onClickItem(shop: Shop) //TODO Shop→FavoriteShop　？？

    // お気に入り追加時の処理
    fun onAddFavorite(shop: Shop)
    // お気に入り削除時の処理
    fun onDeleteFavorite(id: String)
}