package com.algaworks.CommentService.api.client;

import com.algaworks.CommentService.domain.model.Comment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/moderate")
public interface ModerationClient {

    @PostExchange()
    ModerationOutput requestApproval(@RequestBody Comment comment);
}
