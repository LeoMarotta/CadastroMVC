package br.tche.ucpel.bd2.dao;
import br.tche.ucpel.bd2.bean.Departamento;
import br.tche.ucpel.bd2.bean.Funcionario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
/**
 * Pseudo DAO (Data Access Objetc) para realizar as operações de CRUD - expressão em língua 
 * IInglesa Create, Retrieve, Update e Delete.
 * <h1>ATENÇÃO! Para simplificar o entendimento, não foi aplicada o real pattern DAO!!!!</h1>
 * @author mertins
 */
public class FuncionarioDAO {
    private Connection conexao;
    /**
     * Construtor único, para garantir a existência de uma conexão com um SGBD
     * @param conexao Connection já aberta com um SGBD
     */
    public FuncionarioDAO(Connection conexao) {
        this.conexao = conexao;
    }
    /**
     * Se o funcioanário for válido, este método irá fazer o INSERT no SGBD.
     * O Código será inserido pela Sequência e será colocado novamente no objeto Funcionário.
     * @param func Funcionario a ser inserido
     * @throws java.sql.SQLException Qualquer erro entre o Sistema e o Banco será devolvido nesta Exceção
     */
    public void create(Funcionario func) throws SQLException {
        if (this.valida(func)) {
            String sql = " INSERT INTO funcionario(cod, nome, cargo, dtcontratacao, codgerente, salario, coddepartamento) " +
                    " VALUES (NEXTVAL('SEQFUNCIONARIO'), ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = this.conexao.prepareStatement(sql);
            pst.setString(1, func.getNome());
            pst.setString(2, func.getCargo());
            java.sql.Date dtSQL = new java.sql.Date(func.getDtContratacao().getTime());
            pst.setDate(3, dtSQL);
            if (func.getGerente() == null) {
                pst.setNull(4, Types.NUMERIC);
            } else {
                pst.setLong(4, func.getGerente().getCod());
            }
            pst.setDouble(5, func.getSalario().doubleValue());
            pst.setLong(6, func.getDepartamento().getCod());
            pst.executeUpdate();
            Statement st = this.conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT CURRVAL('SEQFUNCIONARIO')");
            if (rs.next()) {
                func.setCod(rs.getInt(1));
            }
            rs.close();
            st.close();
            pst.close();
        }
    }
    /**
     * Retorna o funcionario do SGBD de acordo com o código do funcionario recebido.
     * @param func Funcionario a ser carregado do SGBD
     * @return Funcionario do SGBD
     * @throws java.sql.SQLException Qualquer erro entre o Sistema e o Banco será devolvido nesta Exceção
     */
    public Funcionario retrieve(Funcionario func) throws SQLException {
        Funcionario funcRet = null;
        String sql = "SELECT cod, nome, cargo, dtcontratacao, codgerente, salario, coddepartamento FROM funcionario WHERE cod=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setInt(1, func.getCod());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            funcRet = new Funcionario();
            funcRet.setCod(rs.getInt("COD"));
            funcRet.setNome(rs.getString("NOME"));
            funcRet.setCargo(rs.getString("CARGO"));
            funcRet.setDtContratacao(rs.getDate("DTCONTRATACAO"));
            Integer codGerente = rs.getInt("CODGERENTE");
            if (!rs.wasNull()) {
                Funcionario gerente = new Funcionario();
                gerente.setCod(codGerente);
                funcRet.setGerente(this.retrieve(gerente));
            }
            funcRet.setSalario(new BigDecimal(rs.getDouble("salario")));
            DepartamentoDAO deptDAO = new DepartamentoDAO(conexao);
            Departamento dept = new Departamento(rs.getInt("coddepartamento"));
            dept = deptDAO.retrieve(dept);
            funcRet.setDepartamento(dept);
        }
        rs.close();
        pst.close();
        return funcRet;
    }

    /**
     * Atualiza o funcionario no SGBD.
     * @param func Funcionario a ser atualizado do SGBD
     * @throws java.sql.SQLException Qualquer erro entre o Sistema e o Banco será devolvido nesta Exceção
     */
    public void update(Funcionario func) throws SQLException {
        if (this.valida(func)) {
            String sql = "UPDATE funcionario SET nome=?, cargo=?, dtcontratacao=?, codgerente=?, salario=?, coddepartamento=? WHERE  cod=?";
            PreparedStatement pst = this.conexao.prepareStatement(sql);
            pst.setString(1, func.getNome());
            pst.setString(2, func.getCargo());
            pst.setDate(3, new java.sql.Date(func.getDtContratacao().getTime()));
            if (func.getGerente() == null) {
                pst.setNull(4, Types.NUMERIC);
            } else {
                pst.setLong(4, func.getGerente().getCod());
            }
            pst.setDouble(5, func.getSalario().doubleValue());
            pst.setLong(6, func.getDepartamento().getCod());
            pst.setInt(7, func.getCod());
            pst.executeUpdate();
            pst.close();
        }
    }

    /**
     * Remove o código do funcionario do SGBD.
     * @param func Funcionario a ser excluído. Necessita apenas do atributo COD
     * @throws java.sql.SQLException Qualquer erro entre o Sistema e o Banco será devolvido nesta Exceção
     */
    public void delete(Funcionario func) throws SQLException {
        String sql = "DELETE FROM FUNCIONARIO WHERE COD=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setInt(1, func.getCod());
        pst.executeUpdate();
        pst.close();
    }

    /**
     * Retorna uma Lista com todos os Funcionarios cadastrados no SGBD.
     * @return Lista com os funcionarios.
     * @throws java.sql.SQLException Qualquer erro entre o Sistema e o Banco será devolvido nesta Exceção
     */
    public List<Funcionario> listaTodos() throws SQLException {
        List<Funcionario> lista = new ArrayList<Funcionario>();
        String sql = "SELECT cod, nome, cargo, dtcontratacao, codgerente, salario, coddepartamento FROM funcionario ORDER BY cod";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Funcionario func = new Funcionario();
            func.setCod(rs.getInt("COD"));
            func.setNome(rs.getString("NOME"));
            func.setCargo(rs.getString("CARGO"));
            func.setDtContratacao(rs.getDate("DTCONTRATACAO"));
            Integer codGerente = rs.getInt("CODGERENTE");
            if (!rs.wasNull()) {
                Funcionario gerente = new Funcionario();
                gerente.setCod(codGerente);
                func.setGerente(this.retrieve(gerente));
            }
            func.setSalario(new BigDecimal(rs.getDouble("salario")));
            DepartamentoDAO deptDAO = new DepartamentoDAO(conexao);
            Departamento dept = new Departamento(rs.getInt("coddepartamento"));
            dept = deptDAO.retrieve(dept);
            func.setDepartamento(dept);
            lista.add(func);
        }
        rs.close();
        st.close();
        return lista;
    }

    /**
     * Aplica os testes para as regras de negócio. 
     * @param func Funcioanario a ser testado
     * @return true se o Funcionario atende as regras de negócio, ou false em caso contrário.
     */
    public boolean valida(Funcionario func) {
        boolean ret = false;
        if (func != null) {
            ret = true;
        }
        return ret;
    }
}
