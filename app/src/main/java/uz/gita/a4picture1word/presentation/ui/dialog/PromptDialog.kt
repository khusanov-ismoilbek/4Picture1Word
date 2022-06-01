package uz.gita.a4picture1word.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.a4picture1word.R
import uz.gita.a4picture1word.databinding.DialogPromptBinding

class PromptDialog : DialogFragment(R.layout.dialog_prompt) {
    private val binding by viewBinding(DialogPromptBinding::bind)
    private var onClickYesListener: (() -> Unit)? = null

    /**
    background transparent
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.noBtn.setOnClickListener {
            dismiss()
        }
        binding.yesBtn.setOnClickListener {
            dismiss()
            onClickYesListener?.invoke()
        }

    }

    fun setOnclickYesListener(block: () -> Unit) {
        onClickYesListener = block
    }
}