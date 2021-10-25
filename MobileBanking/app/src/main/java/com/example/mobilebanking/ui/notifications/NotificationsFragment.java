package com.example.mobilebanking.ui.notifications;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilebanking.MainActivity;
import com.example.mobilebanking.R;
import com.example.mobilebanking.databinding.FragmentNotificationsBinding;
import com.example.mobilebanking.model.Transactions;
import com.example.mobilebanking.util.DBConnection;

import java.util.List;

public class NotificationsFragment extends Fragment implements View.OnClickListener{

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                addHeaders();
                addData();

                //textView.setText("Hello");
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {

    }

    // Table View

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(getContext());
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        return tv;
    }

    @NonNull
    public TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    public TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {
        TableLayout tl = binding.table;
        TableRow tr = new TableRow(getView().getContext());
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "DATE", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "STATUS", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "AMOUNT", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tl.addView(tr, getTblLayoutParams());
    }

    /**
     * This function add the data to the table
     **/
    public void addData() {

        TableLayout tl = binding.table;
        DBConnection db = new DBConnection(getView().getContext());
        List<Transactions> t = db.getAllTransactions();

        for (int i = 0; i < t.size(); i++) {
           
           TableRow tr = new TableRow(getView().getContext());
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i, String.valueOf(t.get(i).getTime()), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getView().getContext(), R.color.purple_200)));
            if (t.get(i).getStatus().equals("Add Money")){
                tr.addView(getTextView(i, t.get(i).getStatus(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getView().getContext(), R.color.green)));
                tr.addView(getTextView(i, String.valueOf(t.get(i).getAmount())+" Tk" , Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getView().getContext(), R.color.green)));
            }else{
                tr.addView(getTextView(i, t.get(i).getStatus(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getView().getContext(), R.color.red)));
                tr.addView(getTextView(i, String.valueOf(t.get(i).getAmount()+" Tk") , Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(getView().getContext(), R.color.red)));
            }

            tl.addView(tr, getTblLayoutParams());
        }
    }
}