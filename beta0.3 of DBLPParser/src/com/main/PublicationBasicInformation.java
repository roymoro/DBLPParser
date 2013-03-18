package com.main;

public class PublicationBasicInformation {
private String pages;
private String year;
private String address;
private String journal;
private String volume;
private String number;
private String month;
private String cdrom;
private String cite;
private String publisher;//notice!
private String note;
private String isbn;
private String series;
private String school;
private String chapter;

public String getPages() {
	return pages;
}

public void setPages(String pages) {
	this.pages = pages;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getJournal() {
	return journal;
}
public void setJournal(String journal) {
	this.journal = journal;
}
public String getVolume() {
	return volume;
}
public void setVolume(String volume) {
	this.volume = volume;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public String getMonth() {
	return month;
}
public void setMonth(String month) {
	this.month = month;
}
public String getCdrom() {
	return cdrom;
}
public void setCdrom(String cdrom) {
	this.cdrom = cdrom;
}
public String getCite() {
	return cite;
}
public void setCite(String cite) {
	if(this.cite!=null)
	this.cite += "  cite:"+cite;
	else {this.cite="  cite:"+cite;};
}
public String getPublisher() {
	return publisher;
}
public void setPublisher(String publisher) {
	this.publisher = publisher;
}
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}
public String getIsbn() {
	return isbn;
}
public void setIsbn(String isbn) {
	this.isbn = isbn;
}
public String getSeries() {
	return series;
}
public void setSeries(String series) {
	this.series = series;
}
public String getSchool() {
	return school;
}
public void setSchool(String school) {
	this.school = school;
}
public String getChapter() {
	return chapter;
}
public void setChapter(String chapter) {
	this.chapter = chapter;
}

@Override
public String toString() {
	// TODO Auto-generated method stub
	String basciInformation="  pbi:{";
	
	if(pages!=null) basciInformation+="  pages:"+pages;
	if(year!=null) basciInformation+="  year:"+year;
	
	if(address!=null) basciInformation+="  address:"+address;
	if(journal!=null) basciInformation+="  journal:"+journal;
		
	
	if(volume!=null) basciInformation+="  volume:"+volume;
	if(number!=null) basciInformation+="  number:"+number;
	if(month!=null) basciInformation+="  month:"+month;
	if(cdrom!=null) basciInformation+="  cdrom:"+cdrom;

	if(cite!=null) basciInformation+=cite;//special
	if(publisher!=null) basciInformation+="  publisher:"+publisher;
	if(note!=null) basciInformation+="  note:"+note;
	if(isbn!=null) basciInformation+="  isbn:"+isbn;
	if(series!=null) basciInformation+="  series:"+series;
	if(school!=null) basciInformation+="  school:"+school;
	if(chapter!=null) basciInformation+="  chapter:"+chapter;
	
		basciInformation+="}";
	return basciInformation;
}

}
