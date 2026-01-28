package dam.pmdm.tarea3_aranda_calatayud_carlos.ui.activities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dam.pmdm.tarea3_aranda_calatayud_carlos.R
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.FragmentAjustesBinding


class AjustesFragment : Fragment() {
    private lateinit var binding: FragmentAjustesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAjustesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Cambio Tema
        binding.switchTheme.isChecked = prefs.getBoolean("dark_mode", false)
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            val mode =
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        // Simulación de cambio de Idioma, aunjque no lo hago realmente
        binding.switchLang.setOnCheckedChangeListener { _, isChecked ->
            val lang = if (isChecked) "Inglés" else "Castellano"
            prefs.edit().putString("language", if (isChecked) "en" else "es").apply()
            android.widget.Toast.makeText(
                context,
                "Idioma cambiado a $lang",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        // Logout de Firebase + Navegación
        binding.btnLogout.setOnClickListener {
            // Cierro Sesión en Firebase
            FirebaseAuth.getInstance().signOut()

            // Preparo para ir al LoginActivity
            val intent = android.content.Intent(context, LoginActivity::class.java)

            // limpio el historial para que no pueda volver atras
            intent.flags =
                android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            // cierro la actividad actual
            requireActivity().finish()

        }


    }
}
