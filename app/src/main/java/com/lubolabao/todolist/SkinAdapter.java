package com.lubolabao.todolist;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class SkinAdapter extends RecyclerView.Adapter<SkinAdapter.SkinViewHolder> {

    private List<Skin> skinList;
    private OnSkinClickListener listener;
    private int equippedSkinId;

    public interface OnSkinClickListener {
        void onSkinClick(Skin skin, int position);
    }

    public SkinAdapter(List<Skin> skinList, int equippedSkinId, OnSkinClickListener listener) {
        this.skinList = skinList;
        this.equippedSkinId = equippedSkinId;
        this.listener = listener;
    }

    public void setEquippedSkinId(int equippedSkinId) {
        this.equippedSkinId = equippedSkinId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SkinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skin, parent, false);
        return new SkinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkinViewHolder holder, int position) {
        Skin skin = skinList.get(position);
        holder.tvName.setText(skin.getName());
        holder.tvRarity.setText(skin.getRarity());
        holder.ivImage.setImageResource(skin.getDrawableId());

        int rarityColor;
        switch (skin.getRarity()) {
            case "Elite": rarityColor = Color.parseColor("#1E88E5"); break;
            case "Special": rarityColor = Color.parseColor("#43A047"); break;
            case "Epic": rarityColor = Color.parseColor("#8E24AA"); break;
            case "Legend": rarityColor = Color.parseColor("#FBC02D"); break;
            default: rarityColor = Color.parseColor("#757575"); break;
        }
        holder.cardSkin.setStrokeColor(rarityColor);
        holder.tvRarity.setTextColor(rarityColor);

        if (skin.isUnlocked()) {
            if (skin.getId() == equippedSkinId) {
                holder.btnAction.setText("Equipped");
                holder.btnAction.setEnabled(false);
            } else {
                holder.btnAction.setText("Equip");
                holder.btnAction.setEnabled(true);
            }
        } else {
            holder.btnAction.setText(skin.getPrice() + "g");
            holder.btnAction.setEnabled(true);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onSkinClick(skin, holder.getAdapterPosition());
        });

        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) listener.onSkinClick(skin, holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() { return skinList.size(); }

    public static class SkinViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardSkin;
        ImageView ivImage;
        TextView tvName, tvRarity;
        MaterialButton btnAction;

        public SkinViewHolder(@NonNull View itemView) {
            super(itemView);
            cardSkin = itemView.findViewById(R.id.cardSkin);
            ivImage = itemView.findViewById(R.id.ivSkinImage);
            tvName = itemView.findViewById(R.id.tvSkinName);
            tvRarity = itemView.findViewById(R.id.tvSkinRarity);
            btnAction = itemView.findViewById(R.id.btnBuyEquip);
        }
    }
}
