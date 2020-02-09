package com.erel.movies.base

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.erel.movies.R
import kotlinx.android.synthetic.main.layout_base_activity.*

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    open val layout = R.layout.layout_base_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, getFragmentInstance())
                commit()
            }
        }
    }

    fun setProgress(visible: Boolean) {
        groupBaseProgress.visibility = if (visible) VISIBLE else GONE
    }

    abstract fun getFragmentInstance(): BaseFragment


}