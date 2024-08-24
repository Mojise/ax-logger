package com.mojise.library.util.log.app

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.mojise.library.util.log.Logs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        foo()
    }

    private fun foo() {
//
//        Logs.d("MainActivity foo")
//        Logs.d(arrayOf("1", "2"))
//        Logs.d(arrayOf(1, 2))
//        Logs.d()
//        Logs.withMethodStackTrace(5)
//            .d("MainActivity foo")
//
//        Logs.simpleStyle()
//            .visibleAlways()
//            .withMethodStackTrace(2)
//            .e("MainActivity foo")

        Logs.d("MainActivity foo")

//        try {
//            throw RuntimeException("MainActivity foo")
//        } catch (e: Exception) {
//            Logs.w(e)
//            e.printStackTrace()
//        }

//        Logs.visibleAlways()
//            .withThreadInfo()
//            .withMethodStack()
//            .tag("MainActivity")
//            .d("MainActivity foo")
//
//        Logs.d("MainActivity foo")
//
//        Logs.visibleAlways()
//            .d("MainActivity foo")
//
//        Logs.boxStyle()
//            .d("MainActivity foo")
//
//        Logs.simpleStyle()
//            .visibleAlways()
//            .e("SimpleStyle MainActivity foo")
//
//        Logs.d("MainActivity foo")
//
//        bar {
//            Logs.d("MainActivity foo bar")
//        }

//        Logs.simpleStyle()
//            .withMethodStackTrace(5)
//            .d("MainActivity foo")

//        lifecycleScope.launch(Dispatchers.Main) {
//            //Logs.d("MainActivity foo lifecycleScope.launch")
//            Logs.withMethodStackTrace(Int.MAX_VALUE)
//                .d("MainActivity foo lifecycleScope.launch")
//        }
//        lifecycleScope.launch(Dispatchers.Default) {
//            Logs.d("MainActivity foo lifecycleScope.launch")
//        }
//        lifecycleScope.launch(Dispatchers.IO) {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
//                Logs.d("MainActivity foo lifecycleScope.launch repeatOnLifecycle")
//            }
//        }
//        Thread {
//            Logs.d("MainActivity foo Thread")
//        }.start()
//        timer(period = 1) {
//            cancel()
//            Logs.d("MainActivity foo timer")
//        }
        foo1()
    }

    private fun bar(action: () -> Unit) {
        action()
    }

    private fun foo1() = foo2()
    private fun foo2() = foo3()
    private fun foo3() = foo4()
    private fun foo4() = foo5()
    private fun foo5() = Logs.d("MainActivity foo5")
}