package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.CompanyOutDto;
import br.com.certificatevalid.dto.UserInDto;
import br.com.certificatevalid.dto.UserOutDto;
import br.com.certificatevalid.exception.BadRequestException;
import br.com.certificatevalid.exception.NotFoundException;
import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.repository.CompanyRepository;
import br.com.certificatevalid.repository.UserRepository;
import br.com.certificatevalid.util.Constants;
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
import java.util.Objects;
import java.util.Optional;

import static br.com.certificatevalid.util.Constants.*;
import static java.util.Objects.*;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

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
            throw new BadRequestException(CPF_IN_USE);
    }

    public void validateEmailExists(String email) {
        if (Boolean.TRUE.equals(repository.existsByEmail(email)))
            throw new BadRequestException(EMAIL_IN_USE);
    }

    public ResponseEntity<UserOutDto> addCompany(Long userId, Long companyId) {
        User user = findUser(userId);
        Company company = findCompany(companyId);
        user.setCompany(company);
        company.setCountUser(company.getCountUser()+1);
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(repository.save(user), UserOutDto.class));
    }

    public ResponseEntity<Page<UserOutDto>> findAll(ParameterFind parameterFind) {
        parameterFind.setPage(isNull(parameterFind.getPage()) ? 0 : parameterFind.getPage());
        parameterFind.setSize(isNull(parameterFind.getSize()) ? 10 : parameterFind.getSize());

        Pageable pageRequest = PageRequest.of(parameterFind.getPage(), parameterFind.getSize(), Sort.by("username").ascending());
        Page<User> user = isNull(parameterFind.getName()) || parameterFind.getName().isBlank() ? repository.findAll(pageRequest) :
                repository.findByUsername(parameterFind.getName().toLowerCase(Locale.ROOT), pageRequest);

        return ResponseEntity.ok(user.map(userRequest -> modelMapper.map(userRequest, UserOutDto.class)));
    }

    public ResponseEntity<UserOutDto> findId(Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(findUser(userId), UserOutDto.class));
    }

    private User findUser(Long userId) {
        Optional<User> user = repository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException(USER_NOT_FOUND);
        return user.get();
    }

    private Company findCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty())
            throw new NotFoundException(COMPANY_NOT_FOUND);
        return company.get();
    }

}
