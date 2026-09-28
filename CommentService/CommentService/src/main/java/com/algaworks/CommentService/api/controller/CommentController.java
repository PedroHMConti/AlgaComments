package com.algaworks.CommentService.api.controller;

import com.algaworks.CommentService.api.client.ModerationClient;
import com.algaworks.CommentService.api.client.ModerationOutput;
import com.algaworks.CommentService.api.model.CommentInput;
import com.algaworks.CommentService.api.model.CommentOutput;
import com.algaworks.CommentService.domain.model.Comment;
import com.algaworks.CommentService.domain.repository.CommentRepository;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.UUIDGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final ModerationClient moderationClient;

    private final CommentRepository commentRepository;

    @PostMapping
    public ModerationOutput createComment(@RequestBody CommentInput commentInput) {
        Comment comment = Comment.builder()
                .id(UUID.randomUUID())
                .author(commentInput.getAuthor())
                .text(OffsetDateTime.now().toString())
                .build();
        ModerationOutput moderationOutput = moderationClient.requestApproval(comment);
        commentRepository.saveAndFlush(comment);
        return moderationOutput;
    }

    @GetMapping
    public Page<CommentOutput> search(@PageableDefault Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(this::convertToModel);

    }

    @GetMapping("{commentId}")
    public CommentOutput getDetail(@PathVariable UUID commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToModel(comment);
    }

    private CommentOutput convertToModel(Comment comment) {
        return CommentOutput.builder().id(comment.getId())
                .text(comment.getText())
                .author(comment.getAuthor())
                .CreatedAt(comment.getCreatedAt())
                .build();
    }

}
