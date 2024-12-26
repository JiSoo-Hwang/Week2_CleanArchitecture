package com.jsh.week2_lecture.presentation.controller;

import com.jsh.week2_lecture.application.dto.LectureDto;
import com.jsh.week2_lecture.domain.exception.ErrorResponse;
import com.jsh.week2_lecture.domain.exception.LectureException;
import com.jsh.week2_lecture.presentation.facade.LectureFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LectureControllerTest {

    @Mock
    private LectureFacade lectureFacade;

    @InjectMocks
    private LectureController lectureController;

    @Test
    public void testFindAvailableLectures(){
        //Given
        LocalDate date = LocalDate.of(2024,12,28);

        LectureDto lectureDto = new LectureDto();
        lectureDto.setLectureId(1L);
        lectureDto.setLecturer("율무");
        lectureDto.setLectureName("클린 아키텍처");
        lectureDto.setDate(LocalDate.of(2024,12,28));
        lectureDto.setTime(LocalTime.of(19,0));
        Mockito.when(lectureFacade.findAvailableLectures(date))
                .thenReturn(List.of(lectureDto));

        //When
        ResponseEntity<List<LectureDto>> response = lectureController.findAvailableLectures(String.valueOf(date));

        //Then
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1,response.getBody().size());
        Assertions.assertEquals("클린 아키텍처",response.getBody().get(0).getLectureName());
    }

}
