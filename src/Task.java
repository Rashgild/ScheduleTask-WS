import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Task implements Runnable {

    private String id;
    private String serviceName;
    private String link;
    private String time;


    public Task(String id,String serviceName,String link,String time) {
        this.id = id;
        this.serviceName = serviceName;
        this.link = link;
        this.time = time;
    }

    public String getName(){
        return serviceName;
    }

    public String getId() {
        return id;
    }

    public String getServiceInfo() throws JSONException {
        return new JSONObject()
                .put("id",String.valueOf(id))
                .put("serviceName",serviceName)
                .put("link",link)
                .put("time",time).toString();
    }

    public void run() {
        createGetRequest(link);
    }

    private static String createGetRequest(String path){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(path);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        System.out.println(response);
        return response.getEntity().toString();
    }

}
