package com.example.outdoor

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colorDrawable = ColorDrawable(Color.parseColor("#FF0000"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
                R.id.mainFrameLayout,
                RentalListFragment(), "rentalList"
        )

        transaction.commit()
    }
}