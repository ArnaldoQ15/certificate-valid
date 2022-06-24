package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.UserInDto;
import br.com.certificatevalid.dto.UserOutDto;
import br.com.certificatevalid.exception.BadRequestException;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.repository.UserRepository;
import br.com.certificatevalid.util.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<UserOutDto> persist(UserInDto dto) {
        User entityNew = new User();
        BeanUtils.copyProperties(dto, entityNew);

        validateCpfExists(entityNew.getDocumentCpf());
        validateEmailExists(entityNew.getEmail());

        repository.save(entityNew);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    public void validateCpfExists(String cpf) {
        if (repository.existsByDocumentCpf(cpf))
            throw new BadRequestException(Constants.CPF_IN_USE);
    }

    public void validateEmailExists(String email) {
        if (repository.existsByEmail(email))
            throw new BadRequestException(Constants.EMAIL_IN_USE);
    }

}
