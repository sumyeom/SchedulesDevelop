package com.example.scheduledevelopproject.service.Impl;

import com.example.scheduledevelopproject.dto.CommentRequestDto;
import com.example.scheduledevelopproject.dto.CommentResponseDto;
import com.example.scheduledevelopproject.entity.Comment;
import com.example.scheduledevelopproject.entity.Schedule;
import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.repository.CommentRepository;
import com.example.scheduledevelopproject.repository.ScheduleRepositroy;
import com.example.scheduledevelopproject.repository.UserRepository;
import com.example.scheduledevelopproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepositroy scheduleRepositroy;

    @Override
    public CommentResponseDto createUser(Long scheduleId, CommentRequestDto requestDto) {
        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(scheduleId);
        User user = userRepository.findUserByUsernameOrElseThrow(requestDto.getUsername());
        //Comment createComment = new Comment(requestDto.getComment());
        Comment createdComment = Comment.builder()
                .content(requestDto.getComment())
                .user(user)
                .schedule(schedule)
                .build();
        Comment savedComment = commentRepository.save(createdComment);
        return new CommentResponseDto(savedComment);
    }

    @Override
    public CommentResponseDto findByIdComment(Long scheduleId, Long commentsId) {
        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(scheduleId);
        User user = userRepository.findUserByUsernameOrElseThrow(schedule.getUser().getUsername());
        Comment findComment = commentRepository.findByIdOrElseThrow(commentsId);
        return new CommentResponseDto(findComment);
    }

    @Override
    public List<CommentResponseDto> findAllComment() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    @Override
    public CommentResponseDto updateComment(Long schedulesId, Long commentsId, CommentRequestDto requestDto) {
        Comment findComment = commentRepository.findByIdOrElseThrow(commentsId);

        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(findComment.getId());
        User user = userRepository.findUserByUsernameOrElseThrow(requestDto.getUsername());
        findComment.setContent(requestDto.getComment());
        Comment savedComment = commentRepository.save(findComment);

        return new CommentResponseDto(findComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        commentRepository.delete(comment);
    }
}
