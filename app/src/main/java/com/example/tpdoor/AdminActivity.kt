package com.example.tpdoor

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tpdoor.ui.home.HomeFragment
import com.example.tpdoor.ui.slideshow.SlideshowFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class AdminActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = intent
        val iEmail = intent.getStringExtra("email")
        val iKey = intent.getStringExtra("key")


        bundle.putString("iKey",iKey)

        val dispositivo = HomeFragment()
        dispositivo.arguments=bundle
        //val fragSlideshowFragment = SlideshowFragment()
        //fragSlideshowFragment.arguments = bundle


        //Toast.makeText(this, "Principal: "+iKey, Toast.LENGTH_LONG).show()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val fragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fragmentManager.beginTransaction()
        //ft.add(R.id.nav_host_fragment,dispositivo)
        ft.commit()



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.admin, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        //Toast.makeText(this,bundle.getString("iKey"),Toast.LENGTH_LONG).show()
        navController.navigate(R.id.nav_home,bundle)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
