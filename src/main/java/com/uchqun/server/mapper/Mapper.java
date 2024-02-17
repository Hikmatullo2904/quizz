package com.uchqun.server.mapper;

import com.uchqun.server.model.entity.Question;
import com.uchqun.server.model.entity.Quiz;
import com.uchqun.server.model.entity.Option;
import com.uchqun.server.model.requestDto.QuizRequest;
import com.uchqun.server.model.requestDto.OptionRequest;
import com.uchqun.server.model.responseDto.StudentQuizResponse;
import com.uchqun.server.model.responseDto.QuestionResponse;
import com.uchqun.server.model.responseDto.QuizResponse;
import com.uchqun.server.model.responseDto.OptionResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapper {

    @Value("${image.get.url}")
    private String pictureApi;


    public Quiz mapToQuiz(QuizRequest quizRequest) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        quiz.setDuration(quizRequest.getDuration());
        quiz.setIsVisible(quizRequest.getIsVisible());
        return quiz;
    }



    public Option mapToOption(OptionRequest optionRequest) {
        Option option = new Option();
        option.setLabel(optionRequest.getLabel());
        option.setIsCorrect(optionRequest.getIsCorrect());
        return option;
    }

    public List<Option> mapToOptionList(List<OptionRequest> optionRequestList) {
        return optionRequestList.stream().map(this::mapToOption).toList();
    }




    public OptionResponse mapToOptionResponse(Option option) {
        OptionResponse optionResponse = new OptionResponse();
        optionResponse.setId(option.getId());
        optionResponse.setLabel(option.getLabel());
        optionResponse.setIsCorrect(option.getIsCorrect());
        return optionResponse;
    }

    public List<OptionResponse> mapToOptionResponseList(List<Option> options) {
        if(options == null || options.isEmpty()) {
            return new ArrayList<>();
        }
        return options
                .stream()
                .map(this::mapToOptionResponse)
                .toList();
    }

    public StudentQuizResponse mapToQuizResponse(@NotNull Quiz quiz) {
        StudentQuizResponse testResponse = new StudentQuizResponse();
        testResponse.setId(quiz.getId());
        testResponse.setTestName(quiz.getTitle());
        testResponse.setDescription(quiz.getDescription());
        testResponse.setDuration(quiz.getDuration());
        testResponse.setIsVisible(quiz.getIsVisible());
        testResponse.setTestItemsCount(quiz.getQuestions().size());
        return testResponse;
    }

    public List<QuizResponse> mapToQuizResponseList(List<Quiz> quizzes) {
        if(quizzes == null || quizzes.isEmpty()) {
            return new ArrayList<>();
        }
        List<QuizResponse> quizResponse = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            quizResponse.add(mapToQuizResponse(quiz));
        }
        return quizResponse;
    }

    public List<StudentQuizResponse> mapToStudentQuizResponseList(List<Quiz> quizzes) {
        if(quizzes == null || quizzes.isEmpty()) {
            return new ArrayList<>();
        }
        return quizzes
                .stream()
                .map(this::mapToQuizResponse)
                .toList();
    }


    public QuestionResponse mapToQuestionResponse(Question question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setOptions(mapToOptionResponseList(question.getOptions()));
        questionResponse.setPictureUrl(this.pictureApi + question.getPicture().getId());
        return questionResponse;
    }

    public List<QuestionResponse> mapToQuestionResponses(List<Question> questions) {
        if(questions == null || questions.isEmpty()) {
            return new ArrayList<>();
        }
        return questions
                .stream()
                .map(this::mapToQuestionResponse)
                .toList();
    }
}
