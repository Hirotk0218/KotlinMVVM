package com.example.kotlinmvvm

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.kotlinmvvm.databinding.DialogFragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * BottomSheetDialogFragment
 */
class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFragmentBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_fragment_bottom_sheet,
                container,
                false
            )
        binding.lifecycleOwner = this

        binding.button.setOnClickListener {
            (activity as MainActivity).onClickJoinButton()
            dismiss()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.isCancelable = false
        setDialogMatchParent()
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog =
            BottomSheetDialog(
                requireContext(),
                theme
            )
        bottomSheetDialog.setOnShowListener { dialog ->
            val bottomSheet =
                (dialog as BottomSheetDialog).findViewById<FrameLayout>(R.id.design_bottom_sheet)

            bottomSheet?.let {
                BottomSheetBehavior.from(bottomSheet).apply {
                    state = BottomSheetBehavior.STATE_COLLAPSED
                    skipCollapsed = true
                    isHideable = true
                }
            }
        }
        return bottomSheetDialog
    }

    /**
     * ダイアログを表示
     */
    fun showDialog(fragmentManager: FragmentManager) {
        if (!isShowingDialog(
                fragmentManager,
                BottomSheetDialogFragment::class.java.simpleName
            )
        ) {
            show(fragmentManager, BottomSheetDialogFragment::class.java.simpleName)
        }
    }

    /**
     * ダイアログの表示状態をチェック
     */
    private fun isShowingDialog(manager: FragmentManager?, tag: String?): Boolean {
        val dialogFragment = manager?.findFragmentByTag(tag ?: "")
        dialogFragment?.let {
            if (it is DialogFragment) {
                it.dialog?.let {
                    return true
                }
            }
        }
        return false
    }

    /**
     * ダイアログのMatchParentの設定
     */
    private fun setDialogMatchParent() {
        dialog?.window?.let {
            val params = it.attributes
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            it.attributes = params
            it.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    /**
     * MainActivityのLiveDataを監視
     */
    private fun observeLiveData() {
        (activity as MainActivity).selectedButtonLiveData.observe(this,
            Observer {
                if (it) {
                    binding.button.visibility = View.GONE
                } else {
                    binding.button.visibility = View.VISIBLE
                }
            })
    }
}
