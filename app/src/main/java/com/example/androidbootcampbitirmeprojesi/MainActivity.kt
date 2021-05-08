package com.example.androidbootcampbitirmeprojesi

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setSupportActionBar(findViewById(R.id.toolbar))

        //findViewById<ExtendedFloatingActionButton>(R.id.fab).setOnClickListener { view ->
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        //}

        val prefs = getSharedPreferences("com.example.androidbootcampbitirmeprojesi", Context.MODE_PRIVATE)
        if (prefs.getBoolean("firstRun",true) ) {

            prefs.edit().putBoolean("NowInOne", true).apply()

            prefs.edit().putBoolean("firstRun", false).apply()
        } else {
            prefs.edit().putBoolean("NowInOne", false).apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}