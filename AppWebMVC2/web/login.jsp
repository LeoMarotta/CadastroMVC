<%-- 
    Document   : login-feito
    Created on : 10 de jan de 2024, 11:55:31
    Author     : leomarotta
--%>

<%@page contentType="text/html" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/ServletLogin" method="post">
        <label for="Login">Usuário:</label>
        <input type="text" id="Login" name="Login" required><br>

        <label for="Senha">Senha:</label>
        <input type="password" id="Senha" name="Senha" required><br>

        <c:if test="${param.erro eq 'true'}">
            <p style="color: red;">Credenciais inválidas. Tente novamente.</p>
        </c:if>

        <input type="submit" value="Login">
    </form>
</body>
</html>