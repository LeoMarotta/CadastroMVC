package br.tche.ucpel.tads.doo2.filter;

import br.tche.ucpel.bd2.bean.Mensagem;
import br.tche.ucpel.bd2.dao.MensagemDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author mertins
 */

@WebFilter(filterName = "TipoUsuarioFilter", urlPatterns = {"/index.jsp"})
public class TipoUsuarioFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        DataSource dataSource = null;
        Connection conn = null;
        
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("jdbc/testeAula");
            conn = dataSource.getConnection();
            MensagemDAO msgDAO=new MensagemDAO(conn);

            List<Mensagem> lista=msgDAO.listaUltimasPublicas(3);
            req.getSession().setAttribute("listaMsgs", lista);

            conn.close();
            chain.doFilter(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            TipoUsuarioFilter.dispatcherErro(req, resp, ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
            }
        }
    }

    @Override
    public void destroy() {
    }

    public static void dispatcherErro(HttpServletRequest req, HttpServletResponse resp, String msg) throws ServletException, IOException {
        req.setAttribute("mensagem", msg);
        RequestDispatcher dispatcher = req.getRequestDispatcher("formerro.jsp");
        dispatcher.forward(req, resp);

    }
}