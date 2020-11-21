package uk.geeklife.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvStart = findViewById<TextView>(R.id.tv_start)

        tvStart.setOnClickListener {
            val exerciseIntent = Intent(this, Exercise::class.java)
            startActivity(exerciseIntent)
        }
    }
}