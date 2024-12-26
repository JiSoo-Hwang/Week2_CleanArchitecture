package com.jsh.week2_lecture.application.service;

import com.jsh.week2_lecture.application.dto.LectureDto;
import com.jsh.week2_lecture.application.validator.LectureValidator;
import com.jsh.week2_lecture.domain.entity.Lecture;
import com.jsh.week2_lecture.domain.repository.LectureRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureValidator validator;

    public LectureService(LectureRepository lectureRepository, LectureValidator validator){
        this.lectureRepository = lectureRepository;
        this.validator = validator;
    }

    public List<LectureDto> findAvailableLectures(LocalDate date){
        List<Lecture>lectures = lectureRepository.findAvailableLectures(date);

        return lectures.stream()
                .filter(lecture -> lecture.getApplications().size()<30)
                .map(LectureDto::fromEntity)
                .toList();
    }
}
