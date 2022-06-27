package br.com.certificatevalid.repository;

import br.com.certificatevalid.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByCompanyVerificationCode(String companyVerificationCode);

}
