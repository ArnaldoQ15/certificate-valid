package br.com.certificatevalid.repository;

import br.com.certificatevalid.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE :username%")
    Page<User> findByUsername(String username, Pageable pageRequest);

    Boolean existsByDocumentCpf (String cpf);

    Boolean existsByEmail (String email);

    Boolean existsByUserVerificationCode(String userVerificationCode);

}