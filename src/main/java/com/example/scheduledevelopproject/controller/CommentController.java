package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.dto.CommentRequestDto;
import com.example.scheduledevelopproject.dto.CommentResponseDto;
import com.example.scheduledevelopproject.service.CommentService;
import com.example.scheduledevelopproject.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedulesComment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentservice;

    /**
     * 댓글 작성 API
     * @param schedulesId
     * @param requestDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/{schedulesId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long schedulesId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @SessionAttribute(name = "userId", required = false) Long sessionId,
            BindingResult bindingResult
    ){
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }
        CommentResponseDto commentPostResponseDto = commentservice.createUser(schedulesId, requestDto, sessionId);

        return new ResponseEntity<>(commentPostResponseDto, HttpStatus.OK);
    }

    /**
     * 선택 댓글 조회
     * @param schedulesId
     * @param commentsId
     * @return
     */
    @GetMapping("/{schedulesId}/comments/{commentsId}")
    public ResponseEntity<CommentResponseDto> findByIdComment(
            @PathVariable Long schedulesId,
            @PathVariable Long commentsId
    ){
        CommentResponseDto commentPostResponseDto = commentservice.findByIdComment(schedulesId, commentsId);
        return new ResponseEntity<>(commentPostResponseDto,HttpStatus.OK);
    }

    @GetMapping("/{schedulesId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findBySchduleId(
            @PathVariable Long schedulesId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10",required = false) int size
    ){
        Page<CommentResponseDto> commentResponseDtoPage = commentservice.findByScheduleId(schedulesId,page,size);

        return new ResponseEntity<>(commentResponseDtoPage.getContent(),HttpStatus.OK);
    }

    /**
     * 전체 댓글 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAllComment(
            @RequestParam(defaultValue = "0",required = false) int page,
            @RequestParam(defaultValue = "10",required = false) int size
    ){
        Page<CommentResponseDto> commentPostResponseDtoList = commentservice.findAllComment(page, size);
        return new ResponseEntity<>(commentPostResponseDtoList.getContent(),HttpStatus.OK);
    }

    /**
     * 선택 댓글 수정
     * @param schedulesId
     * @param commentsId
     * @param requestDto
     * @param bindingResult
     * @return
     */
    @PutMapping("/{schedulesId}/comments/{commentsId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long schedulesId,
            @PathVariable Long commentsId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @SessionAttribute(name = "userId", required = false) Long sessionId,
            BindingResult bindingResult
    ){
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }

        CommentResponseDto commentResponseDto = commentservice.updateComment(schedulesId, commentsId, requestDto, sessionId);
        return new ResponseEntity<>(commentResponseDto,HttpStatus.OK);
    }

    /**
     * 선택 댓글 삭제
     * @param schedulesId
     * @param commentsId
     * @return
     */
    @DeleteMapping("/{schedulesId}/comments/{commentsId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long schedulesId,
            @PathVariable Long commentsId,
            @SessionAttribute(name = "userId", required = false) Long sessionId

    ){
        commentservice.deleteComment(commentsId,sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
