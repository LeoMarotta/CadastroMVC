package br.tche.ucpel.tads.doo2.filter;

import br.tche.ucpel.bd2.bean.Mensagem;
import br.tche.ucpel.bd2.dao.MensagemDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
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
@WebFilter(filterName = "TipoUsuarioFilter", urlPatterns = {"/*"})
public class TipoUsuarioFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Logger log = Logger.getLogger(TipoUsuarioFilter.class.getName());

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Boolean autenticado = (Boolean) req.getSession().getAttribute("UsuarioLogado");

        DataSource dataSource = null;
        Connection conn = null;

        try {
            if (autenticado == null || !autenticado) {
                if (!req.getRequestURI().endsWith("/login.jsp")) {
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            TipoUsuarioFilter.dispatcherErro(req, resp, ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
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
