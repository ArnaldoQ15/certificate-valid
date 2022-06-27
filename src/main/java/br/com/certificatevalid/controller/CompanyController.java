package br.com.certificatevalid.controller;

import br.com.certificatevalid.dto.CompanyOutDto;
import br.com.certificatevalid.service.CompanyService;
import br.com.certificatevalid.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService service;


    @PostMapping("/code/new")
    public ResponseEntity<CompanyOutDto> persist () {
        return service.persist();
    }

}
