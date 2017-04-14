/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafal.grzelec.guesstheword.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rafal.grzelec.guesstheword.model.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import com.rafal.grzelec.guesstheword.exceptions.GuessTheWordException;

/**
 * Servlrt to management the state of the game
 * @author Rafał Grzelec
 * @version 1.0
 */
@WebServlet(name = "Game", urlPatterns = {"/Game"})
public class Game extends HttpServlet {
    
    /**
     * Varible stored instance of model logic
     */
    private ModelLogic model;
    
    /**
     * Function to inicjalizace varible model
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException {
        
        model = new ModelLogic();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession mySession = request.getSession();
        
        boolean isSession = false;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
        for(Cookie cookie : cookies)
            if(cookie.getName().equals("session")&&cookie.getValue().equals(mySession.getId())) isSession = true;
        }
            
        if(!isSession )
        {
            ModelData data = new ModelData();
            List<String> history = new ArrayList<String>();
            mySession.setAttribute("data", data);
            mySession.setAttribute("history", history);
            Cookie newCookie = new Cookie("session", mySession.getId());
            newCookie.setMaxAge(30*60);
            response.addCookie(newCookie);
        }
        
        ModelData data = (ModelData)mySession.getAttribute("data");
        List<String> history = (List<String>)mySession.getAttribute("history");
        String action = (String)request.getParameter("action");
        
        if(action != null && data!=null && history!=null)
        {
            switch(action)
            {
                case "newGame":
                {
                String word = (String)request.getParameter("guessesWord");
                if(word != null && word.length() > 0)
                    {
                        try{
                            model.setWord(data, word.toCharArray());
                            model.chooseLevel(data, (String)request.getParameter("level"));
                            model.setLevel(data);
                            model.uncoverRandomLetters(data);
                            history.add("Created new game");
                            changeCookiesCounter(request,response);
                            response.sendRedirect("game.jsp");
                        }catch(GuessTheWordException e)
                        {
                            sendError(e.getMessage(), out);
                        }
                    }
                else sendError("Word not found", out);

                    break;
                }

                case "hint" :
                {
                    try {
                        int position = Integer.parseInt((String)request.getParameter("position"));
                        model.hint(data, position - 1);
                        response.sendRedirect("game.jsp");
                        history.add("Used hint, word: "+data.getGameWord());
                    } catch (GuessTheWordException ex) {
                        sendError(ex.getMessage(),out);
                    } catch(NumberFormatException | ArrayIndexOutOfBoundsException ex)
                    {
                        sendError("It is not a number",out);
                    }
                    break;
                }
                case "checkt":
                {
                    try{
                        String proposedWord = (String)request.getParameter("word");
                        model.shoot(data, proposedWord.toCharArray());
                        history.add("Proposed word : " + proposedWord);
                    }catch(IndexOutOfBoundsException ex)
                    {
                        sendError(ex.getMessage(), out);
                        break;
                    }

                    if(data.getShoots() <= 0 && !data.getWin())
                    {
                        showGameScore("Game over", out);
                        history.add("Game over, correct word : "+data.getEnteredWord());
                        model.clearn(data);
                        break;
                    }

                    if(data.getWin())
                    {
                        showGameScore("You Win\nPoints : " + data.getPoints(), out);
                        history.add("Game win, points : " + data.getPoints());
                        model.clearn(data);
                        break;
                    }
                    response.sendRedirect("game.jsp");
                    break;
                }
                case "pass" :
                    history.add("Game pass, correct word : "+data.getEnteredWord());
                    showGameScore("Game over", out);
                    model.clearn(data);
                    break;
                default:

            };
        }else sendError("",out);  
            
    }
        
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    /**
     * Method to send error to client
     * @param message
     * @param out 
     */
    private void sendError(String message, PrintWriter out)
    {
        out.println("<html>\n"
                    + "<head> <link rel=\"stylesheet\" href=\"css/styles.css\"> </head>"
                    + "<body>"
                    + "<h1>Error : " + message + "</h1>");
            
        out.println("<INPUT Type=\"button\" VALUE=\"Back\" onClick=\"history.go(-1);return true;\">"
                + "</body></html>");
    }
    
    /**
     * Method to show game score to client
     * @param message
     * @param out 
     */
    private void showGameScore(String message, PrintWriter out)
    {
        out.println("<html>\n"
                    + "<head> <link rel=\"stylesheet\" href=\"css/styles.css\"> </head>"
                    + "<body>"
                    + "<h1>" + message + "</h1>");
            
        out.println("<button type=\"button\" id=\"newGame\" onclick=\"location.href='index.html';\">Ok</button>"
                + "</body></html>");
    }
    
    /**
     * Method to increase or create cookie stored counter
     * @param request
     * @param response 
     */
    private void changeCookiesCounter(HttpServletRequest request, HttpServletResponse response)
    {
        boolean isCounter = false;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
        for(Cookie cookie : cookies)
            if(cookie.getName().equals("counter")) 
            {
                int counter = Integer.parseInt(cookie.getValue());
                counter++;
                cookie.setValue(Integer.toString(counter));
                response.addCookie(cookie);
                isCounter = true;
            }   
        }
        if(!isCounter)
        {
            Cookie cookieCounter = null;
            cookieCounter = new Cookie("counter","1");
            cookieCounter.setMaxAge(24*60*60);
            response.addCookie(cookieCounter);
        }
    }
}
