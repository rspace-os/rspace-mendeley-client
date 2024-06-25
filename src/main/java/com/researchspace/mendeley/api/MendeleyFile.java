package com.researchspace.mendeley.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Encapsulation of 'File' object renamed to avoid confusion with java.util.File etc
 *
 */
public class MendeleyFile {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MendeleyFile other = (MendeleyFile) obj;
		if (id == null) {
      return other.id == null;
		} else
      return id.equals(other.id);
  }
	private String id = "";
	private String documentId = "";
	private String mimeType = "";
	private String fileName = "";
	private String size = "";
	private String filehash = "";
	
	
	private String downloadLink;
	@JsonIgnore // this is set in from Header location
	public String getDownloadLink() {
		return downloadLink;
	}
	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonGetter("document_id")
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	@JsonGetter("mime_type")
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	@JsonGetter("file_name")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getFilehash() {
		return filehash;
	}
	public void setFilehash(String filehash) {
		this.filehash = filehash;
	}
	@Override
	public String toString() {
		return "MendeleyFile [id=" + id + ", documentId=" + documentId + ", mimeType=" + mimeType + ", fileName="
				+ fileName + ", size=" + size + ", filehash=" + filehash + "]";
	}
	
	 

}
