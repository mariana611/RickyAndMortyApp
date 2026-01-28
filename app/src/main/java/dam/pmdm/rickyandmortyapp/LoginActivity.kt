package dam.pmdm.rickyandmortyapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Comprobar si el usuario ya está logueado (Opcional, para saltar el login)
        if (auth.currentUser != null) {
            irAlMain()
        }

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {
            // Usamos trim() para eliminar espacios accidentales al inicio o final
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validación de campos vacíos y formato de email
            if (email.isEmpty()) {
                etEmail.error = "Introduce un email"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Formato de email inválido (ej: usuario@gmail.com)"
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                etPassword.error = "La contraseña debe tener al menos 6 caracteres"
                return@setOnClickListener
            }

            // Intentar inicio de sesión
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                        irAlMain()
                    } else {
                        // El mensaje de excepción nos dirá si el usuario no existe
                        val msg = task.exception?.message ?: "Error de autenticación"
                        Toast.makeText(this, "Fallo: $msg", Toast.LENGTH_LONG).show()
                    }
                }
        }

        tvRegister.setOnClickListener {
            // Asegúrate de que el nombre de la clase sea RegisterActivity
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun irAlMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}