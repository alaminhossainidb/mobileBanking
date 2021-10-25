package com.example.mobilebanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilebanking.model.Transactions;
import com.example.mobilebanking.util.DBConnection;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SendMoney extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        EditText nmbr = findViewById(R.id.txtNumber);
        EditText amount = findViewById(R.id.txtAmount);
        Button send = findViewById(R.id.btnSendNext);
        Bundle b = getIntent().getExtras();


        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("E, d/MM/yy 'at' h:m:s");

        DBConnection dbc = new DBConnection(SendMoney.this);

        int cb = dbc.getBalance().getBalance();

        TextView t = findViewById(R.id.txtHeader);
        t.setText(b.get("header").toString());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer amnt = Integer.valueOf(amount.getText().toString());

                if (amnt>cb-50){
                    Toast.makeText(SendMoney.this, "Your Current Balance is : "+cb, Toast.LENGTH_SHORT).show();
                }else if(nmbr.getText().length()<11){
                    Toast.makeText(SendMoney.this, "Wrong Mobile Number !!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(SendMoney.this, ConfirmActivity.class);
                    i.putExtra("ac",nmbr.getText().toString());
                    i.putExtra("money", amnt);
                    i.putExtra("status",b.get("status").toString());
                    i.putExtra("date",format.format(date));
                    startActivity(i);
                }


            }
        });
    }
}