package com.example.adminwaveoffood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminwaveoffood.Model.UserModel
import com.example.adminwaveoffood.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private var nameOfRestaurant : String ?= null
    private var userName : String ?= null
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var googleSignInClient : GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        // initialize firebase auth
        auth = Firebase.auth
        // initialize database
        database = Firebase.database.reference
        // initialize google sign in
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        // luong xu ly don't have account
        binding.doHaveAccount.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // luong xu ly login bang tai khoan thong thuong
        binding.btnLogin.setOnClickListener {
            // lay du lieu tren form
            email = binding.emailLogin.text.toString()
            password = binding.passwordLogin.text.toString()

            // kiem tra du lieu nhap vao
            if(email.isBlank() || password.isBlank()){
                // thong bao nguoi dung dien chi tiet thong tin
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show()
            }else{
                crateUserAccount(email, password)

            }
        }
        // su ly su kien login bang tai khoan google
        binding.btnGoogle.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcer.launch(signIntent)
        }
    }

    // launcher for google sign-in
    private fun crateUserAccount(email: String, password: String) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    updateUI(user)
                }else{
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val user = auth.currentUser
                            Toast.makeText(this, "Tạo tài khoản và đăng nhập thành công", Toast.LENGTH_SHORT).show()
                            saveUserData()
                            updateUI(user)
                        }else{
                            Toast.makeText(this, "Xác thức thất bại", Toast.LENGTH_SHORT).show()
                            Log.d("Account","createAccount: Failure", task.exception)
                        }
                    }
                }
        }
    }

    private fun saveUserData() {
        email = binding.emailLogin.text.toString()
        password = binding.passwordLogin.text.toString()
        val user = UserModel(userName,nameOfRestaurant,email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).child("email").setValue(user)
        }
    }

    private val launcer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            //handleSignInResult(task)
            if(task.isSuccessful){
                val account : GoogleSignInAccount = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                    if(authTask.isSuccessful) {
                        // dang nhap thanh cong bang tai khoan google
                        Toast.makeText(this, "Đăng nhập tài khoản Google thành công", Toast.LENGTH_SHORT).show()
                        updateUI(authTask.result?.user)
                        //startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // kiem tra nguoi dung dang nhap da dang nhap chua
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Đã đăng nhập thành công, in ra mã token
            Log.d("GoogleSignIn", "ID Token: ${account?.idToken}")

            // Thực hiện các hành động cần thiết sau khi đăng nhập thành công
            // Ví dụ: Gửi mã token này đến Firebase để xác thực

        } catch (e: ApiException) {
            // Đăng nhập thất bại
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }
}
