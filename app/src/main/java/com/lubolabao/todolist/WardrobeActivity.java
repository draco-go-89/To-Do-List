package com.lubolabao.todolist;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WardrobeActivity extends AppCompatActivity {

    private DataManager dataManager;
    private SkinAdapter adapter;
    private List<Skin> skinList;
    private TextView tvGold;
    private ImageView skinOverlay;
    private ImageView baseAvatar;
    private ImageView wardrobeBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        dataManager = new DataManager(this);
        
        Toolbar toolbar = findViewById(R.id.toolbarWardrobe);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        wardrobeBackground = findViewById(R.id.wardrobeBackground);
        updateBackground();

        tvGold = findViewById(R.id.tvWardrobeGold);
        skinOverlay = findViewById(R.id.skinOverlay);
        baseAvatar = findViewById(R.id.baseAvatar);
        
        baseAvatar.setImageResource(R.drawable.ic_base_avatar);
        
        initSkinList();
        updateGoldDisplay();
        updateAvatar();

        RecyclerView rvSkins = findViewById(R.id.rvSkins);
        rvSkins.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        
        adapter = new SkinAdapter(skinList, dataManager.getEquippedSkinId(), (skin, position) -> {
            if (skin.isUnlocked()) {
                equipSkin(skin);
            } else {
                if (dataManager.getCurrentGold() >= skin.getPrice()) {
                    buySkin(skin, position);
                } else {
                    Toast.makeText(this, "Not enough gold!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvSkins.setAdapter(adapter);
    }

    private void updateBackground() {
        wardrobeBackground.setImageResource(dataManager.getSelectedWallpaperId());
    }

    private void initSkinList() {
        skinList = new ArrayList<>();
        List<Integer> unlockedIds = dataManager.getUnlockedSkinIds();

        skinList.add(new Skin(0, "Default", R.drawable.ic_skin_common, 0, "Common", true));
        skinList.add(new Skin(1, "Sky Guardian", R.drawable.ic_skin_elite, 100, "Elite", false));
        skinList.add(new Skin(2, "Forest Spirit", R.drawable.ic_skin_special, 250, "Special", false));
        skinList.add(new Skin(3, "Void Walker", R.drawable.ic_skin_epic, 500, "Epic", false));
        skinList.add(new Skin(4, "Sun God", R.drawable.ic_skin_legend, 1000, "Legend", false));

        for (Skin skin : skinList) {
            if (unlockedIds.contains(skin.getId())) {
                skin.setUnlocked(true);
            }
        }
    }

    private void buySkin(Skin skin, int position) {
        dataManager.saveGold(dataManager.getCurrentGold() - skin.getPrice());
        dataManager.addUnlockedSkin(skin.getId());
        skin.setUnlocked(true);
        updateGoldDisplay();
        equipSkin(skin);
        adapter.notifyItemChanged(position);
    }

    private void equipSkin(Skin skin) {
        dataManager.setEquippedSkinId(skin.getId());
        adapter.setEquippedSkinId(skin.getId());
        updateAvatar();
        Toast.makeText(this, skin.getName() + " equipped!", Toast.LENGTH_SHORT).show();
    }

    private void updateAvatar() {
        int equippedId = dataManager.getEquippedSkinId();
        if (equippedId == -1) {
            skinOverlay.setImageDrawable(null);
            return;
        }
        
        for (Skin skin : skinList) {
            if (skin.getId() == equippedId) {
                skinOverlay.setImageResource(skin.getDrawableId());
                return;
            }
        }
    }

    private void updateGoldDisplay() {
        tvGold.setText(dataManager.getCurrentGold() + "g");
    }
}
