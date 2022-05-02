import web.API;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) throws IOException, InterruptedException {
        Path cwd = Path.of("").toAbsolutePath();
        System.out.println(cwd.toString());
        System.out.println("test");
        API api = new API();
        api.startServer();
        /*
        TimeUnit.SECONDS.sleep(10);
        api.stop();
         */
    }
}
