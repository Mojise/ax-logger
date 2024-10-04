package com.mojise.library.util.log.app

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        testJsonStringLog()
    }

    private fun foo() {
        Logs.d()
        Logs.d("")
        Logs.d(listOf(1, 2))
        Logs.d("안녀어엉 %")
        Logs.d("안녀어엉 %s")

        listOf(1,2,3).forEach {
            Logs.boxStyle().d("안녀어엉 %s")
        }

        Logs.d("MainActivity foo")
        Logs.d(arrayOf("1", "2"))
        Logs.d(arrayOf(1, 2))
        Logs.d()
        Logs.withMethodStackTrace(5)
            .d("MainActivity foo")

        Logs.v()
        Logs.v("MainActivity foo")
        Logs.format("abcd=%d", 100)
        Logs.format("abcd=%d")

        Logs.simpleStyle()
            .w("hihi")

        Logs.json("""{ "key": "value" }""", 4) // JSON 출력 (indentWidth = 4)



        try {
            val a = 1 / 0
        } catch (e: Exception) {
            Logs.throwable("Throwable with log message", e)
        }
//
        try {
            val arr = mutableListOf<Int>()
            arr[3]
        } catch (e: Exception) {
            Logs.throwable(e)
        }
        try {
            val arr = mutableListOf<Int>()
            arr[3]
        } catch (e: Exception) {
            Logs.boxStyle().throwable(e)
            Logs.boxStyle().throwable("Throwable with log message !!", e)
        }
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

    private fun testJsonStringLog() {
        val jsonStrings = listOf(
            // 간단한 JSON 예시
            """{"name":"Alice","age":25,"city":"London"}""",
            // 배열이 포함된 JSON
            """{"name":"Bob","hobbies":["reading", "gaming", "hiking"]}""",
            // 중첩된 JSON
            """{"company":{"name":"TechCorp","employees":[{"name":"Carol","role":"Engineer"},{"name":"David","role":"Manager"}]}}""",
            // 배열로 시작하는 JSON
            """[{"name":"Eve","role":"Analyst"},{"name":"Frank","role":"Designer"}]""",
            // 빈 JSON 객체
            """{}""",
            // 빈 배열
            """[]""",
            // 복잡한 중첩 구조
            """{"project":{"name":"SpaceX","mission":"Mars","details":{"launchDate":"2025","crew":[{"name":"Alex","role":"Pilot"},{"name":"Jen","role":"Scientist"}]}}}""",
            // 복잡한 중첩 구조
            """{"project":{"name":"SpaceX","mission":"Mars","details":{"launchDate":"2025","crew":[]}}}""",
            // 복잡한 중첩 구조
            """{"project":{"name":"SpaceX","mission":"Mars","details":{"launchDate":"2025""crew":[{"name":"Alex","role":"Pilot"},{"name":"Jen","role":"Scientist"}]}}}""",
            // JSON 형식에 맞지 않는 문자열 (쉼표가 없음)
            """{"name":"Grace" "age":22}""",
            // JSON 형식에 맞지 않는 문자열 (키가 문자열이 아님)
            """{name:"Henry","age":35}""",
            // JSON 형식에 맞지 않는 문자열 (중괄호가 닫히지 않음)
            """{"name":"Ivy","age":30}""",
            // 중복된 문자열 내 이스케이프 문자
            """{"quote":"She said, \"Hello!\""}""",
            // 숫자와 불리언 포함
            """{"temperature":23.5,"active":true,"warnings":null}""",
            // 복잡한 이스케이프 문자와 공백
            """{"message":"This is a line.\nThis is another line.\tTabbed!"}""",

            // JSON 형식에 맞지 않음 (쉼표 없음)
            """{"name":"Grace" "age":22}""",
            // JSON 형식에 맞지 않음 (키가 따옴표로 감싸지 않음)
            """{name:"Henry","age":35}""",
            // JSON 형식에 맞지 않음 (중괄호가 닫히지 않음)
            """{"name":"Ivy","age":30""",
            // JSON 형식에 맞지 않음 (':'이 없음)
            """{"name":"Ivy" "age"30}""",
        )
        jsonStrings.forEach { jsonString ->
            Logs.json(jsonString, 4)
//            Logs.boxStyle().json(jsonString)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            Logs.json(jsonStrings[0])
            Logs.boxStyle().json(jsonStrings[0])

            Logs.json(null, jsonStrings[0])
            Logs.json("", jsonStrings[0])
            Logs.json(Pair("first", "second"), jsonStrings[0])
            Logs.json("json with message", jsonStrings[0])
            Logs.boxStyle()
                .json("json with message", jsonStrings[0])
        }, 2000)
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
