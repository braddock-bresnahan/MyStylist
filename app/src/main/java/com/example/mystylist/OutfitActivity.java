package com.example.mystylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;
import com.example.mystylist.enums.ETag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class OutfitActivity extends AppCompatActivity implements IOutfitRecyclerHome {

    public ConstraintLayout layout;
    ImageButton backButton;

    Button btnApplySelections;

    //weather checkboxes
    CheckBox weather_hot_checkbox;
    CheckBox weather_cold_checkbox;
    CheckBox weather_fair_checkbox;
    CheckBox weather_rainy_checkbox;

    //gender checkboxes
    CheckBox gender_neutral_checkbox;
    CheckBox gender_masculine_checkbox;
    CheckBox gender_feminine_checkbox;

    //style checkboxes
    CheckBox style_casual_checkbox;
    CheckBox style_smartcasual_checkbox;
    CheckBox style_businesscasual_checkbox;
    CheckBox style_businessprofessional_checkbox;
    CheckBox style_semiformal_checkbox;
    CheckBox style_formal_checkbox;

    //season checkboxes
    CheckBox season_spring_checkbox;
    CheckBox season_winter_checkbox;
    CheckBox season_fall_checkbox;
    CheckBox season_summer_checkbox;

    public Button filterButton;

    private RecyclerView recyclerView;
    private OutfitItemAdapter adapter;

    private List<Outfit> favoritedOutfits;

    private ArrayList<Outfit> outfits;
    public static List<ETag> selectedTags = new LinkedList<ETag>();

    public static List<Outfit> givenOutfits = null;
    public static List<Item> filterItems = null;
    public static Long filterTags = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);

        backButton = findViewById(R.id.back_button);
        filterButton = findViewById(R.id.filter_button);

        recyclerView = findViewById(R.id.list_of_filtered);
        outfits = new ArrayList<>();
        adapter = new OutfitItemAdapter(outfits, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        favoritedOutfits = new LinkedList<>();
        selectedTags = new LinkedList<>();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterPopup();
            }
        });

        // Decide how to load outfits
        if (givenOutfits != null) {
            // Use the outfit(s) given to you.
            outfits = new ArrayList<>(givenOutfits);
        }
        else if (filterItems != null) {
            // Use the outfits from the database that match the items
            Database.getOutfitsMatching(filterItems, new receiveOutfitCallback());
        }
        else if (filterTags != null) {
            // Use the outfits from the database that match the filter
            Database.getOutfitsMatching(filterTags, new receiveOutfitCallback());
        }
        else {
            // Use every outfit from the database
            Database.getOutfits(new receiveOutfitCallback());
        }

        Database.getFavoritedOutfits(LoginActivity.activeAccount.getUsername(), AccountActivity.profileName, new Function<Outfit, Void>() {
            @Override
            public Void apply(Outfit outfit) {
                favoritedOutfits.add(outfit);
                updateFavorites();
                return null;
            }
        });
    }

    public void showFilterPopup() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_filter_outfits, null);
        int width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        btnApplySelections = popupView.findViewById(R.id.btnApplySelections2);

        weather_hot_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxHot);
        weather_cold_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxCold);
        weather_fair_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFair);
        weather_rainy_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxRainy);


        gender_neutral_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxNeutral);
        gender_masculine_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxMasculine);
        gender_feminine_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFeminine);

        style_casual_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxCasual);
        style_smartcasual_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxSmartCasual);
        style_businesscasual_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxBusinessCasual);
        style_businessprofessional_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxBusinessProfessional);
        style_semiformal_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxSemiFormal);
        style_formal_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFormal);

        season_spring_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxSpring);
        season_winter_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxWinter);
        season_fall_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxFall);
        season_summer_checkbox = (CheckBox)popupView.findViewById(R.id.checkboxSummer);

        for (ETag tag : selectedTags) {
            switch (tag) {
                case GENDER_NEUTRAL:
                    gender_neutral_checkbox.setChecked(true);
                    break;
                case GENDER_MASCULINE:
                    gender_masculine_checkbox.setChecked(true);
                    break;
                case GENDER_FEMININE:
                    gender_feminine_checkbox.setChecked(true);
                    break;

                case WEATHER_FAIR:
                    weather_fair_checkbox.setChecked(true);
                    break;
                case WEATHER_HOT:
                    weather_hot_checkbox.setChecked(true);
                    break;
                case WEATHER_COLD:
                    weather_cold_checkbox.setChecked(true);
                    break;
                case WEATHER_RAINY:
                    weather_rainy_checkbox.setChecked(true);
                    break;

                case STYLE_CASUAL:
                    style_casual_checkbox.setChecked(true);
                    break;
                case STYLE_SMART_CASUAL:
                    style_smartcasual_checkbox.setChecked(true);
                    break;
                case STYLE_BUSINESS_CASUAL:
                    style_businesscasual_checkbox.setChecked(true);
                    break;
                case STYLE_BUSINESS_PROFESSIONAL:
                    style_businessprofessional_checkbox.setChecked(true);
                    break;
                case STYLE_SEMI_FORMAL:
                    style_semiformal_checkbox.setChecked(true);
                    break;
                case STYLE_FORMAL:
                    style_formal_checkbox.setChecked(true);
                    break;

                case SEASON_SPRING:
                    season_spring_checkbox.setChecked(true);
                    break;
                case SEASON_SUMMER:
                    season_summer_checkbox.setChecked(true);
                    break;
                case SEASON_FALL:
                    season_fall_checkbox.setChecked(true);
                    break;
                case SEASON_WINTER:
                    season_winter_checkbox.setChecked(true);
                    break;
            }
        }

        weather_hot_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.WEATHER_HOT);
                }
                else {
                    selectedTags.remove(ETag.WEATHER_HOT);
                }
            }
        });

        weather_cold_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.WEATHER_COLD);
                }
                else {
                    selectedTags.remove(ETag.WEATHER_COLD);
                }
            }
        });

        weather_fair_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.WEATHER_FAIR);
                }
                else {
                    selectedTags.remove(ETag.WEATHER_FAIR);
                }
            }
        });

        weather_rainy_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.WEATHER_RAINY);
                }
                else {
                    selectedTags.remove(ETag.WEATHER_RAINY);
                }
            }
        });

        gender_neutral_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.GENDER_NEUTRAL);
                }
                else {
                    selectedTags.remove(ETag.GENDER_NEUTRAL);
                }
            }
        });

        gender_masculine_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.GENDER_MASCULINE);
                }
                else {
                    selectedTags.remove(ETag.GENDER_MASCULINE);
                }
            }
        });

        gender_feminine_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.GENDER_FEMININE);
                }
                else {
                    selectedTags.remove(ETag.GENDER_FEMININE);
                }
            }
        });

        season_fall_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.SEASON_FALL);
                }
                else {
                    selectedTags.remove(ETag.SEASON_FALL);
                }
            }
        });

        season_spring_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.SEASON_SPRING);
                }
                else {
                    selectedTags.remove(ETag.SEASON_SPRING);
                }
            }
        });

        season_summer_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.SEASON_SUMMER);
                }
                else {
                    selectedTags.remove(ETag.SEASON_SUMMER);
                }
            }
        });

        season_winter_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.SEASON_WINTER);
                }
                else {
                    selectedTags.remove(ETag.SEASON_WINTER);
                }
            }
        });

        style_casual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.STYLE_CASUAL);
                }
                else {
                    selectedTags.remove(ETag.STYLE_CASUAL);
                }
            }
        });

        style_smartcasual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.STYLE_SMART_CASUAL);
                }
                else {
                    selectedTags.remove(ETag.STYLE_SMART_CASUAL);
                }
            }
        });

        style_businesscasual_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.STYLE_BUSINESS_CASUAL);
                }
                else {
                    selectedTags.remove(ETag.STYLE_BUSINESS_CASUAL);
                }
            }
        });

        style_businessprofessional_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.STYLE_BUSINESS_PROFESSIONAL);
                }
                else {
                    selectedTags.remove(ETag.STYLE_BUSINESS_PROFESSIONAL);
                }
            }
        });

        style_semiformal_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.STYLE_SEMI_FORMAL);
                }
                else {
                    selectedTags.remove(ETag.STYLE_SEMI_FORMAL);
                }
            }
        });

        style_formal_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedTags.add(ETag.STYLE_FORMAL);
                }
                else {
                    selectedTags.remove(ETag.STYLE_FORMAL);
                }
            }
        });

        btnApplySelections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = outfits.size();
                outfits.clear();
                adapter.notifyItemRangeRemoved(0, size);
                filterTags = ETag.tagsToMask(selectedTags);
                Database.getOutfitsMatching(filterTags, new receiveOutfitCallback());
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);

    }

    private class receiveOutfitCallback implements Function<Outfit, Void> {
        @Override
        public Void apply(Outfit outfit) {
            OutfitActivity context = OutfitActivity.this;

            outfits.add(0, outfit);
            context.adapter.notifyItemInserted(0);
            Log.d("OutfitActivity", "Added outfit to list: " + outfit.toString());

            return null;
        }
    }

    private void updateFavorites() {
        for (int i = 0; i < outfits.size(); i++) {
            OutfitItemAdapter.ViewHolder holder = (OutfitItemAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (holder == null)
                continue;
            CheckBox checkbox = holder.getFavoriteCheckbox();
            if (favoritedOutfits.contains(outfits.get(i))) {
                checkbox.setChecked(true);
            }
            else {
                checkbox.setChecked(false);
            }
        }
    }

    public void favoriteOutfit(Outfit outfit) {
        if (!favoritedOutfits.contains(outfit)) {
            favoritedOutfits.add(outfit);
            Database.addFavoritedOutfit(LoginActivity.activeAccount.getUsername(), AccountActivity.profileName, outfit);
        }
    }

    public void unfavoriteOutfit(Outfit outfit) {
        if (favoritedOutfits.contains(outfit)) {
            favoritedOutfits.remove(outfit);
            Database.removeFavoritedOutfit(LoginActivity.activeAccount.getUsername(), AccountActivity.profileName, outfit);
        }
    }

    public void startDisplayOutfitFragment(Outfit outfit) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("outfit", outfit);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setReorderingAllowed(true);
        trans.add(R.id.fragmentContainerView, OutfitDisplayFragment.class, bundle);
        trans.commit();
    }

    public void stopDisplayOutfitFragment(Fragment fragment) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.remove(fragment);
        trans.commit();
    }

    public static void clearFilters() {
        givenOutfits = null;
        filterItems = null;
        filterTags = null;
    }

    @Override
    public void finish() {
        super.finish();
        clearFilters();
    }


}
