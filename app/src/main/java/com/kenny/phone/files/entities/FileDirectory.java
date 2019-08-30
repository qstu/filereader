package com.kenny.phone.files.entities;

public class FileDirectory {
	private Integer fileType;
	private String fileName;

	public FileDirectory() {
		super();
	}

	public FileDirectory(Integer fileType, String fileName) {
		this.fileType = fileType;
		this.fileName = fileName;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
