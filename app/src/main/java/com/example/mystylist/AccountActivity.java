package com.example.mystylist;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mystylist.closet_activity.ClosetActivity;
import com.example.mystylist.enums.EColor;
import com.example.mystylist.enums.EItemType;
import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Profile;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class AccountActivity extends AppCompatActivity {
    TextView AccountName;
    Button editAccountButton, outfitsButton, closetButton, favoritesButton, changeAccountButton;
    private ConstraintLayout layout;
    public static List<String> OutfitArr = new ArrayList<>();

    public static String profileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        layout = findViewById(R.id.constraint_layout);
        AccountName = findViewById(R.id.AccountName);
        editAccountButton = findViewById(R.id.editButton);
        outfitsButton = findViewById(R.id.outfitsButton);
        closetButton = findViewById(R.id.closetButton);
        favoritesButton = findViewById(R.id.favoritesButton);
        changeAccountButton = findViewById(R.id.change_account_button);

        Log.d("AccountActivity", "Account: " + LoginActivity.activeAccount.toString());

        Database.getProfiles(LoginActivity.activeAccount.getUsername(), new Function<Profile, Void>() {
            @Override
            public Void apply(Profile profile) {
                if (profileName == null) {
                    selectProfile(profile);
                    updateProfileData();
                }
                return null;
            }
        });

        AccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SelectProfileActivity.class);
                startActivity(intent);
            }
        });

        outfitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOutfitPopup();
            }
        });


        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, EditAccountActivity.class);
                startActivity(intent);
            }
        });

        closetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Added for presentation
                Intent intent = new Intent(AccountActivity.this, ClosetActivity.class);
                startActivity(intent);
            }
        });

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        changeAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfileData();
    }

    public void showOutfitPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_outfit_description, null);
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        Spinner type_spinner = popupView.findViewById(R.id.type_spinner);
        Spinner color_spinner = popupView.findViewById(R.id.color_spinner);
        Button button_cancel = popupView.findViewById(R.id.button_cancel);
        Button button_accept = popupView.findViewById(R.id.button_accept);
        Button button_search_all = popupView.findViewById(R.id.button_search_all);

        ArrayAdapter<EItemType> item_adapter = new ArrayAdapter<>(this, R.layout.spinner_row_clothing_type, EItemType.values());
        type_spinner.setAdapter(item_adapter);

        ArrayAdapter<EColor> color_adapter = new ArrayAdapter<>(this, R.layout.spinner_row_clothing_color, EColor.values());
        color_spinner.setAdapter(color_adapter);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from spinners
                EItemType item_type = EItemType.values()[type_spinner.getSelectedItemPosition()];
                EColor color = EColor.values()[color_spinner.getSelectedItemPosition()];

                // Create item from values
                Item item = new Item(item_type, color);

                Intent intent = new Intent(AccountActivity.this, OutfitActivity.class);
                OutfitActivity.clearFilters();
                OutfitActivity.filterItems = new LinkedList<Item>() { { add(item); } };
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        button_search_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, OutfitActivity.class);
                OutfitActivity.clearFilters();
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, -100);
    }

    public void updateProfileData() {
        AccountName.setText(profileName);
    }

    public static void selectProfile(Profile profile) {
        profileName = profile.getName();
        Log.d("AccountActivity", "Activity selected: " + profile.getName());
    }

    @Override
    public void finish() {
        super.finish();
        profileName = null;
    }
}
