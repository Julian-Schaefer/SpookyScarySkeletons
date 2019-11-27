import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class AndereBean implements AndereBeanLocal{

    @PostConstruct
    public void init() {
        System.out.println("Initialized andere Bean2!");
    }

}
