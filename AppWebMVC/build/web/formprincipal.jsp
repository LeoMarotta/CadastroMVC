<%-- 
    Document   : formDepartamento
    Created on : Sep 15, 2010, 2:20:40 PM
    Author     : mertins
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="formerro.jsp"%>
<%
// Evitar cache das páginas
response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
response.setHeader("Pragma", "no-cache"); //HTTP 1.0
response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Protótipo Cadastros Java</title>
    </head>
    <link rel="stylesheet" href="css/layout.css" type="text/css"/>
    <body>
        <div id="container">
            <div id="header">
                <img src="imagens/ucpel.png">
                <div class="tituloPri">Desenvolvimento Orientado a Objetos II</div>
            </div>
            <div id="leftBar">
                <div class="menu">Cadastros</div>
                <a class="menuItem" href="principal?acao=departamento">Departamento</a>
                <a class="menuItem" href="principal?acao=funcionario">Funcionário</a>
                <br/>
                <br/>
                <a class="menuSair" href="principal?acao=logout">Sair</a>
            </div>
            <div id="content">
            </div>
            <div id="footer">
                <div class="tituloSec">Protótipo para exemplificar conteúdos da disciplina! Prof. Luciano Edson Mertins</div>
            </div>
        </div>        
    </body>
</html>