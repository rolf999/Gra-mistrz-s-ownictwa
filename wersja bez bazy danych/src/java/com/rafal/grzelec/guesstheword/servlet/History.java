/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafal.grzelec.guesstheword.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet to show gistory of game
 * @author Rafal Grzelec
 * @version 1.0
 */
@WebServlet(name = "History", urlPatterns = {"/History"})
public class History extends HttpServlet {

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
            HttpSession session = request.getSession();
        
        out.println("<head>"
                + "<link rel=\"stylesheet\" href=\"css/styles.css\">"
                + "</head>"
                + "<body>");
        
            
            
            out.println("<div class=\"menu\">"
                    + "<h1> History of game</h1><br>");
            
            String counter = "0";
            Cookie[] cookies = request.getCookies();
            if(cookies !=null){
            for(Cookie cookie : cookies)
                if(cookie.getName().equals("counter")) counter = cookie.getValue();
            }
            
            out.println("<table id=\"tArchive\" style=\"text-align:center;\">"
                        +"<tr>"
                        +"<td>Played games</td>"
                        +"<td>"+counter+"</td>"
                        +"</tr>"
                        +"<tr>"
                        +"<td colspan=\"2\">Archive</td>"
                        +" </tr>"
            
                        +" <tr>"
                        +"<td> Move </td>"
                        +"<td> Change </td>"
                        +"</tr>"
            );
            
            List<String> history = (List<String>)session.getAttribute("history");
            if(history != null){
            for(int i=0; i< history.size(); i++){
                out.println("<tr>"
                        + "<td>" + i + "</td>"
                        + "<td>" + history.get(i) + "</td>"
                        + "</tr>");
            }
            }
            out.println("</table></div>");

            out.println("<div class=\"back\">"
                    + "<FORM>"
                    + "<INPUT Type=\"button\" VALUE=\"Back\" onClick=\"history.go(-1);return true;\">"
                    + "</FORM>"
                    + "</div>"
                    + "</div>"
                    + "</body>");
        
        
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

}
