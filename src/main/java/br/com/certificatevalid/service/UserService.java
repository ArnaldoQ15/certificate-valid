package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.UserInDto;
import br.com.certificatevalid.dto.UserOutDto;
import br.com.certificatevalid.exception.BadRequestException;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.repository.UserRepository;
import br.com.certificatevalid.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<UserOutDto> persist(UserInDto dto) {
        User entityNew = modelMapper.map(dto, User.class);

        validateCpfExists(entityNew.getDocumentCpf());
        validateEmailExists(entityNew.getEmail());
        entityNew.setPassword(sha256Hex(entityNew.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(repository.save(entityNew), UserOutDto.class));
    }


    public void validateCpfExists(String cpf) {
        if (Boolean.TRUE.equals(repository.existsByDocumentCpf(cpf)))
            throw new BadRequestException(Constants.CPF_IN_USE);
    }

    public void validateEmailExists(String email) {
        if (Boolean.TRUE.equals(repository.existsByEmail(email)))
            throw new BadRequestException(Constants.EMAIL_IN_USE);
    }

}
