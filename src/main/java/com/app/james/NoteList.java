package com.app.james;

 

public class NoteList {
	 
	private Integer id;
    private String subject;
    private String detail;
    private String status;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    
	@Override
	public String toString() {
		return "NoteList [id=" + id + ", subject=" + subject + ", detail="
				+ detail + ", status=" + status + "]";
	}

}
