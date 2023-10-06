package com.motanha.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motanha.pojo.Viagem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ViagemDataFactory {
    public static Viagem criarViagem() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(); // O Mapper faz a ponte entre o JSON e a viagem, pega os dados do JSON e insere na viagem.
        Viagem viagem = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postV1Viagens.json"), Viagem.class);
        return viagem;
    }

    public static Viagem criarViagemValida() {
        Viagem viagemValida = criarViagemValida();

        return viagemValida;
    }

    public static Viagem criarViagemSemLocalDeDestino() throws IOException {
        Viagem viagemSemLocalDeDestino = criarViagem();
        viagemSemLocalDeDestino.setLocalDeDestino("");
        return viagemSemLocalDeDestino;
    }
}
