package com.jsh.week2_lecture.application.service;

import com.jsh.week2_lecture.application.dto.ApplicationDto;
import com.jsh.week2_lecture.application.dto.LectureDto;
import com.jsh.week2_lecture.domain.entity.Application;
import com.jsh.week2_lecture.domain.entity.Lecture;
import com.jsh.week2_lecture.domain.entity.User;
import com.jsh.week2_lecture.domain.repository.ApplicationRepository;
import com.jsh.week2_lecture.domain.repository.LectureRepository;
import com.jsh.week2_lecture.domain.repository.UserRepository;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationRepository applicationRepository;

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

    @Test
    void applyLecture_Success(){
        //Given
        Long userId = 1L;
        Long lectureId = 1L;

        User mockUser = new User();
        mockUser.setUserId(userId);

        Lecture mockLecture = new Lecture();
        mockLecture.setLectureId(lectureId);
        mockLecture.setApplications(new ArrayList<>());

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        Mockito.when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(mockLecture));
        Mockito.when(applicationRepository.existsByUserAndLecture(mockUser,mockLecture)).thenReturn(false);

        //When
        lectureService.applyLecture(userId,lectureId);

        //Then
        Mockito.verify(applicationRepository).save(Mockito.any(Application.class));
    }

    @Test
    void testGetApplicationsByUserId(){
        //Given
        Long userId = 1L;
        Long lectureId = 1L;
        User mockUser = new User();
        mockUser.setUserId(userId);
        Lecture mockLecture = new Lecture();
        mockLecture.setLectureId(lectureId);

        Application application1 = new Application(mockUser,mockLecture);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        Mockito.when(applicationRepository.findByUser(mockUser))
                .thenReturn(List.of(application1));

        //When
        List<ApplicationDto>result = lectureService.getApplicationsByUserId(userId);

        //Then
        Assertions.assertEquals(1, result.size());
    }
}
