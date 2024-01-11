<%@page contentType="text/html" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-size: 12px;
            font-family: verdana, Arial, Helvetica, sans-serif;
            color: #CCCCCC;
            background-color: #2E2E2E;
        }

        form {
            background-color: #FFFFFF;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            margin: 50px auto;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333333;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            box-sizing: border-box;
            border: 1px solid #CCCCCC;
            border-radius: 4px;
        }

        input[type="text"],
        input[type="password"] {
            margin-bottom: 15px;
        }

        input[type="submit"] {
            background-color: #1B1B1B; 
            color: #FFFFFF;
            cursor: pointer;
            border: none;
            border-radius: 4px;
            padding: 10px;
        }

        input[type="submit"]:hover {
            background-color: #2E2E2E;  
        }

        p.error-message {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <form action="${pageContext.request.contextPath}/ServletLogin" method="post">
        <label for="Login">Usuário:</label>
        <input type="text" id="Login" name="Login" required>

        <label for="Senha">Senha:</label>
        <input type="password" id="Senha" name="Senha" required>

        <input type="submit" value="Login">
    </form>
</body>
</html>
