<%-- 
    Document   : index
    Created on : Sep 15, 2010, 2:20:40 PM
    Author     : mertins
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="formerro.jsp"%>
<%
// Evitar cache das páginas
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
            response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<html>
    <head style="margin:0px;">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Protótipo Cadastros Java</title>
    </head>
    <link rel="stylesheet" href="css/layout.css" type="text/css"/>
    <body>
        <div id="container" >
            <div id="header">
                <img src="imagens/ucpel.png">
                <div class="tituloPri">Desenvolvimento Orientado a Objetos II</div>
            </div>
            <div id="leftBar"></div>
            <div id="contentLogin">
                <form action="principal" method="post">
                    <div id="cxLogin">
                        <div id="titCxLogin">Autenticação</div>
                        <br>
                        <label class="labelsLogin" for="txtUsuario">Usuário</label><input class="inputsLogin" id="txtUsuario" name="txtUsuario" type="text"/>
                        <br/>
                        <label class="labelsLogin" for="txtSenha">Senha</label><input class="inputsLogin" id="txtSenha" name="txtSenha" type="password"/>
                        <br>
                        <input class="buttonsLogin" type="submit" value="Ok"/>
                    </div>
                    <input type="hidden" id="acao" name="acao" value="login"/>
                </form>
            </div>
            <div id="footer">
                <div class="tituloSec">Protótipo para exemplificar conteúdos da disciplina! Prof. Luciano Edson Mertins</div>
            </div>
        </div>
    </body>
</html>
