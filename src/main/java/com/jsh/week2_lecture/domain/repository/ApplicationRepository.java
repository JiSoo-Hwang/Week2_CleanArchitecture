package com.jsh.week2_lecture.domain.repository;

import com.jsh.week2_lecture.domain.entity.Application;
import com.jsh.week2_lecture.domain.entity.Lecture;
import com.jsh.week2_lecture.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
    boolean existsByUserAndLecture(User user, Lecture lecture);
}
