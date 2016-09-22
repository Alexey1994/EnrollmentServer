import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;


public class RelationsSaver extends ServerResource
{
	void swap(String s1, String s2){
		String t=s1;
		s1=s2;
		s2=t;
	}
	
	
	@Post("json")
    public String toString(Representation exams)
    {
		JSONParser parser = new JSONParser();
		JSONArray  data   = null;
        JSONArray  array  = null;
        
        try{
            array=(JSONArray)parser.parse(exams.getText());;
        }
        catch(ParseException e){
            System.out.println(e);
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        if(array==null)
            return "{\"status\":\"Error parsing JSON\"}";
        
        
        DB         db            = new DB();
        String     first_prefix  = null;
        String     second_prefix = null;
        String     third_prefix  = null;
        ResultSet  db_set;
        
        db.update("DELETE FROM "+DB.database+".Links");
        
        for(int i=0; i<array.size(); i++)
        {	
        	data   = (JSONArray)array.get(i);
        	
        	first_prefix  = (String)data.get(0);
        	second_prefix = (String)data.get(1);
        	third_prefix  = (String)data.get(2);
        	
        	if(first_prefix==null || second_prefix==null || third_prefix==null)
        		return "{\"status\":\"Expected not null data\"}";
        	
        	if(first_prefix.compareTo(second_prefix)==0 || first_prefix.compareTo(third_prefix)==0)
        		return "{\"status\":\"data is repeated\"}";
        	
        	if(second_prefix.compareTo(third_prefix)==0)
        		return "{\"status\":\"data is repeated\"}";
        	
        	first_prefix  = DB.get_safe_string(first_prefix);
        	second_prefix = DB.get_safe_string(second_prefix);
        	third_prefix  = DB.get_safe_string(third_prefix);
        	
        	String a[]={first_prefix, second_prefix, third_prefix};
        	Arrays.sort(a);
        	
        	first_prefix  = a[0];
        	second_prefix = a[1];
        	third_prefix  = a[2];
        	
        	db_set=db.query("SELECT * FROM "+DB.database+".Links WHERE first_prefix='"+first_prefix+"' and "+
                                                                      "second_prefix='"+second_prefix+"' and "+
        	                                                          "third_prefix='"+third_prefix+"';");

			try {
			if(db_set.next())
			{
				db.close();
				return "{\"status\":\"data is exist\"}";
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}

        	db.update("INSERT INTO "+DB.database+".Links VALUES(1,'"+ first_prefix + "','" + 
        	                                                          second_prefix + "','" + 
        	                                                          third_prefix + "')");
        }
        
		db.close();
    	return "{\"status\":\"Ok\"}";
    }
}
