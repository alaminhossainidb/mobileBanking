package com.example.mobilebanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilebanking.model.Balance;
import com.example.mobilebanking.model.Transactions;
import com.example.mobilebanking.util.DBConnection;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        TextView acView = findViewById(R.id.msg);
        TextView amnt = findViewById(R.id.txtAmountConfirm);
        TextView chrg = findViewById(R.id.txtCharge);
        TextView currentBalance = findViewById(R.id.txtCurrentbalance);
        EditText pass = findViewById(R.id.passConfirm);
        Button confirm = findViewById(R.id.btnConfirm);
        Button cancel = findViewById(R.id.btnCancel);

        // Getting Bundle
        Bundle b = getIntent().getExtras();

        String account = b.getString("ac");
        int amount = (int) b.get("money");
        String date = b.getString("date");
        String status = b.getString("status");

        //Getting Balance
        DBConnection dbc = new DBConnection(ConfirmActivity.this);

        Balance bl = dbc.getBalance();

        int chargesAmount = (int) (amount*.015);
        int cb = bl.getBalance()-(amount+chargesAmount);

        acView.setText(status+" to : "+account);
        amnt.setText(String.valueOf(amount));
        if(status.equals("Mobile Rcharge")){
            chrg.setText("0");
        }else{
            chrg.setText(String.valueOf(chargesAmount));
        }
        currentBalance.setText(String.valueOf(cb));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transactions tr = new Transactions();
                tr.setAccount(account);
                tr.setStatus(status);
                tr.setTime(date);
                tr.setCurrentBalance(cb);
                tr.setAmount(amount);

                Balance balance = new Balance();
                balance.setBalance(cb);

                if(pass.getText().toString().equals("123456")){

                  Long l = dbc.addMoney(tr);
                    if (l>0){
                       dbc.updateBalance(balance);
                       Intent i = new Intent(ConfirmActivity.this, SuccessActivity.class);
                       i.putExtra("msg",tr.getStatus()+" "+"Successful");
                        i.putExtra("currentBalance",tr.getCurrentBalance());
                       startActivity(i);
                    }else{
                       Toast.makeText(ConfirmActivity.this, "Failed!!!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ConfirmActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfirmActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}