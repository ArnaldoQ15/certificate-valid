package br.com.certificatevalid.repository;

import br.com.certificatevalid.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    @Query(value = "SELECT * FROM user_address ua WHERE ua.user_id = :userId", nativeQuery = true)
    List<UserAddress> findByUserId(Long userId);

}