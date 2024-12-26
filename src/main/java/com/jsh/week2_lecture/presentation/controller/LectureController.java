package com.jsh.week2_lecture.presentation.controller;

import com.jsh.week2_lecture.application.dto.LectureDto;
import com.jsh.week2_lecture.domain.exception.LectureException;
import com.jsh.week2_lecture.presentation.facade.LectureFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {


    private final LectureFacade lectureFacade;

    public LectureController(LectureFacade lectureFacade) {
        this.lectureFacade = lectureFacade;
    }

    @GetMapping("/available")
    public ResponseEntity<List<LectureDto>> findAvailableLectures(@RequestParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(lectureFacade.findAvailableLectures(localDate));
    }

    @PostMapping("/{lectureId}/apply")
    public ResponseEntity<Void>applyLecture(
            @PathVariable Long lectureId,
            @RequestHeader("userId") Long userId){
        lectureFacade.applyLecture(userId, lectureId);
        return ResponseEntity.ok().build();
    }

}