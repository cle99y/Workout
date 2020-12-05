package uk.geeklife.workout

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
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
import uk.geeklife.config.Config.Companion.ZERO
import uk.geeklife.config.State
import uk.geeklife.config.Utility
import uk.geeklife.workout.databinding.ActivityExerciseBinding

class Exercise : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = Config.ZERO
    private var currentState = State.REST
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var selectedExercise: Int = 0 // initialised outside of array list
    private var exerciseListSize: Int = 0 // initialise list in onCreate

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


        exerciseList = Utility.defaultExerciseList()
        exerciseListSize = exerciseList!!.size
        setUpView(State.REST)
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
                resetCountDown(COUNTDOWN_REST_TIMER_START_VALUE)
                setProgressBar(COUNTDOWN_REST_TIMER_START_VALUE)
                binding.tvReady.text = GET_READY_TEXT
                binding.ivExercise.visibility = GONE
                binding.tvActivity.visibility = VISIBLE
                binding.tvActivityName.visibility = VISIBLE
                binding.tvActivityName.text = exerciseList!![selectedExercise + 1].getExerciseName()

            }
            State.EXERCISE -> {
                if (selectedExercise < exerciseListSize) { // not to exceed list size
                    selectedExercise++
                    Log.d("DEBUG", "$state")
                    resetCountDown(COUNTDOWN_EXERCISE_TIMER_START_VALUE)
                    setProgressBar(COUNTDOWN_EXERCISE_TIMER_START_VALUE)
                    binding.tvReady.text = exerciseList!![selectedExercise].getExerciseName()
                    binding.ivExercise.setImageResource(exerciseList!![selectedExercise].getImage())
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

}
