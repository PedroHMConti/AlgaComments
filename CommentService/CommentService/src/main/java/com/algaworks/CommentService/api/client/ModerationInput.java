package com.algaworks.CommentService.api.client;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ModerationInput {
    private String text;
    private UUID commentId;
}
