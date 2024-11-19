package com.example.guestlog_eldroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_guests -> {
                    // Navigate to Guests Fragment
                    loadFragment(GuestsFragment())
                    true
                }
                R.id.nav_add_guest -> {
                    // Navigate to Guest Form Fragment
                    loadFragment(GuestForm())
                    true
                }
                R.id.nav_history -> {
                    // Navigate to History Fragment
                    loadFragment(HistoryFragment())
                    true
                }
                else -> false
            }
        }

        // Set default fragment
        if (savedInstanceState == null) {
            bottomNavView.selectedItemId = R.id.nav_guests
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }
}
