package com.jsh.week2_lecture.application.service;

import com.jsh.week2_lecture.domain.entity.Lecture;
import com.jsh.week2_lecture.domain.entity.User;
import com.jsh.week2_lecture.domain.repository.ApplicationRepository;
import com.jsh.week2_lecture.domain.repository.LectureRepository;
import com.jsh.week2_lecture.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class LectureConcurrencyTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureService lectureService;

    private Lecture lecture;

    @BeforeEach
    void setUp() {
        lecture = new Lecture();
        lecture.setLectureName("클린 아키텍처");
        lecture.setLecturer("알렉스");
        lecture.setDate(LocalDate.now());
        lecture.setTime(LocalTime.of(13, 0));
    }

    @Test
    void testApplyLectureWithConcurrency() throws InterruptedException {
        int userCount = 40; //동시 요청자수
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= userCount; i++) {
            User user = new User();
            user.setUserName("User" + i);
            userRepository.save(user);

            Long userId = user.getUserId();
            Long lectureId = lecture.getLectureId();

            executorService.submit(() -> {
                        try {
                            lectureService.applyLecture(userId, lectureId);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
            );
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        
        //30명만 성공적으로 신청되었는지 확인
        int applicationCount = applicationRepository.findAll().size();
        assertThat(applicationCount).isEqualTo(30);//정원 초과 확인
    }
}
