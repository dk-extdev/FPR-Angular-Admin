package com.fpr360.repository;

import com.fpr360.domain.GuruClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the GuruClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuruClientRepository extends JpaRepository<GuruClient, Long>, JpaSpecificationExecutor<GuruClient> {

    @Query("select guru_client from GuruClient guru_client where guru_client.guru.login = ?#{principal.username}")
    List<GuruClient> findByGuruIsCurrentUser();

}
