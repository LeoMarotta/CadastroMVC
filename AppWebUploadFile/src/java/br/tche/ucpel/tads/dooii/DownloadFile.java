package br.tche.ucpel.tads.dooii;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mertins
 */
@WebServlet(name = "DownloadFile", urlPatterns = {"/downloadFile"})
public class DownloadFile extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nomeArquivo = req.getParameter("nomearquivo");
        String local = req.getParameter("localarmaz");
        Logger.getLogger(DownloadFile.class.getName()).log(Level.INFO, String.format("Nome arquivo [%s]    Local[%s]", nomeArquivo, local));
        if (nomeArquivo == null || local == null) {
            resp.sendRedirect("buscarArquivo.jsp");
            return;
        } else {
            if ("ARQUIVO".equals(local)) {
                String msg = String.format("[%s] Precisa encontrar o arquivo no disco,"
                        + " se existir, entregar para o browser"
                        + " se não existir, direcionar para um erro", local);
                Logger.getLogger(DownloadFile.class.getName()).log(Level.INFO, msg);
                msgHtml(req, resp, msg);
            } else {
                String msg = String.format("[%s] Precisa encontrar o arquivo no Banco de Dados,"
                        + " se existir, entregar para o browser"
                        + " se não existir, direcionar para um erro", local);
                Logger.getLogger(DownloadFile.class.getName()).log(Level.INFO, msg);
                msgHtml(req, resp, msg);
            }
        }
    }

    private void msgHtml(HttpServletRequest req, HttpServletResponse resp, String msg) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.write(String.format("<html><body style='background-color: goldenrod'>%s</body></html", msg));
        out.close();
    }
}
