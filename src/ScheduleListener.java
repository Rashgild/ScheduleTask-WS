import org.codehaus.jettison.json.JSONException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

@WebListener()
public class ScheduleListener implements ServletContextListener {
    private static Map<ScheduledExecutorService,Task> map = new HashMap<>();

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("ScheduleTaskListener is start");
        ScheduleService scheduleTasks = new ScheduleService();
        try {
            map = scheduleTasks.startThread(1L,"SomeTask","http://google.com","08:00:00");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("ScheduleTaskListener is destroyed");
        for (Map.Entry entry : map.entrySet()) {
            ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)entry.getKey();
            scheduledExecutorService.shutdownNow();
            scheduledExecutorService.shutdown();
        }
    }

}
