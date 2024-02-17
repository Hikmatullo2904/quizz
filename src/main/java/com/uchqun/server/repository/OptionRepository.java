package com.uchqun.server.repository;


import com.uchqun.server.model.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("select o from Option o where o.question.id = :questionId")
    List<Option> findByQuestionId(@Param("questionId") Long questionId);
}
