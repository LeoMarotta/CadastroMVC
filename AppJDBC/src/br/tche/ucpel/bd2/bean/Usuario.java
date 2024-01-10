package br.tche.ucpel.bd2.bean;

/**
 * Classe que representa a Entidade Usuário no Sistema.
 * 
 * @author mertins
 */
public class Usuario {

    private int cod;
    private String login;
    private String senha;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Método utilizado para validar a senha do usuário
     * @param senha String a ser validada contra a senha do usuário
     * @return true se as duas senhas são iguais, ou false em caso contrário
     */
    public boolean validaSenha(String senha) {
        boolean ret = false;
        try {
            if (this.senha.equals(senha)) {
                ret = true;
            }
        } catch (NullPointerException ex) {
        }
        return ret;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.cod != other.cod) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.cod;
        return hash;
    }
}
