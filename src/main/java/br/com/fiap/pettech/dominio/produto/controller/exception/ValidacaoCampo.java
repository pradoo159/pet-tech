package br.com.fiap.pettech.dominio.produto.controller.exception;

public class ValidacaoCampo {

    private String campo;
    private String mensagem;

    public ValidacaoCampo(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public ValidacaoCampo() {
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
