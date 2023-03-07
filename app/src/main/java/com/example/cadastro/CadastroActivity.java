package com.example.cadastro;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cadastro.validador.ValidaCpf;
import com.example.cadastro.validador.ValidaEmail;
import com.example.cadastro.validador.ValidaTelefone;
import com.example.cadastro.validador.Validador;
import com.example.cadastro.validador.ValidadorPadrao;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.stella.format.CPFFormatter;

public class CadastroActivity extends AppCompatActivity {

    private static final String ERRO_FORMATAO_CPF = "erro formatação cpf";
    private final List<Validador> validadores = new ArrayList<>();


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
        configuraBotaoCadastrar();
    }

    private void configuraBotaoCadastrar() {
        Button botaoCadastrar = findViewById(R.id.botao_cadastrar);
        botaoCadastrar.setOnClickListener(v -> {
            boolean formularioEstaValido = validaTodosCampos();
            if(formularioEstaValido){
                cadastroRealizado();
            }
        });
    }

    private void cadastroRealizado() {
        Toast.makeText(
                CadastroActivity.this,
                "Cadastro realizado com sucesso!",
                Toast.LENGTH_SHORT).show();
    }

    private boolean validaTodosCampos() {
        boolean formularioEstaValido = true;
        for (Validador validador :
                validadores) {
            if(!validador.estaValido()){
                formularioEstaValido = false;
            }
        }
        return formularioEstaValido;
    }

    private void configuraCampoSenha() {
        TextInputLayout textInputSenha = findViewById(R.id._layout_senha);
        adicionaValidacaoPadrao(textInputSenha);
    }

    private void configuraCampoEmail() {
        TextInputLayout textInputEmail = findViewById(R.id._layout_email);
        EditText campoEmail = textInputEmail.getEditText();
        final ValidaEmail validadorEmail = new ValidaEmail(textInputEmail);
        campoEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                validadorEmail.estaValido();
            }
        });
    }


    private void configuraCampoTelefoneComDdd() {
        TextInputLayout textInputEmail = findViewById(R.id._layout_telefone);
        EditText campoEmail = textInputEmail.getEditText();
        final ValidaTelefone validadorTelefone = new ValidaTelefone(textInputEmail);
        campoEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
               validadorTelefone.estaValido();
            }
        });
    }



    private void configuraCampoCpf() {
        TextInputLayout textInputCpf = findViewById(R.id._layout_cpf);
        final EditText campoCpf = textInputCpf.getEditText();
        final CPFFormatter formatador = new CPFFormatter();
        final ValidaCpf validador = new ValidaCpf(textInputCpf);
        campoCpf.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                removeFormatacao(formatador, campoCpf);
            } else {
                validador.estaValido();
            }
        });
    }

    private void removeFormatacao(CPFFormatter formatador, EditText campoCpf) {
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
        final ValidadorPadrao validadorPadrao = new ValidadorPadrao(textInputCampo);
        campo.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validadorPadrao.estaValido();
            }
        });
    }
}