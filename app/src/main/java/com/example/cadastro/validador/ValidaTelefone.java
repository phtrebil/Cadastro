package com.example.cadastro.validador;

import android.widget.EditText;

import com.example.cadastro.formatadores.FormataTelefone;
import com.google.android.material.textfield.TextInputLayout;

public class ValidaTelefone {
    public static final String DEVE_TER_DEZ_OU_ONZE_DIGITOS = "Telefone deve ter entre 10 a 11 dígitos";
    private final TextInputLayout textInputTelefoneComDdd;
    private final EditText campoTelefoneComDdd;
    private final Validador validacaoPadrao;
    private final FormataTelefone formatador = new FormataTelefone();

    public ValidaTelefone(TextInputLayout textInputTelefoneComDdd) {
        this.textInputTelefoneComDdd = textInputTelefoneComDdd;
        this.campoTelefoneComDdd = textInputTelefoneComDdd.getEditText();
        this.validacaoPadrao = new Validador(textInputTelefoneComDdd);
    }

    private boolean validaEntreDezOuOnzeDigitos(String telefoneComDdd){
        int digitos = telefoneComDdd.length();
        if(digitos < 10 || digitos > 11){
            textInputTelefoneComDdd.setError(DEVE_TER_DEZ_OU_ONZE_DIGITOS);
            return false;
        }
        return true;
    }

    public boolean estaValidotelefone(){
        if(!validacaoPadrao.estaValido()) return false;
        String telefoneComDdd = campoTelefoneComDdd.getText().toString();
        if(!validaEntreDezOuOnzeDigitos(telefoneComDdd)) return false;
        adicionaFormatacao(telefoneComDdd);
        return true;
    }

    private void adicionaFormatacao(String telefoneComDdd) {
        String telefoneComDddFormatado = formatador.formata(telefoneComDdd);
        campoTelefoneComDdd.setText(telefoneComDddFormatado);
    }

}
