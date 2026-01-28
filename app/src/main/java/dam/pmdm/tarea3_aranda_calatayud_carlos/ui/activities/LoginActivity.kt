package dam.pmdm.tarea3_aranda_calatayud_carlos.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dam.pmdm.tarea3_aranda_calatayud_carlos.MainActivity
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {

    // Usamos lateinit
    private lateinit var binding: ActivityLoginBinding

    // declaro Firebase auth
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Inflamos el layout
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inicializo Firebase auth
        auth = FirebaseAuth.getInstance()


        // Configuramos el botón de inicio de sesión
        binding.login.setOnClickListener {

            // Obtenemos el email y la contraseña
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            // Validamos que los campos no estén vacíos
            if (email.isNotEmpty() || password.isNotEmpty()) {

                // inicio sesión en la base de datos
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener (this){task ->
                    if (task.isSuccessful){
                        // existe el usuario
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // no existe el usuario
                        Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.gotoRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}