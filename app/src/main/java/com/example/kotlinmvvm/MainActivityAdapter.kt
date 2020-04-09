package com.example.kotlinmvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm.databinding.ItemRecyclerViewBinding

class MainActivityAdapter(
    private val fragment: MainFragment,
    viewModel: MainViewModel
) : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    private val items: MutableList<Int> = mutableListOf()
    private lateinit var recyclerView: RecyclerView

    init {
        viewModel.items.observe(fragment.viewLifecycleOwner, Observer { items ->
            update(items)
        })
    }

    class ViewHolder(val binding: ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemRecyclerViewBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_recycler_view, parent, false)
        binding.vm = MainItemViewModel()
        binding.lifecycleOwner = fragment.viewLifecycleOwner
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vm = holder.binding.vm ?: throw IllegalStateException("")
        vm.set(items[position])
        holder.binding.contentText.text = items[position].toString()
    }

    override fun getItemCount() = items.size

    private fun update(items: List<Int>) {
        val adapter = recyclerView.adapter as MainActivityAdapter
        val diff = DiffUtil.calculateDiff(DiffCallback(adapter.items, items))
        diff.dispatchUpdatesTo(adapter)
        this.items.clear()
        this.items.addAll(items)
    }

    class DiffCallback(private val oldList: List<Int>, private val newList: List<Int>) :
        DiffUtil.Callback() {

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }
    }
}