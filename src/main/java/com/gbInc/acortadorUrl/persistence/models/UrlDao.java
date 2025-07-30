package com.gbInc.acortadorUrl.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Accessors(chain = true)
public class UrlDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String url;

	private String shortCode;

	private String createdAt;

	private String updatedAt;

	private Integer accessCount;
	
	private String createdById;

	@Override
	public String toString() {
		return "id:" + id + "\nurl:" + url + "\nshortCode:" + shortCode + "\ncreatedAt:" + createdAt + "\nupdatedAt:"
				+ updatedAt + "\naccesCount:" + accessCount + "\nCreatedBy:"+createdById;
	}
	
	public UrlDao incrementAccessCount(){
		this.accessCount +=1;
		return this;
	}

}