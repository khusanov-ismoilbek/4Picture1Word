package uz.gita.a4picture1word.presentation.ui.screen

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.a4picture1word.BuildConfig
import uz.gita.a4picture1word.R
import uz.gita.a4picture1word.data.local.MyPreference
import uz.gita.a4picture1word.databinding.ScreenMainBinding
import uz.gita.a4picture1word.presentation.ui.dialog.PromptDialog
import uz.gita.a4picture1word.presentation.ui.dialog.SettingsDialog
import uz.gita.a4picture1word.presentation.viewmodel.MainViewModel
import uz.gita.a4picture1word.presentation.viewmodel.impl.MainViewModelImpl
import javax.inject.Inject

@AndroidEntryPoint
class MainScreen @Inject constructor() : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private lateinit var preference: MyPreference
    private lateinit var playerMain: MediaPlayer
    private lateinit var playerItem: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCLickLottieLiveData.observe(this@MainScreen, onClickLottieObserver)
        viewModel.onClickRateLiveData.observe(this@MainScreen, onClickRateObserver)
        viewModel.onClickSettingsLiveData.observe(this@MainScreen, onClickSettingsObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preference = MyPreference(requireContext())
        playerMain = MediaPlayer.create(requireContext(), R.raw.main)
        playerItem = MediaPlayer.create(requireContext(), R.raw.item_click)

        if (preference.music == 1) {
            playerMain.start()
            playerMain.isLooping = true
        }

        binding.playGame.setOnClickListener {
            viewModel.onClickLottie()
            playerMain.stop()
            if (preference.sound == 1)
                playerItem.start()
        }
        binding.icRate.setOnClickListener {
            if (preference.sound == 1)
                playerItem.start()
            viewModel.onClickRate()
        }
        binding.settings.setOnClickListener {
            if (preference.sound == 1)
                playerItem.start()
            viewModel.onCLickSettings()
        }
        binding.coins.text = preference.coins.toString()
    }

    private val onClickLottieObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_mainScreen_to_gameScreen)
    }
    private val onClickRateObserver = Observer<Unit> {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${requireActivity().packageName}")
            )
        )
    }
    private val onClickSettingsObserver = Observer<Unit> {
        val dialogSettings = SettingsDialog()
        val dialogPrompt = PromptDialog()

        dialogSettings.setOnCLickFeedbackListener {
            if (preference.sound == 1)
                playerItem.start()

            val emailIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "khusanov.ismoilbek@gmail.com"))
            startActivity(emailIntent)
        }
        dialogSettings.setOnClickResetTheLevelListener {
            if (preference.sound == 1)
                playerItem.start()

            dialogPrompt.setOnclickYesListener {
                preference.level = 0
                preference.coins = 0
                binding.coins.text = "0"
            }
            dialogPrompt.show(childFragmentManager, "dialogPrompt")

        }
        dialogSettings.setOnClickSoundListener {
            if (preference.sound == 1)
                playerItem.start()
        }
        dialogSettings.setOnClickMusicListener {
            if (preference.sound == 1)
                playerItem.start()

            preference.music = it
            if (it == 1) {
                playerMain = MediaPlayer.create(requireContext(), R.raw.main)
                playerMain.start()
            } else {
                playerMain.stop()
            }
        }
        dialogSettings.setOnClickShareListener {
            if (preference.sound == 1)
                playerItem.start()

            shareApp()
        }
        dialogSettings.setOnClickMoreGameListener {
            if (preference.sound == 1)
                playerItem.start()

            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=GITA+Dasturchilar+Akademiyasi&hl=ru")
                )
            )
        }
        dialogSettings.show(childFragmentManager, "dialogSettings")
    }

    private fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val body =
            "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
        intent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(intent, "share using"))
    }

    override fun onResume() {
        super.onResume()
        if (preference.music == 1)
            playerMain.start()
    }

    override fun onStop() {
        super.onStop()
        playerMain.stop()
    }
}