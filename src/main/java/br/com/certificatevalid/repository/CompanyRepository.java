package br.com.certificatevalid.repository;

import br.com.certificatevalid.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByContactEmail(String contactEmail);

    @Query("SELECT c FROM Company c WHERE LOWER(c.companyName) LIKE :companyName%")
    Page<Company> findByCompanyName(String companyName, Pageable pageRequest);

    Boolean existsByCompanyPassword(String companyPassword);

    Boolean existsByCompanyVerificationCode(String companyVerificationCode);

}
