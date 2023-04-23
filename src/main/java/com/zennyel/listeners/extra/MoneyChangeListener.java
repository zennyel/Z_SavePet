package com.zennyel.listeners.extra;

import com.zennyel.manager.pet.PetManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetType;
import net.brcdev.shopgui.event.ShopPreTransactionEvent;
import net.brcdev.shopgui.shop.ShopManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;



public class MoneyChangeListener implements Listener {

    private final PetManager manager;

    public MoneyChangeListener(PetManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onMoneyChange(ShopPreTransactionEvent event){
        Player player = event.getPlayer();
        if(event.getShopAction().equals(ShopManager.ShopAction.SELL)){
            int moneyRecived = event.getAmount();
            for(Pet pet : manager.getPlayerPets(player)){
                if (pet.getType().equals(PetType.MONEY)){
                    event.setAmount((int) (moneyRecived + moneyRecived*0.01*pet.getLevel()));
                }
            }
        }
    }

}
