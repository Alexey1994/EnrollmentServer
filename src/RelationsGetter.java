import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class RelationsGetter extends ServerResource
{
	@Get("txt")
    public String toString()
    {
		DB         db       = new DB();
		ResultSet  db_set   = db.query("SELECT * FROM "+DB.database+".Exams");
		JSONArray  triada   = null;
		JSONArray  prefixes = new JSONArray();
		
		try {
			while(db_set.next())
			{
				triada = new JSONArray();
				
				triada.add(db_set.getString("first_prefix"));
				triada.add(db_set.getString("second_prefix"));
				triada.add(db_set.getString("third_prefix"));
				
				prefixes.add(triada);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.close();
    	return prefixes.toJSONString();
    }
}