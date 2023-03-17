package com.example.educationsupport

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var coursesLayoutManager: RecyclerView.LayoutManager
    private lateinit var coursesAdaptor: RecyclerView.Adapter<RecyclerView.ViewHolder>

    private lateinit var myActivitiesRecyclerView: RecyclerView
    private lateinit var myActivitiesLayoutManager: RecyclerView.LayoutManager
    private lateinit var myActivitiesAdaptor: RecyclerView.Adapter<RecyclerView.ViewHolder>

    private lateinit var appBarLayout: AppBarLayout

    private lateinit var scrollView: ScrollView
    private var scrollFlag: Boolean = false

    private lateinit var tvToolbar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Set up the toolbar
         */
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /**
         * Set up the navigation drawer
         */
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        /**
         * Set up the navigation
         */
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this@MainActivity)
        navigationView.itemIconTintList = null

        /**
         * Set up my courses recycler view
         */
        coursesLayoutManager = GridLayoutManager(this@MainActivity, 2)
        coursesRecyclerView = findViewById(R.id.rv_course_cards)
        coursesRecyclerView.setHasFixedSize(true)
        coursesRecyclerView.layoutManager = coursesLayoutManager
        //TODO: Update the adapter
        coursesRecyclerView.adapter = null

        /**
         * Set up my activities recycler view
         */
        myActivitiesLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        myActivitiesRecyclerView = findViewById(R.id.rv_activities_cards)
        myActivitiesRecyclerView.setHasFixedSize(true)
        myActivitiesRecyclerView.layoutManager = myActivitiesLayoutManager
        //TODO: Update the adapter
        myActivitiesRecyclerView.adapter = null

        /**
         * Setup the app bar
         */
        appBarLayout = findViewById(R.id.app_bar)
        appBarLayout.bringToFront()

        /**
         * Setting the Activity's ActionBar
         */
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburgar)

        tvToolbar = findViewById(R.id.txt_toolbar)

        /**
         * Setting the scroll view
         */
        scrollView = findViewById(R.id.scrollView)
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrollView.scrollY
            if (scrollY > 100) {
                if (!scrollFlag) {
                    toolbar.setBackgroundColor(Color.WHITE)
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburgar)
                    tvToolbar.visibility = View.VISIBLE
                    scrollFlag = true
                }
            } else {
                if (scrollFlag) {
                    toolbar.setBackgroundColor(Color.TRANSPARENT)
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburgar)
                    tvToolbar.visibility = View.INVISIBLE
                    scrollFlag = false
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Handle navigation view item clicks here.")
    }
}