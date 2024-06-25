package com.researchspace.mendeley.testutils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Utility class to load user-specific properties from resource file
 *
 */
public class TestProperties {
	
	 private static Properties props;
	 private static final Logger logger = LoggerFactory.getLogger(TestProperties.class);

	   static {
	        String userPropertyFile = System.getProperty("user.name") + ".properties";
	        props = new Properties();
	        InputStream is;
			try {
				is = new ClassPathResource("users/"+userPropertyFile).getInputStream();
				if (is == null) {
          logger.error("Couldn't find user.properties file: {}", userPropertyFile);
		        } else {
		            try {
		                props.load(is);
		                is.close();
		            } catch (IOException e) {
		                logger.error("Couldn't load user.properties file: {}. {}", userPropertyFile, e.getMessage());
		            }
		        }
			} catch (IOException e1) {
				logger.error("Problem opening users file: {}. {}", userPropertyFile, e1.getMessage());
			}
	    }
	  /**
	   * Gets application ID for your Mendeley application
	   */
	  public  static String getId() {
		   return props.getProperty("id");
	  }
	  
	  public  static String getSecret() {
		   return props.getProperty("secret");
	  }
	  
	  public static  String getRefreshToken() {
		   return props.getProperty("refreshToken");
	  }
	  
	  public static  String getADocId() {
		   return props.getProperty("docId");
	  }

	  public static File getTestFileToUpload() {
			return new File("src/test/resources/files/sbsi.pdf");
		}
}
