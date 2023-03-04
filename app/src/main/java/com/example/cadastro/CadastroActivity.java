package com.example.cadastro;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cadastro.validador.ValidaCpf;
import com.example.cadastro.validador.Validador;
import com.google.android.material.textfield.TextInputLayout;

import br.com.caelum.stella.format.CPFFormatter;

public class CadastroActivity extends AppCompatActivity {

    private static final String ERRO_FORMATAO_CPF = "erro formatação cpf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaCampos();
    }

    private void inicializaCampos() {
        configuraCampoNomeCompleto();
        configuraCampoCpf();
        configuraCampoTelefoneComDdd();
        configuraCampoEmail();
        configuraCampoSenha();
    }

    private void configuraCampoSenha() {
        TextInputLayout textInputSenha = findViewById(R.id._layout_senha);
        adicionaValidacaoPadrao(textInputSenha);
    }

    private void configuraCampoEmail() {
        TextInputLayout textInputEmail = findViewById(R.id._layout_email);
        adicionaValidacaoPadrao(textInputEmail);
    }

    private void configuraCampoTelefoneComDdd() {
        TextInputLayout textInputTelefoneComDdd = findViewById(R.id._layout_telefone);
        adicionaValidacaoPadrao(textInputTelefoneComDdd);
    }

    private void configuraCampoCpf() {
        TextInputLayout textInputCpf = findViewById(R.id._layout_cpf);
        final EditText campoCpf = textInputCpf.getEditText();
        final CPFFormatter formatador = new CPFFormatter();
        final ValidaCpf validador = new ValidaCpf(textInputCpf);
        campoCpf.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                adicionaFormatacao(formatador, campoCpf);
            } else {
                validador.estaValido();
            }
        });
    }

    private void adicionaFormatacao(CPFFormatter formatador, EditText campoCpf) {
        String cpf = campoCpf.getText().toString();
        try {
            String cpfSemFormato = formatador.unformat(cpf);
            campoCpf.setText(cpfSemFormato);
        } catch (IllegalArgumentException e){
            Log.e(ERRO_FORMATAO_CPF, e.getMessage());
        }
    }


    private void configuraCampoNomeCompleto() {
        TextInputLayout textInputNomeCompleto = findViewById(R.id._layout_nome);
        adicionaValidacaoPadrao(textInputNomeCompleto);
    }

    private void adicionaValidacaoPadrao(final TextInputLayout textInputCampo) {
        final EditText campo = textInputCampo.getEditText();
        final Validador validador = new Validador(textInputCampo);
        campo.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validador.estaValido();
            }
        });
    }
}