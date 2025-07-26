package com.gbInc.acortadorUrl.sevices;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;

public interface IurlService {

	public UrlDataDTO saveUrl(UrlIncoming url);

	public void retrieveUrl(String urlShort);

	public void updateUrl(String urlShort);

	public void deleteUrl(String urlShort);

	public void urlStats(String urlShort);

}
