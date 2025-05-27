package com.solapp.navtrip.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.solapp.navtrip.R
import com.solapp.navtrip.data.pref.UserModel
import com.solapp.navtrip.databinding.ActivityLoginBinding
import com.solapp.navtrip.ui.MainActivity
import com.solapp.navtrip.ui.ViewModelFactory
import com.solapp.navtrip.ui.register.RegisterActivity
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.signInButton.setOnClickListener {
            signIn()
        }

        setupView()
        setupAction()

        binding.tidakAdaAkun.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
            binding.loginButton.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.loginButton.visibility = View.VISIBLE
        }
    }

    private fun signIn() {
        val credentialManager = CredentialManager.create(this)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.your_web_client_id))
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        lifecycleScope.launch {
            try {
                val result: GetCredentialResponse = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.d("Error", e.message.toString())
                showProgressBar(false) // Menyembunyikan ProgressBar jika ada error
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                        showProgressBar(false) // Menyembunyikan ProgressBar jika error
                    }
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                    showProgressBar(false) // Menyembunyikan ProgressBar jika error
                }
            }
            else -> {
                Log.e(TAG, "Unexpected type of credential")
                showProgressBar(false) // Menyembunyikan ProgressBar jika error
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user: FirebaseUser? = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    showProgressBar(false) // Menyembunyikan ProgressBar jika gagal
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val email = currentUser.email ?: ""
            val token = currentUser.getIdToken(false)?.result?.token ?: "" // Token default jika tidak ada
            val name = currentUser.displayName ?: currentUser.email?.substringBefore('@') ?: "User" // Menggunakan email atau nama default

            Log.d(TAG, "Nama Pengguna: $name") // Log untuk debug

            lifecycleScope.launch {
                viewModel.saveSession(UserModel(email = email, token = token, isLogin = true, name = name, photoUrl = "")) // Tidak menyimpan foto profil

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showProgressBar(true) // Menampilkan ProgressBar saat login

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    showProgressBar(false) // Menyembunyikan ProgressBar setelah login
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val token = user.getIdToken(false)?.result?.token ?: "" // Ambil token

                            lifecycleScope.launch {
                                // Simpan data session
                                viewModel.saveSession(UserModel(email = email, token = token, isLogin = true))

                                // Lanjutkan ke MainActivity
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Login gagal: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}