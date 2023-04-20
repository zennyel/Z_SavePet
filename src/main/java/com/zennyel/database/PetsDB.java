package com.zennyel.database;

import java.sql.*;
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
    }

    public void createPetTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS pets ("
                    + "player_uuid VARCHAR(36) NOT NULL,"
                    + "pet_type VARCHAR(20) NOT NULL,"
                    + "pet_rarity VARCHAR(20),"
                    + "pet_level INT(11) NOT NULL,"
                    + "pet_experience DOUBLE(10,2) NOT NULL"
                    + ")";

            stmt.executeUpdate(sql);
            System.out.println("Table 'pets' created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table 'pets': " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertPet(UUID playerUuid, Pet pet) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO pets (player_uuid, pet_type, pet_rarity, pet_level, pet_experience) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, playerUuid.toString());
            stmt.setString(2, pet.getType().toString());
            if (pet.getRarity() != null) {
                stmt.setString(3, pet.getRarity().toString());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }
            stmt.setInt(4, pet.getLevel());
            stmt.setDouble(5, pet.getExperience());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting pet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Pet> getPets(UUID playerUUID) {
        String sql = "SELECT pet_type, pet_rarity, pet_level, pet_experience FROM pets WHERE player_uuid = ?";
        List<Pet> pets = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, playerUUID.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PetType type = PetType.valueOf(rs.getString("pet_type"));
                    PetRarity rarity = rs.getString("pet_rarity") != null ? PetRarity.valueOf(rs.getString("pet_rarity")) : null;
                    int level = rs.getInt("pet_level");
                    double experience = rs.getDouble("pet_experience");

                    Pet pet = new Pet(type, rarity, level, experience);
                    pets.add(pet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving pets for player " + playerUUID + ": " + e.getMessage());
        }

        return pets;
    }


    public Pet getPet(UUID playerUuid, PetType type) {
        Pet pet = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT pet_type, pet_rarity, pet_level, pet_experience FROM pets WHERE player_uuid = ? AND pet_type = ?")) {

            stmt.setString(1, playerUuid.toString());
            stmt.setString(2, type.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PetType petType = PetType.valueOf(rs.getString("pet_type"));
                PetRarity petRarity = null;
                if (rs.getString("pet_rarity") != null) {
                    petRarity = PetRarity.valueOf(rs.getString("pet_rarity"));
                }
                int level = rs.getInt("pet_level");
                double experience = rs.getDouble("pet_experience");

                pet = new Pet(petType, petRarity, level, experience);
            }

        } catch (SQLException e) {
            System.out.println("Error getting pet: " + e.getMessage());
            e.printStackTrace();
        }
        return pet;
    }

    public void deletePet(UUID playerUuid, PetType type) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM pets WHERE player_uuid = ? AND pet_type = ?")) {

            stmt.setString(1, playerUuid.toString());
            stmt.setString(2, type.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting pet: " + e.getMessage());
            e.printStackTrace();
        }
    }






    public Connection getConnection() {
        return super.getConnection("pets_db.db");
    }

}
