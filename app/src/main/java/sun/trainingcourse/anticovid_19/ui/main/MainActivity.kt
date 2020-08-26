package sun.trainingcourse.anticovid_19.ui.main

import android.content.Context
import android.content.Intent
import sun.trainingcourse.anticovid_19.R
import sun.trainingcourse.anticovid_19.base.BaseActivity

class MainActivity : BaseActivity() {
    override val layoutResource: Int get() = R.layout.activity_main

    companion object {
        fun getIntent(context: Context) =
            Intent(context, MainActivity::class.java)
    }
}
