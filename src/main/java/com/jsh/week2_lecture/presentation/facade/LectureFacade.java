package com.jsh.week2_lecture.presentation.facade;

import com.jsh.week2_lecture.application.dto.LectureDto;
import com.jsh.week2_lecture.application.service.ApplicationService;
import com.jsh.week2_lecture.application.service.LectureService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LectureFacade {

    private final LectureService lectureService;
    private final ApplicationService applicationService;

    public LectureFacade(LectureService lectureService, ApplicationService applicationService){
        this.lectureService = lectureService;
        this.applicationService = applicationService;
    }

    public List<LectureDto> findAvailableLectures(LocalDate date){
        return lectureService.findAvailableLectures(date);
    }
}
