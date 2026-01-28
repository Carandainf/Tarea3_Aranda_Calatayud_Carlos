package dam.pmdm.tarea3_aranda_calatayud_carlos.ui.activities

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam.pmdm.tarea3_aranda_calatayud_carlos.api.RetrofitClient
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.FragmentStatsBinding
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64


class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarEstadisticas()
    }

    private fun cargarEstadisticas() {
        val userId = auth.currentUser?.uid ?: return

        lifecycleScope.launch {
            try {
                // obtengo el total de episodios de la Api
                val response = RetrofitClient.instance.getEpisodes()
                val total = response.info.count

                // Vamos a Firebase para ver cuantos episodios hay marcados como visto
                db.collection("usuarios").document(userId).collection("episodios_vistos")
                    .whereEqualTo("viewed", true).get().addOnSuccessListener { documents ->
                        val vistos = documents.size()
                        val pendientes = total - vistos
                        mostrarGraficas(vistos, pendientes, total)
                    }
            } catch (e: Exception) {
                binding.tvResumen.text = " Error al intentar conectar con la API"
            }
        }
    }

    private fun mostrarGraficas(vistos: Int, pendientes: Int, total: Int) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(vistos.toFloat(), "Vistos"))
        entries.add(PieEntry(pendientes.toFloat(), "Pendientes"))

        val dataSet = PieDataSet(entries, "")

        // Set de colores: Uso el Verde Rick y Gris y le indico que no quiero decimales, aparte de tamaño y color
        dataSet.colors = listOf(Color.parseColor("#97CE4C"), Color.parseColor("#E0E0E0"))
        dataSet.valueTextSize = 16f
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueFormatter = DefaultValueFormatter(0)


        val data = PieData(dataSet)
        binding.pieChart.apply {
            this.data = data
            centerText = "${(vistos * 100 / total)}%"
            setCenterTextSize(22f)
            description.isEnabled = false
            legend.isEnabled = true
            animateY(1200)

            invalidate()
        }
        binding.tvResumen.text = "Has visto $vistos de $total episodios."
    }

}


