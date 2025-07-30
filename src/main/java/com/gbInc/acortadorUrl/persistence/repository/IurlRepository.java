package com.gbInc.acortadorUrl.persistence.repository;

import com.gbInc.acortadorUrl.persistence.models.UrlDao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IurlRepository extends JpaRepository<UrlDao, Long>{
	@Query("SELECT U FROM UrlDao U where U.shortCode=:idf")
	public Optional<UrlDao> getSameIdentifier(String idf);
	
	@Query(value = "SELECT * FROM url_dao where url_dao.created_by_id=:idf LIMIT 1",nativeQuery = true)
	public Optional<UrlDao> getFirstUrlByOwner(String idf);
	
	@Query("SELECT U FROM UrlDao U where U.createdById=:idf")
	public Optional<List<UrlDao>>getAllUrlsByOwnerId(String idf);
	
}
