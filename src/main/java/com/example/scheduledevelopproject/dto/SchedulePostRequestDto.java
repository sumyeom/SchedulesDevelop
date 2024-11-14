package com.example.scheduledevelopproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SchedulePostRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min=0, max=20,message = "제목은 20자 이내로 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 0,max=200,message="일정 내용은 200자 이내로 입력해주세요.")
    private final String content;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 0,max=5,message="이름은 10글자 이내로 입력해주세요.")
    private final String username;

    public SchedulePostRequestDto(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }
}
