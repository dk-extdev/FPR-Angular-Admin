package com.fpr360.repository;

import com.fpr360.domain.CrmVendor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CrmVendor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrmVendorRepository extends JpaRepository<CrmVendor, Long>, JpaSpecificationExecutor<CrmVendor> {

}
