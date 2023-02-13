package com.example.bmstu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.bmstu_project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'bmstu_project' library on application startup.
    static {
        System.loadLibrary("bmstu_project");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'bmstu_project' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}