package com.example.bmstu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bmstu_project.databinding.ActivityMainBinding;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static native byte[] randomBytes(int no);
    public static native int initRng();

    static {
        System.loadLibrary("bmstu_project");
        System.loadLibrary("mbedcrypto");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        initRng();
        binding.firstPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(FirstPart.class);
            }
        });
        binding.secondPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(SecondPart.class);
            }
        });
        binding.thirdPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(ThirdPart.class);
            }
        });
        binding.fourthPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(FourthPart.class);
            }
        });
        setContentView(binding.getRoot());
    }

    public void navigateTo(Class part){
        Intent intent = new Intent(this, part);
        startActivity(intent);
    }

}