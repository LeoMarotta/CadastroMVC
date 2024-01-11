package controle;

import br.tche.ucpel.bd2.bean.Mensagem;
import br.tche.ucpel.bd2.dao.MensagemDAO;
import br.tche.ucpel.bd2.bean.Usuario;
import br.tche.ucpel.bd2.dao.UsuarioDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author leomarotta
 */
@WebServlet(name = "ServletLogin", urlPatterns = {"/ServletLogin"})
public class ServletLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("Login");
        String password = request.getParameter("Senha");

        boolean autenticado = autenticarUsuario(username, password);
        int codUser = descobrirUsuario(request.getParameter("Login"));
        
        if (autenticado) {
            request.getSession().setAttribute("UsuarioLogado", true);
            request.getSession().setAttribute("codigoUsuario", codUser);
            request.getSession().setAttribute("nomeUsuarioLogado", username);
            
            adicionarCookieAutenticacao(response, username);
            
            obterMensagensPrivadas(request, codUser);
            
            response.sendRedirect(request.getContextPath() + "/loginaceito.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?erro=true");
        }
    }
    
    private boolean autenticarUsuario(String username, String password) {
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("jdbc/testeAula");
            Connection conn = dataSource.getConnection();

            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

            Usuario usuario = usuarioDAO.retrieve(username);
            conn.close();
            return usuario != null && usuario.getSenha().equals(password);

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

        private int descobrirUsuario(String username) {
            Usuario usuario = null;
            try {
                Context context = new InitialContext();
                DataSource dataSource = (DataSource) context.lookup("jdbc/testeAula");
                Connection conn = dataSource.getConnection();

                UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

                usuario = usuarioDAO.retrieve(username);
                conn.close();
                return usuario.getCod();

            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
            return usuario.getCod();
        }
    
        private void obterMensagensPrivadas(HttpServletRequest request, int codUsuario) {
            DataSource dataSource = null;
            Connection conn = null;

            try {
                Context context = new InitialContext();
                dataSource = (DataSource) context.lookup("jdbc/testeAula");
                conn = dataSource.getConnection();

                MensagemDAO msgDAO = new MensagemDAO(conn);

                List<Mensagem> todasMensagensPrivadas = msgDAO.listaMensagensComUsuario();
                List<Mensagem> listaPrivadas = filtrarMensagensPorUsuario(todasMensagensPrivadas, codUsuario);

                request.getSession().setAttribute("listaMsgsPrivadas", listaPrivadas);
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close(); 
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        private List<Mensagem> filtrarMensagensPorUsuario(List<Mensagem> mensagens, int codUsuario) {
            List<Mensagem> mensagensFiltradas = new ArrayList<>();

            for (Mensagem mensagem : mensagens) {
                if (mensagem.getUsuario().getCod() == codUsuario) {
                    mensagensFiltradas.add(mensagem);
                }
            }
            return mensagensFiltradas;
        }
        
        private void adicionarCookieAutenticacao(HttpServletResponse response, String username) {
            Cookie cookie = new Cookie("usuarioLogado", username);
            cookie.setMaxAge(10 * 60);
            response.addCookie(cookie);
        }
        
}