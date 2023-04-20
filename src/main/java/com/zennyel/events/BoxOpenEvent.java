package com.zennyel.events;

import com.zennyel.item.PetBox;
import org.bukkit.entity.Player;

public class BoxOpenEvent extends CustomEvent{

    private Player player;
    private PetBox petBox;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PetBox getPetBox() {
        return petBox;
    }

    public void setPetBox(PetBox petBox) {
        this.petBox = petBox;
    }
}
