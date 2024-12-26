package com.jsh.week2_lecture.presentation.facade;

import com.jsh.week2_lecture.application.dto.ApplicationDto;
import com.jsh.week2_lecture.application.dto.LectureDto;
import com.jsh.week2_lecture.application.service.LectureService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LectureFacade {

    private final LectureService lectureService;

    public LectureFacade(LectureService lectureService){
        this.lectureService = lectureService;
    }

    public List<LectureDto> findAvailableLectures(LocalDate date){
        return lectureService.findAvailableLectures(date);
    }

    public void applyLecture(Long userId, Long lectureId) {
        lectureService.applyLecture(userId,lectureId);
    }

    public List<ApplicationDto> getApplicationsByUserId(Long userId) {
        return lectureService.getApplicationsByUserId(userId);
    }
}
