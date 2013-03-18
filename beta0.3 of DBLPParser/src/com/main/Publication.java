package com.main;
/*
 * Created on 07.06.2005
 */

import java.util.*;

/**
 * @author ley
 *
 * created first in project xml5_coauthor_graph
 */
public class Publication {
    private static Set<Publication> ps= new HashSet<Publication>(650000);
    private static int maxNumberOfAuthors = 0;
    private String typeOfPaper;//文章
    private String idkey;//文章唯一编号
    

	private String key;
    private String mdate;
    public String getMdate() {
		return mdate;
	}

	public void setMdate(String mdate) {
		this.mdate = mdate;
	}

	private Person[] authors;	// or editors
    private String title;// or booktitle
    private String url;
    private String ee;
    private String crossref;
    private boolean finishedTags;//标注是否完成文章录入
    private PublicationBasicInformation pbi;
    /**
     * 初始化数据方法
     * @return
     */
    
    
    public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public String getTypeOfPaper() {
		return typeOfPaper;
	}

	public void setTypeOfPaper(String typeOfPaper) {
		this.typeOfPaper = typeOfPaper;
	}
    public static Set<Publication> getPs() {
		return ps;
	}

	public static void setPs(Set<Publication> ps) {
		Publication.ps = ps;
	}

	public String getKey() {
		
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEe() {
		return ee;
	}

	public void setEe(String ee) {
		this.ee = ee;
	}

	public String getCrossref() {
		return crossref;
	}

	public void setCrossref(String crossref) {
		this.crossref = crossref;
	}

	public PublicationBasicInformation getPbi() {
		return pbi;
	}

	public void setPbi(PublicationBasicInformation pbi) {
		this.pbi = pbi;
	}

	public Publication(String key, Person[] persons) {
        this.key = key;
        authors = persons;
        ps.add(this);
        if (persons.length > maxNumberOfAuthors)
            maxNumberOfAuthors = persons.length;
    }
    
    public Publication(boolean finishedTags) {
		
    	this.finishedTags=finishedTags;
    	ps.add(this);
	}

	public boolean isFinishedTags() {
		return finishedTags;
	}

	public void setFinishedTags(boolean finishedTags) {
		this.finishedTags = finishedTags;
	}

	public void setAuthors(Person[] authors) {
		this.authors = authors;
	}

	public static int getNumberOfPublications() {
        return ps.size();
    }
    
    public static int getMaxNumberOfAuthors() {
        return maxNumberOfAuthors;
    }
    
    public String getAuthorsString(){
    	String returnStr="";
    	returnStr+=" {authors:";
    		
    	for (Person iterable_element : authors) {
    		returnStr+=iterable_element.getName()+"    ";
    		
		}
    	returnStr+="}#\n";
    	return returnStr;
    }
    public Person[] getAuthors() {
    	//测试打印
    	System.out.print("{authors:");
    	for (Person iterable_element : authors) {
			System.out.print(iterable_element.getName()+"    ");
    		
		}
    	System.out.println("}");
        return authors;
    }
    
    static Iterator<Publication> iterator() {
        return ps.iterator();
    }
}
