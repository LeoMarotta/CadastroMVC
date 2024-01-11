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
    <style>
        body {
            margin: 0;
            padding: 0;
            font-size: 16px;
            font-family: verdana, Arial, Helvetica, sans-serif;
            color: #333333;
            background-color: #F4F4F4;
        }

        h1, h2 {
            color: #1B1B1B;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            background-color: #FFFFFF;
            border: 1px solid #CCCCCC;
            border-radius: 5px;
            margin-bottom: 10px;
            padding: 10px;
        }

        a {
            color: #1B1B1B;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            color: #2E2E2E;
        }

        /* Estilo para a lista de mensagens públicas */
        #public-messages {
            margin-top: 20px;
        }

        /* Estilo para a lista de mensagens privadas */
        #private-messages {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h1>Bem-vindo<%= (session.getAttribute("nomeUsuarioLogado") != null) ? (", " + session.getAttribute("nomeUsuarioLogado")) : "" %></h1>

    <h2 id="public-messages">Mensagens Públicas:</h2>
    <ul>
        <%
            List<Mensagem> mensagensPublicas = (List<Mensagem>) session.getAttribute("listaMsgs");
            if (mensagensPublicas != null && !mensagensPublicas.isEmpty()) {
                for (Mensagem mensagem : mensagensPublicas) {
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
            <li>Você não possui mensagens públicas.</li>
        <%
            }
        %>
    </ul>

    <h2 id="private-messages">Mensagens Privadas:</h2>
    <ul>
        <%
            List<Mensagem> mensagensPrivadas = (List<Mensagem>) session.getAttribute("listaMsgsPrivadas");
            if (mensagensPrivadas != null && !mensagensPrivadas.isEmpty()) {
                for (Mensagem mensagem : mensagensPrivadas) {
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
            <li>Você não possui mensagens privadas.</li>
        <%
            }
        %>
    </ul>
</body>
</html>