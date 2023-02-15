package com.example.bmstu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bmstu_project.databinding.ActivityMainBinding;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    public static native int initRng();

    public static native byte[] randomBytes(int no);

    public static native byte[] encrypt(byte[] key, byte[] data);

    public static native byte[] decrypt(byte[] key, byte[] data);
    public native String stringFromJNI();

    // Used to load the 'bmstu_project' library on application startup.
    static {
        System.loadLibrary("bmstu_project");
        System.loadLibrary("mbedcrypto");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int res = initRng();
        byte[] text = "Hello max".getBytes(StandardCharsets.UTF_8);

        TextView tvEncoded = binding.tvEncoded;
        TextView tvDecoded = binding.tvDecoded;
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] key = randomBytes(16);
                byte[] encryptByteArray = encrypt(key,text);
                String encrypt = new String(encryptByteArray, StandardCharsets.UTF_8);
                String decrypt = new String(decrypt(key, encryptByteArray), StandardCharsets.UTF_8);
                tvEncoded.setText(encrypt);
                tvDecoded.setText(decrypt);
            }
        });
    }

    /**
     * A native method that is implemented by the 'bmstu_project' native library,
     * which is packaged with this application.
     */

}