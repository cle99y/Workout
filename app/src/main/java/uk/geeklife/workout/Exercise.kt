package uk.geeklife.workout

import android.media.MediaPlayer
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.webkit.RenderProcessGoneDetail
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*
import uk.geeklife.config.Config
import uk.geeklife.config.Config.Companion.ALL_DONE_TEXT
import uk.geeklife.config.Config.Companion.COUNTDOWN_EXERCISE_TIMER_START_VALUE
import uk.geeklife.config.Config.Companion.COUNTDOWN_INTERVAL_1SEC
import uk.geeklife.config.Config.Companion.COUNTDOWN_REST_TIMER_START_VALUE
import uk.geeklife.config.Config.Companion.GET_READY_TEXT
import uk.geeklife.config.Config.Companion.REST_CHIME
import uk.geeklife.config.Config.Companion.ZERO
import uk.geeklife.config.State
import uk.geeklife.config.Utility
import uk.geeklife.config.Utility.Companion.defaultExerciseList
import uk.geeklife.workout.databinding.ActivityExerciseBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class Exercise : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null
    private var media: MediaPlayer? = null

    private var restProgress = ZERO
    private var currentState = State.REST
    private var selectedExercise: Int = ZERO // initialised outside of array list
    private var exerciseListSize: Int = ZERO // initialise list in onCreate

    // the following initialised in onCreate()
    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var binding: ActivityExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setSupportActionBar(binding.toolbarExerciseActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = defaultExerciseList()
        exerciseListSize = exerciseList.size
        textToSpeech = TextToSpeech(this, this)
        setUpView(State.REST)
    }

    override fun onDestroy() {
        restTimer?.cancel()
        restProgress = ZERO

        textToSpeech.stop()
        textToSpeech.shutdown()

        media?.stop()

        super.onDestroy()
    }


    private fun setProgressBar(timeInSecs: Int) {

        val timeInMillis: Long = (timeInSecs * 1000).toLong()
        binding.progressBar.max = timeInSecs

        binding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(timeInMillis, COUNTDOWN_INTERVAL_1SEC) {

            override fun onTick(millisUntilFinished: Long) {

                val timeLeft = timeInSecs - restProgress
                binding.progressBar.progress = timeLeft
                binding.tvTimer.text = timeLeft.toString()
                restProgress++
            }

            override fun onFinish() {
                when (currentState) {
                    State.REST -> setUpView(State.EXERCISE)
                    State.EXERCISE -> setUpView(State.REST)
                }
            }
        }.start()

    }

    private fun setUpView(state: State) {

        when (state) {
            State.REST -> {
                Log.d("DEBUG", "$state")

                // play notification sound
                try {
                    playMedia(REST_CHIME)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                resetCountDown(COUNTDOWN_REST_TIMER_START_VALUE)
                setProgressBar(COUNTDOWN_REST_TIMER_START_VALUE)
                binding.tvReady.text = GET_READY_TEXT
                binding.ivExercise.visibility = GONE
                binding.tvActivity.visibility = VISIBLE
                binding.tvActivityName.visibility = VISIBLE
                binding.tvActivityName.text = exerciseList[selectedExercise + 1].getExerciseName()

            }
            State.EXERCISE -> {
                if (selectedExercise < exerciseListSize) { // not to exceed list size
                    selectedExercise++
                    Log.d("DEBUG", "$state")
                    resetCountDown(COUNTDOWN_EXERCISE_TIMER_START_VALUE)
                    setProgressBar(COUNTDOWN_EXERCISE_TIMER_START_VALUE)
                    val exerciseText = exerciseList[selectedExercise].getExerciseName()

                    binding.tvReady.text = exerciseText
                    speak(exerciseText)

                    binding.ivExercise.setImageResource(exerciseList[selectedExercise].getImage())
                    binding.ivExercise.visibility = VISIBLE
                    binding.tvActivity.visibility = GONE
                    binding.tvActivityName.visibility = GONE
                } else {
                    binding.tvReady.text = ALL_DONE_TEXT
                    binding.ivExercise.visibility = GONE
                }
            }
        }
        currentState = state
    }

    private fun resetCountDown(timeInSecs: Int) {

        Log.d("DEBUG", "Time in seconds = $timeInSecs")

        if (restTimer != null) {

            restTimer!!.cancel()
            restProgress = ZERO
            binding.tvTimer.text = timeInSecs.toString()

        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set language locale
            val textLocal = textToSpeech.setLanguage(Locale.UK)
            if (textLocal == TextToSpeech.LANG_MISSING_DATA || textLocal == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("LANG", "Language not supported exception")
            }
        } else {
            Log.e("LANG", "Language not initiated exception")
        }
    }

    private fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun playMedia(sound: Int) {
        media = MediaPlayer.create(applicationContext, sound)
        media!!.isLooping = false
        media!!.start()
    }

}
