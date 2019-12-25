package com.example.kotlinmvvm

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm.di.inject
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inject()

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        recyclerView.adapter = MainActivityAdapter(this, viewModel) { item ->
            toastMake(item, 0, 0)
        }

        setupSearchView()

        viewModel.load()
    }

    private fun toastMake(message: String, x: Int, y: Int) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, x, y)
        toast.show()
    }

    private fun setupSearchView() {
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            var handle = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchText.observe(viewLifecycleOwner, Observer {
                    it?.let { name ->
                        if (name.isNotEmpty()){
                            viewModel.searchTargetList(name)
                        }
                    }
                })

                searchEditText.clearFocus()
                hideKeyboard()
                handle = true
            }

            return@setOnEditorActionListener handle
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { unwrap ->
                    if (unwrap.toString() != "") {
                        searchEditText.setCompoundDrawablesWithIntrinsicBounds(
                            resources.getDrawable(R.drawable.ic_search),
                            null,
                            resources.getDrawable(R.drawable.ic_close),
                            null
                        )
                    }
                    viewModel.searchText.value = unwrap.toString()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        searchEditText.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= searchEditText.right - searchEditText.compoundDrawables[0].bounds.width()) {
                    searchEditText.setCompoundDrawablesWithIntrinsicBounds(
                        resources.getDrawable(R.drawable.ic_search),
                        null,
                        null,
                        null)
                    searchEditText.clearFocus()
                    hideKeyboard()
                    searchEditText.setText("", TextView.BufferType.NORMAL)
                    viewModel.clearSearchText()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
