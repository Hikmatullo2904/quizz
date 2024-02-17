package com.uchqun.server.repository;

import com.uchqun.server.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("select q from Quiz q where q.teacher.id = :teacherId")
    List<Quiz> getByTeacherId(@Param("teacherId") Long teacherId);

    @Query("select q from Quiz q where q.isVisible = true")
    List<Quiz> getAllVisibleQuizzes();

    @Query("select q from Quiz q where q.isVisible = true and q.teacher.id = :teacherId")
    List<Quiz> getVisibleQuizByTeacherId(@Param("teacherId") Long teacherId);

}
