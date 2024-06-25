package com.researchspace.mendeley.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import com.researchspace.mendeley.api.Mendeley;
import com.researchspace.mendeley.impl.MendeleyTemplate;

public class MendeleyServiceProvider extends AbstractOAuth2ServiceProvider<Mendeley> {


    public MendeleyServiceProvider(String clientId, String clientSecret) {
        super( new OAuth2Template(clientId, clientSecret,       	
	            "https://api.mendeley.com/oauth/authorize",
	            "https://api.mendeley.com/oauth/token"));
    }
	@Override
	public Mendeley getApi(String accessToken) {	
		return new MendeleyTemplate(accessToken);
	}
	
}
