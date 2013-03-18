package handlerTags;

import com.main.Publication;
import com.main.TagsType;

public class PBITagsHandler implements TagsType {
	private String tagsname;
	private String value;
	boolean testok;
	@Override
	public void endSet(Publication p) {
		// TODO Auto-generated method stub
		 try {
		    	if(!testok) throw new Exception();
				if(tagsname.equals("pages"))
		    	{p.getPbi().setPages(value);}//1
				else if(tagsname.equals("year")){
					p.getPbi().setYear(value);//2
				}else if(tagsname.equals("address")){
					p.getPbi().setAddress(value);//3
				}else if(tagsname.equals("journal")){
					
					p.getPbi().setJournal(value);//4
				}
				else if(tagsname.equals("volume")){
					p.getPbi().setVolume(value);//5
				}
				else if(tagsname.equals("number")){
				p.getPbi().setNumber(value);//6
				}else if(tagsname.equals("month")){
					p.getPbi().setMonth(value);//7
				}else if(tagsname.equals("cdrom")){
					p.getPbi().setCdrom(value);//8
				}
				else if(tagsname.equals("cite")){
					p.getPbi().setCite(value);//9
				}else if(tagsname.equals("publisher")){
					p.getPbi().setPublisher(value);//10
				}else if(tagsname.equals("note")){
					p.getPbi().setNote(value);//11
				}
				else if(tagsname.equals("isbn")){
					p.getPbi().setIsbn(value);//12
				}else if(tagsname.equals("series")){
					p.getPbi().setSeries(value);//13
				}else if(tagsname.equals("school")){
					p.getPbi().setSchool(value);//14
				}else if(tagsname.equals("chapter")){
					p.getPbi().setChapter(value);//15
				}
		 } catch (Exception e) {
					// TODO: handle exception
					System.out.println("pbi error:"+tagsname);
				}
	}

	@Override
	public void startElement(String tagsname) {
		// TODO Auto-generated method stub
		this.tagsname=tagsname;
		this.testok=false;
	}

	@Override
	public void characters(String value) {
		// TODO Auto-generated method stub
		this.value=value;
	      this.testok=true;
	}

}
