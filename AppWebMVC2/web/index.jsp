<%-- 
    Document   : newjsp
    Created on : Sep 14, 2011, 10:06:51 AM
    Author     : mertins
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List,br.tche.ucpel.bd2.bean.Mensagem" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="EN" lang="EN" dir="ltr">
    <head profile="http://gmpg.org/xfn/11">
        <title>OnlineStudio</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <meta http-equiv="imagetoolbar" content="no" />
        <link rel="stylesheet" href="styles/layout.css" type="text/css" />
    </head>
    <body id="top">
        <div class="wrapper col1">
            <div id="header">
                <div id="topnav">
                    <ul>
                        <li><a href="login.jsp">Login</a><span>Faça o login para verificar suas mensagens</span></li>
                        <li class="active"><a href="index.jsp">Página Principal</a></li>
                    </ul>
                </div>
                <div id="logo">
                    <h1><a href="#">OnlineStudio</a></h1>
                    <p>Free CSS Website Template</p>
                </div>
                <br class="clear" />
            </div>
        </div>
        <div class="wrapper col2">
            <div id="intro">
                <div class="fl_left">
                    <h1>Sistema de Mensagens</h1>
                    <p>Teste da disciplina de Desenvolvimento Orientado a Objetos II - Prof. Luciano Edson Mertins</a></p>
                    <a href="http://www.free-css.com/free-css-templates/page118/onlinestudio.php#bookmarks">Fonte do Layout</a>
                </div>
                <div class="fl_right">
                    <ul>
                        <li><a href="#"><img src="images/rss.gif" alt="" border="0" style="width: 99px; height: 99px;"/></a></li>
                        <li class="last"><a href="#"><img src="images/twitter.gif" alt="" border="0" style="width: 99px; height: 99px;"/></a></li>
                    </ul>
                </div>
                <br class="clear" />
            </div>
        </div>
        <div class="wrapper col3">
            <div id="latest">
                <ul>
                    <%
                        List<Mensagem> lista = (List<Mensagem>) session.getAttribute("listaMsgs");
                        int count=0;
                        for (Mensagem msg : lista) {
                    %>

                    <li  <%=++count%3==0?"class='last'":""%> >
                        <h2><%=msg.getTitulo()%></h2>
                        <p><%=msg.getTexto()%><a href="<%=msg.getLink()%>" target="_blank"> Mais</a></p>
                    </li>
                    <%   }%>
                </ul>
                <br class="clear" />
            </div>
        </div>
        <div class="wrapper col6">
            <div id="copyright">
                <p class="fl_left">Copyright &copy; 2011 - All Rights Reserved - <a href="#">Domain Name</a></p>
                <p class="fl_right">Template by <a href="http://www.os-templates.com/" title="Free Website Templates">OS Templates</a></p>
                <br class="clear" />
            </div>
        </div>
    </body>
</html>
