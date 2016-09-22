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


public class SpecialitiesSaver extends ServerResource
{
	@Post("json")
    public String toString(Representation exams)
    {
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
        
        
        long       id          = 1;
        DB         db          = new DB();
        String     specialty   = null;
        long       score       = 0;
        String     competition = null;
        String     university  = null;
        String     faculty     = null;
        ResultSet  db_set;
        
        db.update("DELETE FROM "+DB.database+".Specialities");
        
        for(int i=0; i<array.size(); i++)
        {	
        	data        = (JSONObject)array.get(i);
        	
        	specialty   = (String)data.get("specialty");
            score       = (long)data.get("score");
            competition = (String)data.get("competition");
            university  = (String)data.get("university");
            faculty     = (String)data.get("faculty");
            
            specialty   = DB.get_safe_string(specialty);
            competition = DB.get_safe_string(competition);
            university  = DB.get_safe_string(university);
            faculty     = DB.get_safe_string(faculty);

            if(   specialty==null 
               || score==0 
               || competition==null 
               || university==null)
            {
            	return "{\"status\":\"Expected not null data\"}";
            }
            
            db_set=db.query("SELECT * FROM "+DB.database+".Specialities WHERE specialty='"+specialty+"' and "+
                                                                             "competition='"+competition+"' and "+
                                                                             "university='"+university+"' and "+
                                                                             "faculty='"+faculty+"';");
            
            try {
    			if(db_set.next())
    			{
    				db.close();
    				return "{\"status\":\"data is exist\"}";
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        	
        	db.update("INSERT INTO "+DB.database+".Specialities VALUES("+id+",'"+
        			                                                     specialty + "'," +
        	                                                             score + ",'" +
					                                                     competition + "','" +
					                                                     university + "','" +
					                                                     faculty + "');");
        }
        
		db.close();
    	return "{\"status\":\"Ok\"}";
    }
}
