package com.gbInc.acortadorUrl.persistence.repository;

import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IurlRepository extends JpaRepository<UrlDao, Long>{
	@Query("SELECT U FROM UrlDao U where U.shortCode=:idf")
	public Optional<UrlDao> getSameIdentifier(String idf);
}
