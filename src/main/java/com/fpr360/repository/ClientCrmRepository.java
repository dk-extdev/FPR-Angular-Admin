package com.fpr360.repository;

import com.fpr360.domain.ClientCrm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientCrm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientCrmRepository extends JpaRepository<ClientCrm, Long>, JpaSpecificationExecutor<ClientCrm> {

}
