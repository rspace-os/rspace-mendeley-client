package com.researchspace.mendeley.connect;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.social.connect.UserProfile;

import com.researchspace.mendeley.api.Mendeley;
import com.researchspace.mendeley.api.Profile;

public class MendeleyAdaptorTest {
	
	@Rule public MockitoRule mockito = MockitoJUnit.rule();
	@Mock Mendeley mendeley;
	MendeleyAPIAdapter adaptor = new MendeleyAPIAdapter();
	
	@Test
	public void testFetchProfile () {
		final Profile testProfile = createAnyProfile();
		when (mendeley.getMyProfile()).thenReturn(testProfile);
		UserProfile up = adaptor.fetchUserProfile(mendeley);
		assertEquals("id", up.getUsername());
		assertEquals("last", up.getLastName());
		assertEquals("first", up.getFirstName());
		assertEquals("email", up.getEmail());
	}

	@Test
	public void testTestConnection () {	
		when (mendeley.test()).thenReturn(true);
		assertTrue( adaptor.test(mendeley));
		
		when (mendeley.test()).thenReturn(false);
		assertFalse( adaptor.test(mendeley));
	}
	
	private Profile createAnyProfile() {
		return new Profile("id","first", "last","email");
	}
}
