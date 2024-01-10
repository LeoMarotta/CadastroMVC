<%--
    Document   : formDepartamento
    Created on : Sep 15, 2010, 2:20:40 PM
    Author     : mertins
--%>
<%@page import="br.tche.ucpel.bd2.bean.Departamento"%>
<%@page import="java.util.List"%>
<%@page errorPage="formerro.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Protótipo Cadastros JSP</title>
    </head>
    <link rel="stylesheet" href="css/layout.css" type="text/css"/>
    <script type="text/javascript">
        function limpar(){
            document.getElementById("txtCodigo").value="";
            document.getElementById("txtDescricao").value="";
            document.getElementById("txtLocalizacao").value="";
        }
         
        function salvar(){
            document.getElementById("acaoCRUD").value="salvar";
            document.formdept.submit();
        }

        function excluir(){
            document.getElementById("acaoCRUD").value="excluir";
            document.formdept.submit();
        }

        function atualizar(){
            document.getElementById("acaoCRUD").value="atualizar";
            document.formdept.submit();
        }

        function clickTabela(cod){
            document.getElementById("txtCodigo").value=cod;
            document.getElementById("acaoCRUD").value="carregar";
            document.formdept.submit();
        }
    </script>
    <body>
        <div id="container2">
            <div id="header">
                <img src="imagens/ucpel.png"/>
                <div class="tituloPri">Desenvolvimento Orientado a Objetos II</div>
            </div>
            <div id="leftBar">
                <div class="menu">Cadastros</div>
                <a class="menuItem" href="principal?acao=departamento">Departamento</a>
                <br/>
                <br/>
                <a class="menuSair" href="principal?acao=logout">Sair</a>
            </div>
            <div id="content">
                <form id="formdept" name="formdept" action="principal" method="post">
                    <div class="tituloCadastros">Cadastro de Departamento</div>
                    <%
                                Departamento dept = (Departamento) request.getAttribute("departamento");
                    %>
                    <br/>
                    <label class="labels" for="txtCodigo">Código</label><input readonly="true" style="background-color: #afafaf " class="inputs" id="txtCodigo" name="txtCodigo" type="text" value="<%=dept != null ? dept.getCod() : ""%>"/>
                    <br/>
                    <label class="labels" for="txtDescricao">Descrição</label><input class="inputs" id="txtDescricao" name="txtDescricao" type="text" value="<%=dept != null ? dept.getDescricao() : ""%>"/>
                    <br/>
                    <label class="labels" for="txtLocalizacao">Localização</label><input class="inputs" id="txtLocalizacao" name="txtLocalizacao" type="text" value="<%=dept != null ? dept.getLocalizacao() : ""%>"/>
                    <br/>
                    <input class="buttons" type="button" onclick="limpar()" value="Limpar"/>
                    <input class="buttons" type="button" onclick="salvar()" value="Salvar"/>
                    <input class="buttons" type="button" onclick="excluir()" value="Excluir"/>
                    <input class="buttons" type="submit" value="Atualizar"/>
                    <input type="hidden" id="acaoCRUD" name="acaoCRUD" value=""/>
                    <input type="hidden" name="acao" value="departamento"/>
                    <br/>
                    <br/>
                    <div class="tabela">
                        <table style="width:100%" border="0">
                            <thead class="cabecalhoTabela">
                                <tr><td>Código</td><td>Descrição</td><td>Localização</td></tr>
                            </thead>
                            <tbody>
                                <%
                                            List<Departamento> departamentos = (List<Departamento>) request.getAttribute("departamentos");
                                            for (Departamento deptLista : departamentos) {
                                                out.print(String.format("<tr onClick=\"clickTabela(%d)\" class=\"linhaTabela\">", deptLista.getCod()));
                                                out.print(String.format("<td class=\"linhaTabela\">%s</td><td>%s</td><td>%s</td>", deptLista.getCod(), deptLista.getDescricao(), deptLista.getLocalizacao()));
                                                out.print("</tr>");
                                            }
                                %>
                            </tbody>                    
                        </table>
                    </div>
                </form>
            </div>
            <div id="footer">
                <div class="tituloSec">Protótipo para exemplificar conteúdos da disciplina! Prof. Luciano Edson Mertins</div>
            </div>
        </div>        
    </body>
</html>
