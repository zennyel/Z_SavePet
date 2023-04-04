package com.zennyel.database.type;

import com.zennyel.SavePets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class SQLite {

    private Connection connection;
    private SavePets instance;

    public SQLite(SavePets instance) {
        this.instance = instance;
    }

    public Connection getConnection(String file){
        File dataFolder = instance.getDataFolder();

        if(!dataFolder.exists()){
            dataFolder.mkdir();
        }
        File dbFile = new File(dataFolder, file);
        if(!dbFile.exists()){
            try {
                dbFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
