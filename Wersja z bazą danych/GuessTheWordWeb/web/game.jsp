<%-- 
    Document   : game
    Created on : 2017-01-20, 15:35:08
    Author     : Rafal Grzelec
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,
                javax.servlet.*,
                javax.servlet.http.*,
                java.util.*,
                com.rafal.grzelec.guesstheword.model.ModelData"  %>
<% 
    
String gameWord  = "";
String position = "";
Integer leftHint = 0;
Integer leftShoot = 0;
ModelData data = (ModelData)session.getAttribute("data");
if(data != null)
{
    for(int i = 1; i<= data.getLenghtWord();i++)
    {
        position += Integer.toString(i) + " ";
    }
    gameWord = ((ModelData)session.getAttribute("data")).getGameWord();
    leftHint = ((ModelData)session.getAttribute("data")).getHint();
    leftShoot = ((ModelData)session.getAttribute("data")).getShoots();
}


    %>
<!DOCTYPE html>
<html>
    <head>
        <title>Guess the word</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/styles.css">
        
    </head>
    <body>
        <h1>GAME</h1>
        <div class="menu">
            Second player: <br><br>
            <%= gameWord%><br>
            <%= position%><br>
            <br>
            Left hint - <%= leftHint%><br>
            Left shoots - <%=leftShoot%><br><br>
            <form action="Game" method="GET">
                Position :<br><input type="text" name=position id="position">
                <button type="submit" name="action" value="hint" class="button">Hint</button><br>
                Proposed word:<br><input type="text" name=word id="word">
                <button type="submit" name="action" value="checkt" class="button">Checkt</button><br><br>
            </form>
        </div>
        <div class="option">
            <form action="Game" method="GET">
                <button type="submit" name="action" value="pass" class="button">Pass</button>
            </form>
            <form action="History" method="GET">
                <button type="submit" class="button">History</button>
            </form>
        </div>
    </body>
</html>
