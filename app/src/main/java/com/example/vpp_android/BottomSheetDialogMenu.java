package com.example.vpp_android;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import savingdata_class.Account;

public class BottomSheetDialogMenu extends BottomSheetDialogFragment {
    TextView logout;
    View view;

    BottomSheetDialogMenu() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(v->{
            SharedPreferences settings = getContext().getSharedPreferences(Account.getFILE(), 0);
            SharedPreferences.Editor editor = settings.edit();
            SharedPreferences sp = getContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
            String name = sp.getString("user_name", "");

            if (name.equals("")){
                Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_SHORT).show();
            }else
            {
            editor.clear();
            editor.commit();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(view.getContext(), "User logged out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
