package br.com.certificatevalid.controller;

import br.com.certificatevalid.dto.UserInDto;
import br.com.certificatevalid.dto.UserOutDto;
import br.com.certificatevalid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


    @Transactional
    @PostMapping("/new")
    public ResponseEntity<UserOutDto> persist(@RequestBody @Valid UserInDto dto) {
        return service.persist(dto);
    }

}
