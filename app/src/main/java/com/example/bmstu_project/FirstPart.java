package com.example.bmstu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bmstu_project.databinding.ActivityMainBinding;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FirstPart extends AppCompatActivity {



    public static native byte[] encrypt(byte[] key, byte[] data);

    public static native byte[] decrypt(byte[] key, byte[] data);
    public native String stringFromJNI();
    private com.example.bmstu_project.databinding.ActivityFirstPartBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.bmstu_project.databinding.ActivityFirstPartBinding.inflate(getLayoutInflater());
        stringFromJNI();
        int res = MainActivity.initRng();
        byte[] text = "Hello max".getBytes(StandardCharsets.UTF_8);
        TextView tvEncoded = binding.tvEncoded;
        TextView tvDecoded = binding.tvDecoded;
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] key = MainActivity.randomBytes(16);
                byte[] encryptByteArray = encrypt(key,text);
                String encrypt = new String(encryptByteArray, StandardCharsets.UTF_8);
                String decrypt = new String(decrypt(key, encryptByteArray), StandardCharsets.UTF_8);
                tvEncoded.setText(encrypt);
                tvDecoded.setText(decrypt);
            }
        });
        setContentView(binding.getRoot());
    }
}