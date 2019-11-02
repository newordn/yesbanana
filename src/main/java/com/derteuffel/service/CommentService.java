package com.derteuffel.service;

import com.derteuffel.data.Comment;
import com.derteuffel.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;



    public void save(Comment comment){
        commentRepository.save(comment);
    }

    public Comment get(Long commentId){
        return commentRepository.getOne(commentId);
    }

    public List<Comment> findByEvent(Long eventId){
        return commentRepository.findAllByEvent_EventId(eventId);
    }



    public void delete(Long commentId){
        commentRepository.deleteById(commentId);
    }


}
