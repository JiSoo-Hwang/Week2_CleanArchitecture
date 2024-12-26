package com.jsh.week2_lecture.application.service;

import com.jsh.week2_lecture.application.dto.LectureDto;
import com.jsh.week2_lecture.domain.entity.Application;
import com.jsh.week2_lecture.domain.entity.Lecture;
import com.jsh.week2_lecture.domain.entity.User;
import com.jsh.week2_lecture.domain.exception.ErrorResponse;
import com.jsh.week2_lecture.domain.exception.LectureException;
import com.jsh.week2_lecture.domain.repository.ApplicationRepository;
import com.jsh.week2_lecture.domain.repository.LectureRepository;
import com.jsh.week2_lecture.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public LectureService(LectureRepository lectureRepository,
                          ApplicationRepository applicationRepository,
                          UserRepository userRepository){
        this.lectureRepository = lectureRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public List<LectureDto> findAvailableLectures(LocalDate date){
        List<Lecture>lectures = lectureRepository.findAvailableLectures(date);

        return lectures.stream()
                .filter(lecture -> lecture.getApplications().size()<30)
                .map(LectureDto::fromEntity)
                .toList();
    }

    @Transactional
    public void applyLecture(Long userId, Long lectureId) {
        // 강의가 존재하는지 확인
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new LectureException(ErrorResponse.LECTURE_NOT_FOUND));

        //사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(()->new LectureException(ErrorResponse.USER_NOT_FOUND));

        // 중복 신청 여부
        if(applicationRepository.existsByUserAndLecture(user,lecture)){
            throw new LectureException(ErrorResponse.DUPLICATE_APPLICATION);
        }

        // 정원 초과 여부
        int currentApplications = lecture.getApplications().size();

        if(currentApplications >=30){
            throw new LectureException(ErrorResponse.LECTURE_FULL);
        }

        // 신청 처리
        Application application = new Application(user, lecture);
        applicationRepository.save(application);
    }

}
