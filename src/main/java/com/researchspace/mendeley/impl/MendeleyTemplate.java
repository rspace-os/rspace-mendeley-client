package com.researchspace.mendeley.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import com.researchspace.mendeley.api.Catalog;
import com.researchspace.mendeley.api.Document;
import com.researchspace.mendeley.api.DocumentType;
import com.researchspace.mendeley.api.Mendeley;
import com.researchspace.mendeley.api.MendeleyFile;
import com.researchspace.mendeley.api.Profile;


public class MendeleyTemplate extends AbstractOAuth2ApiBinding implements Mendeley {

	Logger log = LoggerFactory.getLogger(MendeleyTemplate.class);
	
	final String baseURL = "https://api.mendeley.com/";

	public MendeleyTemplate(String accessToken) {
		super(accessToken);
	}

	String doi = "10.1103/PhysRevA.20.1521";
	MediaType profilesType = new MediaType("application", "vnd.mendeley-profiles.1+json");
	MediaType documentType = new MediaType("application", "vnd.mendeley-document.1+json");
	MediaType fileType = new MediaType("application", "vnd.mendeley-file.1+json");
	MediaType documentTypeType = new MediaType("application", "vnd.mendeley-document-type.1+json");

	@Override
	public List<Catalog> getByDOI(String doi) {
		ResponseEntity<List<Catalog>> rc = getRestTemplate().exchange(createUrl("catalog?doi=" + doi), HttpMethod.GET,
				null, new ParameterizedTypeReference<>() {
        });
		return rc.getBody();
	}

	@Override
	public Profile getMyProfile() {
		HttpEntity<String> entity = createRequestEntityForContentType(profilesType);
		ResponseEntity<Profile> rc = getRestTemplate().exchange(createUrl("/profiles/me"), HttpMethod.GET, entity,
				Profile.class);
		return rc.getBody();
	}

	@Override
	public boolean test() {
		return !getByDOI(doi).isEmpty();
	}

	@Override
	public List<Document> getDocuments() {
		HttpEntity<String> entity = createRequestEntityForContentType(documentType);
		ResponseEntity<List<Document>> rc = getRestTemplate().exchange(createUrl("documents"), HttpMethod.GET, entity,
        new ParameterizedTypeReference<>() {
        });
		return rc.getBody();
	}

	@Override
	public Document getDocumentById(String id) {
		HttpEntity<String> entity = createRequestEntityForContentType(documentType);
		ResponseEntity<Document> rc = getRestTemplate().exchange(createUrl("documents/{id}"), HttpMethod.GET, entity,
        new ParameterizedTypeReference<>() {
        }, id);
		return rc.getBody();
	}

	@Override
	public Document createDocument(Document doc) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(documentType));
		headers.setContentType(documentType);
		HttpEntity<Document> entity = new HttpEntity<>(doc, headers);
		

		ResponseEntity<Document> rc = getRestTemplate().postForEntity(createUrl("documents"), entity, Document.class);
		return rc.getBody();
	}

	private HttpEntity<String> createRequestEntityForContentType(MediaType mediatype) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(mediatype));
		HttpEntity<String> entity = new HttpEntity<>("", headers);
		return entity;
	}

	@Override
	public List<DocumentType> getDocumentTypes() {
		HttpEntity<String> entity = createRequestEntityForContentType(documentTypeType);
		ResponseEntity<List<DocumentType>> rc = getRestTemplate().exchange(createUrl("document_types"), HttpMethod.GET,
				entity, new ParameterizedTypeReference<>() {
        });
		return rc.getBody();
	}

	private String createUrl(String path) {
		return baseURL + path;
	}

	@Override
	public MendeleyFile uploadFile(String docId, File file) {
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("multipart", "form-data");
		headers.setContentType(mediaType);
		headers.add("Content-Disposition", "attachment; filename=\""+file.getName() +"\"");
		headers.setAccept(Collections.singletonList(fileType));

		headers.set("Link", "<"+createUrl("documents/")+docId+">; rel=\"document\"");
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", new FileSystemResource(file.getAbsolutePath()));
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parts, headers);
		try {
        ResponseEntity<MendeleyFile> mf = getRestTemplate().postForEntity(createUrl("files"), entity, MendeleyFile.class);
        return mf.getBody();
		}catch (RestClientException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Document createDocumentFromFile(File file) {
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("multipart", "form-data");
		headers.setContentType(mediaType);
		headers.add("Content-Disposition", "attachment; filename=\""+file.getName() +"\"");
		headers.setAccept(Collections.singletonList(documentType));

		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", new FileSystemResource(file.getAbsolutePath()));
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parts, headers);
		try {
        ResponseEntity<Document> mf = getRestTemplate().postForEntity(createUrl("documents"), entity, Document.class);
        return mf.getBody();
		}catch (RestClientException e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<MendeleyFile> getFiles() {
		HttpEntity<String> entity = createRequestEntityForContentType(fileType);
		ResponseEntity<List<MendeleyFile>> rc = getRestTemplate().exchange(createUrl("files"), HttpMethod.GET, entity,
        new ParameterizedTypeReference<>() {
        });
		return rc.getBody();
	}
	@Override
	public List<MendeleyFile> getFilesByDocId(String docId) {
		HttpEntity<String> entity = createRequestEntityForContentType(fileType);
		ResponseEntity<List<MendeleyFile>> rc = getRestTemplate().exchange(createUrl("files?document_id="+docId), HttpMethod.GET, entity,
        new ParameterizedTypeReference<>() {
        });
		return rc.getBody();
	}

	// this doesn't seem to work very well.
	@Override
	public List<MendeleyFile> getFileById(String id) {
		String h = getRestTemplate().execute(createUrl("files/{file_id}"), HttpMethod.GET,
				 new AddHeader(), new StringFromHeadersExtractor(), id);
    return Collections.EMPTY_LIST;
	}
	
	private static class StringFromHeadersExtractor implements ResponseExtractor<String> {
		public String extractData(ClientHttpResponse response) {
			return response.getHeaders().getLocation().toString();
		}
	}
	
	private static class AddHeader implements RequestCallback {

		@Override
		public void doWithRequest(ClientHttpRequest request) {
			request.getHeaders().setAccept(List.of(MediaType.ALL));
			
		}

	}
}
