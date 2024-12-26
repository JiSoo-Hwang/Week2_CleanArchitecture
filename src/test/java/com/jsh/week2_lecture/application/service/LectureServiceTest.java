package com.jsh.week2_lecture.application.service;

import com.jsh.week2_lecture.application.dto.LectureDto;
import com.jsh.week2_lecture.domain.entity.Lecture;
import com.jsh.week2_lecture.domain.repository.LectureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    public void testFindAvailableLectures(){
        // Given
        LocalDate date = LocalDate.of(2024,12,28);

        Lecture lecture1 = new Lecture();
        lecture1.setLectureName("동시성 제어");
        lecture1.setLecturer("율무");
        lecture1.setDate(date);
        lecture1.setApplications(new ArrayList<>());//정원 초과되지 않은 강의

        Lecture lecture2 = new Lecture();
        lecture2.setLectureName("클린 아키텍처");
        lecture2.setLecturer("허재");
        lecture2.setDate(date);
        lecture2.setApplications(new ArrayList<>());

        Lecture lecture3 = new Lecture();
        lecture3.setLectureName("시나리오 기반 서버 구축");
        lecture3.setLecturer("이석범");
        lecture3.setDate(LocalDate.of(2025,1,4));
        lecture3.setApplications(new ArrayList<>());

        Lecture lecture4 = new Lecture();
        lecture4.setLectureName("장애 대응");
        lecture4.setLecturer("한상진");
        lecture4.setDate(LocalDate.of(2025,1,4));
        lecture4.setApplications(new ArrayList<>());

        Mockito.when(lectureRepository.findAvailableLectures(date))
                .thenReturn(List.of(lecture1, lecture2));

        // When
        List<LectureDto> result = lectureService.findAvailableLectures(date);

        // Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("율무", result.get(0).getLecturer());
        Assertions.assertEquals("클린 아키텍처", result.get(1).getLectureName());
    }

}
