package com.code.chatboat;

import android.content.Intent;
import android.view.View;

import com.code.chatboat.activity.ChatActivity;
import com.code.chatboat.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        binding.btnStartGuj.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra("type","Levenshtein").putExtra("typeLanguage","eng"));
        });
        binding.btnStartNGramGuj.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra("type","NGram").putExtra("typeLanguage","eng"));
        });
        binding.btnStartNGramGuj.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra("type","NGram").putExtra("typeLanguage","guj")));
        binding.btnStartGuj.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra("type","Levenshtein").putExtra("typeLanguage","guj"));
        });
    }

}