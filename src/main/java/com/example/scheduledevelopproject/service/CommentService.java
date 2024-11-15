package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.CommentRequestDto;
import com.example.scheduledevelopproject.dto.CommentResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface CommentService {
    CommentResponseDto createUser(Long scheduleId, CommentRequestDto requestDto, Long sessionId);

    CommentResponseDto findByIdComment(Long scheduleId, Long commentsId);

    Page<CommentResponseDto> findAllComment(int page, int size);

    CommentResponseDto updateComment(Long schedulesId, Long commentsId, @Valid CommentRequestDto requestDto, Long sessionId);

    void deleteComment(Long commentId, Long sessionId);

    Page<CommentResponseDto> findByScheduleId(Long scheduleId, int page, int size);
}
