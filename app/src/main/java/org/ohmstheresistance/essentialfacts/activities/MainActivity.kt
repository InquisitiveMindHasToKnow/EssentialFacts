package org.ohmstheresistance.essentialfacts.activities

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.ohmstheresistance.essentialfacts.R
import org.ohmstheresistance.essentialfacts.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")

        val binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        val navController = this.findNavController(R.id.navHostFragment)

        drawerLayout = binding.drawerLayout
        binding.navView.setItemTextAppearance(R.style.ThemeOverlay_AppCompat_navTheme)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        NavigationUI.setupWithNavController(binding.navView, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
