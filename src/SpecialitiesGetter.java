import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class SpecialitiesGetter extends ServerResource
{
	@Get("txt")
    public String toString()
    {
		DB         db     = new DB();
		ResultSet  db_set = db.query("SELECT * FROM "+DB.database+".Specialities;");
		JSONObject exam   = null;
		JSONArray  exams  = new JSONArray();
		
		try {
			while(db_set.next())
			{
				exam = new JSONObject();
				
				exam.put("specialty",   db_set.getString("specialty"));
				exam.put("score",       db_set.getString("score"));
				exam.put("competition", db_set.getString("competition"));
				exam.put("university",  db_set.getString("university"));
				exam.put("faculty",     db_set.getString("faculty"));
				
				exams.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.close();
    	return exams.toJSONString();
    }
}