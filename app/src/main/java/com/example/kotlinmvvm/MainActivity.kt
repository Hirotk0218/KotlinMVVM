package com.example.kotlinmvvm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.kotlinmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val selectedButtonLiveData: LiveData<Boolean> by lazy { selectedButtonMutableLiveData }

    private lateinit var binding: ActivityMainBinding
    private val selectedButtonMutableLiveData = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.button.setOnClickListener {
            BottomSheetDialogFragment().showDialog(supportFragmentManager)
        }
        binding.cancelButton.setOnClickListener {
            selectedButtonMutableLiveData.value = false
        }
    }

    /**
     * 参加するボタンクリック時
     */
    fun onClickJoinButton() {
        selectedButtonMutableLiveData.value = true
    }

    /**
     * LiveDataを監視
     */
    private fun observeLiveData() {
        selectedButtonLiveData.observe(this,
            Observer {
                if (it) {
                    binding.inJoinText.visibility = View.VISIBLE
                    binding.cancelButton.visibility = View.VISIBLE
                } else {
                    binding.inJoinText.visibility = View.GONE
                    binding.cancelButton.visibility = View.GONE
                }
            })
    }
}
