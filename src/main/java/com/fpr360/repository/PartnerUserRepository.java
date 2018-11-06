package com.fpr360.repository;

import com.fpr360.domain.PartnerUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PartnerUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerUserRepository extends JpaRepository<PartnerUser, Long>, JpaSpecificationExecutor<PartnerUser> {

    @Query("select partner_user from PartnerUser partner_user where partner_user.user.login = ?#{principal.username}")
    List<PartnerUser> findByUserIsCurrentUser();

}
