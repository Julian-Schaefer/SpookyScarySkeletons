import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class TestBean implements TestBeanLocal {

    @PostConstruct
    public void init() {
        System.out.println("Initialized Bean!");
    }

    public void writeSomething() {
        System.out.println("Hallo ich bin ein Test!");
    }

    @Override
    public void doSomething() {
        writeSomething();
    }
}
