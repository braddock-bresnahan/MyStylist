package com.example.mystylist;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystylist.structures.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private final List<Profile> profiles;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final int view_type;
        public ViewHolder(View view){
            super(view);
            this.view_type = 0;
        }
        protected ViewHolder(View view, int view_type){
            super(view);
            this.view_type = view_type;
        }
    }

    public static class AddViewHolder extends ProfileAdapter.ViewHolder {
        public static final int VIEW_TYPE = 0;
        public static final int LAYOUT_ID = R.layout.recycler_row_add;
        private final ImageButton plus_button;

        public AddViewHolder(View view) {
            super(view, VIEW_TYPE);
            plus_button = view.findViewById(R.id.plus_button);
        }

        public ImageButton getPlusButton() {
            return plus_button;
        }
    }

    public static class ProfileViewHolder extends ProfileAdapter.ViewHolder {
        public static final int VIEW_TYPE = 1;
        public static final int LAYOUT_ID = R.layout.recycler_row_profile;
        private final TextView profile_name_text;
        private final Button select_profile_button;

        public ProfileViewHolder(View view) {
            super(view, VIEW_TYPE);
            profile_name_text = view.findViewById(R.id.profile_name_text);
            select_profile_button = view.findViewById(R.id.select_profile_button);
        }

        public TextView getProfileNameText() { return profile_name_text; }
        public Button getSelectProfileButton() { return select_profile_button; }

    }

    public ProfileAdapter(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return ProfileAdapter.AddViewHolder.VIEW_TYPE;
        return ProfileAdapter.ProfileViewHolder.VIEW_TYPE;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProfileAdapter.ViewHolder view_holder;
        if (viewType == ProfileAdapter.AddViewHolder.VIEW_TYPE)
            view_holder = new ProfileAdapter.AddViewHolder(LayoutInflater.from(parent.getContext()).inflate(AddViewHolder.LAYOUT_ID, parent, false));
        else if (viewType == ProfileAdapter.ProfileViewHolder.VIEW_TYPE)
            view_holder = new ProfileAdapter.ProfileViewHolder(LayoutInflater.from(parent.getContext()).inflate(ProfileViewHolder.LAYOUT_ID, parent, false));
        else
            view_holder = null;

        assert view_holder != null;
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        if (holder.view_type == ProfileAdapter.AddViewHolder.VIEW_TYPE)
            bindAddViewHolder((ProfileAdapter.AddViewHolder) holder, position);
        else if (holder.view_type == ProfileAdapter.ProfileViewHolder.VIEW_TYPE)
            bindProfileViewHolder((ProfileAdapter.ProfileViewHolder) holder, position);
    }

    private void bindAddViewHolder(ProfileAdapter.AddViewHolder holder, int position) {
        ImageButton plus_button = holder.getPlusButton();
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SelectProfileActivity) v.getContext()).showAddProfilePopup();
            }
        });
    }

    private void bindProfileViewHolder(ProfileAdapter.ProfileViewHolder holder, int position) {
        Profile profile = profiles.get(positionToIndex(position));

        TextView profile_name = holder.getProfileNameText();
        Button select_profile_button = holder.getSelectProfileButton();

        profile_name.setText(profile.getName());

        select_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SelectProfileActivity) unwrap(view.getContext())).selectProfile(profile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1 + profiles.size();
    }

    public Profile getProfileAt(int position) {
        int index = positionToIndex(position);
        if (index >= 0 && index < profiles.size())
            return profiles.get(index);
        else
            return null;
    }

    public Profile addProfileAt(int position, Profile profile) {
        if (profiles.contains(profile))
            return null;
        profiles.add(positionToIndex(position), profile);
        notifyItemInserted(position);
        return profile;
    }

    public Profile removeProfileAt(int position) {
        int index = positionToIndex(position);
        if (index < 0 || index >= profiles.size())
            return null;

        Profile removed = profiles.remove(index);
        this.notifyItemRemoved(position);
        return removed;
    }

    private int positionToIndex(int position) {
        return position - 1;
    }

    private static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) context;
    }
}
