package com.example.mystylist;

import androidx.fragment.app.Fragment;

import com.example.mystylist.structures.Outfit;

public interface IOutfitRecyclerHome {
    void favoriteOutfit(Outfit outfit);

    void unfavoriteOutfit(Outfit outfit);

    void startDisplayOutfitFragment(Outfit outfit);

    void stopDisplayOutfitFragment(Fragment fragment);
}
