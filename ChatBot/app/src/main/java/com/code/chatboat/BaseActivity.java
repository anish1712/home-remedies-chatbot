package com.code.chatboat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    protected T binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        setContentView(binding.getRoot());
        initData();
    }

    protected abstract T getViewBinding();

    protected abstract void initData();

}