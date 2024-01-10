package br.tche.ucpel.doo2.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mertins
 */
public class Autenticacao implements Filter {

    public Autenticacao() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Logger log = Logger.getLogger(Autenticacao.class.getName());
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Boolean autenticado = (Boolean) req.getSession().getAttribute("UsuarioLogado");
        if (autenticado == null || !autenticado) {
            log.log(Level.INFO, String.format("Sem usuário autenticado [%s]", autenticado));
            if (!acessoPermitido(req)) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.forward(req, resp);
                return;
            }
        }
        log.log(Level.INFO, String.format("Usuário autenticado [%s]", autenticado));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private boolean acessoPermitido(HttpServletRequest req) {
        boolean ret = false;
        String pagina = req.getRequestURI();
        String acao = req.getParameter("acao");
        if (pagina != null && "login".equals(acao) && pagina.endsWith("principal")) {
            Logger.getLogger(Autenticacao.class.getName()).log(Level.INFO, String.format("Acesso autorizado sem autenticação à página [%s]", pagina));
            ret = true;
        }
        return ret;
    }
}
