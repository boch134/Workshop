package com.sip.workshop.repository;
import com.sip.workshop.entities.*;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface providerRepository extends CrudRepository<Provider,Integer> {
	@Query("FROM Article a WHERE a.provider.id = ?1")
	List<Article> findArticlesByProvider(int id);

}
