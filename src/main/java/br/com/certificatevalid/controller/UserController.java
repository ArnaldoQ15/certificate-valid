package br.com.certificatevalid.controller;

import br.com.certificatevalid.dto.UserInDto;
import br.com.certificatevalid.dto.UserOutDto;
import br.com.certificatevalid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/new")
    public ResponseEntity<UserOutDto> persist(@RequestBody UserInDto dto) {
        service.persist(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
