package com.gbInc.acortadorUrl.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlDataDTO {

	private Long id;

	private String url;

	private String shortCode;

	private String createdAt;

	private String updatedAt;

	private Integer accesCount;
	
	private String ownerId;

}
