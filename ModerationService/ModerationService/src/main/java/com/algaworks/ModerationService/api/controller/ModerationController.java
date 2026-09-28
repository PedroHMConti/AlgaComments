package com.algaworks.ModerationService.api.controller;


import com.algaworks.ModerationService.api.model.ModerationInput;
import com.algaworks.ModerationService.api.model.ModerationOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/moderate")
public class ModerationController {
    List<String> palavrasProibidas = List.of("idiota", "imbecil", "burro", "otario", "babaca", "estupido", "lixo", "inutil", "palhaco", "ridiculo");

    @PostMapping
    public ModerationOutput moderation(@RequestBody ModerationInput moderationInput) {
        String texto = moderationInput.getText();
        if(!contemPalavraProibida(texto)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }else {
            return ModerationOutput.builder()
                    .reason("aprovado pelo moderador!")
                    .approved(true)
                    .build();
        }
    }
    public boolean contemPalavraProibida(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")   // remove acentos
                .toLowerCase();

        return Arrays.stream(normalizado.split("\\W+"))
                .anyMatch(palavrasProibidas::contains);
    }
}
