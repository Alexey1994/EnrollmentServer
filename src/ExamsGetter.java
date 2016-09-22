import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class ExamsGetter extends ServerResource
{
	@Get("txt")
    public String toString()
    {
		DB         db     = new DB();
		//ResultSet  db_set = db.query("SELECT * FROM Enrollment.Exams");
		ResultSet  db_set = db.query("SELECT * FROM "+DB.database+".Exams");
		JSONObject exam   = null;
		JSONArray  exams  = new JSONArray();
		
		try {
			while(db_set.next())
			{
				exam = new JSONObject();
				
				exam.put("name",     db_set.getString("name"));
				exam.put("prefix",   db_set.getString("prefix"));
				exam.put("language", db_set.getString("language"));
				exam.put("type",     db_set.getString("type"));
				
				exams.add(exam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.close();
    	return exams.toJSONString();
    }
}