package handlerTags;
import com.main.Publication;
import com.main.TagsType;
/**
 * ¥¶¿Ì url ∫Õ ee
 * @author eric wang
 *
 */
public class UrlTagsHandler implements TagsType {
	private String tagsname;
	private String value;
	boolean testok;
	@Override
	public void endSet(Publication p) {
		// TODO Auto-generated method stub
		  try {
		    	if(!testok) throw new Exception();
				if(tagsname.equals("url"))
		    	p.setUrl(value);
				else if(tagsname.equals("ee")){
					
					p.setEe(value);
				}else if(tagsname.equals("crossref")){
					p.setCrossref(value);
				}
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
