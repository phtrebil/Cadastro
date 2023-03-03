package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.atomic.AtomicReference;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        carregaViews();


    }

    private void carregaViews() {
        referenciaViews(R.id._layout_nome, R.id.nome);
        configuraCampoCpf();
        referenciaViews(R.id._layout_telefone, R.id.telefone);
        referenciaViews(R.id._layout_email, R.id.email);
        referenciaViews(R.id._layout_senha, R.id.senha);
    }

    private void configuraCampoCpf() {
        final TextInputLayout textInputCpf = findViewById(R.id._layout_cpf);
        final EditText campoCpf = textInputCpf.getEditText();
        final CPFFormatter cpfFormatter = new CPFFormatter();
        campoCpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String cpf = campoCpf.getText().toString();
                if (!hasFocus) {
                    if (!validaCampoObrigatorio(cpf, textInputCpf)) return;
                    if (!validaCampoComOnzeDigitos(cpf, textInputCpf)) return;
                    if (!validaCalculoCpf(cpf, textInputCpf)) return;

                    removeErro(textInputCpf);

                    String cpfFormatado = cpfFormatter.format(cpf);
                    campoCpf.setText(cpfFormatado);
                } else {
                    try {
                        String cpfSemFormato = cpfFormatter.unformat(cpf);
                        campoCpf.setText(cpfSemFormato);
                    } catch (IllegalArgumentException e){
                        Log.e("erro formatação cpf", e.getMessage());
                    }
                }
            }
        });
    }

    private void removeErro(TextInputLayout textInputCpf) {
        textInputCpf.setError(null);
        textInputCpf.setErrorEnabled(false);
    }

    private boolean validaCalculoCpf(String cpf, TextInputLayout textInputCpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e){
            textInputCpf.setError("CPF inválido");
            return false;
        }
        return true;
    }

    private boolean validaCampoObrigatorio(String texto, TextInputLayout textInputCampo) {
        if (texto.isEmpty()) {
            textInputCampo.setError("Campo obrigatório");
            return false;
        }
        return true;
    }

    private boolean validaCampoComOnzeDigitos(String cpf, TextInputLayout textInputCpf) {
        if (cpf.length() != 11) {
            textInputCpf.setError("O CPF precisa ter 11 dígitos");
            return false;
        }
        return true;
    }


    private void referenciaViews(int textLayout, int item) {
        TextInputLayout layoutNome = findViewById(textLayout);
        EditText editName = findViewById(item);
        validaCampoVazio(editName, layoutNome);

    }

    private void validaCampoVazio(EditText editItem, TextInputLayout text) {

        editItem.setOnFocusChangeListener((view, b) -> {

            String itemEditado = editItem.getText().toString();
            if (!b) {
                if (itemEditado.isEmpty()) {
                    text.setError("Campo obrigatório");
                } else {
                    text.setError(null);
                    text.setEnabled(false);

                }
            }

        });
    }
}