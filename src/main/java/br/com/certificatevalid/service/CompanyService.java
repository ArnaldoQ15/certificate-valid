package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.CompanyInDto;
import br.com.certificatevalid.dto.CompanyOutDto;
import br.com.certificatevalid.exception.BadRequestException;
import br.com.certificatevalid.exception.NotFoundException;
import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.repository.CompanyRepository;
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

import static br.com.certificatevalid.util.Constants.COMPANY_NOT_FOUND;
import static br.com.certificatevalid.util.Constants.CONTACT_EMAIL_IN_USE;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<CompanyOutDto> persist(CompanyInDto dto) {
        if (Boolean.TRUE.equals(repository.existsByContactEmail(dto.getContactEmail())))
            throw new BadRequestException(CONTACT_EMAIL_IN_USE);

        Company entityNew = modelMapper.map(dto, Company.class);
        entityNew.setCountUser(0L);
        entityNew.setCompanyVerificationCode(verificationCodeService.generateCompanyCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(repository.save(entityNew), CompanyOutDto.class));
    }

    public ResponseEntity<Page<CompanyOutDto>> findAll(ParameterFind parameterFind) {
        parameterFind.setPage(Objects.isNull(parameterFind.getPage()) ? 0 : parameterFind.getPage());
        parameterFind.setSize(Objects.isNull(parameterFind.getSize()) ? 10 : parameterFind.getSize());

        Pageable pageRequest = PageRequest.of(parameterFind.getPage(), parameterFind.getSize(), Sort.by("companyName").ascending());
        Page<Company> companies = Objects.isNull(parameterFind.getName()) || parameterFind.getName().isBlank() ? repository.findAll(pageRequest) :
                repository.findByCompanyName(parameterFind.getName().toLowerCase(Locale.ROOT), pageRequest);

        return ResponseEntity.ok(companies.map(company -> modelMapper.map(company, CompanyOutDto.class)));
    }

    public ResponseEntity<CompanyOutDto> findId(Long companyId) {
        return ResponseEntity.ok(modelMapper.map(repository.findById(companyId).orElseThrow(() ->
                new NotFoundException(COMPANY_NOT_FOUND)), CompanyOutDto.class));
    }

}