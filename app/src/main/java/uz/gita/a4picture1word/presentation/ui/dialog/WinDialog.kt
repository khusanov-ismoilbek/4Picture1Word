package uz.gita.a4picture1word.presentation.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.a4picture1word.R
import uz.gita.a4picture1word.databinding.DialogWinBinding

class WinDialog : DialogFragment(R.layout.dialog_win) {
    private val binding by viewBinding(DialogWinBinding::bind)
    private var onClickContinueListener: (() -> Unit)? = null

    private val rotate = RotateAnimation(
        0F, 360F,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )

    /**
    full screen dialog
     * */
    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    /**
    background transparent
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        rotate.duration = 1500
        rotate.interpolator = LinearInterpolator()
        rotate.repeatCount = Animation.INFINITE
        rotate.fillAfter = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.answer.text = arguments?.getString("ANSWER")
        binding.coinText.text = arguments?.getString("COIN")

        binding.winLight.startAnimation(rotate)

        binding.continueGame.setOnClickListener {
            onClickContinueListener?.invoke()
            binding.winLight.clearAnimation()
            dismiss()
        }
    }

    fun setOnClickContinueListener(block: () -> Unit) {
        onClickContinueListener = block
    }

}