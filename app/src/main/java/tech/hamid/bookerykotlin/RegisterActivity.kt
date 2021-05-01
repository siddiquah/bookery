package tech.hamid.bookerykotlin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var mFullName : EditText
    lateinit var mPassword : EditText
    lateinit var mPhone : EditText
    lateinit var mEmail : EditText
    lateinit var mRegisterBtn : Button
    lateinit var mLoginBtn : TextView
    lateinit var fAuth : FirebaseAuth
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        mFullName = findViewById(R.id.fullname)
        mEmail = findViewById(R.id.email)
        mPassword = findViewById(R.id.password)
        mPhone = findViewById(R.id.phoneno)
        mRegisterBtn = findViewById(R.id.registerbtn)
        mLoginBtn = findViewById(R.id.createtext)
        progressBar = findViewById(R.id.progressBar)

        fAuth = FirebaseAuth.getInstance()

        mRegisterBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var email: String = mEmail.text.toString().trim()
                var password: String = mPassword.text.toString().trim()


                if (TextUtils.isEmpty(email)) {
                    mEmail.error = "Email is required."
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.error = "Password is required"
                    return
                }
                if (password.length < 6) {
                    mPassword.error = "password must be greater or equal to six characters."
                    return
                }
                progressBar.visibility = View.VISIBLE

                Log.d("anjum", "1")
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    Log.d("anjum", "2")
                    if (task.isSuccessful) {
                        Log.d("anjum", "3")
                        Toast.makeText(this@RegisterActivity, "User created", Toast.LENGTH_SHORT).show()
                        var intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        Log.d("anjum", "33")
                        finishAffinity()
                    } else {
                        Log.d("anjum", "4")
                        Toast.makeText(this@RegisterActivity, "Couldn't Register, please try again", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("anjum", "5")
                }


            }
        })

        mLoginBtn.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}