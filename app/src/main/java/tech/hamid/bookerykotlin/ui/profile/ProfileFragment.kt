package tech.hamid.bookerykotlin.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import tech.hamid.bookerykotlin.LoginActivity
import tech.hamid.bookerykotlin.R

class ProfileFragment : Fragment() {

    private lateinit var dashboardViewModel: ProfileViewModel

    lateinit var emailField : TextView
    lateinit var fAuth : FirebaseAuth
    lateinit var user : FirebaseUser
    lateinit var storageReference : StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val btn : Button = root.findViewById(R.id.button)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        btn.setOnClickListener { logout() }
        emailField = root.findViewById(R.id.email)
        storageReference = FirebaseStorage.getInstance().getReference()
        fAuth = FirebaseAuth.getInstance()
        user = fAuth.currentUser

        if(user!=null) {
            emailField.text = user.email
        }
        else {
            Toast.makeText(activity, "We gotta fix this!", Toast.LENGTH_LONG).show()
        }

        return root
    }





    fun logout() {
        FirebaseAuth.getInstance().signOut()
        val activity = activity
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity!!.finishAffinity()
    }
}