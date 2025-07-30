package com.gbInc.acortadorUrl.sevices;

import com.gbInc.acortadorUrl.DTO.UrlDataDTO;
import com.gbInc.acortadorUrl.DTO.UrlIncoming;
import java.util.List;

public interface IurlService {

	public UrlDataDTO saveUrl(UrlIncoming url);

	public UrlDataDTO retrieveUrl(String urlShort);

	public UrlDataDTO updateUrl(String urlShort,UrlIncoming newUrl);

	public void deleteUrl(String urlShort);

	public UrlDataDTO urlStats(String urlShort);

	public List<UrlDataDTO> getAllUrlsByOwner(UrlIncoming urlIncoming);
}
