package br.com.certificatevalid.service;

import br.com.certificatevalid.exception.NotFoundException;
import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.model.Course;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.repository.CompanyRepository;
import br.com.certificatevalid.repository.CourseRepository;
import br.com.certificatevalid.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static br.com.certificatevalid.util.Constants.*;
import static java.util.Locale.ROOT;

public class BaseService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CourseRepository courseRepository;


    /**Método para encontrar um user no banco de dados a partir do ID.*/
    public User findUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException(USER_NOT_FOUND);
        return user.get();
    }

    /**Método para gerar um código único de validação com 10 dígitos para USER.*/
    public String generateUserVerificationCode() {
        String code = RandomString.make(10).toUpperCase(ROOT);
        if (Boolean.TRUE.equals(userRepository.existsByUserVerificationCode(code)))
            generateUserVerificationCode();
        return code;
    }

    /**Método para encontrar uma company no banco de dados a partir do ID.*/
    public Company findCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty())
            throw new NotFoundException(COMPANY_NOT_FOUND);
        return company.get();
    }

    /**Método para gerar um código único de validação com 10 dígitos para COMPANY.*/
    public String generateCompanyVerificationCode() {
        String code = RandomString.make(10).toUpperCase(ROOT);
        if (Boolean.TRUE.equals(companyRepository.existsByCompanyVerificationCode(code)))
            generateCompanyVerificationCode();
        return code;
    }

    /**Método para encontrar um course no banco de dados a partir do ID.*/
    public Course findCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty())
            throw new NotFoundException(COURSE_NOT_FOUND);
        return course.get();
    }

    /**Método para gerar um código único de validação com 10 dígitos para COURSE.*/
    public String generateCourseVerificationCode() {
        String code = RandomString.make(10).toUpperCase(ROOT);
        if (Boolean.TRUE.equals(courseRepository.existsByCourseVerificationCode(code)))
            generateCourseVerificationCode();
        return code;
    }

}
