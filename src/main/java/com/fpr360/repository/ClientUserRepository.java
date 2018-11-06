package com.fpr360.repository;

import com.fpr360.domain.ClientUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ClientUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientUserRepository extends JpaRepository<ClientUser, Long>, JpaSpecificationExecutor<ClientUser> {

    @Query("select client_user from ClientUser client_user where client_user.user.login = ?#{principal.username}")
    List<ClientUser> findByUserIsCurrentUser();

}
