package br.com.certificatevalid.controller;

import br.com.certificatevalid.dto.CompanyInDto;
import br.com.certificatevalid.dto.CompanyOutDto;
import br.com.certificatevalid.service.CompanyService;
import br.com.certificatevalid.util.ParameterFind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService service;


    @Transactional
    @PostMapping("/new")
    public ResponseEntity<CompanyOutDto> persist (@RequestBody @Valid CompanyInDto dto) {
        return service.persist(dto);
    }

    @GetMapping
    public ResponseEntity<Page<CompanyOutDto>> findAll(@RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "size", required = false) Integer size,
                                                       @RequestParam(value = "name", required = false) String name) {
        ParameterFind parameterFind = ParameterFind.builder().page(page).size(size).name(name).build();
        return service.findAll(parameterFind);
    }

}
