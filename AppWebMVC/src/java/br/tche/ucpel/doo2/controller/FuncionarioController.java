package br.tche.ucpel.doo2.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.tche.ucpel.bd2.bean.Departamento;
import br.tche.ucpel.bd2.bean.Funcionario;
import br.tche.ucpel.bd2.dao.FuncionarioDAO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;


public class FuncionarioController {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Connection conn;

    public FuncionarioController(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
        this.req = req;
        this.resp = resp;
        this.conn = conn;
    }

    public void processo() throws ServletException, IOException, SQLException, java.text.ParseException {
        String acaoCRUD = req.getParameter("acaoCRUD");

        if ("salvar".equals(acaoCRUD)) {
            salvar();
        } else if ("carregar".equals(acaoCRUD)) {
            carregar();
        } else if ("excluir".equals(acaoCRUD)) {
            excluir();
        }

        listaTodos();
        RequestDispatcher dispatcher = req.getRequestDispatcher("formfuncionario.jsp");
        dispatcher.forward(req, resp);
    }

    private void salvar() throws ServletException, IOException, SQLException, java.text.ParseException {
        String nome = req.getParameter("txtNome");
        String cargo = req.getParameter("txtCargo");
        String salarioStr = req.getParameter("txtSalario");
        String codDepartamentoStr = req.getParameter("txtCodDepartamento");
        
        String dtContratacaoStr = req.getParameter("txtDtContratacao");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dtContratacao = sdf.parse(dtContratacaoStr);
        
        BigDecimal salario = new BigDecimal(salarioStr);
        int codDepartamento = Integer.parseInt(codDepartamentoStr);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCargo(cargo);
        funcionario.setDtContratacao(dtContratacao);
        funcionario.setSalario(salario);

        Departamento departamento = new Departamento();
        departamento.setCod(codDepartamento);
        funcionario.setDepartamento(departamento);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conn);
        try {
            if (funcionario.getCod() == 0) {
                funcionarioDAO.create(funcionario);
            } else {
                funcionarioDAO.update(funcionario);
            }
        } catch (Exception ex) {
            ServletPrincipal.dispatcherErro(req, resp, String.format("Não foi possível salvar o funcionário. [%s]", ex.getMessage()));
        }
    }

    private void carregar() throws ServletException, IOException {
        int cod = Integer.parseInt(req.getParameter("cod"));
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conn);
        Funcionario funcionario = new Funcionario(cod);
        try {
            funcionario = funcionarioDAO.retrieve(funcionario);
            req.setAttribute("funcionario", funcionario);
        } catch (SQLException ex) {
            ServletPrincipal.dispatcherErro(req, resp, String.format("Não foi possível carregar o funcionário. [%s]", ex.getMessage()));
        }
    }

    private void excluir() throws ServletException, IOException {
        try {
            int cod = Integer.parseInt(req.getParameter("txtCodigo")); // Alteração aqui
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conn);
            funcionarioDAO.delete(new Funcionario(cod));
        } catch (Exception ex) {
            ServletPrincipal.dispatcherErro(req, resp, String.format("Não foi possível excluir o funcionário. [%s]", ex.getMessage()));
        }
    }

    private void listaTodos() throws ServletException, IOException, SQLException {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conn);
        List<Funcionario> funcionarios = funcionarioDAO.listaTodos();
        req.setAttribute("funcionarios", funcionarios);
    }
}