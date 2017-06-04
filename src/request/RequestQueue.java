package request;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Juilee on 5/28/2017.
 */
public class RequestQueue {

    private Queue<Request> requestQueue;

    public RequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    public Queue<Request> getRequestQueue() {
        return requestQueue;
    }

}
