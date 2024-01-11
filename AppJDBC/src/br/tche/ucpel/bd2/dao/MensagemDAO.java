package br.tche.ucpel.bd2.dao;

import br.tche.ucpel.bd2.bean.Mensagem;
import br.tche.ucpel.bd2.bean.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mertins
 */
public class MensagemDAO {

    private Connection conexao;

    public MensagemDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Mensagem> listaUltimasPublicas(int numElementos) throws SQLException {
        List<Mensagem> lista = new ArrayList<Mensagem>();
        String sql = "SELECT COD,TITULO,TEXTO,LINK FROM MENSAGEM WHERE CODUSUARIO IS NULL ORDER BY COD DESC";
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int count = 0;
        while (rs.next() && count < numElementos) {
            Mensagem elem = new Mensagem();
            elem.setCod(rs.getInt("COD"));
            elem.setTitulo(rs.getString("TITULO"));
            elem.setTexto(rs.getString("TEXTO"));
            elem.setLink(rs.getString("LINK"));
            lista.add(elem);
            count++;
        }
        rs.close();
        st.close();
        return lista;
    }
    
    public List<Mensagem> listaMensagensComUsuario() throws SQLException {
        List<Mensagem> lista = new ArrayList<>();
        String sql = "SELECT COD, TITULO, TEXTO, LINK, CODUSUARIO FROM MENSAGEM WHERE CODUSUARIO IS NOT NULL ORDER BY COD DESC";

        try (PreparedStatement ps = this.conexao.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mensagem elem = new Mensagem();
                    elem.setCod(rs.getInt("COD"));
                    elem.setTitulo(rs.getString("TITULO"));
                    elem.setTexto(rs.getString("TEXTO"));
                    elem.setLink(rs.getString("LINK"));

                    int codUsuario = rs.getInt("CODUSUARIO");
                    UsuarioDAO usuarioDAO = new UsuarioDAO(this.conexao);
                    Usuario usuario = usuarioDAO.retrieveByCod(codUsuario);
                    elem.setUsuario(usuario);
                    
                    lista.add(elem);
                }
            }
        }
        return lista;
    }

}
