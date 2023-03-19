package com.example.bmstu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bmstu_project.databinding.ActivityPinpadBinding;

import java.util.Arrays;

public class PinpadActivity extends AppCompatActivity {

    private ActivityPinpadBinding binding;
    private TextView tvPin;
    String pin = "";

    private static final int MAX_KEYS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinpadBinding.inflate(getLayoutInflater());
        tvPin = binding.txtPin;
        ShuffleKeys();
        binding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.putExtra("pin", pin);
                setResult(RESULT_OK, it);
                finish();
            }
        });
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pin = "";
                tvPin.setText("");
            }
        });
        setContentView(binding.getRoot());
    }

    public void keyClick(View view) {
        String key = ((TextView)view).getText().toString();
        int sz = pin.length();
        if (sz < 4)
        {
            pin += key;
            tvPin.setText("****".substring(3 - sz));
        }
    }

    protected void ShuffleKeys()
    {
        Button[] keys = new Button[] {
                binding.btnKey0,
                binding.btnKey1,
                binding.btnKey2,
                binding.btnKey3,
                binding.btnKey4,
                binding.btnKey5,
                binding.btnKey6,
                binding.btnKey7,
                binding.btnKey8,
                binding.btnKey9,
        };

        byte[] rnd = MainActivity.randomBytes(MAX_KEYS);
        for(int i = 0; i < MAX_KEYS; i++)
        {
            int idx = (rnd[i] & 0xFF) % 10;
            CharSequence txt = keys[idx].getText();
            keys[idx].setText(keys[i].getText());
            keys[i].setText(txt);
        }
    }
}