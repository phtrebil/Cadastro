package com.example.cadastro.formatadores;

public class FormataTelefone {
    public String formata(String telefoneComDdd) {
        return telefoneComDdd
                .replaceAll("([0-9]{2})([0-9]{4,5})([0-9]{4})", "($1) $2-$3");
    }

    public String remove(String telefoneComDdd) {
        return telefoneComDdd
                .replace("(", "")
                .replace(")", "")
                .replace(" ", "")
                .replace("-", "");
    }
}
