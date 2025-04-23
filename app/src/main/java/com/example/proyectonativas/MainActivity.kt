package com.example.proyectonativas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyectonativas.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navcontroller: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var drawerToogle: ActionBarDrawerToggle
    private lateinit var navView2: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*configuracion de la barra de herramientas*/
        toolbar = findViewById(R.id.header)
        setSupportActionBar(toolbar)
        /* navegacion de fragment*/
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment2)

        navcontroller = navHostFragment?.findNavController()!!
        drawerLayout = findViewById(R.id.main)

        navView2 = findViewById(R.id.nav_view2)

        drawerToogle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(drawerToogle)
        drawerToogle.syncState()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.favoritoFragment,
                R.id.productosFragment,
                R.id.TiendasFragment,
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navcontroller, appBarConfiguration)
        navView2.setupWithNavController(navcontroller)

        navView2.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navcontroller.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.CerrarSesion -> {
                val intent = Intent(this, LoginregistroActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }

            else -> {

                val handled = androidx.navigation.ui.NavigationUI.onNavDestinationSelected(
                    item,
                    navcontroller
                )
                if (handled) {
                    drawerLayout.closeDrawer(navView2)
                }
                return handled
            }
        }
    }
}