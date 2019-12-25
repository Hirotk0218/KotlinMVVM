package com.example.kotlinmvvm.repository

import com.example.kotlinmvvm.model.MainItem

class NumberRepository {

    fun items() = listOf(
        MainItem("インターネット・コンピュータ"),
        MainItem("エンターテインメント"),
        MainItem("生活・文化"),
        MainItem("社会・経済"),
        MainItem("健康と医療"),
        MainItem("ペット"),
        MainItem("グルメ"),
        MainItem("住まい"),
        MainItem("花・ガーデニング"),
        MainItem("育児"),
        MainItem("旅行・観光"),
        MainItem("写真"),
        MainItem("手芸・ハンドクラフト"),
        MainItem("スポーツ"),
        MainItem("アウトドア"),
        MainItem("美容・ビューティー"),
        MainItem("ファッション"),
        MainItem("恋愛・結婚"),
        MainItem("趣味・ホビー"),
        MainItem("ゲーム")
    )
}