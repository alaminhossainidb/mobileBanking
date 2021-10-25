package com.example.mobilebanking.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBindings;

import com.example.mobilebanking.AddMoneyActivity;
import com.example.mobilebanking.ConfirmActivity;
import com.example.mobilebanking.MainActivity;
import com.example.mobilebanking.R;
import com.example.mobilebanking.SendMoney;
import com.example.mobilebanking.databinding.FragmentHomeBinding;
import com.example.mobilebanking.model.Balance;
import com.example.mobilebanking.ui.notifications.NotificationsFragment;
import com.example.mobilebanking.util.DBConnection;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               View send = binding.sendMoney;
               View cOut = binding.cashOut;
               View add = binding.addMoney;
               View mobile = binding.mobileRecharge;
               TextView balance = binding.balance;

               // Getting Balance
                DBConnection dbc = new DBConnection(getView().getContext());

               balance.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Balance cb = dbc.getBalance();
                       String blnc = String.valueOf(cb.getBalance());
                       balance.setText(blnc+" Tk");
                   }
               });

               send.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent i = new Intent(view.getContext(), SendMoney.class);
                       i.putExtra("header", "SEND MONEY");
                       i.putExtra("status","Send Money");
                       startActivity(i);
                   }
               });

                cOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), SendMoney.class);
                        i.putExtra("header", "CASH OUT");
                        i.putExtra("status","Cash Out");
                        startActivity(i);
                    }
                });
                mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), SendMoney.class);
                        i.putExtra("header", "MOBILE RECHARGE");
                        i.putExtra("status","Mobile Rcharge");
                        startActivity(i);
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), AddMoneyActivity.class);
                        startActivity(i);
                    }
                });

                //textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}