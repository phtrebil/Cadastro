package com.example.cadastro.validador;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ValidaEmail implements Validador{

    private final TextInputLayout textInputEmail;
    private final EditText campoEmail;
    private final ValidadorPadrao validadorPadrao;

    public ValidaEmail(TextInputLayout textInputEmail) {
        this.textInputEmail = textInputEmail;
        this.campoEmail = this.textInputEmail.getEditText();
        this.validadorPadrao = new ValidadorPadrao(this.textInputEmail);
    }

    private boolean validaPadrao(String email){
        if(email.matches(".+@.+\\..+")){
            return true;
        }
        textInputEmail.setError("E-mail inválido");
        return false;
    }

    @Override
    public boolean estaValido(){
        if(!validadorPadrao.estaValido()) return false;
        String email = campoEmail.getText().toString();
        return validaPadrao(email);
    }
}
