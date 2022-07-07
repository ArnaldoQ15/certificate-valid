package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.CourseInDto;
import br.com.certificatevalid.dto.CourseOutDto;
import br.com.certificatevalid.dto.CourseUpdateDto;
import br.com.certificatevalid.enums.CourseDataStatusEnum;
import br.com.certificatevalid.exception.BadRequestException;
import br.com.certificatevalid.model.Course;
import br.com.certificatevalid.repository.CourseRepository;
import br.com.certificatevalid.util.ParameterFind;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static br.com.certificatevalid.enums.CourseDataStatusEnum.ACTIVE;
import static br.com.certificatevalid.util.Constants.*;
import static java.time.OffsetDateTime.now;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class CourseService extends BaseService {

    @Autowired
    private CourseRepository repository;
    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<CourseOutDto> persist(CourseInDto dto) {
        if (Boolean.TRUE.equals(repository.existsByTitle(dto.getTitle().toLowerCase(Locale.ROOT))))
            throw new BadRequestException(COURSE_TITLE_IN_USE);

        Course entityNew = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .courseVerificationCode(generateCourseVerificationCode())
                .company(findCompany(dto.getCompanyId()))
                .dataStatus(ACTIVE)
                .finishDate(dto.getFinishDate())
                .build();
        repository.save(entityNew);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(entityNew, CourseOutDto.class));
    }

    public ResponseEntity<Page<CourseOutDto>> findAll(ParameterFind parameterFind) {
        parameterFind.setPage(isNull(parameterFind.getPage()) ? 0 : parameterFind.getPage());
        parameterFind.setSize(isNull(parameterFind.getSize()) ? 10 : parameterFind.getSize());

        Pageable pageRequest = PageRequest.of(parameterFind.getPage(), parameterFind.getSize(), Sort.by("title").ascending());
        Page<Course> courses = isNull(parameterFind.getName()) || parameterFind.getName().isBlank() ? repository.findAll(pageRequest) :
                repository.findByTitle(parameterFind.getName().toLowerCase(Locale.ROOT), pageRequest);
        return ResponseEntity.ok(courses.map(course -> modelMapper.map(course, CourseOutDto.class)));
    }

    public ResponseEntity<CourseOutDto> findId(Long courseId) {
        return ResponseEntity.ok(modelMapper.map(findCourse(courseId), CourseOutDto.class));
    }

    public ResponseEntity<CourseOutDto> update(Long courseId, CourseUpdateDto dto) {
        Course course = findCourse(courseId);
        if (course.getFinishDate().isBefore(now()))
            throw new BadRequestException(COURSE_EXPIRED);
        if (nonNull(dto.getFinishDate()) && dto.getFinishDate().isBefore(now()))
            throw new BadRequestException(INVALID_COURSE_DATE);

        course.setTitle(isNull(dto.getTitle()) ? course.getTitle() : dto.getTitle());
        course.setDescription(isNull(dto.getDescription()) ? course.getDescription() : dto.getDescription());
        course.setFinishDate(isNull(dto.getFinishDate()) ? course.getFinishDate() : dto.getFinishDate());
        repository.save(course);
        return ResponseEntity.ok(modelMapper.map(course, CourseOutDto.class));
    }

    public ResponseEntity<Void> cancel(Long courseId) {
        Course course = findCourse(courseId);
        course.setDataStatus(CourseDataStatusEnum.CANCELED);
        repository.save(course);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> finish(Long courseId) {
        Course course = findCourse(courseId);
        course.setDataStatus(CourseDataStatusEnum.FINISHED);
        repository.save(course);
        return ResponseEntity.noContent().build();
    }

}