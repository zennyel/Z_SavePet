package com.zennyel.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.zennyel.SavePets;
import com.zennyel.database.type.SQLite;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;

public class PetsDB extends SQLite {

    public PetsDB(SavePets instance) {
        super(instance);
        createPetTable();
        createPlayerPetTable();
    }

    public void createPlayerPetTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player_db(uuid VARCHAR(36) PRIMARY KEY NOT NULL,type VARCHAR(12), petId VARCHAR(36))";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.execute();
        } catch (SQLException e) {
            System.out.println("Error creating player table");
        }
    }

    public void insertPlayerPet(UUID playerUUID, PetType type, UUID petId) {
        String selectSql = "SELECT petId FROM player_db WHERE uuid = ? AND type = ?";
        String updateSql = "UPDATE player_db SET petId = ? WHERE uuid = ? AND type = ?";
        try (Connection conn = getConnection();
             PreparedStatement selectStm = conn.prepareStatement(selectSql);
             PreparedStatement updateStm = conn.prepareStatement(updateSql)) {
            selectStm.setString(1, playerUUID.toString());
            selectStm.setString(2, type.toString());
            ResultSet rs = selectStm.executeQuery();
            if (rs.next()) {
                String petIds = rs.getString("petId");
                petIds += "," + petId.toString();
                updateStm.setString(1, petIds);
                updateStm.setString(2, playerUUID.toString());
                updateStm.setString(3, type.toString());
                updateStm.executeUpdate();
            } else {
                String insertSql = "INSERT INTO player_db(uuid,type, petId) VALUES (?,?,?)";
                try (PreparedStatement insertStm = conn.prepareStatement(insertSql)) {
                    insertStm.setString(1, playerUUID.toString());
                    insertStm.setString(2, type.toString());
                    insertStm.setString(3, petId.toString());
                    insertStm.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("error when inserting player on pets_db");
        }
    }


    public List<Pet> getPlayerPets(UUID playerUUID) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT petId FROM player_db WHERE uuid = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, playerUUID.toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                UUID petId = UUID.fromString(rs.getString("petId"));
                pets.add(selectPet(petId));
            }
        } catch (SQLException e) {
            System.out.println("Error when getting player pets from database");
        }
        return pets;
    }


    public void createPetTable() {
        String sql = "CREATE TABLE IF NOT EXISTS pets_db(petId VARCHAR(36) PRIMARY KEY NOT NULL,type VARCHAR(12), lvl INT,experience DOUBLE)";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.execute();
        } catch (SQLException exception) {
            System.out.println("Error creating pets table.");
        }
    }

    public void insertPet(Pet pet) {
        String sql = "INSERT INTO pets_db(petId, type, lvl, experience) VALUES (?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, pet.getPetId().toString());
            stm.setString(2, pet.getType().toString());
            stm.setInt(3, pet.getLevel());
            stm.setDouble(4, pet.getExperience());
            stm.execute();
        } catch (SQLException e) {
            System.out.println("Error inserting pet on pets_db");
        }
    }


    public Pet selectPet(UUID uuid){
        String sql = "SELECT * FROM pets_db WHERE petId = ?";
        try(Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)){
            stm.setString(1, uuid.toString());
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                PetType type = PetType.valueOf(rs.getString("type").toUpperCase());
                int level = rs.getInt("lvl");
                double experience = rs.getDouble("experience");
                PetRarity rarity = PetRarity.valueOf(rs.getString("petRarity").toUpperCase());
                Pet pet = new Pet(type, rarity, level, experience, uuid);
                return  pet;
            }
        } catch (SQLException e) {
            System.out.println("Error when selecting a pet from the database.");
        }
        return null;
    }





    public Connection getConnection() {
        return super.getConnection("pets_db.db");
    }

}
