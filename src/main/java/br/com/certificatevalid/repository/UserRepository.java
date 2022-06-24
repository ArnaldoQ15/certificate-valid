package br.com.certificatevalid.repository;

import br.com.certificatevalid.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByDocumentCpf (String cpf);

    Boolean existsByEmail (String email);

}
