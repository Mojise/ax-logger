package com.mojise.library.util.log.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mojise.library.util.log.Logs

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

//        Logs.d("savedInstanceState=$savedInstanceState")

        foo()
    }

    private fun foo() {
        Logs.d()
        Logs.d("")
        Logs.d(listOf(1, 2))

        Logs.d("MainActivity foo")
        Logs.d(arrayOf("1", "2"))
        Logs.d(arrayOf(1, 2))
        Logs.d()
        Logs.withMethodStackTrace(5)
            .d("MainActivity foo")

        Logs.v()
        Logs.v("MainActivity foo")
        Logs.v("abcd=%d", 100)

        Logs.simpleStyle()
            .w("hihi")

//        try {
//            val a = 1 / 0
//        } catch (e: Exception) {
//            Logs.throwable("Error 1!!!", e)
//        }
//
//        try {
//            val arr = mutableListOf<Int>()
//            arr[3]
//        } catch (e: Exception) {
//            Logs.throwable(e)
//        }
//        try {
//            val arr = mutableListOf<Int>()
//            arr[3]
//        } catch (e: Exception) {
//            Logs.simpleStyle().throwable(e)
//        }
//
//        Logs.i("abcd=%d", 100)

        //Log.d("", listOf(1, 2))
//
//        Logs.simpleStyle()
//            .visibleAlways()
//            .withMethodStackTrace(2)
//            .e("MainActivity foo")
//
//        Logs.w("MainActivity foo")
//        Logs.d("MainActivity foo \nabcd")
//        Logs.simpleStyle()
//            .visibleForced()
//            .withThreadInfo()
//            .withMethodStackTrace(5)
//            .tag("MainActivity")
//            .v("Hello World!!")
//
//        logd("hihihihihi")
//        Logs.withoutThreadInfo()
//            .withMethodStackTrace(5)
//            .v("Logs.withoutThreadInfo()\n" +
//                    "    .withMethodStackTrace(5)".repeat(5000))
//
//        Log.d("TAG", "    .withMethodStackTrace(5)".repeat(5000))
//
//        try {
//            throw RuntimeException("Hello World!!")
//        } catch (e: Exception) {
//            //Log.w("TAG", "Hello RuntimeException", e)
//            Logs.w(e)
//            e.printStackTrace()
//        }

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
//            Logs.boxStyle()
//                .d("MainActivity foo lifecycleScope.launch")
//        }
//        lifecycleScope.launch(Dispatchers.Default) {
//            Logs.boxStyle()
//                .d("MainActivity foo lifecycleScope.launch")
//        }
//        lifecycleScope.launch(Dispatchers.IO) {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
//                Logs.boxStyle()
//                    .d("MainActivity foo lifecycleScope.launch repeatOnLifecycle")
//            }
//        }
//        Thread {
//            Logs.boxStyle()
//                .d("MainActivity foo Thread")
//        }.start()
//        timer(period = 1) {
//            cancel()
//            Logs.boxStyle()
//                .withMethodStackTrace()
//                .d("MainActivity foo timer")
//        }
        //foo1()
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