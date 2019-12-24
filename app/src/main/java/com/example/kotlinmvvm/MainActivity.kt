package com.example.kotlinmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.recyclerView

class MainActivity : AppCompatActivity() {

    //ポケモンデータリスト
    private val items = listOf(
            "フシギダネ",
            "フシギソウ",
            "フシギバナ",
            "ヒトカゲ",
            "リザード",
            "リザードン",
            "ゼニガメ",
            "カメール",
            "カメックス",
            "キャタピー",
            "トランセル",
            "バタフリー",
            "ビートル",
            "コクーン",
            "スピアー",
            "ポッポ",
            "ピジョン",
            "ピジョット",
            "コラッタ",
            "ラッタ"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        recyclerView.adapter = MainActivityAdapter(items)
    }
}
