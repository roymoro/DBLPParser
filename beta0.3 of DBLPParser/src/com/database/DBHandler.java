package com.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.main.BasicMethod;
import com.main.Publication;
import com.main.TagsType;
import com.main.TypeOfPaperEnum;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBHandler {
	
public  void writeIntoDb(){
	Connection conn=DB.SQlConnection();
	for(Publication element : Publication.getPs()){
		if(BasicMethod.DefineEffectiveofEle(element))
	if(element.getTypeOfPaper().equals(TypeOfPaperEnum.article.toString())||element.getTypeOfPaper().equals(TypeOfPaperEnum.inproceedings.toString())){
		System.out.println("yes, insert into database");
		String sql=insertIntoDatabase(element);
		try {
			Statement stm=(Statement) conn.createStatement();
			stm.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}else {
		
		System.out.println("this is not an article");
	}
	
}
	try {
		conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
private String insertIntoDatabase(Publication element){
	List <String> ls=new ArrayList<String>();
String idkey=element.getIdkey();
String key=element.getKey();
String mdate=element.getMdate();
String authors=element.getAuthorsString();
String title=element.getTitle();
String url=element.getUrl();
String ee=element.getEe();
ls.add(idkey);
ls.add(key);
ls.add(mdate);
ls.add(authors);
ls.add(title);
ls.add(url);
ls.add(ee);
	
	ls=handlerString(ls);

String sql = "insert into publications values ('"+ls.get(0)+"','"+ls.get(1)+"','"+ls.get(2)+"','"+ls.get(3)+"','"+ls.get(4)+"','"+ls.get(5)+"','"+ls.get(6)+"')";
        
    
	return sql;
		
	}
	
public static void main(String[] args) {
	System.out.println("/'".contains("/"));
	System.out.println("test:");
	List<String> ls=new ArrayList<String>();
	
	ls.add("conf/nips/dAlch\\'{e}-BucGA01");
	
	ls.add("{authors:Song Shen    Gregory M. P. O'Hare    Rem W. Collier    }#");
	ls.add("{authors:Angela Chang    M. Sile O'Modhrain    Robert J. K. Jacob    Eric Gunther    Hiroshi Ishii    }#");
	ls.add("authors:F. d'Alch\'{e}-Buc    Y. Grandvalet    C. Ambroise   ");
	new DBHandler().handlerString(ls);
	System.out.println(ls.get(0));
}
private List<String> handlerString(List<String> ls){
	
	for(String str:ls){
		//str不为空的原因是防止空指针
		//处理特殊字符串\\的问题
		if(str!=null&&str.contains("\\'")){
			System.out.println("====");
			System.out.println(str);
			System.out.println(str.contains("\\\u0027"));
			System.out.println(str.indexOf("\\"));
		StringBuffer sb=new StringBuffer(str);
		sb.delete(str.indexOf("\\"), str.indexOf("\\")+1);
		ls.set(ls.indexOf(str), sb.toString().replace("'", "''"));
			System.out.println(str);
		}
		else if(str!=null&&str.contains("'")){
			
		String	str0=str.replaceAll("'", "''");
		
	ls.set(ls.indexOf(str), str0);
			System.out.println("被替换");
		}
		else {
			
			System.out.println("没有改变");
		}
		 
		
	}
	return ls;
}
}

