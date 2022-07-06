package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.certificatevalid.enums.DataStatusEnum.ACTIVE;
import static br.com.certificatevalid.util.Constants.*;
import static java.util.Objects.*;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<UserOutDto> persist(UserInDto dto) {
        User entityNew = modelMapper.map(dto, User.class);

        validateCpfExists(entityNew.getDocumentCpf());
        validateEmailExists(entityNew.getEmail());
        entityNew.setPassword(validPassword(entityNew.getPassword()));
        entityNew.setDataStatus(ACTIVE);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(repository.save(entityNew), UserOutDto.class));
    }

    public void validateCpfExists(String cpf) {
        if (Boolean.TRUE.equals(repository.existsByDocumentCpf(cpf)))
            throw new BadRequestException(CPF_IN_USE);
    }

    public String validateEmailExists(String email) {
        if (Boolean.TRUE.equals(repository.existsByEmail(email)))
            throw new BadRequestException(EMAIL_IN_USE);
        return email;
    }

    public ResponseEntity<UserOutDto> addCompany(Long userId, Long companyId) {
        User user = findUser(userId);
        Company company = companyService.findCompany(companyId);
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

    public ResponseEntity<UserOutDto> update(Long userId, UserUpdateDto dto) {
        User user = findUser(userId);
        user.setEmail(isNull(dto.getEmail()) ? user.getEmail() : validateEmailExists(dto.getEmail()));
        user.setUsername(isNull(dto.getUsername()) ? user.getUsername() : dto.getUsername());
        user.setPassword(isNull(dto.getPassword()) ? user.getPassword() : validPassword(dto.getPassword()));
        user.setDataStatus(isNull(dto.getDataStatus()) ? user.getDataStatus() : dto.getDataStatus());
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(repository.save(user), UserOutDto.class));
    }

    private String validPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        boolean matches = m.matches();

        if(!matches)
            throw new BadRequestException(WEAK_PASSWORD);

        return sha256Hex(password);
    }

    public ResponseEntity<UserOutDto> resetPassword(Long userId, UserResetPasswordDto dto) {
        User user = findUser(userId);
        if (!dto.getDocumentCpf().equals(user.getDocumentCpf()) || !dto.getEmail().equalsIgnoreCase(user.getEmail()))
            throw new BadRequestException(INVALID_CREDENTIALS);

        user.setPassword(validPassword(dto.getNewPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(repository.save(user), UserOutDto.class));
    }

}
