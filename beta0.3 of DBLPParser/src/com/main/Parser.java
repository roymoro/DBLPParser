package com.main;


import handlerTags.PBITagsHandler;
import handlerTags.TitleTagsHandler;
import handlerTags.UrlTagsHandler;

import java.io.*;
import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import com.database.DBHandler;
/**
 * sax解析类
 * @author ericwang
 *
 */
public class Parser {
   
   private final int maxAuthorsPerPaper = 200;//每篇文章作者最大数目
public static double idKey=0; //idKey 
   private class ConfigHandler extends DefaultHandler {

        private Locator locator;//未使用
        Person[] persons1=new Person[maxAuthorsPerPaper];// 文章作者
List <Person> persons=new ArrayList<Person>();
       private String key;
       private String recordTag; //记录首节点名称
     private Publication p; //文章
     private String tempValue;//各标签临时值
     private int personCount;
     private int countPublications; //计数文章数目
private TagsType tagstypeTemp; 
        private boolean insidePerson;

        private String title;// title 特殊
        @Override
		public void setDocumentLocator(Locator locator) {
            this.locator = locator;
            countPublications=0;
        }

        @Override
		public void startElement(String namespaceURI, String localName,
                String rawName, Attributes atts) throws SAXException {
        	String k;
        	/**
        	 * 此处处理inproceeding 一类element
        	 */
           if(atts.getLength()>0&&((k = atts.getValue("key"))!=null)){
        	   boolean finishedTag=false;//标记为创建
        	   p=new Publication(finishedTag);
        	   p.setTypeOfPaper(rawName);//设置文章分类2011/3/2
        	   PublicationBasicInformation pbi=new PublicationBasicInformation();
        	   p.setPbi(pbi);//设置文章基本信息
        	   //缺少此处设置唯一编号
        	   p.setKey(k);
        	   if(atts.getValue("mdate")!=null)
        	   p.setMdate(atts.getValue("mdate"));
        	   recordTag=rawName;
        	   return;
        	 }
           /**
            * 此处处理特殊标签作者
            */
           if(rawName.equals("author") || rawName.equals("editor")){
        	   tempValue="";//清除痕迹
        	   return;
        	   
           }
           /**
            * 处理通用标签属性
            */
           if(rawName.equals("sup")||rawName.equals("i")||rawName.equals("sub")||rawName.equals("ref")||rawName.equals("tt")){
        	   
        	   return;
           }
           tempValue="";
           tagstypeTemp=DefinedTags(rawName);
           if(tagstypeTemp!=null)
           tagstypeTemp.startElement(rawName);
          
           
      
        }

        @Override
		public void endElement(String namespaceURI, String localName,
                String rawName) throws SAXException {
        //此处处理inproceedings一类element结束
        	if(recordTag.equals(rawName)){
        		
        		int personsSize=persons.size();
        		Person[] pers=new Person[personsSize];
        		for(int i=0;i<personsSize;i++) {
					pers[i]=persons.get(i);
        		}
        		persons.clear();//清除persons全局变量
        		p.setAuthors(pers);//设置作者集
        		
        		p.setFinishedTags(true);
        	p=null;//释放p
        	
        	if(countPublications>30000){
        		
        		writeIntoFiles();
        		Publication.getPs().clear();
        		countPublications=0;
        	}else {
        	countPublications++;}
        		 return;
               }
        	//处理作者
        	   if (rawName.equals("author") || rawName.equals("editor")) 
        	   {//产生作者如果已存在则不产生新的，否则产生新的；  并添加到persons list中
        		   Person p;
        		   if ((p = Person.searchPerson(tempValue)) == null) {
                       p = new Person(tempValue);
                   }
        		   persons.add(p);
        		   return;
         	   }
        	   /*
        	    * 处理通用类
        	    */
        	   
        	   if(rawName.equals("sup")||rawName.equals("i")||rawName.equals("sub")||rawName.equals("ref")||rawName.equals("tt")){
        		   
        		   return;
        	   }
        	   if(tagstypeTemp!=null){
        		   
        		   tagstypeTemp.endSet(p);
        		   tagstypeTemp=null;
        		   
        	   }
            
        }

        @Override
		public void characters(char[] ch, int start, int length)
                throws SAXException {
           if(tagstypeTemp!=null){
        	   tempValue+=new String(ch,start,length);
        	   tagstypeTemp.characters(tempValue);
        	   return;
           }else {
            tempValue+=new String(ch,start,length);
           }
        }

        private void Message(String mode, SAXParseException exception) {
            System.out.println(mode + " Line: " + exception.getLineNumber()
                    + " URI: " + exception.getSystemId() + "\n" + " Message: "
                    + exception.getMessage());
        }

        @Override
		public void warning(SAXParseException exception) throws SAXException {

            Message("**Parsing Warning**\n", exception);
            throw new SAXException("Warning encountered");
        }

        @Override
		public void error(SAXParseException exception) throws SAXException {

            Message("**Parsing Error**\n", exception);
            throw new SAXException("Error encountered");
        }

        @Override
		public void fatalError(SAXParseException exception) throws SAXException {

            Message("**Parsing Fatal Error**\n", exception);
            throw new SAXException("Fatal Error encountered");
        }
    }

   private void nameLengthStatistics() {
    
   }
   
   private void publicationCountStatistics() {
      
   }
   
  public  Parser(String uri) {
      try {
	  //   SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	    // SAXParser parser = parserFactory.newSAXParser();
	     XMLReader parser=XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
    	  System.out.println(parser.getClass());
	     ConfigHandler handler = new ConfigHandler();
	     parser.setContentHandler(handler);
	     parser.setErrorHandler(handler);
	     parser.parse(new InputSource(new FileReader(uri)));
//         parser.getXMLReader().setFeature(
//	          "http://xml.org/sax/features/validation", true);
//         parser.parse(new File(uri), handler);
      } catch (IOException e) {
         System.out.println("Error reading URI: " + e.getMessage());
         e.printStackTrace();
      } catch (SAXException e) {
         System.out.println("Error in parsing: " + e.getMessage());
         e.printStackTrace();
//      } catch (ParserConfigurationException e) {
//         System.out.println("Error in XML parser configuration: " +
//			    e.getMessage());
   }
      
      System.out.println("The number of publication:"+Publication.getNumberOfPublications());
      //最后调用必须要有
      writeIntoFiles();
      
   }

  public TagsType DefinedTags(String Tagsname){
	  if(Tagsname.equals("title"))
	  return new TitleTagsHandler();
	  else if(Tagsname.equals("url")||Tagsname.equals("ee")||Tagsname.equals("crossref")){
		  
		  return new UrlTagsHandler();
	  }
	  else if(pbiTrue(Tagsname)){
//pbi 属性处理
		  return new PBITagsHandler();
	  }
	  else return null;
  }
  
  
  public void writeIntoFiles(){
	
	 MD5 md5;   //算文件分布
     
	   FileWriter fw,fw2;
		try {
			fw = new FileWriter("c:\\text1.txt",true);
			fw2=new FileWriter("c:\\text2.txt",true);
			PrintWriter pw=new PrintWriter(fw);   
			PrintWriter pw2=new PrintWriter(fw2); 
			//向文件中写入记录
     for (Publication element : Publication.getPs()) {
    	 
    	 
		//判断标签是否完成，暂未使用，默认值true
    	 if(element.isFinishedTags())
   	  {    
    		 String tempStr=BasicMethod.basicItemsOutput(element);
			/*String tempStr="#"+"title:"+element.getTitle()+"  key:"+element.getKey()+"  mdate:"+element.getMdate();
			
			if(element.getEe()!=null)
			tempStr+="  ee:"+element.getEe();
			if(element.getUrl()!=null)
				tempStr+="  url:"+element.getUrl();
			
			tempStr+=element.getAuthorsString();**/
			/**
			 * 添加到文件
			 */
						
			 md5 = new MD5(element.getKey());   
		        String postString = md5.compute();
		        System.out.println(postString);
		        int numberFile= element.hashCode();
		        if(numberFile%2==0)
		            pw.println(tempStr);   
		        else pw2.println(tempStr);
			//测试用
		        System.out.println(element.getTitle()+element.getKey()+element.getMdate());
			element.getAuthors();
   	  }}
     
     pw.close(); 
     pw2.close();
     /////////////////////////////////////////
     //文件按类型处理
     FileWriter[] fwFileWriters=new FileWriter[8];
     PrintWriter[] printWriters=new PrintWriter[8];
     //初始化文件流
     for(int i=0;i<8;i++){
    	 fwFileWriters[i]=new FileWriter("c:\\test\\"+TypeOfPaperEnum.values()[i].name()+".txt",true);
    	 printWriters[i]=new PrintWriter(fwFileWriters[i]);
     }
     /**
      * 文章按照文件流处理
      */
     
     for (Publication element : Publication.getPs()) {
 		//判断标签是否完成，暂未使用，默认值true
     	 if(element.isFinishedTags()&&BasicMethod.DefineEffectiveofEle(element))
    	  {    idKey++;
     		 element.setIdkey(BasicMethod.MakeIdKey(idKey, element));
     		 String tempStr=BasicMethod.basicItemOutput2(element);
     		 switch (TypeOfPaperEnum.valueOf(element.getTypeOfPaper())) {
			case article:
				printWriters[0].println(tempStr);
				break;
			case book:
				printWriters[1].println(tempStr);
				break;
			case incollection:
				printWriters[2].println(tempStr);
				break;
			case inproceedings:
				printWriters[3].println(tempStr);
				break;
			case mastersthesis:
				printWriters[4].println(tempStr);
				break;
			case phdthesis:
				printWriters[5].println(tempStr);
				break;
			case proceedings:
				printWriters[6].println(tempStr);
				break;
			case www:
				printWriters[7].println(tempStr);
				break;
			default:
				try {
					
					throw new Exception("switch fasle");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
     	}
     	 
     }
     
     
     //关闭文件流
     for(int i=0;i<8;i++){
    	 printWriters[i].close();
    	 fwFileWriters[i].close();
     }
     
     
		} catch (IOException e) {
			
			e.printStackTrace();
		}   
		//写入数据库
	//	new DBHandler().writeIntoDb(); 
  }
  
  //判断是否属于pbi  
  boolean pbiTrue(String tagsname){
	  boolean b_result=false;
	  if(tagsname.equals("pages"))
  	{b_result=true;}//1
		else if(tagsname.equals("year")){
			b_result=true;;//2
		}else if(tagsname.equals("address")){
			b_result=true;//3
		}else if(tagsname.equals("journal")){
			
			b_result=true;//4
		}
		else if(tagsname.equals("volume")){
			b_result=true;//5
		}
		else if(tagsname.equals("number")){
			b_result=true;//6
		}else if(tagsname.equals("month")){
			b_result=true;//7
		}else if(tagsname.equals("cdrom")){
			b_result=true;//8
		}
		else if(tagsname.equals("cite")){
			b_result=true;//9
		}else if(tagsname.equals("publisher")){
			b_result=true;//10
		}else if(tagsname.equals("note")){
			b_result=true;//11
		}
		else if(tagsname.equals("isbn")){
			b_result=true;//12
		}else if(tagsname.equals("series")){
			b_result=true;//13
		}else if(tagsname.equals("school")){
			b_result=true;//14
		}else if(tagsname.equals("chapter")){
			b_result=true;//15
		}
	  return b_result; 
  }

  public static void main(String[] args) {
      if (args.length < 1) {
         System.out.println("Usage: java Parser [input]");
         System.exit(0);
      }
      System.out.println("begin");
      Parser p = new Parser(args[0]);
      
   }
   
}


