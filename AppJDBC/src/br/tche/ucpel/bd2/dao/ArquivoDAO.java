package br.tche.ucpel.bd2.dao;

import br.tche.ucpel.bd2.bean.Arquivo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mertins
 */
public class ArquivoDAO {

    private Connection conexao;

    public ArquivoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void create(Arquivo arquivo, long size) throws SQLException {
        PreparedStatement ps = conexao.prepareStatement("INSERT INTO upload (nome,contenttype,conteudo) VALUES (?, ?, ?)");
        ps.setString(1, arquivo.getNome());
        ps.setString(2, arquivo.getContentType());
        ps.setBinaryStream(3, arquivo.getConteudo(), (int) size);    // sem o int dá bug em alguns drivers jdbc do postgresql
        ps.executeUpdate();
    }
    
        public Arquivo download(String nomeArquivo) throws SQLException {
        try (PreparedStatement ps = conexao.prepareStatement("SELECT * FROM upload WHERE nome = ?")) {
            ps.setString(1, nomeArquivo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Arquivo arquivo = new Arquivo();
                    arquivo.setId(rs.getInt("cod"));
                    arquivo.setNome(rs.getString("nome"));
                    arquivo.setContentType(rs.getString("contenttype"));
                    arquivo.setConteudo(rs.getBinaryStream("conteudo"));
                    return arquivo;
                }
            }
        }
        return null;
    }
}
