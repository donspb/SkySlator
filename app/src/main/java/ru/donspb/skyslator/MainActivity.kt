package ru.donspb.skyslator

import androidx.appcompat.app.AppCompatActivity
import moxy.MvpAppCompatActivity
import android.os.Bundle
import moxy.ktx.moxyPresenter
import ru.donspb.skyslator.databinding.ActivityMainBinding
import ru.donspb.skyslator.presenter.MainPresenter
import ru.donspb.skyslator.view.MainFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit()
        }
    }
}