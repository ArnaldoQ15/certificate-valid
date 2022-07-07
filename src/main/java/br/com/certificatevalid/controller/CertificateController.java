package br.com.certificatevalid.controller;

import br.com.certificatevalid.dto.CertificateInDto;
import br.com.certificatevalid.dto.CertificateOutDto;
import br.com.certificatevalid.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/certificates")
public class CertificateController {

    @Autowired
    private CertificateService service;

    @Transactional
    @PostMapping("/new")
    public ResponseEntity<CertificateOutDto> persist(@RequestBody @Valid CertificateInDto dto) {
        return service.persist(dto);
    }

}