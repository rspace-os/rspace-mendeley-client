package com.researchspace.mendeley.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.researchspace.mendeley.api.Mendeley;
import com.researchspace.mendeley.api.Profile;
/**
 * Adapts Mendeley API to Spring Social Connection API
 *
 */
public class MendeleyAPIAdapter implements ApiAdapter<Mendeley> {

	Logger log = LoggerFactory.getLogger(MendeleyAPIAdapter.class);
	
	@Override
	public boolean test(Mendeley api) {
		return api.test();
	}

	@Override
	public void setConnectionValues(Mendeley api, ConnectionValues values) {
		log.info("calling set connection values");
		Profile profile = api.getMyProfile();
		values.setDisplayName(profile.getDisplayName());
		values.setProfileUrl(profile.getLink());
		values.setProviderUserId(profile.getId());
	}

	@Override
	public UserProfile fetchUserProfile(Mendeley api) {
		Profile profile = api.getMyProfile();
		UserProfile rc = new UserProfileBuilder()
				  .setEmail(profile.getEmail())
				  .setFirstName(profile.getFirstName())
				  .setLastName(profile.getLastName())
				  .setUsername(profile.getId())
				  .build();
		return rc;
	}

	@Override
	public void updateStatus(Mendeley api, String message) {
		log.info("updating Mendeley status with message {}", message);
	}

}
