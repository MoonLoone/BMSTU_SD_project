package com.example.bmstu_project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class SecondPart extends AppCompatActivity {

    public static native byte[] encrypt(byte[] key, byte[] data);
    public static native byte[] decrypt(byte[] key, byte[] data);
    private com.example.bmstu_project.databinding.ActivitySecondPartBinding binding;
    private ActivityResultLauncher activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.bmstu_project.databinding.ActivitySecondPartBinding.inflate(getLayoutInflater());
        binding.showInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TDEA();
            }
        });
        binding.startPinpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPinpad();
            }
        });
        activityResultLauncher  = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback() {
                    @Override
                    public void onActivityResult(Object result) {
                        ActivityResult res = (ActivityResult)result;
                        if (res.getResultCode() == Activity.RESULT_OK) {
                            Intent data = res.getData();
                            String pin = data.getStringExtra("pin");
                            Toast.makeText(SecondPart.this, pin, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        setContentView(binding.getRoot());
    }

    public void TDEA(){
        byte[] key = stringToHex("0123456789ABCDEF0123456789ABCDE0");
        byte[] enc = encrypt(key, stringToHex("000000000000000102"));
        byte[] dec = decrypt(key, enc);
        String s = new String(Hex.encodeHex(dec)).toUpperCase();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void navigateToPinpad(){
        Intent intent = new Intent(this, PinpadActivity.class);
        activityResultLauncher.launch(intent);
    }

    public static byte[] stringToHex(String s)
    {
        byte[] hex;
        try
        {
            hex = Hex.decodeHex(s.toCharArray());
        }
        catch (DecoderException ex)
        {
            hex = null;
        }
        return hex;
    }

}