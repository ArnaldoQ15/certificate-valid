package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.CompanyOutDto;
import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<CompanyOutDto> persist() {
        Company entityNew = new Company();
        entityNew.setCompanyVerificationCode(verificationCodeService.generateCompanyCode(entityNew));

        repository.save(entityNew);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
