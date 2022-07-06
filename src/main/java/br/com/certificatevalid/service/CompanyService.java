package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.*;
import br.com.certificatevalid.exception.BadRequestException;
import br.com.certificatevalid.exception.NotFoundException;
import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.repository.CompanyRepository;
import br.com.certificatevalid.util.ParameterFind;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static br.com.certificatevalid.util.Constants.*;
import static java.util.Objects.*;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Service
public class CompanyService extends BaseService {

    @Autowired
    private CompanyRepository repository;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<CompanyOutCreateDto> persist(CompanyInDto dto) {
        if (Boolean.TRUE.equals(repository.existsByContactEmail(dto.getContactEmail())))
            throw new BadRequestException(CONTACT_EMAIL_IN_USE);

        Company entityNew = modelMapper.map(dto, Company.class);
        entityNew.setCountUser(0L);
        entityNew.setCompanyVerificationCode(generateCompanyVerificationCode());
        entityNew.setCompanyPassword(generateCompanyPassword());

        Company entityPersisted = new Company();
        BeanUtils.copyProperties(entityNew, entityPersisted);
        entityPersisted.setCompanyPassword(sha256Hex(entityNew.getCompanyPassword()));
        repository.save(entityPersisted);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(entityNew, CompanyOutCreateDto.class));
    }

    public String generateCompanyPassword () {
        String password = (RandomString.make(20));
        if (Boolean.TRUE.equals(repository.existsByCompanyPassword(password)))
            password = generateCompanyPassword();
        return password;
    }

    public ResponseEntity<Page<CompanyOutDto>> findAll(ParameterFind parameterFind) {
        parameterFind.setPage(isNull(parameterFind.getPage()) ? 0 : parameterFind.getPage());
        parameterFind.setSize(isNull(parameterFind.getSize()) ? 10 : parameterFind.getSize());

        Pageable pageRequest = PageRequest.of(parameterFind.getPage(), parameterFind.getSize(), Sort.by("companyName").ascending());
        Page<Company> companies = isNull(parameterFind.getName()) || parameterFind.getName().isBlank() ? repository.findAll(pageRequest) :
                repository.findByCompanyName(parameterFind.getName().toLowerCase(Locale.ROOT), pageRequest);

        return ResponseEntity.ok(companies.map(company -> modelMapper.map(company, CompanyOutDto.class)));
    }

    public ResponseEntity<CompanyOutDto> findId(Long companyId) {
        return ResponseEntity.ok(modelMapper.map(findCompany(companyId), CompanyOutDto.class));
    }

    public ResponseEntity<CompanyOutDto> update(Long companyId, CompanyUpdateDto dto) {
        User user = findUser(dto.getUserId());
        Company company = findCompany(companyId);
        if (!user.getEmail().equals(company.getContactEmail()) || !dto.getCompanyPassword().equals(company.getCompanyPassword()))
            throw new BadRequestException(CANT_UPDATE_COMPANY);

        company.setCompanyName(isNull(dto.getCompanyName()) || dto.getCompanyName().isBlank() ? company.getCompanyName() : dto.getCompanyName());
        company.setContactEmail(isNull(dto.getContactEmail()) || dto.getCompanyName().isBlank() ? company.getContactEmail() : dto.getContactEmail());

        return ResponseEntity.ok(modelMapper.map(repository.save(company), CompanyOutDto.class));
    }

    public ResponseEntity<CompanyOutCreateDto> resetPassword(Long companyId, CompanyInDto dto) {
        Company company = findCompany(companyId);
        if (!company.getContactEmail().equals(dto.getContactEmail()) || !dto.getCompanyName().equalsIgnoreCase(company.getCompanyName()))
            throw new BadRequestException(CANT_UPDATE_COMPANY);
        company.setCompanyPassword(generateCompanyPassword());
        String newPassword = company.getCompanyPassword();
        company.setCompanyPassword(sha256Hex(newPassword));
        repository.save(company);

        company.setCompanyPassword(newPassword);
        return ResponseEntity.ok(modelMapper.map(company, CompanyOutCreateDto.class));
    }

}