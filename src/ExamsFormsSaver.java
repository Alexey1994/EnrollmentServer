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


public class ExamsFormsSaver extends ServerResource
{
	@Post("json")
    public String toString(Representation exams)
    {
		long       id     = 1;
		JSONParser parser = new JSONParser();
        JSONObject data   = null;
        JSONArray  array  = null;
        
        try{
            array=(JSONArray)parser.parse(exams.getText());
        }
        catch(ParseException e){
            System.out.println(e);
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        if(array==null)
            return "{\"status\":\"Error parsing JSON\"}";
        
        
        DB         db          = new DB();
        long       num_tests   = 0;
        long       num_exams   = 0;
        ResultSet  db_set;
        
        db.update("DELETE FROM "+DB.database+".Forms");
        
        for(int i=0; i<array.size(); i++)
        {
        	data   = (JSONObject)array.get(i);

        	num_tests = (long)data.get("tests");
        	num_exams = (long)data.get("exams");
        	
        	db_set=db.query("SELECT * FROM "+DB.database+".Forms WHERE num_tests='"+num_tests+"' and "+
        	                                                          "num_exams='"+num_exams+"';");
        	
        	try {
    			if(db_set.next())
    			{
    				db.close();
    				return "{\"status\":\"data is exist\"}";
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        	
			db.update("INSERT INTO "+DB.database+".Forms VALUES("+id+", "+num_tests + "," + num_exams + ")");
        }
        
		db.close();
    	return "{\"status\":\"Ok\"}";
    }
}
