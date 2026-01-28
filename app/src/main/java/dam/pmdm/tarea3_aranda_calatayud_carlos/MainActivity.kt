package dam.pmdm.tarea3_aranda_calatayud_carlos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // acceso a las vistas (mis elementos botones, menus, etc.)
    private lateinit var navController: NavController // me permite moverme de un fragment a otro
    private lateinit var appBarConfiguration: AppBarConfiguration // configura la barra superior


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater) // inflamos el layout

        setContentView(binding.root)
        //ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
        //    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //    insets
        //}

        setSupportActionBar(binding.toolbar) // le estoy indicando que use la barra de título que he creado

        // busco el contenedor de fragmentos que he creado en mi activity_main.xml y le asigno mi controlador de navegación
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // le informamos a la pantalla que tiene un menu lateral
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_episodes, R.id.nav_stats, R.id.nav_settings, R.id.nav_about), binding.drawerLayout)

        // configuramos la barra superior
        setupActionBarWithNavController(navController, appBarConfiguration)

        // configuramos el menu lateral, para que cambie de fragmento al elegir una opción
        binding.navView.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }
}