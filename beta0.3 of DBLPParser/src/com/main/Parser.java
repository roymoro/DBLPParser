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
 * sax������
 * @author ericwang
 *
 */
public class Parser {
   
   private final int maxAuthorsPerPaper = 200;//ÿƪ�������������Ŀ
public static double idKey=0; //idKey 
   private class ConfigHandler extends DefaultHandler {

        private Locator locator;//δʹ��
        Person[] persons1=new Person[maxAuthorsPerPaper];// ��������
List <Person> persons=new ArrayList<Person>();
       private String key;
       private String recordTag; //��¼�׽ڵ�����
     private Publication p; //����
     private String tempValue;//����ǩ��ʱֵ
     private int personCount;
     private int countPublications; //����������Ŀ
private TagsType tagstypeTemp; 
        private boolean insidePerson;

        private String title;// title ����
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
        	 * �˴�����inproceeding һ��element
        	 */
           if(atts.getLength()>0&&((k = atts.getValue("key"))!=null)){
        	   boolean finishedTag=false;//���Ϊ����
        	   p=new Publication(finishedTag);
        	   p.setTypeOfPaper(rawName);//�������·���2011/3/2
        	   PublicationBasicInformation pbi=new PublicationBasicInformation();
        	   p.setPbi(pbi);//�������»�����Ϣ
        	   //ȱ�ٴ˴�����Ψһ���
        	   p.setKey(k);
        	   if(atts.getValue("mdate")!=null)
        	   p.setMdate(atts.getValue("mdate"));
        	   recordTag=rawName;
        	   return;
        	 }
           /**
            * �˴����������ǩ����
            */
           if(rawName.equals("author") || rawName.equals("editor")){
        	   tempValue="";//����ۼ�
        	   return;
        	   
           }
           /**
            * ����ͨ�ñ�ǩ����
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
        //�˴�����inproceedingsһ��element����
        	if(recordTag.equals(rawName)){
        		
        		int personsSize=persons.size();
        		Person[] pers=new Person[personsSize];
        		for(int i=0;i<personsSize;i++) {
					pers[i]=persons.get(i);
        		}
        		persons.clear();//���personsȫ�ֱ���
        		p.setAuthors(pers);//�������߼�
        		
        		p.setFinishedTags(true);
        	p=null;//�ͷ�p
        	
        	if(countPublications>30000){
        		
        		writeIntoFiles();
        		Publication.getPs().clear();
        		countPublications=0;
        	}else {
        	countPublications++;}
        		 return;
               }
        	//��������
        	   if (rawName.equals("author") || rawName.equals("editor")) 
        	   {//������������Ѵ����򲻲����µģ���������µģ�  ����ӵ�persons list��
        		   Person p;
        		   if ((p = Person.searchPerson(tempValue)) == null) {
                       p = new Person(tempValue);
                   }
        		   persons.add(p);
        		   return;
         	   }
        	   /*
        	    * ����ͨ����
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
      //�����ñ���Ҫ��
      writeIntoFiles();
      
   }

  public TagsType DefinedTags(String Tagsname){
	  if(Tagsname.equals("title"))
	  return new TitleTagsHandler();
	  else if(Tagsname.equals("url")||Tagsname.equals("ee")||Tagsname.equals("crossref")){
		  
		  return new UrlTagsHandler();
	  }
	  else if(pbiTrue(Tagsname)){
//pbi ���Դ���
		  return new PBITagsHandler();
	  }
	  else return null;
  }
  
  
  public void writeIntoFiles(){
	
	 MD5 md5;   //���ļ��ֲ�
     
	   FileWriter fw,fw2;
		try {
			fw = new FileWriter("c:\\text1.txt",true);
			fw2=new FileWriter("c:\\text2.txt",true);
			PrintWriter pw=new PrintWriter(fw);   
			PrintWriter pw2=new PrintWriter(fw2); 
			//���ļ���д���¼
     for (Publication element : Publication.getPs()) {
    	 
    	 
		//�жϱ�ǩ�Ƿ���ɣ���δʹ�ã�Ĭ��ֵtrue
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
			 * ��ӵ��ļ�
			 */
						
			 md5 = new MD5(element.getKey());   
		        String postString = md5.compute();
		        System.out.println(postString);
		        int numberFile= element.hashCode();
		        if(numberFile%2==0)
		            pw.println(tempStr);   
		        else pw2.println(tempStr);
			//������
		        System.out.println(element.getTitle()+element.getKey()+element.getMdate());
			element.getAuthors();
   	  }}
     
     pw.close(); 
     pw2.close();
     /////////////////////////////////////////
     //�ļ������ʹ���
     FileWriter[] fwFileWriters=new FileWriter[8];
     PrintWriter[] printWriters=new PrintWriter[8];
     //��ʼ���ļ���
     for(int i=0;i<8;i++){
    	 fwFileWriters[i]=new FileWriter("c:\\test\\"+TypeOfPaperEnum.values()[i].name()+".txt",true);
    	 printWriters[i]=new PrintWriter(fwFileWriters[i]);
     }
     /**
      * ���°����ļ�������
      */
     
     for (Publication element : Publication.getPs()) {
 		//�жϱ�ǩ�Ƿ���ɣ���δʹ�ã�Ĭ��ֵtrue
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
     
     
     //�ر��ļ���
     for(int i=0;i<8;i++){
    	 printWriters[i].close();
    	 fwFileWriters[i].close();
     }
     
     
		} catch (IOException e) {
			
			e.printStackTrace();
		}   
		//д�����ݿ�
	//	new DBHandler().writeIntoDb(); 
  }
  
  //�ж��Ƿ�����pbi  
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


