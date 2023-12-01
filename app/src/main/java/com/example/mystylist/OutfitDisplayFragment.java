package com.example.mystylist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.mystylist.enums.ETag;
import com.example.mystylist.structures.Item;
import com.example.mystylist.structures.Outfit;

import java.util.List;

public class OutfitDisplayFragment extends Fragment {
    private Fragment frag = this;
    private Outfit outfit;
    private ImageButton backButton;
    private TextView nameText;
    private TextView descText;
    private TextView numberOfItemsText;
    private TextView tagsText;

    public OutfitDisplayFragment() {
        super(R.layout.fragment_outfit_display);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        outfit = requireArguments().getSerializable("outfit", Outfit.class);

        backButton = view.findViewById(R.id.back_button);
        nameText = view.findViewById(R.id.name_text);
        descText = view.findViewById(R.id.desc_text);
        numberOfItemsText = view.findViewById(R.id.number_of_items_text);
        tagsText = view.findViewById(R.id.tags_text);

        // Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IOutfitRecyclerHome activity = (IOutfitRecyclerHome) getActivity();
                assert activity != null;
                activity.stopDisplayOutfitFragment(frag);
            }
        });

        // Name
        nameText.setText(outfit.getOutfitName());

        // Desc
        descText.setText(outfit.getOutfitDesc());

        // Number of Items
        numberOfItemsText.setText(String.valueOf(outfit.numberOfItems()));

        // Tags
        List<ETag> tags = ETag.maskToTags(outfit.getTagFlags());
        StringBuilder stringOfTags = new StringBuilder();
        for (ETag tag : tags) {
            stringOfTags.append(tag.toString()).append(", ");
        }
        tagsText.setText(stringOfTags.toString());

        // Item List
        LinearLayout layout = view.findViewById(R.id.ScrollViewLinearLayout);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (Item item : outfit.getItems()) {
            View itemView = inflater.inflate(R.layout.recycler_row_item_closet, null);

            ImageView type_image = itemView.findViewById(R.id.type_image);
            TextView type_text = itemView.findViewById(R.id.type_text);
            TextView color_text = itemView.findViewById(R.id.color_text);
            CardView color_preview = itemView.findViewById(R.id.color_preview);
            CheckBox check_box = itemView.findViewById(R.id.check_box);

            type_image.setImageResource(item.drawable_id);

            type_text.setText(item.type.toString());

            color_text.setText(item.color.toString());

            color_preview.setCardBackgroundColor(item.color.toInt());

            check_box.setEnabled(false);
            check_box.setVisibility(View.GONE);

            layout.addView(itemView);
        }


    }
}