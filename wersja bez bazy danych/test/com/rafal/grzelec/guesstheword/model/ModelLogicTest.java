/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafal.grzelec.guesstheword.model;
/*
I do not know what the resource is missing from the library hamcrest, 
it seems to me that I did not use this library for testing
*/
import com.rafal.grzelec.guesstheword.exceptions.GuessTheWordException;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Test class
 * @author Rafał Grzelec
 * @version 1.0
 */
public class ModelLogicTest {
    
    public ModelLogicTest() {
    }

    /**
     * Test of setLevel method, of class ModelLogic.
     * @throws java.lang.Exception
     */
    @Test
    public void testSetLevel() throws Exception {
        System.out.println("setLevel");
        ModelData modelData = new ModelData();
        ModelData.Level level = ModelData.Level.EASY;
        ModelLogic instance = new ModelLogic();
        instance.setWord(modelData,"example".toCharArray());
        
        instance.setLevel(modelData, "easy");
        assertEquals(level, modelData.getLevel());
        
        try{
        instance.setLevel(modelData, null);
        }catch(GuessTheWordException e)
        {
            assertEquals("NoData\n", e.getMessage());
        }
        
        try{
        instance.setLevel(modelData, "aaa");
        }catch(GuessTheWordException e)
        {
            assertEquals("BadLevel\n", e.getMessage());
        }
        
        try{
        instance.setLevel(null, "eazy");
        }catch(GuessTheWordException e)
        {
            assertEquals("NoData\n", e.getMessage());
        }

    }

    /**
     * Test of chooseLevel method, of class ModelLogic.
     * @throws java.lang.Exception
     */
    @Test
    public void testChooseLevel_ModelData_String() throws Exception {
        System.out.println("chooseLevel");
        String level = "EASY";
        ModelData modelData = new ModelData();
        ModelData.Level expected = ModelData.Level.EASY;
        ModelLogic instance = new ModelLogic();
        instance.setWord(modelData,"example".toCharArray());
        
        instance.chooseLevel(null, level);
        assertEquals(modelData.getLevel(),null);
        
        instance.chooseLevel(modelData, level);
        assertEquals(modelData.getLevel(),expected);
       
    }

    /**
     * Test of chooseLevel method, of class ModelLogic.
     */
    @Test
    public void testChooseLevel_ModelData_ModelDataLevel() {
        System.out.println("chooseLevel");
        ModelData modelData = new ModelData();
        ModelData.Level expected = ModelData.Level.EASY;
        ModelLogic instance = new ModelLogic();
        
        instance.chooseLevel(null, expected);
        assertEquals(modelData.getLevel(),null);
        
        instance.chooseLevel(modelData, expected);
        assertEquals(modelData.getLevel(),expected);
        
    }

    /**
     * @throws java.lang.Exception 
     * Test of setLevel method, of class ModelLogic.
     */
    @Test
    public void testSetLevel_ModelData() throws Exception {
        System.out.println("setLevel");
        ModelData modelData = new ModelData();
        ModelData.Level level = ModelData.Level.EASY;
        ModelLogic instance = new ModelLogic();
        instance.setWord(modelData,"example".toCharArray());
        instance.chooseLevel(modelData, level);
        instance.setLevel(modelData);
        
        try{
        instance.setWord(null,"example".toCharArray());
        }catch(GuessTheWordException e)
        {
            assertEquals("Error: find null\n", e.getMessage());
        }
        
        ModelData expected = new ModelData();
        instance.setWord(expected,"example".toCharArray());
        
        instance.setLevel(expected,"easy");
        assertTrue(equals(modelData, expected));
        
        
    }
    
    public void uncoverReadFromFileWord_ModelData() throws Exception
    {
        System.out.println("uncoverReadFromFileWord");
        ModelData modelData = new ModelData();
        ModelLogic instance = new ModelLogic();
        instance.setWord(modelData,"example".toCharArray());
        instance.setLevel(modelData, "EAZY");
        instance.uncoverRandomLetters(modelData);
        
        String expected = modelData.getGameWord();                              //with uncowered random letters
        instance.setWord(modelData,"example".toCharArray());
        modelData.setGameWord(expected);                                        //set word with uncovered random letters
        instance.uncoverReadFromFileWord(modelData);
        
        assertEquals(modelData.getGameWord(),expected);
        
        instance.uncoverReadFromFileWord(null);                                 //chcek if return error
        
    }

    /**
     * Test of setWord method, of class ModelLogic.
     * @throws java.lang.Exception
     */
    @Test
    public void testSetWord() throws Exception {
        System.out.println("setWord");
        ModelData modelData = new ModelData();
        char[] word = "example".toCharArray();
        ModelLogic instance = new ModelLogic();
        
        instance.setWord(modelData, word);
        assertEquals("example",modelData.getEnteredWord());
        
        word = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".toCharArray();
        try{
        instance.setWord(modelData, word);
        }catch(GuessTheWordException e)
        {
            assertEquals("TooLong\n", e.getMessage());
        }
        
        try{
        instance.setWord(modelData, null);
        }catch(GuessTheWordException e)
        {
            assertEquals("Error: find null\n", e.getMessage());
        }
        
        try{
            word = "example".toCharArray();
            instance.setWord(null,word);
        }catch(GuessTheWordException e)
        {
            assertEquals("Error: find null\n", e.getMessage());
        }



        
        
    }

    /**
     * Test of uncoverLetters method, of class ModelLogic.
     * @throws java.lang.Exception
     */
    @Test
    public void testUncoverLetters() throws Exception{
        System.out.println("uncoverLetters");
        ModelData modelData = new ModelData();
        char[] letters = null;
        ModelLogic instance = new ModelLogic();
        
        try{
        instance.setWord(null,"example".toCharArray());    
        }catch(GuessTheWordException e)
        {
            assertEquals("Error: find null\n", e.getMessage());
        }
        
        instance.setWord(modelData,"example".toCharArray());
        instance.uncoverLetters(modelData, letters);
        assertEquals("_ _ _ _ _ _ _ ",modelData.getGameWord());
        
        instance.uncoverLetters(modelData, 'e', 'e');
        assertEquals("e _ _ _ _ _ e ",modelData.getGameWord());
        
        instance.uncoverLetters(modelData, 'b', 'c','g');
        assertEquals("e _ _ _ _ _ e ",modelData.getGameWord());
        
    }

    /**
     * Test of uncoverRandomLetters method, of class ModelLogic.
     */
    @Test
    public void testUncoverRandomLetters() {
        System.out.println("uncoverRandomLetters");
        ModelLogic instance = new ModelLogic();
        
        instance.uncoverRandomLetters(null);
        
    }

    /**
     * Test of hint method, of class ModelLogic.
     * @throws java.lang.Exception
     */
    @Test
    public void testHint() throws Exception {
        System.out.println("hint");
        ModelData modelData = new ModelData();
        int position = 0;
        ModelLogic instance = new ModelLogic();
        instance.hint(modelData, position);
        
        char[] word = "example".toCharArray();
        
        instance.setWord(modelData, word);
        instance.setLevel(modelData, "easy");
        
        
        instance.hint(modelData, 0);
        assertEquals("e _ _ _ _ _ _ ",modelData.getGameWord());
        
        instance.hint(modelData, 6);
        assertEquals("e _ _ _ _ _ e ",modelData.getGameWord());
        
        instance.hint(null, 1);
        assertEquals("e _ _ _ _ _ e ",modelData.getGameWord());
        
        try{
            instance.hint(modelData,8);
        }catch(GuessTheWordException e)
        {
            assertEquals("BadPosition", e.getMessage());
        }
        
        
    }

    /**
     * Test of shoot method, of class ModelLogic.
     * @throws java.lang.Exception
     */
    @Test
    public void testShoot() throws Exception{
        System.out.println("shoot");
        ModelData modelData = new ModelData();
        char[] word = "example".toCharArray();
        char[] word2 = "Example".toCharArray();

        ModelLogic instance = new ModelLogic();
        
        
        instance.setWord(modelData,word);
        instance.setLevel(modelData, "easy");
        
        instance.shoot(modelData, word2);                                        
        assertFalse(modelData.getWin());
        
        instance.shoot(null, word2); 
        instance.shoot(modelData, null);                                                  
        assertFalse(modelData.getWin());
        
        instance.shoot(modelData, word);                                                   
        assertTrue(modelData.getWin());

    }

    /**
     * Test of pass method, of class ModelLogic.
     * @throws java.lang.Exception
     */
    @Test
    public void testPass() throws Exception{
        System.out.println("pass");
        ModelData modelData = new ModelData();
        ModelLogic instance = new ModelLogic();
        
        instance.pass(null);
        
        instance.pass(modelData);
        assertFalse(modelData.getWin());

    }

    /**
     * Test of clearn method, of class ModelLogic.
     */
    @Test
    public void testClearn() {
        System.out.println("clearn");
        ModelData modelData = null;
        ModelLogic instance = new ModelLogic();
        instance.clearn(modelData);                                             //null test if error
        
        instance.clearn(null);
    }

    /**
     * Test of save method, of class ModelLogic.
     * @throws java.lang.Exception
     */
    @Test
    public void testSaveAndRead() throws Exception {
        System.out.println("save");
        ModelData data = new ModelData();
        ModelData expectedData = new ModelData();
        ModelLogic instance = new ModelLogic();
        ModelData.Level level = ModelData.Level.EASY;
        instance.setWord(data,"example".toCharArray());
        instance.chooseLevel(data, level);
        instance.setLevel(data);
        instance.save(data);
        instance.read(expectedData);
        
        assertTrue(equals(data, expectedData));
        
        instance.save(null);
        instance.read(null);
        
    }
    
    /**
     * Method to compare two value of data
     * @param set
     * @param expected
     * @return
     */
    public boolean equals(ModelData set, ModelData expected)
    {
        Boolean correct = true;
        
        if(set.getPoints() != expected.getPoints()) correct = false;
        if(set.getLenghtWord() != expected.getLenghtWord()) correct = false;
        if(set.getHint() != expected.getHint()) correct = false;
        if(set.getShoots() != expected.getShoots()) correct = false;
        if(set.getWin() != expected.getWin()) correct = false;
        if(set.getLevel() != expected.getLevel()) correct = false;
        if(!set.getEnteredWord().equals(expected.getEnteredWord())) correct = false;
        if(!set.getGameWord().equals(expected.getGameWord())) correct = false;
                
        
        return correct;
    }
    
}
