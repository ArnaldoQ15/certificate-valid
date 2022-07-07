package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.CertificateInDto;
import br.com.certificatevalid.dto.CertificateOutDto;
import br.com.certificatevalid.model.Certificate;
import br.com.certificatevalid.model.Course;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CertificateService extends BaseService {

    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private CertificateRepository repository;


    public ResponseEntity<CertificateOutDto> persist(CertificateInDto dto) {
        User user = findUser(dto.getUserId());
        Course course = findCourse(dto.getCourseId());
        Certificate certificate = Certificate.builder()
                .course(course)
                .user(user)
                .verificationCode(verificationCodeService.generateUniqueCode(course.getCompany().getCompanyVerificationCode(),
                        user.getUserVerificationCode(), course.getCourseVerificationCode()))
                .build();

        repository.save(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(CertificateOutDto.builder()
                        .username(user.getUsername())
                        .companyName(certificate.getCourse().getCompany().getCompanyName())
                        .verificationCode(certificate.getVerificationCode().getFullField())
                .build());
    }

}