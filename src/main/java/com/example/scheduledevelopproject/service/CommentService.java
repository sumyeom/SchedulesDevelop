package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.CommentRequestDto;
import com.example.scheduledevelopproject.dto.CommentResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CommentService {
    CommentResponseDto createUser(Long scheduleId, CommentRequestDto requestDto);

    CommentResponseDto findByIdComment(Long scheduleId, Long commentsId);

    List<CommentResponseDto> findAllComment();

    CommentResponseDto updateComment(Long schedulesId, Long commentsId, @Valid CommentRequestDto requestDto);

    void deleteComment(Long commentId);
}
