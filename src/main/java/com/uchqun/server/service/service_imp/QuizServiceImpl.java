package com.uchqun.server.service.service_imp;

import com.uchqun.server.exception.CustomBadRequestException;
import com.uchqun.server.exception.CustomNotFoundException;
import com.uchqun.server.mapper.Mapper;
import com.uchqun.server.model.entity.*;
import com.uchqun.server.model.requestDto.QuestionRequest;
import com.uchqun.server.model.requestDto.QuizRequest;
import com.uchqun.server.model.requestDto.OptionRequest;
import com.uchqun.server.model.responseDto.QuestionResponse;
import com.uchqun.server.model.responseDto.QuizResponse;
import com.uchqun.server.payload.ApiResponse;
import com.uchqun.server.repository.QuestionRepository;
import com.uchqun.server.repository.QuizRepository;
import com.uchqun.server.repository.UserRepository;
import com.uchqun.server.service.interfaces.PictureService;
import com.uchqun.server.service.interfaces.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final Mapper mapper;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final PictureService pictureService;
    private final UserRepository userRepository;


    @Override
    public ApiResponse createQuiz(Long teacherId, QuizRequest quizRequest) {
        Quiz quiz = mapper.mapToQuiz(quizRequest);
        User teacher = userRepository.findById(teacherId).orElseThrow(() -> new CustomNotFoundException("user not found"));
        quiz.setTeacher(teacher);
        List<QuestionRequest> questionRequestList = quizRequest.getQuestions();
        for (QuestionRequest teq : questionRequestList) {
            Question question = makeQuestion(teq, quiz);
            quiz.getQuestions().add(question);
        }
        quizRepository.save(quiz);
        return new ApiResponse("saved successfully", HttpStatus.OK);
    }

    private Question makeQuestion(QuestionRequest teq, Quiz quiz) {
        Question question = new Question();
        question.setQuestion(teq.getQuestion());
        question.setPicture(pictureService.save(teq.getPicture()));
        if (!checkVariants(teq.getOptions())) {
            throw new CustomBadRequestException("there is a problem with correct answers");
        }
        List<Option> variants = mapper.mapToOptionList(teq.getOptions());
        variants.forEach(v -> v.setQuestion(question));
        question.setOptions(variants);
        question.setQuiz(quiz);
        return question;
    }

    boolean checkVariants(List<OptionRequest> optionRequests) {
        int count = 0;
        for(OptionRequest req : optionRequests) {
            if(req.getIsCorrect())
                count++;
        }

        return count == 1;
    }



    @Override
    public ApiResponse updateQuiz(Long testId, QuizRequest quizRequest) {
        Quiz quizById = getTestById(testId);
        if(quizRequest.getTitle() != null)
            quizById.setTitle(quizRequest.getTitle());
        if(quizRequest.getDescription() != null)
            quizById.setDescription(quizRequest.getDescription());
        if(quizRequest.getIsVisible() != null)
            quizById.setIsVisible(quizRequest.getIsVisible());
        quizRepository.save(quizById);

        return null;
    }

    @Override
    public ApiResponse deleteQuiz(Long testId) {
        Quiz quizById = getTestById(testId);
        for(Question question : quizById.getQuestions()) {
            Picture picture = question.getPicture();
            pictureService.delete(picture.getId());
        }
        quizById.setTeacher(null);
        quizRepository.delete(quizById);
        return new ApiResponse("Deleted successfully", HttpStatus.OK);
    }

    @Override
    public List<QuizResponse> getQuizzes() {
        List<Quiz> all = quizRepository.findAll();
        return mapper.mapToQuizResponseList(all);
    }

    @Override
    public List<QuizResponse> getQuizByTeacherId(Long teacherId) {
        List<Quiz> byTeacherId = quizRepository.getByTeacherId(teacherId);
        return mapper.mapToQuizResponseList(byTeacherId);
    }

    @Override
    public List<QuestionResponse> getQuestionList(Long testId) {
        List<Question> allByTestId = questionRepository.findAllByQuizId(testId);
        return mapper.mapToQuestionResponses(allByTestId);
    }

    public Quiz getTestById(Long id) {
        Optional<Quiz> byId = quizRepository.findById(id);
        if(byId.isPresent()) {
            return byId.get();
        }
        throw new CustomNotFoundException("Test not found");
    }

    @Override
    public List<QuizResponse> getVisibleQuizByTeacherId(Long teacherId) {
        List<Quiz> byTeacherId = quizRepository.getVisibleQuizByTeacherId(teacherId);
        return mapper.mapToQuizResponseList(byTeacherId);
    }

    @Override
    public List<QuizResponse> getAllVisibleQuiz() {
        List<Quiz> allVisibleQuiz = quizRepository.getAllVisibleQuizzes();
        return mapper.mapToQuizResponseList(allVisibleQuiz);
    }


    public ApiResponse updateQuestion(Long id, QuestionRequest questionRequest) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Question not found"));
        questionRepository.save(makeQuestion(questionRequest, question.getQuiz()));
        return new ApiResponse("updated successfully", HttpStatus.OK);
    }

    @Override
    public ApiResponse toggleVisible(Long id) {
        Quiz quiz = getTestById(id);
        quiz.setIsVisible(!quiz.getIsVisible());
        quizRepository.save(quiz);
        return new ApiResponse("updated successfully", HttpStatus.OK);
    }

    @Override
    public QuizResponse getQuizById(Long id) {
        Quiz quiz = getTestById(id);
        return mapper.mapToQuizResponse(quiz);
    }
}
