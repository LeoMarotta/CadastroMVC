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
        <title>Upload de arquivos</title>
    </head>
    <body style="background-color: wheat">
        <%
                    Object o = request.getAttribute("uploadSuccess");
                    Object onde=request.getAttribute("ondeGravou");
                    if (o != null) {
        %>
        <div style="font-style: italic;background-color: yellowgreen"><%=o.toString()%></div>
        <div style="font-style: italic;background-color: yellowgreen">Gravou no <%=onde.toString()%></div>
        <%
                    }
        %>
        <form action="uploadFileApache"  enctype="multipart/form-data" method="post">
            <table>
                <tr>
                    <td>Texto na frente do nome</td>
                    <td> <input name="nomearquivo" type="text"/> </td>
                </tr>
                <tr>
                    <td>Onde armazenar?</td>
                    <td> 
                        <input type="radio" name="localarmaz" value="ARQUIVO" checked/>
                        <span>Disco</span>
                        <input type="radio" name="localarmaz" value="BD" />
                        <span>Banco de Dados</span>
                    </td>
                </tr>
                <tr>
                    <td>Arquivo a armazenar</td>
                    <td><input name="uploaded" type="file" value="teste"/></td>
                </tr>
                <tr>
                    <td><input name="save" type="submit" value="Enviar para o Servidor" /></td>
                </tr>
            </table>
        </form>
    </body>
</html>
