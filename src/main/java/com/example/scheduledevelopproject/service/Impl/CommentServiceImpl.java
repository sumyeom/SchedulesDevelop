package com.example.scheduledevelopproject.service.Impl;

import com.example.scheduledevelopproject.dto.CommentRequestDto;
import com.example.scheduledevelopproject.dto.CommentResponseDto;
import com.example.scheduledevelopproject.entity.Comment;
import com.example.scheduledevelopproject.entity.Schedule;
import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.exception.CustomException;
import com.example.scheduledevelopproject.exception.ErrorCode;
import com.example.scheduledevelopproject.repository.CommentRepository;
import com.example.scheduledevelopproject.repository.ScheduleRepositroy;
import com.example.scheduledevelopproject.repository.UserRepository;
import com.example.scheduledevelopproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.scheduledevelopproject.exception.ErrorCode.INVALID_USER_NAME;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepositroy scheduleRepositroy;
    private Long sessionId;

    @Override
    public CommentResponseDto createUser(Long scheduleId, CommentRequestDto requestDto, Long sessionId) {
        User user = userRepository.findByIdOrElseThrow(sessionId);
        if(!requestDto.getUsername().equals(user.getUsername())){
            throw new CustomException(INVALID_USER_NAME);
        }

        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(scheduleId);
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
        Comment findComment = schedule.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(commentsId))
                .findFirst()
                .orElseThrow(
                        ()-> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        return new CommentResponseDto(findComment);
    }

    @Override
    public Page<CommentResponseDto> findByScheduleId(Long scheduleId, int page, int size) {
        if(page > 0){
            page--;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        Page<Comment> commentsPage = commentRepository.findByScheduleId(scheduleId, pageable);

        // Comment -> CommentResponseDto 매핑
        return commentsPage.map(CommentResponseDto::new);

    }

    @Override
    public Page<CommentResponseDto> findAllComment(int page, int size) {
        if(page > 0){
             page--;
        }

        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Order.asc("schedule_id")));
        Page<Comment> comments = commentRepository.findAll(pageable);

        return comments.map(CommentResponseDto::new);
    }

    @Override
    public CommentResponseDto updateComment(Long schedulesId, Long commentsId, CommentRequestDto requestDto, Long sessionId) {
        User user = userRepository.findByIdOrElseThrow(sessionId);
        if(!requestDto.getUsername().equals(user.getUsername())){
            throw new CustomException(INVALID_USER_NAME);
        }

        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(schedulesId);
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
    public void deleteComment(Long commentId,Long sessionId) {
        User user = userRepository.findByIdOrElseThrow(sessionId);
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        if(!comment.getUser().getUsername().equals(user.getUsername())){
            throw new CustomException(INVALID_USER_NAME);
        }

        commentRepository.delete(comment);
    }


}
