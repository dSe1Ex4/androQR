package x.cross.androqr.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import x.cross.androqr.databinding.ActivityDetailBinding
import x.cross.androqr.databinding.ActivitySplashBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var view: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(view.root)
    }
}