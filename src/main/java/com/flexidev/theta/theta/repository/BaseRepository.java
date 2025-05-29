package com.flexidev.theta.theta.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
/*`@NoRepositoryBean` is an annotation that marks this interface as a non-repository bean. 
It indicates that this interface should not be used directly to create a Spring Data repository,
 but rather it should serve as a base for other repository interfaces */
public interface BaseRepository extends CrudRepository<T, ID> {
    @Transactional
    @Query("SELECT next value from theta_sequence")
    Long sequence();
    
}
