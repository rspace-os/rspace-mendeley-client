package com.researchspace.mendeley.api;

import java.io.File;
import java.util.List;

import org.springframework.social.ApiBinding;
import org.springframework.web.client.RestClientException;
/**
 * Top-level access to Mendeley bindings.
 * If we add more bindings we should consider extracting these into separate classes - e.g. ProfileOps, DocumentOps etc
 * <p>
 * All these methods will throw {@link RestClientException}s if the response code is 4xx or 5xx
 * <p>
 *
 */
public interface Mendeley extends ApiBinding {
	
	/**
	 * Gets a list of Catalog entries specified by the given DOI
   */
	List<Catalog> getByDOI(String doi);
	
	/**
	 * Tests a connection
	 * @return <code>true</code> if an authorised connection is functional and webservice is running.
	 */
	boolean test() ;
	
	/**
	 * Gets current subject's profile.
   */
	Profile getMyProfile ();
	
	/**
	 * Gets a list of documents
   */
	List<Document> getDocuments();
	
	/**
	 * Gets a static list of DocumentTypes
   */
	List<DocumentType> getDocumentTypes();

	/**
	 * Gets a document by its ID (its UUID)
   */
	Document getDocumentById(String id);

	/**
	 * Posts a document to the server. <code>type</code> and <code>title</code> fields are mandatory
	 * @return the created document; will be a different object to the <code>document</code> parameter.
	 */
	Document createDocument(Document doc);
	
	/**
	 * Creates a document from a file
	 * @return a Document with information extracted from the file
	 */
	Document createDocumentFromFile(File file);
	
	/**
	 * 
	 * @param docId The document ID that this file will be attached to.
	 * @param file a readable file
   */
	MendeleyFile uploadFile(String docId, File file);
	
	/**
	 * Gets all files for the authorised user.
   */
	List<MendeleyFile> getFiles();


	/**
	 * Gets all files for the authorised user linked to the specified document id.
   */
	List<MendeleyFile> getFilesByDocId(String docId);

	/**
	 * Gets a file by its ID
   */
	List<MendeleyFile> getFileById(String id);
	

}
