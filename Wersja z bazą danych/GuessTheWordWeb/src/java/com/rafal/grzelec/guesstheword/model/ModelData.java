/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafal.grzelec.guesstheword.model;


import com.rafal.grzelec.guesstheword.exceptions.GuessTheWordException;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class of the application. It allows information about the game
 * @author Rafał Grzelec
 * @version 1.2
 */
public class ModelData {
    
    /**
     * permitted levels of difficulty, must be public to understand result of getLevel method in another class
     */
    public enum Level                                                          
    { EASY, NORMAL, HARD ;}
    
    /**
     * structure to store status of letter
     */
    public static class Field{ 
        
        /**
         * letter from guesed word
         */
        /**
         * status of letter - visible or invisible
         */
        private final char letter;
        private boolean visible;
        
        /**
         * Constructor to Field
         * @param letter
         * @param visible 
         */
        public Field(char letter, boolean visible)
        {
            this.visible = visible;
            this.letter = letter;
        }
        /**
         * Method convert Field to string
         * @return conwerted value
         */
        @Override
        public String toString()
        {
            return letter + "/0";
        }
        /**
         * Method to get character stored in Field covered or uncovered 
         * @return character
         */
        public char getField() { return visible ? letter : '_'; }
        /**
         * Method to get visible
         * @return visible
         */
        public boolean getVisible() {return visible;}
        /**
         * Method to get letter
         * @return letter
         */
        public char getLetter() {return letter;}
        /**
         * Method change status of visible to opposed
         */
        public void changeVisible() {visible = !visible;}
    }
    
    /**
     * level of game, currently unused in game
     */
    private Level level;    
    /**
     * points earned in the game
     */
    private int points;                                                         
    /**
     * multiplier of points
     */
    private int multiplier;                                                     
    /**
     * lenght of guess word
     */
    private int lenght_word;                                                    
    /**
     * number of possible exposures of the letters in the guess word
     */
    private int hint;                                                           
    /**
     * number of possible guess word
     */
    private int shoots;                                                         
    /**
     * number of visible letters
     */
    private int visible;                                                        
    /**
     * checks statsu of game
     */
    private boolean win;                                                        
    /**
     * store word to guess in collection, on this structure  
     */
    private List<Field> word ;
    /**
     * strore uncover word to guess
     */
    private String enteredWord;
    /**
     * Store word to guess with cover letter
     */
    private String gameWord;
    /**
     * constructor
     */
    public ModelData(){
        level = null;
        points = 0;
        multiplier = 0;
        lenght_word = 0;
        hint = 0;
        shoots = 0;
        visible = 0;
        win = false;
        enteredWord = "";
        gameWord = "";
        
        word = new ArrayList<Field>();        
        
    }
    
    public ModelData(String level, int hint, int shoots,  boolean win, String enteredWord, String gameWord )
    {
        word = new ArrayList<Field>();
        ModelLogic logic = new ModelLogic();
        
        try {
            //this.enteredWord = enteredWord;
            logic.setWord(this, enteredWord.toCharArray());
            logic.chooseLevel(this, level);
            this.gameWord = gameWord;
            logic.uncoverReadFromFileWord(this);
            logic.setLevel(this);
            this.hint = hint;
            this.shoots = shoots;
            this.win = win;
        } catch (GuessTheWordException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Method to get game point
     * @return game point
     */
    public int getPoints()  {return points;}
    /**
     * Method to get lenght of guessing word
     * @return point
     */
    public int getLenghtWord()  {return lenght_word;}
    /**
     * Method to get number of having hint
     * @return hint
     */
    public int getHint(){return hint;}
    /**
     * Method to get number of available shoots
     * @return shoots
     */
    public int getShoots(){return shoots;}
    /**
     * Method to get result of game
     * @return result of game (win or lose)
     */
    public boolean getWin() {return win;}
    /**
     * Method to get level of game
     * @return level
     */
    public Level getLevel() {return level;}
    /**
     * Method to get entered word
     * @return entered word
     */
    public String getEnteredWord(){return enteredWord;}
    /**
     * Method to get game word
     * @return game word
     */
    public String getGameWord(){return gameWord;}
    
    /**
     * Method to get multiplier
     * @return game word
     */
    public int getMultiplier(){return multiplier;}
    
    /**
     * Method to get number of visible letters
     * @return game word
     */
    public int getVisible(){return visible;}
    
    /**
     * Method to get list of field in wond
     * @return game word
     */
    public List<Field> getListOfFieldInWord(){return word;}
    
    
    /**
     * Method to set game point
     * @param points
     */
    public void setPoints(int points)  {this.points = points;}
    /**
     * Method to set lenght of guessing word
     * @param lenght_word
     */
    public void setLenghtWord(int lenght_word)  {this.lenght_word = lenght_word;}
    /**
     * Method to set number of having hint
     * @param hint
     */
    public void setHint(int hint){this.hint = hint;}
    /**
     * Method to set number of available shoots
     * @param shoots
     */
    public void setShoots(int shoots){this.shoots = shoots;}
    /**
     * Method to set result of game
     * @param win result of game (win or lose)
     */
    public void setWin(boolean win) {this.win = win;}
    /**
     * Method to set level of game
     * @param level
     */
    public void setLevel(Level level) {this.level = level;}
    /**
     * Method to set entered word
     * @param enteredWord
     */
    public void setEnteredWord(String enteredWord){this.enteredWord = enteredWord;}
    /**
     * Method to set game word
     * @param gameWord
     */
    public void setGameWord(String gameWord){this.gameWord = gameWord;}
    
    /**
     * Method to set multiplier
     * @param multiplier
     */
    public void setMultiplier(int multiplier){this.multiplier = multiplier;}
    
    /**
     * Method to set number of visible letters
     * @param visible
     */
    public void setVisible(int visible){this.visible = visible;}
    
    /**
     * Method to set list of field in wond
     * @param word
     */
    public void setListOfFieldInWond(List<Field> word){this.word = word;}
}

