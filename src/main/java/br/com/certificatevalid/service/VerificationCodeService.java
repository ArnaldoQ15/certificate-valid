package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.VerificationCodeDto;
import br.com.certificatevalid.exception.NotFoundException;
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

import java.util.Optional;

import static java.util.Locale.ROOT;

@Service
public class VerificationCodeService extends BaseService {

    @Autowired
    private VerificationCodeRepository repository;


    public VerificationCode generateUniqueCode(String companyCode, String userCode, String courseCode) {
        VerificationCode entityNew = VerificationCode.builder()
                .firstField(companyCode)
                .secondField(userCode)
                .thirdField(courseCode)
                .fourthField(RandomString.make(10).toUpperCase(ROOT))
                .firthField(RandomString.make(10).toUpperCase(ROOT))
                .build();

        entityNew.setFullField(entityNew.getFirstField() + entityNew.getSecondField() +
                entityNew.getThirdField() + entityNew.getFourthField() + entityNew.getFirthField());

        repository.save(entityNew);
        return entityNew;
    }

}