package handlerTags;

import com.main.Publication;
import com.main.TagsType;

public class TitleTagsHandler implements TagsType {
	private String tagsname;
	private String value;
	boolean testok;
	@Override
	public void endSet(Publication p) {
		// TODO Auto-generated method stub
    try {
    	if(!testok) throw new Exception();
		p.setTitle(value);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
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
