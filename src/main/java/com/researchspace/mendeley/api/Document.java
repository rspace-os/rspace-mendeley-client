package com.researchspace.mendeley.api;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Document {

	@Override
	public String toString() {
		return "Document [title=" + title + ", type=" + type + ", id=" + id + ", profileId=" + profileId + ", group_id="
				+ group_id + ", created=" + created + ", lastModified=" + lastModified + ", docAbstract=" + docAbstract
				+ ", source=" + source + "]";
	}
	private String title="";
	private String type="";
	private String id="";
	private String profileId="";
	private String group_id="";
	private String created="";
	private String lastModified="";
	private String docAbstract="";
	private String source="";
	@JsonGetter("abstract")
	public String getDocAbstract() {
		return docAbstract;
	}
	public void setDocAbstract(String docAbstract) {
		this.docAbstract = StringUtils.abbreviate(docAbstract, 10000);
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = StringUtils.abbreviate(source, 255);
	}
	public String getId() {
		return id;
	}
	private void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = StringUtils.abbreviate(title, 255);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@JsonGetter("profile_id")
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	@JsonGetter("group_id")
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getCreated() {
		return created;
	}
	 void setCreated(String created) {
		this.created = created;
	}
	public String getLastModified() {
		return lastModified;
	}
	 void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

}
