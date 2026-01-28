package dam.pmdm.tarea3_aranda_calatayud_carlos.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflamos el layout
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inicializo Firebase auth
        auth = FirebaseAuth.getInstance()


        // Configuramos el botón de registro
        binding.btnRegister.setOnClickListener {
            // Obtenemos los datos del usuario
            val name = binding.registerName.text.toString().trim()
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()

            // Validamos que los campos no estén vacíos
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

                // registramos el usuario en la base de datos
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registro exitoso
                            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Error al registrar el usuario: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            } else if (name.isEmpty()) {
                binding.registerName.error = "El nombre de usuario es obligatorio"
                binding.registerName.requestFocus()
            } else if (email.isEmpty()) {
                binding.registerEmail.error = "El email es obligatorio"
                binding.registerEmail.requestFocus()
            } else if (password.isEmpty()) {
                binding.registerPassword.error = "La contraseña es obligatoria"
                binding.registerPassword.requestFocus()
            }
        }

        binding.btnRegisterVolverLogin.setOnClickListener {
            finish() // como quiero salir, borro la pantalla actual
        }


    }


}