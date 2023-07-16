package com.example.MyTest_Spring.service;

import java.util.List;
import java.util.Optional;

import com.example.MyTest_Spring.entity.Comment;
import com.example.MyTest_Spring.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Optional<Comment> getCommentByRentalId(int id) {
        return commentRepository.findByRentalId(id);
    }
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }


    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }
}
