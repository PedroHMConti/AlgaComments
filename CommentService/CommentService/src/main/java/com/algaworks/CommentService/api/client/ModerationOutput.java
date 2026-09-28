package com.algaworks.CommentService.api.client;

import lombok.Data;

@Data
public class ModerationOutput {

    private String reason;
    private Boolean approved;
}
