package br.tche.ucpel.bd2.dao;

import br.tche.ucpel.bd2.bean.Mensagem;
import java.sql.Connection;
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
}
