package com.jsh.week2_lecture.domain.repository;

import com.jsh.week2_lecture.domain.entity.Lecture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class LectureRepositoryTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testFindAvailableLectures(){
        // Given
        LocalDate date = LocalDate.of(2024,12,28);

        Lecture lecture1 = new Lecture();
        lecture1.setLectureName("동시성 제어");
        lecture1.setLecturer("율무");
        lecture1.setDate(date);
        lecture1.setTime(LocalTime.of(19,0));

        Lecture lecture2 = new Lecture();
        lecture2.setLectureName("클린 아키텍처");
        lecture2.setLecturer("허재");
        lecture2.setDate(date);
        lecture2.setTime(LocalTime.of(20,0));

        Lecture lecture3 = new Lecture();
        lecture3.setLectureName("시나리오 기반 서버 구축");
        lecture3.setLecturer("이석범");
        lecture3.setDate(LocalDate.of(2025,1,4));
        lecture3.setTime(LocalTime.of(19,0));

        Lecture lecture4 = new Lecture();
        lecture4.setLectureName("장애 대응");
        lecture4.setLecturer("한상진");
        lecture4.setDate(LocalDate.of(2025,1,4));
        lecture4.setTime(LocalTime.of(20,0));

        lectureRepository.save(lecture1);
        lectureRepository.save(lecture2);
        lectureRepository.save(lecture3);
        lectureRepository.save(lecture4);

        // 강제 flush
        entityManager.flush();

        //When
        List<Lecture> lectures =lectureRepository.findAvailableLectures(date);

        //Then
        Assertions.assertEquals(2,lectures.size());
        Assertions.assertEquals("율무", lectures.get(0).getLecturer());
        Assertions.assertEquals("클린 아키텍처", lectures.get(1).getLectureName());
    }
}
