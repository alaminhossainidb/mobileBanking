package com.example.mobilebanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilebanking.model.Balance;
import com.example.mobilebanking.model.Transactions;
import com.example.mobilebanking.util.DBConnection;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMoneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        EditText acNumber = findViewById(R.id.txtACNumber);
        EditText amount = findViewById(R.id.AmountAdd);
        EditText pass = findViewById(R.id.AddMoneyPassword);
        Button add = findViewById(R.id.btnAdd);


        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("E, d/MM/yy 'at' h:m:s");

        DBConnection dbc = new DBConnection(AddMoneyActivity.this);
        Balance b = dbc.getBalance();
        Integer balance = b.getBalance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer amnt = Integer.valueOf(amount.getText().toString());
                Transactions t = new Transactions();
                t.setAccount(acNumber.getText().toString());
                t.setTime(format.format(date));
                t.setAmount(amnt);
                t.setStatus("Add Money");
                t.setCurrentBalance(balance+amnt);

                Balance bl = new Balance();
                bl.setBalance(balance+amnt);

                dbc.updateBalance(bl);
               Long l = dbc.addMoney(t);

               if (l>0){
                   Intent i = new Intent(AddMoneyActivity.this, SuccessActivity.class);
                   i.putExtra("msg",t.getStatus()+" "+"Successful");
                   i.putExtra("currentBalance",t.getCurrentBalance());
                   startActivity(i);
               }else {
                   Toast.makeText(AddMoneyActivity.this, "Add Money Failed.!!!!", Toast.LENGTH_SHORT).show();
               }

            }
        });
    }
}