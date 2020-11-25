package uk.geeklife.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*
import uk.geeklife.Config

class Exercise : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = Config.ZERO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        setUpRestView()
    }

    override fun onDestroy() {
        super.onDestroy()
        resetCountDown()
    }

    private fun setProgressBar() {

        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                Log.d("DEBUG", "$millisUntilFinished")
                val timeLeft = Config.COUNTDOWN_TIMER_START_VALUE - restProgress
                progressBar.progress = timeLeft
                tvTimer.text = timeLeft.toString()
                restProgress++
            }

            override fun onFinish() {
                Toast.makeText(this@Exercise, "Start the exercise", Toast.LENGTH_SHORT).show()
            }
        }.start()

    }

    private fun setUpRestView() {

        resetCountDown()
        setProgressBar()

    }

    private fun resetCountDown() {

        if ( restTimer!= null) {
            restTimer!!.cancel()
            restProgress = Config.COUNTDOWN_TIMER_START_VALUE
            tvTimer.text = Config.COUNTDOWN_TIMER_START_VALUE.toString()
        }

    }

}
