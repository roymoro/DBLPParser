package com.main;

import java.text.DecimalFormat;

import javax.lang.model.element.Element;

public class BasicMethod {
public static String basicItemsOutput(Publication element)
{
String tempStr="#"+"ID:"+element.getIdkey()+"	from:"+element.getTypeOfPaper()+":"+"	title:"+element.getTitle()+"  key:"+element.getKey()+"  mdate:"+element.getMdate();

if(element.getEe()!=null)
tempStr+="  ee:"+element.getEe();
if(element.getUrl()!=null)
	tempStr+="  url:"+element.getUrl();
if(element.getCrossref()!=null)
	tempStr+="  crossref:"+element.getCrossref();
if(element.getPbi().toString()!=""){
	tempStr+=element.getPbi().toString();
}
tempStr+=element.getAuthorsString();

return tempStr;
}

/**
 * 输出文章
 * @param element
 */
public static String basicItemOutput2(Publication element){
	String tempStr="****begin\n"+"ID:"+element.getIdkey()+"\nfrom:"+element.getTypeOfPaper()+":"+"\ntitle:"+element.getTitle()+"\nkey:"+element.getKey()+"\nmdate:"+element.getMdate();

	if(element.getEe()!=null)
	tempStr+="\nee:"+element.getEe();
	if(element.getUrl()!=null)
		tempStr+="\nurl:"+element.getUrl();
	if(element.getCrossref()!=null)
		tempStr+="\ncrossref:"+element.getCrossref();
	if(element.getPbi().toString()!=""){
		tempStr+="\n"+element.getPbi().toString();
	}
	tempStr+="\n"+element.getAuthorsString();
    tempStr+="\n****end";
	return tempStr;
}
/**
 * 判断是否为有效元素
 * @param element
 * @return
 */
public static boolean DefineEffectiveofEle(Publication element){
	//暂时只处理  没有作者的情况 
return element.getAuthors().length!=0?true:false;	
}
/**
 *  生成 idKey
 */
public static String MakeIdKey(double x,Publication element){
	String pattern="00000000";
	DecimalFormat df = new DecimalFormat(pattern); 

	return element.getTypeOfPaper().substring(0,2)+df.format(x);
}

//测试
public static void main(String[] args) {
	Publication p=new Publication(true);
	p.setTypeOfPaper("Test");
	System.out.println(MakeIdKey(12,p));
}
}
