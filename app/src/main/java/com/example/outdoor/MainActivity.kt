package com.example.outdoor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.outdoor.fragments.RentalListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
                R.id.mainFrameLayout,
            RentalListFragment(), "rentalList"
        )

        transaction.commit()
    }
}