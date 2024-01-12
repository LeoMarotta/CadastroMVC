package br.tche.ucpel.tads.dooii;

import br.tche.ucpel.bd2.bean.Arquivo;
import br.tche.ucpel.bd2.dao.ArquivoDAO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author mertins
 */

@WebServlet(name = "DownloadFile", urlPatterns = {"/downloadFile"})
public class DownloadFile extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String nomeArquivo = req.getParameter("nomearquivo");
        String local = req.getParameter("localarmaz");
        
        Logger.getLogger(DownloadFile.class.getName()).log(Level.INFO, String.format("Nome arquivo [%s]    Local[%s]", nomeArquivo, local));
        
        if (nomeArquivo == null || local == null) {
            resp.sendRedirect("buscarArquivo.jsp");
            return;
        }
        
        if ("ARQUIVO".equals(local)) {
            realizarDownloadDoDisco(req, resp, nomeArquivo);
        } else if ("BD".equals(local)) {
            try {
                realizarDownloadDoBancoDeDados(req, resp, nomeArquivo);
            } catch (NamingException ex) {
                Logger.getLogger(DownloadFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void msgHtml(HttpServletRequest req, HttpServletResponse resp, String msg) 
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.write(String.format("<html><body style='background-color: goldenrod'>%s</body></html", msg));
        out.close();
    }
    
    private void realizarDownloadDoDisco(HttpServletRequest req, HttpServletResponse resp, String nomeArquivo)
            throws ServletException, IOException {
        String caminhoDoArquivo = "home/leomarotta/Documentos/" + nomeArquivo;

        if (Files.exists(Paths.get(caminhoDoArquivo))) {
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");

            try (InputStream arquivoStream = new FileInputStream(caminhoDoArquivo);
                 OutputStream outputStream = resp.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = arquivoStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String msg = String.format("[%s] Arquivo não encontrado no disco", nomeArquivo);
            Logger.getLogger(DownloadFile.class.getName()).log(Level.INFO, msg);
            msgHtml(req, resp, msg);
        }
    }

    private void realizarDownloadDoBancoDeDados(HttpServletRequest req, HttpServletResponse resp, String nomeArquivo)
            throws ServletException, IOException, NamingException {
        
        Connection conn = null;
        
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("jdbc/testeAula");
            conn = dataSource.getConnection();
            ArquivoDAO dao= new ArquivoDAO(conn);

            Arquivo arquivo = dao.download(nomeArquivo);

            if (arquivo != null) {
                resp.setContentType(arquivo.getContentType());
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");

                try (InputStream arquivoStream = arquivo.getConteudo();
                     OutputStream outputStream = resp.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = arquivoStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                String msg = String.format("[%s] Arquivo não encontrado no Banco de Dados", nomeArquivo);
                Logger.getLogger(DownloadFile.class.getName()).log(Level.INFO, msg);
                msgHtml(req, resp, msg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
