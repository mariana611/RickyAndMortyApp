package dam.pmdm.rickyandmortyapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // IDs que son "destinos principales" (flecha de atrás vs hamburguesa)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_episodes, R.id.nav_stats, R.id.nav_settings),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        // Conexión automática para los fragmentos
        navView.setupWithNavController(navController)

        // Interceptamos SOLO el clic de "Acerca de" sin romper el resto
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_about -> {
                    showAboutDialog()
                    drawerLayout.closeDrawers()
                    true
                }
                else -> {
                    // Esto permite que Episodios, Stats y Ajustes sigan funcionando
                    val handled = menuItem.onNavDestinationSelected(navController)
                    if (handled) drawerLayout.closeDrawers()
                    handled
                }
            }
        }
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.acerca_de))
            .setMessage("Desarrollador: Tu Nombre\nVersión: 1.0.0\nRicky and Morty App - DAM")
            .setPositiveButton("Cerrar", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}