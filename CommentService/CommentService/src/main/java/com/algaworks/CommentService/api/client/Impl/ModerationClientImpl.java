package com.algaworks.CommentService.api.client.Impl;

import com.algaworks.CommentService.api.client.ModerationClient;
import com.algaworks.CommentService.api.client.ModerationInput;
import com.algaworks.CommentService.api.client.ModerationOutput;
import com.algaworks.CommentService.api.client.RestClientFactory;
import com.algaworks.CommentService.domain.model.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class ModerationClientImpl implements ModerationClient {

    private RestClient restClient;

    public ModerationClientImpl(RestClientFactory factory) {
        this.restClient = factory.moderationClient();
    }
    @Override
    public ModerationOutput requestApproval(Comment comment) {
        ModerationInput moderationInput = ModerationInput.builder()
                .commentId(comment.getId())
                .text(comment.getText())
                .build();
        return restClient.post().body(moderationInput).retrieve().body(ModerationOutput.class);
    }
}
