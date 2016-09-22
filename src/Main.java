import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.resource.ServerResource;
import org.restlet.Component;


public class Main extends ServerResource
{
    public static void main(String[] args) throws Exception 
    {
    	//DB.install_driver();
    	
    	Component component   = new Component();
        Server    server      = component.getServers().add(Protocol.HTTP, 8182);
        
        Directory dir         = new Directory(component.getContext().createChildContext(), "https://alexey1994.github.io/Enrollment");
        component.getClients().add(Protocol.HTTPS);
        
        //Directory dir         = new Directory(component.getContext().createChildContext(), "file:///c:/Users/Lex/AdminPage/");
        //component.getClients().add(Protocol.FILE);
        
        component.getDefaultHost().attach(dir);
        component.getDefaultHost().attach("/exams",            ExamsGetter.class);
        component.getDefaultHost().attach("/exams/save",       ExamsSaver.class);
        
        component.getDefaultHost().attach("/examsForms",       ExamsFormsGetter.class);
        component.getDefaultHost().attach("/examsforms/save",  ExamsFormsSaver.class);
        
        component.getDefaultHost().attach("/exam_prefixes",    PrefixGetter.class);
        
        component.getDefaultHost().attach("/relations",        RelationsGetter.class);
        component.getDefaultHost().attach("/relations/save",   RelationsSaver.class);
        
        component.getDefaultHost().attach("/specialties",      SpecialitiesGetter.class);
        component.getDefaultHost().attach("/specialties/save", SpecialitiesSaver.class);

        server.start();
    }
}