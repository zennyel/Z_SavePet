package com.zennyel.events;

import com.zennyel.GUI.PetBoxGUI;
import com.zennyel.item.PetBox;
import org.bukkit.entity.Player;

public class BoxOpenEvent extends CustomEvent{

    private Player player;
    private PetBoxGUI petBox;

    public BoxOpenEvent(Player player, PetBoxGUI petBox) {
        this.player = player;
        this.petBox = petBox;
    }

    public PetBoxGUI getPetBox() {
        return petBox;
    }

    public void setPetBox(PetBoxGUI petBox) {
        this.petBox = petBox;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
