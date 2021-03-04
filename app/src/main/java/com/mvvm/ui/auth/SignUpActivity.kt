package com.mvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mvvm.R
import com.mvvm.data.db.entities.User
import com.mvvm.databinding.ActivitySignupBinding
import com.mvvm.ui.home.HomeActivity
import com.mvvm.util.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(),KodeinAware {

    override val kodein by kodein()
    private val factory:AuthViewModelFactory by instance()
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
         binding  =DataBindingUtil.setContentView(this,R.layout.activity_signup)
         viewModel=ViewModelProvider(this,factory).get(AuthViewModel::class.java)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel.getLoggedInUser().observe(this, Observer {user->
            if(user!=null){
                Intent(this,HomeActivity::class.java).also {
                    it.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
        binding.contentSignup.buttonSignUp.setOnClickListener {
            binding.progressBar.show()
            userSignup()
        }
    }

    private fun userSignup() {
        val name = binding.contentSignup.editTextName.text.toString().trim()
        val email = binding.contentSignup.editTextEmail.text.toString().trim()
        val password = binding.contentSignup.editTextPassword.text.toString().trim()
        val password1 = binding.contentSignup.editTextPassword.text.toString().trim()

        //@todo add input validations

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userSignup(name, email, password)
                binding.progressBar.hide()
                if (authResponse.user != null) {
                    viewModel.saveLoggedInUser(authResponse.user)
                } else {
                    binding.root.snackbar(authResponse.message!!)
                }
            } catch (e: ApiException) {
                binding.progressBar.hide()
                binding.root.snackbar(e.message!!)
                println("api exception>>>>>> ${e.message}")
                e.printStackTrace()
            } catch (e: NoInternetException) {
                binding.progressBar.hide()
                binding.root.snackbar(e.message!!)
                println("internet exception>>>>>> ${e.message}")
                e.printStackTrace()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}