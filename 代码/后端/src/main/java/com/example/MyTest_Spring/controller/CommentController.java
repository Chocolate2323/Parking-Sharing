package com.example.MyTest_Spring.controller;

import com.example.MyTest_Spring.entity.Comment;
import com.example.MyTest_Spring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/commentList")
    public List<Comment> getAllComment(){
        return commentService.getAllComments();
    }

    @PostMapping("/parking/addcomment")
    public ResponseEntity<Comment> addComment(@RequestBody Map<String, String> request) {
        int rentalId = Integer.parseInt(request.get("Rental_ID"));
        int parkingId = Integer.parseInt(request.get("Parking_ID"));
        int userId = Integer.parseInt(request.get("User_ID"));
        String comments = request.get("Comments");
        float score = Float.parseFloat(request.get("Score"));

        Comment comment = new Comment(rentalId,parkingId,userId,comments,score);
        // 保存到数据库
        commentService.createComment(comment);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/parking/searchcomment")
    public ResponseEntity<Comment> searchComment(@RequestBody Map<String, String> request) {
        int rentalId = Integer.parseInt(request.get("Rental_ID"));
        Optional<Comment> optionalComment = commentService.getCommentByRentalId(rentalId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.noContent().build();
    }
    // 处理其他自定义的API请求...
}
