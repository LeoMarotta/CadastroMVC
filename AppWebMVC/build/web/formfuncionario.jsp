<%-- 
    Document   : formfuncionario
    Created on : 9 de jan de 2024, 08:32:22
    Author     : leomarotta
--%>

<%@page import="br.tche.ucpel.bd2.bean.Funcionario"%>
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
        function limpar() {
            document.getElementById("txtCodigo").value = "";
            document.getElementById("txtNome").value = "";
            document.getElementById("txtCargo").value = "";
            document.getElementById("txtDtContratacao").value = "";
            document.getElementById("txtSalario").value = "";
            document.getElementById("txtCodDepartamento").value = "";
        }

        function salvar() {
            document.getElementById("acaoCRUD").value = "salvar";
            document.formfunc.submit();
        }

        function excluir() {
            document.getElementById("acaoCRUD").value = "excluir";
            document.formfunc.submit();
        }

        function atualizar() {
            document.getElementById("acaoCRUD").value = "atualizar";
            document.formfunc.submit();
        }

        function clickTabela(cod) {
            document.getElementById("txtCodigo").value = cod;
            document.getElementById("acaoCRUD").value = "funcionario";
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
                <a class="menuItem" href="principal?acao=funcionario">Funcionario</a>
                <br/>
                <br/>
                <a class="menuSair" href="principal?acao=logout">Sair</a>
            </div>
            <div id="content">
                <form id="formfunc" name="formfunc" action="principal" method="post">
                <div class="tituloCadastros">Cadastro de Funcionário</div>
                 <% Funcionario func = (Funcionario) request.getAttribute("funcionario"); %>
                <br/>
                <label class="labels" for="txtCodigo">Código</label><input readonly="true" style="background-color: #afafaf " class="inputs" id="txtCodigo" name="txtCodigo" type="text" value="<%=func != null ? func.getCod() : ""%>"/>
                <br/>
                <label class="labels" for="txtNome">Nome</label><input class="inputs" id="txtNome" name="txtNome" type="text" value="<%=func != null ? func.getNome() : ""%>"/>
                <br/>
                <label class="labels" for="txtCargo">Cargo</label><input class="inputs" id="txtCargo" name="txtCargo" type="text" value="<%=func != null ? func.getCargo() : ""%>"/>
                <br/>
                <label class="labels" for="txtDtContratacao">Data de Contratação</label><input class="inputs" id="txtDtContratacao" name="txtDtContratacao" type="text" value="<%=func != null ? func.getDtContratacao() : ""%>"/>
                <br/>
                <label class="labels" for="txtSalario">Salário</label><input class="inputs" id="txtSalario" name="txtSalario" type="text" value="<%=func != null ? func.getSalario() : ""%>"/>
                <br/>
                <label class="labels" for="txtCodDepartamento">Código do Departamento</label><input class="inputs" id="txtCodDepartamento" name="txtCodDepartamento" type="text" value="<%=func != null && func.getDepartamento() != null ? func.getDepartamento().getCod() : ""%>"/>
                <br/>
                <input class="buttons" type="button" onclick="limpar()" value="Limpar"/>
                <input class="buttons" type="button" onclick="salvar()" value="Salvar"/>
                <input class="buttons" type="button" onclick="excluir()" value="Excluir"/>
                <input class="buttons" type="submit" value="Atualizar"/>
                <input type="hidden" id="acaoCRUD" name="acaoCRUD" value=""/>
                <input type="hidden" name="acao" value="funcionario"/>
                <input type="hidden" id="txtDepartamentoCod" name="txtDepartamentoCod" value="<%=func != null && func.getDepartamento() != null ? func.getDepartamento().getCod() : ""%>"/>
                
                <div class="tabela">
                           <table style="width:100%" border="0">
                               <thead class="cabecalhoTabela">
                                   <tr>
                                       <td>Código</td>
                                       <td>Nome</td>
                                       <td>Cargo</td>
                                       <td>Data de Contratação</td>
                                       <td>Salário</td>
                                       <td>Departamento</td>
                                   </tr>
                               </thead>
                               <tbody>
                                   <% List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
                                       for (Funcionario funcLista : funcionarios) {
                                           out.print(String.format("<tr onClick=\"clickTabela(%d)\" class=\"linhaTabela\">", funcLista.getCod()));
                                           out.print(String.format("<td class=\"linhaTabela\">%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
                                               funcLista.getCod(), funcLista.getNome(), funcLista.getCargo(), funcLista.getDtContratacao(), funcLista.getSalario(), funcLista.getDepartamento().getDescricao()));
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
