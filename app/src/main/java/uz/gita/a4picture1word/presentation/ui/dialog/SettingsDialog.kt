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
import uz.gita.a4picture1word.data.local.MyPreference
import uz.gita.a4picture1word.databinding.DialogSettingsBinding

class SettingsDialog : DialogFragment(R.layout.dialog_settings) {
    private val binding by viewBinding(DialogSettingsBinding::bind)
    private var onCLickFeedbackListener: (() -> Unit)? = null
    private var onClickResetTheLevelListener: (() -> Unit)? = null
    private var onClickSoundListener: ((Int) -> Unit)? = null
    private var onClickMusicListener: ((Int) -> Unit)? = null
    private var onClickShareListener: (() -> Unit)? = null
    private var onClickMoreGameListener: (() -> Unit)? = null
    private lateinit var preference: MyPreference

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
        preference = MyPreference(requireContext())

        binding.musicSwitch.isChecked = (preference.music == 1)
        binding.soundSwitch.isChecked = (preference.sound == 1)

        binding.feedback.setOnClickListener {
            onCLickFeedbackListener?.invoke()
        }
        binding.resetTheLevel.setOnClickListener {
            onClickResetTheLevelListener?.invoke()
        }

        binding.share.setOnClickListener {
            onClickShareListener?.invoke()
        }
        binding.moreGames.setOnClickListener {
            onClickMoreGameListener?.invoke()
        }

        binding.musicSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onClickMusicListener?.invoke(1)
                preference.music = 1
            } else {
                onClickMusicListener?.invoke(0)
                preference.music = 0
            }
        }
        binding.soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onClickSoundListener?.invoke(1)
                preference.sound = 1
            } else {
                onClickSoundListener?.invoke(0)
                preference.sound = 0
            }
        }

//        binding.soundSwitch.setOnClickListener {
//            if (binding.soundSwitch.isChecked) {
//                onClickSoundListener?.invoke(1)
//            } else {
//                onClickSoundListener?.invoke(0)
//            }
//        }

    }

    fun setOnCLickFeedbackListener(block: () -> Unit) {
        onCLickFeedbackListener = block
    }

    fun setOnClickResetTheLevelListener(block: () -> Unit) {
        onClickResetTheLevelListener = block
    }

    fun setOnClickSoundListener(block: (Int) -> Unit) {
        onClickSoundListener = block
    }

    fun setOnClickMusicListener(block: (Int) -> Unit) {
        onClickMusicListener = block
    }

    fun setOnClickShareListener(block: () -> Unit) {
        onClickShareListener = block
    }

    fun setOnClickMoreGameListener(block: () -> Unit) {
        onClickMoreGameListener = block
    }

}