package br.tche.ucpel.bd2.dao;

import br.tche.ucpel.bd2.bean.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Pseudo DAO (Data Access Objetc) para realizar as operações de CRUD - expressão em língua Inglesa Create, Retrieve, Update e Delete.
 * <h1>ATENÇÃO! Para simplificar o entendimento, não foi aplicada o real pattern DAO!!!!</h1>
 * @author mertins
 */
public class UsuarioDAO {

    private Connection conexao;

    /**
     * Construtor único, para garantir a existência de uma conexão com um SGBD
     * @param conexao Connection já aberta com um SGBD
     */
    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Retorna o usuário do SGBD de acordo com o login especificado.
     * @param dept Departamento a ser carregado do SGBD
     * @return Departamento do SGBD
     * @throws java.sql.SQLException Qualquer erro entre o Sistema e o Banco será devolvido nesta Exceção
     */
    public Usuario retrieve(String login) throws SQLException {
        Usuario usrDept = new Usuario();
        String sql = "SELECT COD,LOGIN,SENHA FROM USUARIO WHERE LOGIN=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setString(1, login);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            usrDept.setCod(rs.getInt("COD"));
            usrDept.setLogin(rs.getString("LOGIN"));
            usrDept.setSenha(rs.getString("SENHA"));
        }
        rs.close();
        pst.close();
        return usrDept;
    }
    
    public Usuario retrieveByCod(int cod) throws SQLException {
        Usuario usrDept = new Usuario();
        String sql = "SELECT COD, LOGIN, SENHA FROM USUARIO WHERE COD=?";
        try (PreparedStatement pst = this.conexao.prepareStatement(sql)) {
            pst.setInt(1, cod);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    usrDept.setCod(rs.getInt("COD"));
                    usrDept.setLogin(rs.getString("LOGIN"));
                    usrDept.setSenha(rs.getString("SENHA"));
                }
            }
        }
        return usrDept;
    }
    
}
