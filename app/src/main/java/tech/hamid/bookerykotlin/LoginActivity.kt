package tech.hamid.bookerykotlin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var mEmail : EditText
    lateinit var mPassword : EditText
    lateinit var mLoginBtn : Button
    lateinit var mCreateBtn : TextView
    lateinit var forgotPasswordLink : TextView
    lateinit var progressBar: ProgressBar
    lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmail = findViewById(R.id.email)
        mPassword = findViewById(R.id.password)
        progressBar = findViewById(R.id.progressBar2)
        fAuth = FirebaseAuth.getInstance()
        mLoginBtn = findViewById(R.id.Loginbtn)
        mCreateBtn = findViewById(R.id.createtext)
        forgotPasswordLink = findViewById(R.id.forgotpassword)

        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        mLoginBtn.setOnClickListener(object : View.OnClickListener {
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

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
                        var intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        Toast.makeText(this@LoginActivity, "Couldn't Login, please try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        mCreateBtn.setOnClickListener {
            finish()
        //    startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        forgotPasswordLink.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val resetMail = EditText(v?.context)
                val passwordResetDialog = v?.let { AlertDialog.Builder(it.context) }

                if (passwordResetDialog != null) {
                    passwordResetDialog.setTitle("Reset Password ?")
                    passwordResetDialog.setMessage("Enter your Email to recieve reset link.")
                    passwordResetDialog.setView(resetMail)
                }

                passwordResetDialog!!.setPositiveButton("yes") { dialog, which ->
                    val mail = resetMail.text.toString()
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener {
                        Toast.makeText(
                            this@LoginActivity,
                            "Reset link has been sent to your Email.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            this@LoginActivity,
                            "Error! Reset Link is not sent" + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                passwordResetDialog.setNegativeButton(
                    "No",
                    DialogInterface.OnClickListener { dialog, which -> })
                passwordResetDialog.create().show()
            }
        })
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
