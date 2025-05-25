package com.solapp.navtrip.ui.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.solapp.navtrip.R
import com.solapp.navtrip.ui.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repasswordEditText: EditText
    private lateinit var signupButton: Button

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Menghubungkan view dengan ID
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        repasswordEditText = findViewById(R.id.repasswordEditText)
        signupButton = findViewById(R.id.signupButton)

        signupButton.setOnClickListener {
            performRegister()
        }

        setupView()
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

    // Fungsi untuk melakukan validasi dan proses registrasi
    private fun performRegister() {
        // Mendapatkan nilai input
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val repassword = repasswordEditText.text.toString()

        // Validasi input
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
            showToast("Harap lengkapi semua kolom")
            return
        }

        if (password != repassword) {
            showToast("Password dan Ulangi Password tidak cocok")
            return
        }

        if (password.length < 6) {
            showToast("Password harus lebih dari 6 karakter")
            return
        }

        // Mendaftar dengan Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Jika registrasi berhasil, alihkan ke halaman utama (HomeActivity)
                    showToast("Registrasi Berhasil")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Menutup RegisterActivity agar tidak bisa kembali ke halaman registrasi
                } else {
                    // Menampilkan pesan kesalahan jika registrasi gagal
                    showToast("Registrasi Gagal: ${task.exception?.message}")
                }
            }
    }

    // Fungsi untuk menampilkan pesan toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}