package com.example.bmstu_project;

import static com.example.bmstu_project.SecondPart.stringToHex;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bmstu_project.databinding.ActivityThirdPartBinding;

public class ThirdPart extends AppCompatActivity implements TransactionEvents {

    private ActivityThirdPartBinding binding;
    private String pin;
    private ActivityResultLauncher activityResultLauncher;
    public native boolean transaction(byte[] trd);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdPartBinding.inflate(getLayoutInflater());
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(()-> {
                    try {
                        byte[] trd = stringToHex("9F0206000000000100");
                        transaction(trd);
                    } catch (Exception ex) {
                        Log.d("!!!", ex.getMessage());
                    }
                }).start();
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
                            // обработка результата
                            //String pin = data.getStringExtra("pin");
                            //Toast.makeText(MainActivity.this, pin, Toast.LENGTH_SHORT).show();
                            pin = data.getStringExtra("pin");
                            synchronized (ThirdPart.this) {
                                ThirdPart.this.notifyAll();
                            }
                        }
                    }
                });

        setContentView(binding.getRoot());
    }

    @Override
    public String enterPin(int ptc, String amount) {
        pin = "";
        Intent it = new Intent(ThirdPart.this, PinpadActivity.class);
        it.putExtra("ptc", ptc);
        it.putExtra("amount", amount);
        synchronized (ThirdPart.this) {
            activityResultLauncher.launch(it);
            try {
                ThirdPart.this.wait();
            } catch (Exception ex) {
                Log.d("!!!", ex.getMessage());
            }
        }
        return pin;
    }

    @Override
    public void transactionResult(boolean result) {
        runOnUiThread(()-> {
            Toast.makeText(ThirdPart.this, result ? "ok" : "failed", Toast.LENGTH_SHORT).show();
        });
    }
}