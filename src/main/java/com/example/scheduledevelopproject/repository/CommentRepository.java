package com.example.scheduledevelopproject.repository;

import com.example.scheduledevelopproject.entity.Comment;
import com.example.scheduledevelopproject.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.example.scheduledevelopproject.exception.ErrorCode.COMMENT_NOT_FOUND;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->
                        new CustomException(COMMENT_NOT_FOUND));
    }
}
