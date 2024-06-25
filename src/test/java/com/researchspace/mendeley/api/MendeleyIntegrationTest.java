package com.researchspace.mendeley.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.web.client.RestClientException;

import com.researchspace.mendeley.connect.MendeleyConnectionFactory;
import com.researchspace.mendeley.impl.MendeleyTemplate;
import com.researchspace.mendeley.testutils.TestProperties;
/**
 * Requires access token to run that needs to be acquired manually from 
 * https://mendeley-show-me-access-tokens.herokuapp.com/
 *
 */
@Ignore // need to implement passing tokens as part of build
public class MendeleyIntegrationTest {
	
	String doi = "10.1103/PhysRevA.20.1521";
	private static String mendeleySecret = TestProperties.getSecret();
	private static String id = TestProperties.getId();
	private static String refreshToken = TestProperties.getRefreshToken();
	private static String accessToken = null;
	
	@BeforeClass
	public static void beforeClass() {
		if (accessToken == null) {
			getNewAccessToken();
			return;
		}
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		try {
			api.getDocumentTypes();
		} catch (RestClientException e) {
			getNewAccessToken();
		}
	}
	
	@Test
	public void testUploadFile() {	
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		MendeleyFile uploaded = api.uploadFile(TestProperties.getADocId(), TestProperties.getTestFileToUpload());
		System.err.println("uploaded is " +uploaded);
		assertEquals(TestProperties.getADocId(), uploaded.getDocumentId());
		
	}
	
	@Test
	public void testCreateDocumentFromFile() {	
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		Document doc  = api.createDocumentFromFile( TestProperties.getTestFileToUpload());
		assertEquals("SBSI", doc.getTitle().substring(0, 4));
		assertEquals("journal", doc.getType());
	}

	private static void getNewAccessToken() {
		MendeleyConnectionFactory connectionFactory =
		    new MendeleyConnectionFactory(id, mendeleySecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		AccessGrant grant  = oauthOperations.refreshAccess(refreshToken, null);
		accessToken = grant.getAccessToken();
		System.err.println("New token is " + grant.getAccessToken());
	}


	@Test
	public void testGetMyRealProfile() {
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		Profile myProfile = api.getMyProfile();
		System.err.println(myProfile.toString());		
	}
	@Test
	public void testGetMyRealDocByDOI() {
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		api.getByDOI(doi);
	}
	
	@Test
	public void testGetMyRealDocs() {
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		List<Document> docs = api.getDocuments();
		System.err.println(docs.get(0).toString());
	}
	
	@Test
	public void testGetMyDocumentTypes() {
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		List<DocumentType> docs = api.getDocumentTypes();
		System.err.println(StringUtils.join(docs, ","));
	}
	
	@Test
	public void testGetMyDocumentById() {
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		Document doc  = api.getDocumentById(TestProperties.getADocId());
		assertEquals(TestProperties.getADocId(), doc.getId());
		System.err.println(doc);
	}
	
	@Test
	public void testGetFiles() {
	
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		MendeleyFile file  = api.getFiles().get(0);
		System.err.println(file);
	}
	
	@Test
	public void testGetFilesByDocId() {
		// this is a document with attached files
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		MendeleyFile file  = api.getFilesByDocId(TestProperties.getADocId()).get(0);
		System.err.println("retrieved file is " + file);
	
	}
	
	
	@Test
	public void testCreateDoc() {
		Document doc = new Document();
		doc.setTitle("From test-" + new Date());
		doc.setType("report");
		MendeleyTemplate api = new MendeleyTemplate(accessToken);
		 doc  = api.createDocument(doc);
		 assertNotNull(doc.getId());
		System.err.println(doc);
	}
	
}
