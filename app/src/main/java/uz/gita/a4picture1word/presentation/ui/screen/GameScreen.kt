package uz.gita.a4picture1word.presentation.ui.screen

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.a4picture1word.R
import uz.gita.a4picture1word.data.local.MyPreference
import uz.gita.a4picture1word.databinding.ScreenGameBinding
import uz.gita.a4picture1word.presentation.ui.dialog.WinDialog
import uz.gita.a4picture1word.presentation.viewmodel.GameViewModel
import uz.gita.a4picture1word.presentation.viewmodel.impl.GameViewModelImpl
import javax.inject.Inject

@AndroidEntryPoint
class GameScreen @Inject constructor() : Fragment(R.layout.screen_game) {
    private val binding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private val answers = ArrayList<TextView>()
    private val variants = ArrayList<TextView>()
    private lateinit var preference: MyPreference
    private var answer: String = ""
    private lateinit var variant: List<Char>
    private var tagAnswer = StringBuilder()
    private var tagVariant = StringBuilder()
    private lateinit var playerItem: MediaPlayer
    private lateinit var playerWin: MediaPlayer
    private lateinit var playerNot: MediaPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playerItem = MediaPlayer.create(requireContext(), R.raw.item_click)
        playerWin = MediaPlayer.create(requireContext(), R.raw.winner_game)
        playerNot = MediaPlayer.create(requireContext(), R.raw.not)
        preference = MyPreference(requireContext())
        loadView()

        viewModel.onClickBackLiveData.observe(viewLifecycleOwner, onClickBackObserver)
        viewModel.setVariantsLiveData.observe(viewLifecycleOwner, setVariantsObserver)
        viewModel.setAnswerLengthLiveData.observe(viewLifecycleOwner, setAnswerLengthObserver)
        viewModel.setImagesLiveData.observe(viewLifecycleOwner, setImagesObserver)
        viewModel.clearAnswerLiveData.observe(viewLifecycleOwner, clearAnswerObserver)
        viewModel.setCoinsLiveData.observe(viewLifecycleOwner, setCoinsObserver)
        viewModel.setCurrentLevelLiveData.observe(viewLifecycleOwner, setCurrentLevelObserver)
        viewModel.onClickVariantsLiveData.observe(viewLifecycleOwner, onClickVariantsObserver)
        viewModel.onClickAnswerLiveData.observe(viewLifecycleOwner, onClickAnswerObserver)

        binding.btnBackOfGameActivity.setOnClickListener {
            if (preference.sound == 1)
                playerItem.start()

            viewModel.onClickBack()
        }

    }

    private val onClickBackObserver = Observer<Unit> {
        findNavController().popBackStack()
    }

    private fun loadView() {
        val data = viewModel.getDataByLevel(preference.level)
        variant = data.variant.toList().shuffled()
        answer = data.answer
        tagAnswer = StringBuilder("########".substring(0, answer.length))
        tagVariant = StringBuilder("############")

        answers.add(binding.answer1)
        answers.add(binding.answer2)
        answers.add(binding.answer3)
        answers.add(binding.answer4)
        answers.add(binding.answer5)
        answers.add(binding.answer6)
        answers.add(binding.answer7)
        answers.add(binding.answer8)

        variants.add(binding.variant1)
        variants.add(binding.variant2)
        variants.add(binding.variant3)
        variants.add(binding.variant4)
        variants.add(binding.variant5)
        variants.add(binding.variant6)
        variants.add(binding.variant7)
        variants.add(binding.variant8)
        variants.add(binding.variant9)
        variants.add(binding.variant10)
        variants.add(binding.variant11)
        variants.add(binding.variant12)

        viewModel.clearAnswer()
        viewModel.setCoins(preference.coins)
        viewModel.setCurrentLevel(preference.level)
        viewModel.setImages(preference.level)
        viewModel.setVariants()
        viewModel.setAnswerLength(preference.level)

        onCLickButtons()
    }

    private fun onCLickButtons() {
        for (i in 0 until 12) {
            variants[i].setOnClickListener {
                if (preference.sound == 1 && tagAnswer.indexOf('#') != -1)
                    playerItem.start()

                viewModel.onClickVariant(i)
            }
        }
        for (i in 0 until 8) {
            answers[i].setOnClickListener {
                if (preference.sound == 1)
                    playerItem.start()

                viewModel.onClickAnswer(i)
            }
        }
    }

    private val setVariantsObserver = Observer<Unit> {
        for (i in variant.indices) {
            variants[i].text = variant[i].toString()
        }
    }
    private val setAnswerLengthObserver = Observer<Int> {
        for (i in 0 until it) {
            answers[i].visibility = View.VISIBLE
        }
        for (i in it until 8) {
            answers[i].visibility = View.GONE
        }
    }
    private val setImagesObserver = Observer<List<Int>> {
        binding.image1.setImageResource(it[0])
        binding.image2.setImageResource(it[1])
        binding.image3.setImageResource(it[2])
        binding.image4.setImageResource(it[3])
    }
    private val clearAnswerObserver = Observer<Unit> {
        for (i in 0 until answers.size) {
            answers[i].text = ""
            answers[i].isEnabled = false
        }
        for (i in 0 until variants.size) {
            variants[i].isEnabled = true
        }
    }
    private val setCoinsObserver = Observer<Int> {
        binding.coinsOfGameActivity.text = it.toString()
    }
    private val setCurrentLevelObserver = Observer<Int> {
        binding.levelN.text = "Level ${it + 1}"
    }
    private val onClickVariantsObserver = Observer<Int> {
        val letter = variant[it]
        val indexTagAnswer = tagAnswer.indexOf("#")
        if (indexTagAnswer != -1) {
            tagAnswer.setCharAt(indexTagAnswer, letter)
            tagVariant.setCharAt(it, letter)

            setLetterToAnswerByPos(letter, indexTagAnswer)
            disableVariantButtonByPos(it)

            if (tagAnswer.indexOf("#") == -1) {
                isCorrect(tagAnswer.toString())
            }
        }else{
            playerNot.start()
            val animShake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            binding.answerRoot.startAnimation(animShake)
        }
    }

    private fun setLetterToAnswerByPos(letter: Char, pos: Int) {
        answers[pos].isEnabled = true
        answers[pos].text = letter.toString()
    }

    private fun disableVariantButtonByPos(pos: Int) {
        variants[pos].isEnabled = false
        variants[pos].text = ""
    }

    private val onClickAnswerObserver = Observer<Int> {
        val letter = tagAnswer[it]
        tagAnswer.setCharAt(it, '#')
        disableAnswerButtonByPos(it)
        val index = tagVariant.indexOf(letter.toString())
        tagVariant.setCharAt(index, '#')
        setLetterToVariantButton(index, letter)
    }

    private fun disableAnswerButtonByPos(pos: Int) {
        answers[pos].isEnabled = false
        answers[pos].text = ""
    }

    private fun setLetterToVariantButton(pos: Int, letter: Char) {
        variants[pos].isEnabled = true
        variants[pos].text = letter.toString()
    }

    private fun isCorrect(ans: String) {
        if (ans == answer) {
            if (preference.sound == 1)
                playerWin.start()

            preference.coins += answer.length * 2
            preference.level++
            viewModel.setCoins(preference.coins)

            val dialogWin = WinDialog()

            val args = Bundle()
            args.putString("ANSWER", ans)
            args.putString("COIN", "+" + (answer.length * 2))
            dialogWin.arguments = args

            dialogWin.setOnClickContinueListener {
                if (preference.level != viewModel.getSize()) {
                    loadView()
                } else {
                    preference.level = 0
                    loadView()
                }
            }
            dialogWin.isCancelable = false
            dialogWin.show(childFragmentManager, "dialogWin")
        } else {
            playerNot.start()

            val animShake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            binding.answerRoot.startAnimation(animShake)

            // Animation with programmatically
//            val animatorSet = AnimatorSet()
//            animatorSet.playTogether(
//                ObjectAnimator.ofFloat(
//                    binding.answerRoot,
//                    "rotation",
//                    0f,
//                    25f,
//                    -25f,
//                    25f,
//                    -25f,
//                    15f,
//                    -15f,
//                    6f,
//                    -6f,
//                    0f
//                )
//            )
//            animatorSet.start()

        }
    }
}