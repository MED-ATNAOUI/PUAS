package com.pfe.learningplatform.repository;

import com.pfe.learningplatform.model.Course;
import com.pfe.learningplatform.model.Enrollment;
import com.pfe.learningplatform.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    /*
     * =====================================
     * FIND BY STUDENT
     * =====================================
     */

    List<Enrollment> findByStudent(User student);

    /*
     * =====================================
     * FIND BY COURSE
     * =====================================
     */

    List<Enrollment> findByCourse(Course course);

    /*
     * =====================================
     * FIND BY STUDENT AND COURSE
     * =====================================
     */

    Optional<Enrollment> findByStudentAndCourse(
            User student,
            Course course
    );
}
