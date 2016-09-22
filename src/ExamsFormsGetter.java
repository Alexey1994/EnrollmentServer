import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class ExamsFormsGetter extends ServerResource
{
	@Get("txt")
    public String toString()
    {
		DB         db     = new DB();
		ResultSet  db_set = db.query("SELECT * FROM "+DB.database+".Forms");
		JSONObject exam   = null;
		JSONArray  exams  = new JSONArray();
		
		try {
			while(db_set.next())
			{
				exam = new JSONObject();
				
				exam.put("tests",   db_set.getLong("num_tests"));
				exam.put("exams",   db_set.getLong("num_exams"));
				
				exams.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.close();
    	return exams.toJSONString();
    }
}
