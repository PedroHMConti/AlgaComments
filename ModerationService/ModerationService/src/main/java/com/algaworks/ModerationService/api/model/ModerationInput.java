package com.algaworks.ModerationService.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class ModerationInput {
    private String text;
    private UUID commentId;
}
