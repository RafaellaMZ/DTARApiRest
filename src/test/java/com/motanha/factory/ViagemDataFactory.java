package com.motanha.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motanha.pojo.Viagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ViagemDataFactory {
    public static Viagem criarViagem() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(); // O Mapper faz a ponte entre o json e a Viagem
        Viagem viagem = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postV1Viagens.json"), Viagem.class);
        //Viagem viagem = objectMapper.readValue(Files.newInputStream(Paths.get("src/test/resources/requestBody/postV1Viagens.json")), Viagem.class);
        return viagem;
    }

    public static Viagem criarViagemValida() throws IOException {
        Viagem viagemValida = criarViagem();

        return viagemValida;
    }

    public static Viagem criarViagemSemLocalDeDestino() throws IOException {
        Viagem viagemSemLocalDeDestino = criarViagem();
        viagemSemLocalDeDestino.setLocalDeDestino("");
        return viagemSemLocalDeDestino;
    }
}
