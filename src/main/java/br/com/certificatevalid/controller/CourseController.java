package br.com.certificatevalid.controller;

import br.com.certificatevalid.dto.CourseInDto;
import br.com.certificatevalid.dto.CourseOutDto;
import br.com.certificatevalid.dto.CourseUpdateDto;
import br.com.certificatevalid.service.CourseService;
import br.com.certificatevalid.util.ParameterFind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/company/course")
public class CourseController {

    @Autowired
    private CourseService service;

    @Transactional
    @PostMapping("/new")
    public ResponseEntity<CourseOutDto> persist(@RequestBody @Valid CourseInDto dto) {
        return service.persist(dto);
    }

    @GetMapping
    public ResponseEntity<Page<CourseOutDto>> findAll(@RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "size", required = false) Integer size,
                                                      @RequestParam(value = "name", required = false) String name) {
        ParameterFind parameterFind = ParameterFind.builder().page(page).size(size).name(name).build();
        return service.findAll(parameterFind);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseOutDto> findId(@PathVariable Long courseId) {
        return service.findId(courseId);
    }

    @PutMapping("/{courseId}/update")
    public ResponseEntity<CourseOutDto> update(@PathVariable Long courseId, @RequestBody CourseUpdateDto dto) {
        return service.update(courseId, dto);
    }

    @DeleteMapping("/{courseId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long courseId) {
        return service.cancel(courseId);
    }

    @DeleteMapping("/{courseId}/finish")
    public ResponseEntity<Void> finish(@PathVariable Long courseId) {
        return service.finish(courseId);
    }

}