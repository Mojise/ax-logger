package com.mojise.library.util.log.app;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mojise.library.util.log.Logs;

import java.util.ArrayList;
import java.util.List;

public class MainActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logs.d();
        Logs.d("Hello World");
        Logs.d("Hello World a=%d, b=%d", 1);
//        Logs.visibleAlways()
//                .withThreadInfo()
//                .withMethodStack()
//                .d("");

        List<String> permissions = new ArrayList<>();
        Logs.d(permissions);
    }
}
