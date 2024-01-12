package br.tche.ucpel.tads.dooii;

import br.tche.ucpel.bd2.bean.Arquivo;
import br.tche.ucpel.bd2.dao.ArquivoDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author mertins
 */

@WebServlet(name = "uploadFileApache", urlPatterns = {"/uploadFileApache"})
public class UploadFileApache extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> itens = upload.parseRequest(req);
                boolean escreveArquivo = false;
                String fileName = null;
                String valorDoParametro = "";
                for (FileItem item : itens) {
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        if ("nomearquivo".equals(name)) {
                            valorDoParametro = item.getString();
                        } else if ("localarmaz".equals(name)) {
                            Logger.getLogger(UploadFileApache.class.getName()).log(Level.INFO, String.format("Informação no localarmaz [%s]", item.getString()));
                            escreveArquivo = "ARQUIVO".equals(item.getString());
                        }
                    } else {
                        String fieldName = item.getFieldName();
                        fileName = item.getName();
                        String contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();
                        if (escreveArquivo) {
                            this.gravaFile(valorDoParametro, fileName, item);
                        } else {
                            InputStream uploadedStream = item.getInputStream();
                            this.gravaBD(valorDoParametro, fileName, sizeInBytes, contentType, uploadedStream);
                            uploadedStream.close();
                        }
                    }
                }
                RequestDispatcher rd = req.getRequestDispatcher("enviarArquivo.jsp");
                req.setAttribute("uploadSuccess", String.format("%s_%s", valorDoParametro, fileName));
                req.setAttribute("ondeGravou", escreveArquivo == true ? "Arquivo" : "Banco de Dados");
                rd.forward(req, resp);
            } catch (Exception ex) {
                throw new ServletException("Não foi possível fazer o upload", ex);
            }
        }
    }

    private void gravaFile(String valorDoParametro, String fileName, FileItem item) throws IOException, Exception {
        File arquivo = new File(String.format("/home/leomarotta/Documentos/%s_%s", valorDoParametro, fileName));
        arquivo.createNewFile();
        item.write(arquivo);
        Logger.getLogger(UploadFileApache.class.getName()).log(Level.INFO, String.format("Gravou no Arquivo [%s]", String.format("%s_%s", valorDoParametro, fileName)));
    }

    private void gravaBD(String valorDoParametro, String fileName, long size, String contentType, InputStream in) throws NamingException, SQLException {
        Connection conn = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("jdbc/testeAula");
            conn = dataSource.getConnection();
            ArquivoDAO dao= new ArquivoDAO(conn);
            Arquivo arq=new Arquivo();
            arq.setNome(String.format("%s_%s", valorDoParametro, fileName));
            arq.setContentType(contentType);
            arq.setConteudo(in);
            dao.create(arq, size);
            Logger.getLogger(UploadFileApache.class.getName()).log(Level.INFO, String.format("Gravou no BD [%s]", String.format("%s_%s", valorDoParametro, fileName)));
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
            }
        }
    }
}
