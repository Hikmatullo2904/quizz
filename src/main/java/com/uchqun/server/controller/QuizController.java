package com.uchqun.server.controller;

import com.uchqun.server.model.entity.User;
import com.uchqun.server.model.requestDto.QuestionRequest;
import com.uchqun.server.model.requestDto.QuizRequest;
import com.uchqun.server.model.responseDto.QuizDetailedResponse;
import com.uchqun.server.model.responseDto.QuizResponse;
import com.uchqun.server.payload.ApiResponse;
import com.uchqun.server.service.interfaces.QuizService;
import com.uchqun.server.util.annotations.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/all")
    public List<QuizResponse> getAll() {
        return quizService.getQuizzes();
    }



    @GetMapping("/teacher")
    public List<QuizResponse> getTestByTeacherId(@CurrentUser User user) {
        return quizService.getQuizByTeacherId(user.getId());
    }

    @GetMapping("/{id}")
    public QuizResponse getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/{id}/detailed")
    public QuizDetailedResponse getDetailedQuizById(@PathVariable Long id) {
        return quizService.getDetailedQuiz(id);
    }

    @PostMapping
    public ApiResponse save(@CurrentUser User user , @RequestBody QuizRequest quizRequest) {
        return quizService.createQuiz(user.getId(), quizRequest);
    }


    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody QuizRequest quizRequest) {
        return quizService.updateQuiz(id, quizRequest);
    }

    @PutMapping("/{id}/question")
    public ApiResponse updateQuestion(@PathVariable Long id, @RequestBody QuestionRequest questionRequest) {
        return quizService.updateQuestion(id, questionRequest);
    }

    @PatchMapping("/{id}/visible-toggle")
    public ApiResponse toggleVisible(@PathVariable Long id) {
        return quizService.toggleVisible(id);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        return quizService.deleteQuiz(id);
    }

}
