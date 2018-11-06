package com.fpr360.repository;

import com.fpr360.domain.PartnerClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartnerClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerClientRepository extends JpaRepository<PartnerClient, Long>, JpaSpecificationExecutor<PartnerClient> {

}
