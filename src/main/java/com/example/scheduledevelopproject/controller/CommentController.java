package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.dto.CommentRequestDto;
import com.example.scheduledevelopproject.dto.CommentResponseDto;
import com.example.scheduledevelopproject.service.CommentService;
import com.example.scheduledevelopproject.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules/{schedulesId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentservice;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long schedulesId,
            @Valid @RequestBody CommentRequestDto requestDto,
            BindingResult bindingResult
    ){
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }
        CommentResponseDto commentPostResponseDto = commentservice.createUser(schedulesId, requestDto);

        return new ResponseEntity<>(commentPostResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{commentsId}")
    public ResponseEntity<CommentResponseDto> findByIdComment(
            @PathVariable Long schedulesId,
            @PathVariable Long commentsId
    ){
        CommentResponseDto commentPostResponseDto = commentservice.findByIdComment(schedulesId, commentsId);
        return new ResponseEntity<>(commentPostResponseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAllComment(){
        List<CommentResponseDto> commentPostResponseDtoList = commentservice.findAllComment();
        return new ResponseEntity<>(commentPostResponseDtoList,HttpStatus.OK);
    }

    @PutMapping("/{commentsId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long schedulesId,
            @PathVariable Long commentsId,
            @Valid @RequestBody CommentRequestDto requestDto,
            BindingResult bindingResult
    ){
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }

        CommentResponseDto commentResponseDto = commentservice.updateComment(schedulesId, commentsId, requestDto);
        return new ResponseEntity<>(commentResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{commtensId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long schedulesId,
            @PathVariable Long commtensId
    ){
        commentservice.deleteComment(commtensId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
