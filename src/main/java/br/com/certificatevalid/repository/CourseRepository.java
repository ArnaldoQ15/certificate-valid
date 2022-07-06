package br.com.certificatevalid.repository;

import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE LOWER(c.title) LIKE :title%")
    Page<Course> findByTitle(String title, Pageable pageRequest);

    Boolean existsByTitle(String title);

}
