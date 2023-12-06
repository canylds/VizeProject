package com.example.vizeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import android.widget.Toast;

public class SmsActivity extends AppCompatActivity {

    EditText et_phone, et_message;
    Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        et_phone = findViewById(R.id.et_phone);
        et_message = findViewById(R.id.et_message);
        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SmsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SmsActivity.this, new String[] {Manifest.permission.SEND_SMS}, 100);
                }
                else {
                    String phone = et_phone.getText().toString();
                    String message = et_message.getText().toString();
                    if (!(phone == null || message == null)) {
                        sendMessage(phone, message);
                    }
                    else {
                        Toast.makeText(SmsActivity.this, "Tel ve mesaj alanları boş girilemez.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendMessage(String phone, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message, null ,null);
        Toast.makeText(SmsActivity.this, "Mesaj gönderildi.", Toast.LENGTH_SHORT).show();
    }
}