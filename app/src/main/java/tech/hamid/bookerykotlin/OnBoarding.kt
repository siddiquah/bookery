package tech.hamid.bookerykotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class OnBoarding : AppCompatActivity() {

    lateinit var btn: Button
    lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        fAuth = FirebaseAuth.getInstance()
        btn = findViewById(R.id.cta)
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                gotomain()
            }
        })
    }

    fun gotomain() {
        val intent : Intent
        if(fAuth.currentUser == null) {
            intent = Intent(this, RegisterActivity::class.java)
        }
        else {
            intent = Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}