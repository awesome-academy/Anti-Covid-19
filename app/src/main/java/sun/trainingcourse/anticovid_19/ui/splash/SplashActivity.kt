package sun.trainingcourse.anticovid_19.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sun.trainingcourse.anticovid_19.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.getIntent(this))
        finish()
    }
}
