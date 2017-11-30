package com.questionqate.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.JsonParser
import com.questionqate.Pojo.Global_Strings
import com.questionqate.Pojo.Teacher
import com.questionqate.R
import css.fingerprint.Networking.OkhttpObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var email: EditText
    private lateinit var pass: EditText


    private lateinit var forgetPass: TextView
    private lateinit var signup: TextView

    private lateinit var rememberMe: CheckBox
    private lateinit var sharedPreferences: SharedPreferences
    private var check = false
    private lateinit var userID: String

    internal lateinit var global: Global_Strings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        global = Global_Strings

        val username = sharedPreferences!!.getString("username", "")
        val password = sharedPreferences!!.getString("password", "")
        if (username!!.length > 2 && password!!.length > 2) {

            userID = sharedPreferences!!.getString("user_id", "id")
            startActivity(Intent(this@MainActivity, StudentSlideMenu::class.java).putExtra("user_id", userID))
            finish()
        }


        mAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.UserName)
        pass = findViewById(R.id.Password)
        rememberMe = findViewById(R.id.rememberMe)
        forgetPass = findViewById(R.id.forget_pass)
        signup = findViewById(R.id.signup)


        forgetPass!!.setOnClickListener(this)
        signup!!.setOnClickListener(this)


        rememberMe!!.setOnCheckedChangeListener { buttonView, isChecked ->
            check = isChecked
        }

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.forget_pass -> startActivity(Intent(this@MainActivity, ForgetPasswordActivity::class.java))
            R.id.signup -> startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
        }
    }

    fun OnLoginClicked(view: View) {

        var form = FormBody.Builder()
        form.add("email", email.text.trim().toString())
        form.add("password", pass.text.trim().toString())

        OkhttpObservable.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/checkIfTeacher", form)
                .subscribeOn(Schedulers.io())
                .map { response -> JsonParser().parse(response.body()!!.string()) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { e ->
                    run {
                        if (e.asJsonObject.get("isTeacher").asBoolean) {
                            if (!e.asJsonObject.get("auth").asBoolean) {
                                Toast.makeText(this@MainActivity, " Wrong Email or Password", Toast.LENGTH_LONG).show()
                            } else {

                                System.out.println( e.asJsonObject.get("t_subjects").asJsonArray)

                                Teacher.currentTeacher = Teacher.teacherInfo(
                                        e.asJsonObject.get("t_subjects").asJsonArray,
                                        e.asJsonObject.get("t_name").asString,
                                        e.asJsonObject.get("t_id").asInt)

                                startActivity(Intent(this@MainActivity, LecturerHome::class.java))
                            }
                        } else {
                            mAuth!!.signInWithEmailAndPassword(email!!.text.toString(), pass!!.text.toString())
                                    .addOnCompleteListener(this@MainActivity) { task ->
                                        Log.w("firebaseLogin", "signInWithEmail:onComplete:" + task.isSuccessful)
                                        if (!task.isSuccessful) {
                                            Log.w("firebaseLogin", "signInWithEmail:failed", task.exception)
                                            Toast.makeText(this@MainActivity,
                                                    "Login failed",
                                                    Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(this@MainActivity,
                                                    "Login success ",
                                                    Toast.LENGTH_SHORT).show()
                                            getUID()

                                        }
                                    }

                        }
                    }


                }.subscribe()

    }

    private fun getUID() {
        val user = mAuth!!.currentUser
        if (user != null) {

            if (check) {
                val editor = sharedPreferences!!.edit()
                editor.putString("username", email!!.text.toString())
                editor.putString("password", pass!!.text.toString())
                editor.putString("user_id", user.uid)
                editor.apply()

                global.student_UID_firebase = user.uid
                startActivity(Intent(this@MainActivity, StudentSlideMenu::class.java))
            } else {


                global.student_UID_firebase = user.uid
                startActivity(Intent(this@MainActivity, StudentSlideMenu::class.java))
            }
        } else {
            // User is signed out
            Log.d("firebase", "onAuthStateChanged:signed_out")
        }
    }

}