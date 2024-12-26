package com.jsh.week2_lecture.application.dto;

import com.jsh.week2_lecture.domain.entity.Lecture;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class LectureDto {

    private Long lectureId;
    private String lectureName;
    private String lecturer;
    private LocalDate date;
    private LocalTime time;
    private int availableSlots;

    public static LectureDto fromEntity(Lecture lecture) {
        LectureDto dto = new LectureDto();
        dto.lectureId = lecture.getLectureId();
        dto.lectureName = lecture.getLectureName();
        dto.lecturer = lecture.getLecturer();
        dto.date = lecture.getDate();
        dto.time = lecture.getTime();
        dto.availableSlots = 30 - lecture.getApplications().size();
        return dto;
    }
}
