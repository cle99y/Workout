package uk.geeklife.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*
import uk.geeklife.Config
import uk.geeklife.Config.Companion.COUNTDOWN_EXERCISE_TIMER_START_VALUE
import uk.geeklife.Config.Companion.COUNTDOWN_INTERVAL_1SEC
import uk.geeklife.Config.Companion.COUNTDOWN_REST_TIMER_START_VALUE
import uk.geeklife.config.State
import uk.geeklife.workout.databinding.ActivityExerciseBinding

class Exercise : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = Config.ZERO

    private lateinit var binding: ActivityExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        setUpRestView(State.REST)
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
                Toast.makeText(this@Exercise, "Start the exercise", Toast.LENGTH_SHORT).show()
            }
        }.start()

    }

    private fun setUpRestView(state: State) {

        when (state) {
            State.REST -> {
                Log.d("DEBUG", "$state")
                resetCountDown(COUNTDOWN_REST_TIMER_START_VALUE)
                setProgressBar(COUNTDOWN_REST_TIMER_START_VALUE)
            }
            State.EXERCISE -> {
                Log.d("DEBUG", "$state")
                resetCountDown(COUNTDOWN_EXERCISE_TIMER_START_VALUE)
                setProgressBar(COUNTDOWN_EXERCISE_TIMER_START_VALUE)
            }
        }


    }

    private fun resetCountDown(timeInSecs: Int) {

        Log.d("DEBUG", "Time in seconds = $timeInSecs")

        if (restTimer != null) {

            restTimer!!.cancel()
            binding.progressBar.max = timeInSecs
            restProgress = timeInSecs
            binding.tvTimer.text = timeInSecs.toString()

        }

    }

}
