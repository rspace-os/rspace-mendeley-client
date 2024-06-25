package com.researchspace.mendeley.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.researchspace.mendeley.api.Mendeley;

public class MendeleyConnectionFactory extends OAuth2ConnectionFactory<Mendeley> {

	public MendeleyConnectionFactory(String clientId, String clientSecret) {
		super("mendeley", new MendeleyServiceProvider(clientId, clientSecret),
				new MendeleyAPIAdapter());
	}

}
