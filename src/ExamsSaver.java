import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;


public class ExamsSaver extends ServerResource
{
	@Post("json")
    public String toString(Representation exams)
    {
		long       id     = 1;
		JSONParser parser = new JSONParser();
        JSONObject data   = null;
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
        
        
        DB         db          = new DB();
        String     name        = null;
        String     prefix      = null;
        String     language    = null;
        String     type        = null;
        ResultSet  db_set;
        
        //db.update("DELETE FROM Enrollment.Exams");
        db.update("DELETE FROM "+DB.database+".Exams");
        
        for(int i=0; i<array.size(); i++)
        {	
        	data   = (JSONObject)array.get(i);
        	
        	name     = (String)data.get("name");
        	prefix   = (String)data.get("prefix");
        	language = (String)data.get("language");
        	type     = (String)data.get("type");
        	
        	name     = DB.get_safe_string(name);
        	prefix   = DB.get_safe_string(prefix);
        	language = DB.get_safe_string(language);
        	type     = DB.get_safe_string(type);
        	
        	if(name==null || prefix==null || language==null || type==null)
        		return "{\"status\":\"Expected not null data\"}";
        	
        	db_set=db.query("SELECT * FROM "+DB.database+".Exams WHERE name='"+name+"' and "+
        	                                                    "prefix='"+prefix+"' and "+
        	                                                    "language='"+language+"' and "+
        	                                                    "type='"+type+"';");
        	
        	try {
    			if(db_set.next())
    			{
    				db.close();
    				return "{\"status\":\"data is exist\"}";
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        	
        	db.update("INSERT INTO "+DB.database+".Exams VALUES("+id+",'"+
        	                                                      name + "','" + 
                                                                  prefix + "','" + 
                                                                  language + "','" + 
                                                                  type + "')");
        }
        
		db.close();
    	return "{\"status\":\"Ok\"}";
    }
}
