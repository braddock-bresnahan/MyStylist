package com.example.mystylist.closet_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystylist.R;
import com.example.mystylist.structures.Closet;
import com.example.mystylist.structures.Item;

public class ClosetItemAdapter extends RecyclerView.Adapter<ClosetItemAdapter.ViewHolder>{
    private final Closet closet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final int view_type;
        public ViewHolder(View view){
            super(view);
            view_type = -1;
        }
        protected ViewHolder(View view, int view_type){
            super(view);
            this.view_type = view_type;
        }
    }

    public static class AddViewHolder extends ViewHolder {
        public static final int VIEW_TYPE = 0;
        private final ImageButton plus_button;

        public AddViewHolder(View view) {
            super(view, VIEW_TYPE);
            plus_button = view.findViewById(R.id.plus_button);
        }

        public ImageButton getPlusButton() {
            return plus_button;
        }
    }

    public static class ItemViewHolder extends ViewHolder {
        public static final int VIEW_TYPE = 1;
        private final ImageView type_image;
        private final TextView type_text;
        private final TextView color_text;
        private final CardView color_preview;
        private final CheckBox check_box;

        public ItemViewHolder(View view) {
            super(view, VIEW_TYPE);
            type_image = view.findViewById(R.id.type_image);
            type_text = view.findViewById(R.id.type_text);
            color_text = view.findViewById(R.id.color_text);
            color_preview = view.findViewById(R.id.color_preview);
            check_box = view.findViewById(R.id.check_box);
        }

        public ImageView getTypeImage(){
            return type_image;
        }
        public TextView getTypeText() {
            return type_text;
        }
        public TextView getColorText() {
            return color_text;
        }
        public CardView getColorPreview() {return color_preview;}
        public CheckBox getCheckBox() { return check_box; }

    }

    public ClosetItemAdapter(Closet closet) {
        this.closet = closet;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return AddViewHolder.VIEW_TYPE;
        return ItemViewHolder.VIEW_TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view_holder;
        if (viewType == AddViewHolder.VIEW_TYPE)
            view_holder = new AddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_add, parent, false));
        else if (viewType == ItemViewHolder.VIEW_TYPE)
            view_holder = new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item_closet, parent, false));
        else
            view_holder = null;

        assert view_holder != null;
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.view_type == AddViewHolder.VIEW_TYPE)
            bindAddViewHolder((AddViewHolder) holder, position);
        else if (holder.view_type == ItemViewHolder.VIEW_TYPE)
            bindItemViewHolder((ItemViewHolder) holder, position);
    }

    private void bindAddViewHolder(AddViewHolder holder, int position) {
        ImageButton plus_button = holder.getPlusButton();
        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClosetActivity) v.getContext()).showAddItemPopup();
            }
        });
    }

    private void bindItemViewHolder(ItemViewHolder holder, int position) {
        Item item = closet.getItemAt(positionToIndex(position));

        ImageView type_image = holder.getTypeImage();
        type_image.setImageResource(item.drawable_id);

        TextView type_text = holder.getTypeText();
        type_text.setText(item.type.toString());

        TextView color_text = holder.getColorText();
        color_text.setText(item.color.toString());

        CardView color_preview = holder.getColorPreview();
        color_preview.setCardBackgroundColor(item.color.toInt());

        CheckBox check_box = holder.getCheckBox();
        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClosetActivity context = (ClosetActivity) buttonView.getContext();
                if (isChecked) {
                    context.selectedItems.add(item);
                }
                else {
                    context.selectedItems.remove(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1 + closet.getItemCount();
    }

    public Item getItemAt(int position) {
        int index = positionToIndex(position);
        if (index >= 0 && index < closet.getItemCount())
            return closet.getItemAt(index);
        else
            return null;
    }

    public Item addItemAt(int position, Item item) {
        if (closet.addItem(positionToIndex(position), item) == null)
            return null;
        notifyItemInserted(position);
        return item;
    }

    public Item removeItemAt(int position) {
        int index = positionToIndex(position);
        if (index < 0 || index >= closet.getItemCount())
            return null;

        Item removed = closet.removeItemAt(index);
        this.notifyItemRemoved(position);
        return removed;
    }

    private int positionToIndex(int position) {
        return position - 1;
    }

}

