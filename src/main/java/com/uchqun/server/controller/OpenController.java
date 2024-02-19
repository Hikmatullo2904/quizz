package com.uchqun.server.controller;

import com.uchqun.server.config.service.CustomUserDetails;
import com.uchqun.server.model.responseDto.QuestionResponse;
import com.uchqun.server.model.responseDto.QuizResponse;
import com.uchqun.server.service.interfaces.QuizService;
import com.uchqun.server.util.annotations.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/open")
@RequiredArgsConstructor
public class OpenController {
    private final QuizService quizService;

    @GetMapping("/visible/teacher/{id}")
    public List<QuizResponse> getVisibleTestByTeacherId(@PathVariable Long id) {
        return quizService.getVisibleQuizByTeacherId(id);
    }

    @GetMapping("/all/visible")
    public List<QuizResponse> getAllVisibleTest() {
        return quizService.getAllVisibleQuiz();
    }

    @GetMapping("/{id}")
    public List<QuestionResponse> getQuestionList(@PathVariable Long id) {
        return quizService.getQuestionList(id);
    }

}
