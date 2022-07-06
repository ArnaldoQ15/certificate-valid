package br.com.certificatevalid.service;

import br.com.certificatevalid.exception.NotFoundException;
import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.repository.CompanyRepository;
import br.com.certificatevalid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static br.com.certificatevalid.util.Constants.COMPANY_NOT_FOUND;
import static br.com.certificatevalid.util.Constants.USER_NOT_FOUND;

public class BaseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public User findUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException(USER_NOT_FOUND);
        return user.get();
    }

    public Company findCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty())
            throw new NotFoundException(COMPANY_NOT_FOUND);
        return company.get();
    }

}
