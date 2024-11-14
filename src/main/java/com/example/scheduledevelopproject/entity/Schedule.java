package com.example.scheduledevelopproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "longtext")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(){
    }

    public Schedule(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setUser(User user){
        this.user = user;
    }

}
