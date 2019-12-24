package com.example.kotlinmvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm.databinding.ItemRecyclerViewBinding
import com.example.kotlinmvvm.model.MainItem

class MainActivityAdapter(
        val fragment: MainFragment,
        viewModel: MainViewModel,
        private val onItemClick: (item: String) -> Unit = {}
) : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    private val items: MutableList<MainItem> = mutableListOf()

    init {
        viewModel.items.observe(fragment.viewLifecycleOwner, Observer { items ->
            update(items)
        })
    }

    class ViewHolder(val binding: ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemRecyclerViewBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_recycler_view, parent, false)
        binding.vm = MainItemViewModel()
        binding.lifecycleOwner = fragment.viewLifecycleOwner
        val vh = ViewHolder(binding)
        binding.root.setOnClickListener {
            val item = items[vh.adapterPosition]
            onItemClick(item.number)
        }
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vm = holder.binding.vm ?: throw IllegalStateException("")
        vm.set(items[position])
        holder.binding.contentText.text = items[position].number
    }

    override fun getItemCount() = items.size

    private fun update(items: List<MainItem>) {
        this.items.clear()
        this.items.addAll(items)
    }
}