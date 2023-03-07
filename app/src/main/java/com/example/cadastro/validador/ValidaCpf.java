package com.example.cadastro.validador;

import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidaCpf implements Validador {

    private static final String ERRO_FORMATAO_CPF = "erro formatação cpf";
    public static final String CPF_INVALIDO = "CPF inválido";
    public static final String DEVE_TER_ONZE_DIGITOS = "O CPF precisa ter 11 dígitos";
    private final TextInputLayout textInputCpf;
    private final EditText campoCpf;
    private final ValidadorPadrao validadorPadrao;
    private final CPFFormatter formatador = new CPFFormatter();

    public ValidaCpf(TextInputLayout textInputCpf) {
        this.textInputCpf = textInputCpf;
        this.campoCpf = textInputCpf.getEditText();
        this.validadorPadrao = new ValidadorPadrao(textInputCpf);
    }

    private boolean validaCalculoCpf(String cpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e){
            textInputCpf.setError(CPF_INVALIDO);
            return false;
        }
        return true;
    }

    private boolean validaCampoComOnzeDigitos(String cpf) {
        if (cpf.length() != 11) {
            textInputCpf.setError(DEVE_TER_ONZE_DIGITOS);
            return false;
        }
        return true;
    }

    @Override
    public boolean estaValido(){
        if(!validadorPadrao.estaValido()) return false;
        String cpf = getCpf();
        String cpfSemFormato = cpf;
        try {
            cpfSemFormato = formatador.unformat(cpf);
        } catch (IllegalArgumentException e){
            Log.e(ERRO_FORMATAO_CPF, e.getMessage());
        }
        if(!validaCampoComOnzeDigitos(cpfSemFormato)) return false;
        if(!validaCalculoCpf(cpfSemFormato)) return false;
        adicionaFormatacao(cpfSemFormato);
        return true;
    }

    private void adicionaFormatacao(String cpf) {
        String cpfFormatado = formatador.format(cpf);
        campoCpf.setText(cpfFormatado);
    }

    @NonNull
    private String getCpf() {
        return campoCpf.getText().toString();
    }
}
