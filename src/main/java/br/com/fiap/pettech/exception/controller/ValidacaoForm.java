package br.com.fiap.pettech.exception.controller;

import br.com.fiap.pettech.exception.service.GenericError;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoForm extends GenericError {

    private List<ValidacaoCampo> mensagens = new ArrayList<ValidacaoCampo>();

    public List<ValidacaoCampo> getMensagens() {
        return mensagens;
    }

    public void addMensagens(String campo, String mensagem) {
        mensagens.add(new ValidacaoCampo(campo, mensagem));
    }
}
