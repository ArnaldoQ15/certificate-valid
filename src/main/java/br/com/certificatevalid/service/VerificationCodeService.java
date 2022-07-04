package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.VerificationCodeDto;
import br.com.certificatevalid.exception.NotFoundException;
import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.model.VerificationCode;
import br.com.certificatevalid.repository.CompanyRepository;
import br.com.certificatevalid.repository.UserRepository;
import br.com.certificatevalid.repository.VerificationCodeRepository;
import br.com.certificatevalid.util.Constants;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

import static java.util.Locale.*;

@Service
public class VerificationCodeService {

    @Autowired
    private VerificationCodeRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private ModelMapper modelMapper;


    public String generateCompanyCode() {
        VerificationCode entityNew = new VerificationCode();
        entityNew.setFirstField(RandomString.make(10).toUpperCase(ROOT));
        return validateCodeExists(entityNew.getFirstField());
    }

    private String validateCodeExists(String firstField) {
        if (Boolean.TRUE.equals(verificationCodeRepository.existsByFirstField(firstField))) {
            firstField = RandomString.make(10);
            validateCodeExists(firstField);
        }
        return firstField;
    }

    private VerificationCodeDto generateUniqueCode(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException(Constants.USER_NOT_FOUND);

        VerificationCode entityNew = new VerificationCode();

        entityNew.builder()
                .firstField(RandomString.make(5))
                .secondField(RandomString.make(5))
                .thirdField(RandomString.make(5))
                .fourthField(RandomString.make(5))
                .firthField(RandomString.make(5))
                .build();

        VerificationCodeDto teste = new VerificationCodeDto();
        return teste;
    }

}
