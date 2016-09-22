import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class PrefixGetter extends ServerResource
{
	@Get("txt")
    public String toString()
    {
		DB         db     = new DB();
		//ResultSet  db_set = db.query("SELECT * FROM Enrollment.Exams");
		ResultSet  db_set = db.query("SELECT distinct prefix FROM "+DB.database+".Exams");
		JSONObject exam   = null;
		JSONArray  exams  = new JSONArray();
		
		try {
			while(db_set.next())
			{	
				exams.add(db_set.getString("prefix"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.close();
    	return exams.toJSONString();
    }
}