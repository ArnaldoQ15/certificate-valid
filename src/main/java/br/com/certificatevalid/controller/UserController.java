package br.com.certificatevalid.controller;

import br.com.certificatevalid.dto.*;
import br.com.certificatevalid.service.UserService;
import br.com.certificatevalid.util.ParameterFind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Transactional
    @PutMapping("/{userId}/add-company")
    public ResponseEntity<UserOutDto> addCompany(@PathVariable Long userId, @RequestParam @Valid Long companyId) {
        return service.addCompany(userId, companyId);
    }

    @GetMapping
    public ResponseEntity<Page<UserOutDto>> findAll(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "size", required = false) Integer size,
                                                    @RequestParam(value = "name", required = false) String name) {
        ParameterFind parameterFind = ParameterFind.builder().page(page).size(size).name(name).build();
        return service.findAll(parameterFind);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserOutDto> findId(@PathVariable Long userId) {
        return service.findId(userId);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<UserOutDto> update(@PathVariable Long userId, @RequestBody UserUpdateDto dto) {
        return service.update(userId, dto);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<UserOutResetPasswordDto> resetPassword(@RequestParam Long userId,
                                                    @RequestBody @Valid UserInResetPasswordDto dto) {
        return service.resetPassword(userId, dto);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        return service.delete(userId);
    }

}