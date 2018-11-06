package com.fpr360.repository;

import com.fpr360.domain.MerchantAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MerchantAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantAccountRepository extends JpaRepository<MerchantAccount, Long>, JpaSpecificationExecutor<MerchantAccount> {

}
