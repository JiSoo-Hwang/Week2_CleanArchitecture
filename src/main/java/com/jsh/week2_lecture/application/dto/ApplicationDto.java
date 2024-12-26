package com.jsh.week2_lecture.application.dto;

import com.jsh.week2_lecture.domain.entity.Application;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationDto {

    private Long applicationId;
    private Long lectureId;
    private String lectureName;
    private String lecturer;
    private LocalDateTime appliedAt;

    public static ApplicationDto fromEntity(Application application){
        ApplicationDto dto = new ApplicationDto();
        dto.applicationId = application.getApplicationId();
        dto.lectureId = application.getLecture().getLectureId();
        dto.lectureName = application.getLecture().getLectureName();
        dto.lecturer = application.getLecture().getLecturer();
        dto.appliedAt = application.getAppliedAt();
        return dto;
    }

}
