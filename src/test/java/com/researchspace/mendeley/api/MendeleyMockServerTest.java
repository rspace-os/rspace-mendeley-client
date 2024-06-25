package com.researchspace.mendeley.api;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import com.researchspace.mendeley.impl.MendeleyTemplate;
import com.researchspace.mendeley.testutils.TestProperties;
/**
 * Tests for bindings of returned json objects back to classes, without calling the real server
 *
 */
public class MendeleyMockServerTest {
	
	private String token = "ANY";
	private String doi ="10.1103/PhysRevA.20.1521";

	@Test
	public void testGetByDOI() {
		
		MendeleyTemplate api = new MendeleyTemplate(token);
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(api.getRestTemplate());
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.APPLICATION_JSON);

	    mockServer.expect(requestTo("https://api.mendeley.com/catalog?doi="+doi))
	              .andExpect(method(HttpMethod.GET))
	              .andRespond(withSuccess(jsonResource("CatalogEntry"), MediaType.APPLICATION_JSON));
		List<Catalog> objects = api.getByDOI(doi);
		assertEquals(1, objects.size());
		System.err.println(objects.get(0));
		assertEquals("Wineland", objects.get(0).getAuthors().get(0).getLastName());
	}
	
	@Test
	public void testGetMyProfile() {	
		MendeleyTemplate api = new MendeleyTemplate(token);
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(api.getRestTemplate());
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.APPLICATION_JSON);

	    mockServer.expect(requestTo("https://api.mendeley.com/profiles/me"))
	              .andExpect(method(HttpMethod.GET))
	              .andRespond(withSuccess(jsonResource("Profile"), MediaType.APPLICATION_JSON));
		Profile profile  = api.getMyProfile();
		assertEquals("Harry Potter", profile.getDisplayName());
	}
	
	@Test
	public void testGetFileById() {	
		MendeleyTemplate api = new MendeleyTemplate(token);
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(api.getRestTemplate());
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
	    String id = "any";
	    mockServer.expect(requestTo("https://api.mendeley.com/files"))
	              .andExpect(method(HttpMethod.GET))
	              .andRespond(withSuccess(jsonResource("Files"), MediaType.APPLICATION_JSON));
		MendeleyFile file  = api.getFiles().get(0);
		assertEquals("810522", file.getSize());
	}
	
	@Test
	public void testUploadFile() {	
		MendeleyTemplate api = new MendeleyTemplate(token);
		api.uploadFile("any", new File("src/test/resources/sbsi.pdf"));
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(api.getRestTemplate());
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
	    String id = "any";
	    mockServer.expect(requestTo("https://api.mendeley.com/files"))
	              .andExpect(method(HttpMethod.POST))
	              .andRespond(withSuccess(jsonResource("File"), MediaType.APPLICATION_JSON));
		MendeleyFile file  = api.uploadFile(id,  TestProperties.getTestFileToUpload());
		assertEquals("810522", file.getSize());
	}

	private Resource jsonResource(String filename) {
	    return new ClassPathResource("json/"+filename+".json");
	}
}
