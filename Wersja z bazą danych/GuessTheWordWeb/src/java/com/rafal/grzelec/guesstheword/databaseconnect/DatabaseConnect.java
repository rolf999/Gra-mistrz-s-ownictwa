/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafal.grzelec.guesstheword.databaseconnect;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.rafal.grzelec.guesstheword.model.*;
import com.rafal.grzelec.guesstheword.exceptions.GuessTheWordException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to support database
 * @author Rafal Grzelec
 * @version 1.0
 */
public class DatabaseConnect {
    
    /**
     * values stored information about database, and name of driver
     */
    private final static String dataBaseURL = "jdbc:derby://localhost:1527/lab";
    private final static String dataBaseUser = "root";
    private final static String dataBasePass = "root";
    private final static String dataBaseDriver = "org.apache.derby.jdbc.ClientDriver";
 
    //object creates a database connection.
    private Connection connection;
    //object allows you to create a new SQL statement
    private Statement statement;
    
    /**
     * method to create connection to database
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private void startConnect() throws InstantiationException, IllegalAccessException,
                 ClassNotFoundException, SQLException
    {
        Class.forName(dataBaseDriver).newInstance();
        connection = DriverManager.getConnection(dataBaseURL, dataBaseUser, dataBasePass);
        statement = connection.createStatement();
    }
 
    /**
     * method to close connection to data base
     * @throws SQLException 
     */
    private void closeConnect() throws SQLException
    {
        statement.close();
        connection.close();
    }
    
    /**
     * method to set data to database
     * @param data
     * @param ID_player
     */
    public void setData(ModelData data , String ID_player) throws GuessTheWordException
    {
        try{
            startConnect();
            
            String query = "SELECT move FROM gamedata WHERE IDPlayer LIKE '"+ID_player+"' and "
                    + "move = (SELECT MAX(move)FROM gamedata WHERE IDPlayer LIKE '"+ID_player+"')";
            
            ResultSet result = statement.executeQuery(query);
            int move = 0;
            if(result.next())
                move = result.getInt("move");
            move++;
            
            query = "INSERT INTO gamedata(IDPlayer,move,level,hint,shoots,win,enteredWord,gameWord) VALUES ('"
                    + ID_player + "' , " + move + " , '" + data.getLevel() + "' , " + data.getHint() + " , " + data.getShoots() 
                    + " , " + data.getWin() + " , '" + data.getEnteredWord() + "' , '" + data.getGameWord() + "' )";
            statement.executeUpdate(query);
            
            closeConnect();
        }catch(SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex)
        {
            throw new GuessTheWordException("Cant set data");
        }
    }
    
    /**
     * method to read modeldata form database
     * @param ID_player
     * @return
     * @throws GuessTheWordException 
     */
    public ModelData getData(String ID_player) throws GuessTheWordException
    {
        ModelData data = null;
        try{
            startConnect();
            String query = "SELECT * FROM gamedata WHERE IDPlayer LIKE '"+ID_player+"' and "
                    + "move = (SELECT MAX(move)FROM gamedata WHERE IDPlayer LIKE '"+ID_player+"')";
            
            ResultSet result = statement.executeQuery(query);
            
            if(result.next())
            {
                data = new ModelData(result.getString("level"),result.getInt("hint"),result.getInt("shoots"),
                        result.getBoolean("win"),result.getString("enteredWord"),result.getString("gameWord"));
            }
            
            closeConnect();
        }catch(SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex)
        {
            throw new GuessTheWordException("Cant find data");
        }
        if(data == null) data = new ModelData();
        
        return data;
    }
    
    /**
     * method to get history from database
     * @param ID_player
     * @return
     * @throws GuessTheWordException 
     */
    public List<String> getHistory(String ID_player) throws GuessTheWordException
    {
        List<String> history = new ArrayList<String>();
        try{
            startConnect();
            String query = "SELECT * FROM gamedata WHERE IDPlayer LIKE '"+ID_player+"'";
            
            ResultSet result = statement.executeQuery(query);
            
            while(result.next())
            {
                history.add(result.getString("move"));
                history.add(result.getString("level"));
                history.add(result.getString("hint"));
                history.add(result.getString("shoots"));
                history.add(result.getString("gameWord"));
                history.add(result.getString("win"));
            }
        }catch(SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex)
        {
            throw new GuessTheWordException("Cant find data");
        }
        
        return history;
    }
}
