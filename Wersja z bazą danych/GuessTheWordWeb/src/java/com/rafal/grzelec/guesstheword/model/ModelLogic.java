/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafal.grzelec.guesstheword.model;

import com.rafal.grzelec.guesstheword.exceptions.GuessTheWordException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Model class of the application .It operated operates on the information about the game
 * @author Rafał Grzelec
 * @version 1.2
 */
public class ModelLogic {
    /**
     * Enter the level of game
     * @param modelData
     * @param level - level of the game as it has to be set
     * @throws GuessTheWordException
     */
    @Deprecated
    public void setLevel(ModelData modelData, String level ) throws GuessTheWordException               
    {
        if(level != null && modelData != null && modelData.getLenghtWord() >0)
        {
            switch (level) {
                case "easy":
                    modelData.setLevel(ModelData.Level.EASY);
                    modelData.setMultiplier(1);
                    modelData.setVisible((int)(0.4*(double)modelData.getLenghtWord()));
                    modelData.setHint((int)(0.3*(double)modelData.getLenghtWord()));
                    modelData.setShoots(3);
                    break;
                case "normal":
                    modelData.setLevel(ModelData.Level.NORMAL);
                    modelData.setMultiplier(6);
                    modelData.setVisible((int)(0.3*(double)modelData.getLenghtWord()));
                    modelData.setHint((int)(0.3*(double)modelData.getLenghtWord()));
                    modelData.setShoots(2);
                    break;
                case "hard":
                    modelData.setLevel(ModelData.Level.HARD);
                    modelData.setMultiplier(12);
                    modelData.setVisible((int)(0.3*(double)modelData.getLenghtWord()));
                    modelData.setHint((int)(0.2*(double)modelData.getLenghtWord()));
                    modelData.setShoots(1);
                    break;
                default:
                    throw new GuessTheWordException("BadLevel\n");
            }
        }   
    }
    
    /**
     * Method to choose level, without change in multiplier, visible, hint, shoot
     * @param modelData
     * @param level
     * @throws GuessTheWordException 
     */
    public void chooseLevel(ModelData modelData, String level ) throws GuessTheWordException               
    {
        if(level != null && modelData != null)
        {
            switch (level) {
                case "EASY":
                    modelData.setLevel(ModelData.Level.EASY);
                    break;
                case "NORMAL":
                    modelData.setLevel(ModelData.Level.NORMAL);
                    break;
                case "HARD":
                    modelData.setLevel(ModelData.Level.HARD);
                    break;
                default:
                    throw new GuessTheWordException("BadLevel\n");
            }
        }
    }
    
    /**
     * Method to choose level of game, without change in multiplier, visible, hint, shoot
     * @param modelData
     * @param level 
     */
    public void chooseLevel(ModelData modelData, ModelData.Level level )
    {
        if ( modelData != null)
            modelData.setLevel(level);
    }
    
    /**
     * Method sets multiplier, visible, hint, shoot in data, depending on the level
     * @param modelData
     * @throws GuessTheWordException 
     */
    public void setLevel(ModelData modelData) throws GuessTheWordException               
    {
        if(modelData != null && modelData.getLevel() != null && modelData.getLenghtWord() >0)
        {                
            switch(modelData.getLevel())
            {
                case EASY :
                {
                    modelData.setMultiplier(1);
                    modelData.setVisible((int)(0.4*(double)modelData.getLenghtWord()));
                    modelData.setHint((int)(0.3*(double)modelData.getLenghtWord()));
                    modelData.setShoots(3);
                    break;
                }
                case NORMAL:
                    modelData.setMultiplier(6);
                    modelData.setVisible((int)(0.3*(double)modelData.getLenghtWord()));
                    modelData.setHint((int)(0.3*(double)modelData.getLenghtWord()));
                    modelData.setShoots(2);
                    break;
                case HARD:
                    modelData.setMultiplier(12);
                    modelData.setVisible((int)(0.3*(double)modelData.getLenghtWord()));
                    modelData.setHint((int)(0.2*(double)modelData.getLenghtWord()));
                    modelData.setShoots(1);
                    break;
                default :
            }
        }
        else
            throw new GuessTheWordException("Error : Word or level not found\n");
            
    }
    
    /**
     * Introduces a word to the list of fields
     * @param modelData
     * @param word word to guess
     * @throws GuessTheWordException
     */
    public void setWord(ModelData modelData, char[] word) throws GuessTheWordException
    {   
        if (word != null && modelData != null)
        {
            if(word.length <= 10){
                modelData.setLenghtWord(word.length);
                for(char letter : word)
                {
                    modelData.getListOfFieldInWord().add(new ModelData.Field(letter,false));
                }
            }
            else{
              throw new GuessTheWordException("TooLong\n");  
            }
            modelData.setEnteredWord(String.valueOf(word));
            updateGameWord(modelData);
        }else
            throw new GuessTheWordException("Error: find null\n");
    }
    
    /**
     * Uncover letters in word which are given as parameters
     * to Single plaer
     * @param modelData
     * @param letters - letters to uncover
     */
    public void uncoverLetters(ModelData modelData , char ... letters)               
    {                                                                           
        if(letters != null && modelData != null)
        {
            for(char letter : letters)
            {

                for(ModelData.Field field : modelData.getListOfFieldInWord())
                {                
                    if(field.getLetter() == letter && field.getVisible() == false)
                    {
                        field.changeVisible();
                        break;
                    }
                }
            }
            updateGameWord(modelData);   
        }
    }
    
    /**
     * Method to uncover letters in word readed from file 
     * @param modelData 
     */
    public void uncoverReadFromFileWord(ModelData modelData)
    {
        if(modelData != null)
        {
            char[] gameWord = modelData.getGameWord().toCharArray();
            for(int i = 0; i<2*modelData.getLenghtWord();i++)
            {
                if(gameWord[i] != '_')
                    modelData.getListOfFieldInWord().get(i/2).changeVisible();
                i++;
            }
        }

    }
    
    /**
     * Uncower ramdom letters in the word, numbers of letters is store in
     * @param modelData varible visible
     */
    public void uncoverRandomLetters(ModelData modelData)
    {
        if(modelData != null)
        {
            Random generator = new Random();

            for(int i = 0; i< modelData.getVisible() ; i++)
            {
                while(true)
                {
                    int rand = generator.nextInt(modelData.getLenghtWord());
                    if (modelData.getListOfFieldInWord().get(rand).getVisible() == false)
                    {
                        modelData.getListOfFieldInWord().get(rand).changeVisible();
                        break;
                    } 
                }
            }

            updateGameWord(modelData);
        }
    }
    
    
    /**
     * Uncover letter on specific position
     * @param modelData in word
     * @param position - position of letter in word
     * @throws GuessTheWordException 
     */
    public void hint(ModelData modelData, int position) throws GuessTheWordException
    {
        if(modelData != null && modelData.getHint()>0)
        {
            if(position >= 0 && position < modelData.getLenghtWord())
            {
                if(modelData.getListOfFieldInWord().get(position).getVisible() == false)
                    modelData.getListOfFieldInWord().get(position).changeVisible();
                modelData.setHint(modelData.getHint()-1);                                               //if given already dicsavered letters - use hint
            }
            else
                throw new GuessTheWordException("BadPosition\n");
            
            updateGameWord(modelData);
        }
    }
    
    /**
     * Validation of input words with guesses word
     * @param modelData
     * @param word - word to check
     */
    public void shoot(ModelData modelData, char[] word)
    {
        if(word != null && modelData != null)
        {
            if(modelData.getShoots() >0)
            {
                modelData.setShoots(modelData.getShoots()-1);
                if(Arrays.equals(modelData.getEnteredWord().toCharArray(), word))
                    modelData.setWin(true);

                if(modelData.getWin())
                {
                    uncoverLetters(modelData, modelData.getEnteredWord().toCharArray());
                    modelData.setMultiplier(modelData.getMultiplier()+ modelData.getHint()+ modelData.getShoots());
                    modelData.setPoints(modelData.getMultiplier()*50);
                }
                
                if(modelData.getShoots() == 0)
                    uncoverLetters(modelData, modelData.getEnteredWord().toCharArray());
            }
            
        }
    }
    
    /**
     * download the guesses word. If letter is unvisible in its place inserting is character
     * @param modelData'_' 
     */
    private void updateGameWord(ModelData modelData)
    {
        if(modelData != null)
        {
            char[] result = new char[2*modelData.getListOfFieldInWord().size()];

            for( int i = 0;i< 2*modelData.getListOfFieldInWord().size(); i++)
            {
                result[i]= modelData.getListOfFieldInWord().get(i/2).getField();
                i++;
                result[i]= ' ';

            }     
            modelData.setGameWord(String.valueOf(result));
        }
    }
    
    /**
     * end of the game
     * @param modelData
     */
    public void pass(ModelData modelData) {
        if(modelData != null)
            modelData.setShoots(0);
    }
    /**
     * cleran the structure to new game
     * @param modelData
     */
    public void clearn(ModelData modelData)
    {
        if(modelData != null)
        {
            modelData.setPoints(0);                                                         
            modelData.setMultiplier(0);                                                     
            modelData.setLenghtWord(0);                                                    
            modelData.setHint(0);                                                           
            modelData.setShoots(0);                                                         
            modelData.setVisible(0);                                                        
            modelData.setWin(false);
            modelData.getListOfFieldInWord().clear();   
            modelData.setEnteredWord("");
            modelData.setGameWord("");
        }
    }
    
    /**
     * Method to save data of game
     * @param data
     * @throws GuessTheWordException 
     */
    public void save(ModelData data) throws GuessTheWordException
    {
        if(data != null)
        {
            PrintWriter save;
            try{
                save = new PrintWriter("save.sv");
            }catch(FileNotFoundException e)
            {
                throw new GuessTheWordException("Can't create file");
            }

            save.println(data.getMultiplier());
            save.println(data.getLenghtWord());
            save.println(data.getHint());
            save.println(data.getShoots());
            save.println(data.getEnteredWord());
            save.println(data.getGameWord());
            save.println(data.getLevel());
            save.close();
        }
    }
    
    /**
     * Method to read data of game
     * @param data 
     * @throws com.rafal.grzelec.guesstheword.exceptions.GuessTheWordException 
     */
    public void read(ModelData data) throws GuessTheWordException
    {
        if(data != null)
        {
            clearn(data);
            File read = new File("save.sv");
            Scanner scanner;

            try{
                scanner = new Scanner(read);
            }catch(FileNotFoundException e)
            {
                throw new GuessTheWordException("Can't create file");
            }

            data.setMultiplier(scanner.nextInt());
            data.setLenghtWord(scanner.nextInt());
            data.setHint(scanner.nextInt());
            data.setShoots(scanner.nextInt());
            scanner.nextLine();
            data.setEnteredWord(scanner.nextLine());
            setWord(data,data.getEnteredWord().toCharArray());
            data.setGameWord(scanner.nextLine());
            chooseLevel(data,scanner.nextLine());
            uncoverReadFromFileWord(data);
            setLevel(data);
        }
    }
}
