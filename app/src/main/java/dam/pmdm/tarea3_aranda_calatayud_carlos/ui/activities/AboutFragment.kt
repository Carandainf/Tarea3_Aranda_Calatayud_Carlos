package dam.pmdm.tarea3_aranda_calatayud_carlos.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btExitApp.setOnClickListener { mostrarConfirmacionSalida() }
    }

    private fun mostrarConfirmacionSalida() {
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Salir de la aplicación")
            .setMessage("¿Vas a cerrar tu sesión de Firebase y la App. ¿Estas seguro?")
            .setPositiveButton("Sí, salir") { _, _ ->
                // Cerrar sesión de Firebase
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                // Salimos de la App
                requireActivity().finishAffinity()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

