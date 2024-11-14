package com.example.scheduledevelopproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SchedulePutRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min=0, max=20,message = "제목은 10자 이내로 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 0,max=200,message="일정 내용은 200자 이내로 입력해주세요")
    private final String content;

    public SchedulePutRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
