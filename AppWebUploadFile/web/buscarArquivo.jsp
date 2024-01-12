<%-- 
    Document   : buscaArquivo
    Created on : Sep 15, 2010, 2:29:45 PM
    Author     : mertins
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body style="background-color: goldenrod">
        <form action="downloadFile"  method="post">
            <table>
                <tr>
                    <td>Nome do arquivo</td>
                    <td> <input name="nomearquivo" type="text"/> </td>
                </tr>
                <tr>
                    <td>Onde buscar?</td>
                    <td>
                        <input type="radio" name="localarmaz" value="ARQUIVO" checked/>
                        <span>Disco</span>
                        <input type="radio" name="localarmaz" value="BD" />
                        <span>Banco de Dados</span>
                    </td>
                </tr>
                <tr>
                    <td><input name="save" type="submit" value="Buscar do Servidor" /></td>
                </tr>
            </table>
    </body>
</html>
