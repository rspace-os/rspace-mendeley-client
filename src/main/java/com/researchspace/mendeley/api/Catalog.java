package com.researchspace.mendeley.api;

import java.util.ArrayList;
import java.util.List;


public class Catalog {
	private String title;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Catalog(String title, String id) {
		super();
		this.title = title;
		this.id = id;
	}

	public Catalog() {
		super();
	}

	@Override
	public String toString() {
		return "MendeleyCatalog [title=" + title + ", type=" + type + ", id=" + id + ", authors=" + authors + "]";
	}
	
	private List<Author> authors = new ArrayList<>();

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	

}
