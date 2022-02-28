package com.dricolas.perfectmeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//*** Main activity, the navigation is managed via a fragment navigator component
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}