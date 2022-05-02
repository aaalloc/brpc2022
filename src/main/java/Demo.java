import web.API;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) throws IOException, InterruptedException {
        API.start();
        TimeUnit.SECONDS.sleep(5);
        API.close();
    }
}
