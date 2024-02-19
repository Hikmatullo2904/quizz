package com.uchqun.server.service.interfaces;

import com.uchqun.server.model.requestDto.QuestionRequest;
import com.uchqun.server.model.requestDto.QuizRequest;
import com.uchqun.server.model.responseDto.QuestionResponse;
import com.uchqun.server.model.responseDto.QuizResponse;
import com.uchqun.server.payload.ApiResponse;

import java.util.List;

public interface QuizService {

    ApiResponse createQuiz(Long teacherId, QuizRequest quizRequest);
    ApiResponse updateQuiz(Long testId, QuizRequest quizRequest);
    ApiResponse deleteQuiz(Long testId);

    List<QuizResponse> getQuizzes();
    List<QuestionResponse> getQuestionList(Long testId);
    List<QuizResponse> getQuizByTeacherId(Long teacherId);
    List<QuizResponse> getVisibleQuizByTeacherId(Long teacherId);
    List<QuizResponse> getAllVisibleQuiz();
    ApiResponse updateQuestion(Long id, QuestionRequest questionRequest);

    ApiResponse toggleVisible(Long id);
}
