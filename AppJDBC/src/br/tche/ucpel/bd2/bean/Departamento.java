package br.tche.ucpel.bd2.bean;

/**
 * Classe que representa a Entidade Departamento no Sistema
 * @author mertins
 */
public class Departamento {
    private int cod;
    private String descricao;
    private String localizacao;

    public Departamento() {
    }
    
    public Departamento(int cod) {
        this.cod = cod;
    }

    public Departamento(int cod, String descricao, String localizacao) {
        this.cod = cod;
        this.descricao = descricao;
        this.localizacao = localizacao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Departamento other = (Departamento) obj;
        if (this.cod != other.cod) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.cod;
    }
}
