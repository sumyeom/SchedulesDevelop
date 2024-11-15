package com.example.scheduledevelopproject.repository;

import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.example.scheduledevelopproject.exception.ErrorCode.INVALID_EMAIL;
import static com.example.scheduledevelopproject.exception.ErrorCode.USER_NOT_FOUND;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByEmail(String email);

    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email)
                .orElseThrow(() -> new CustomException(INVALID_EMAIL));
    }
    default User findUserByUsernameOrElseThrow(String username){
        return findUserByUsername(username)
                .orElseThrow(()->
                        new CustomException(USER_NOT_FOUND));
    }
    default User findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(() ->
                        new CustomException(USER_NOT_FOUND));
    }
}
