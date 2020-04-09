package com.example.kotlinmvvm

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm.di.inject
import com.example.kotlinmvvm.model.EndlessScrollListener
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var endlessScrollListener: EndlessScrollListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inject()

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        endlessScrollListener = object : EndlessScrollListener(
            layoutManager = layoutManager,
            firstPage = 1
        ) {
            override fun onLoadMore(page: Int) {
                viewModel.loadMore(page)
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        recyclerView.adapter = MainActivityAdapter(this, viewModel)
        recyclerView.addOnScrollListener(endlessScrollListener)

        viewModel.load()
    }

    private fun toastMake(message: String, x: Int, y: Int) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, x, y)
        toast.show()
    }
}
