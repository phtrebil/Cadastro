package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        carregaViews();


    }

    private void carregaViews() {
        referenciaViews(R.id._layout_nome, R.id.nome);
        referenciaViews(R.id._layout_cpf, R.id.cpf);
        referenciaViews(R.id._layout_telefone, R.id.telefone);
        referenciaViews(R.id._layout_email, R.id.email);
        referenciaViews(R.id._layout_senha, R.id.senha);
    }

    private void referenciaViews(int textLayout, int item) {
        TextInputLayout layoutNome = findViewById(textLayout);
        EditText editName = findViewById(item);
        validaCampoVazio(editName, layoutNome);
    }

    private void validaCampoVazio(EditText editItem, TextInputLayout text) {

        editItem.setOnFocusChangeListener((view, b) -> {

            String itemEditado = editItem.getText().toString();
                if(!b){
                    if (itemEditado.isEmpty()) {
                        text.setError("Campo obrigatório");
                    }else{
                        text.setError(null);
                        text.setEnabled(false);

                    }
                }

        });
    }
}