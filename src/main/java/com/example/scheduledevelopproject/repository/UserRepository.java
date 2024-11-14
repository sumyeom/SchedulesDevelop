package com.example.scheduledevelopproject.repository;

import com.example.scheduledevelopproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByEmail(String email);

    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 실패"));
    }
    default User findUserByUsernameOrElseThrow(String username){
        return findUserByUsername(username)
                .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist username = " + username));
    }
    default User findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist user id = " + id));
    }
}
