package com.zennyel.pet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class PetItem {
        public ItemStack getItemByType(PetType type, FileConfiguration configuration){
            boolean skullEnabled = configuration.getBoolean("petItems." + type.toString() + ".skull_enabled");
            String skullValue = configuration.getString("petItems." + type.toString() + ".skull");
            Material material = Material.getMaterial(configuration.getString("petItems." + type + ".material"));

            if (skullEnabled) {
                return getSkullItem(skullValue);
            } else {
                return new ItemStack(material);
            }
        }

        private ItemStack getSkullItem(String texture) {
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            UUID hashAsId = new UUID(texture.hashCode(), texture.hashCode());
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(hashAsId));
            meta.setDisplayName(ChatColor.RESET + "");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            item.setItemMeta(meta);
            return Bukkit.getUnsafe().modifyItemStack(item, "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + texture + "\"}]}}}");
        }
    }
    

