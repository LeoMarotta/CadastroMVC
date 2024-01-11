<%-- 
    Document   : loginaceito
    Created on : 11 de jan de 2024, 08:47:54
    Author     : leomarotta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.tche.ucpel.bd2.bean.Mensagem"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Aceito</title>
</head>
<body>
    <h1>Bem-vindo<%= (session.getAttribute("nomeUsuarioLogado") != null) ? (", " + session.getAttribute("nomeUsuarioLogado")) : "" %>
</h1>

    <h2>Suas Mensagens:</h2>
    <ul>
        <%
            List<Mensagem> mensagens = (List<Mensagem>) session.getAttribute("listaMsgs");
            if (mensagens != null && !mensagens.isEmpty()) {
                for (Mensagem mensagem : mensagens) {
        %>
            <li>
                <strong><%= mensagem.getTitulo() %></strong>
                <p><%= mensagem.getTexto() %></p>
                <p>Link: <a href="<%= mensagem.getLink() %>">Mais</a></p>
            </li>
        <%
                }
            } else {
        %>
            <li>Você não possui mensagens.</li>
        <%
            }
        %>
    </ul>
</body>
</html>