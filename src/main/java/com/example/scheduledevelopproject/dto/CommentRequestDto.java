package com.example.scheduledevelopproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private final String username;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 0,max=70,message="댓글은 70자 이내로 입력해주세요.")
    private final String comment;

    public CommentRequestDto(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }
}
