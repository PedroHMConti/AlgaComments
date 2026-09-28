package com.algaworks.CommentService.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class CommentOutput {

        UUID id;
        String text;
        String author;
        OffsetDateTime CreatedAt;

}
