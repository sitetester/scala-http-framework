package framework.routing.annotation;

public class RouteReader {

    public void read() {

        String method = RouteDemo.class.getAnnotation(Route.class).method();
        String name = RouteDemo.class.getAnnotation(Route.class).name();

        System.out.println(method);
        System.out.println(name);

    }
}
