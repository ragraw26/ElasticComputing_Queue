package threads;

import java.text.SimpleDateFormat;
import java.util.Date;
import request.Request;
import server.ProcessServer;

import java.util.concurrent.CopyOnWriteArrayList;
import server.ServerPool;
import userinterface.MainFrame;

/**
 * Created by Juilee on 5/30/2017.
 */
public class RequestProcessThread implements Runnable {

    private CopyOnWriteArrayList<ProcessServer> availableServers;
    private MainFrame mainFrame;
    private ServerPool serverPool;

    public RequestProcessThread(MainFrame mainFrame, CopyOnWriteArrayList<ProcessServer> availableServers, ServerPool serverPool) {
        this.mainFrame = mainFrame;
        this.availableServers = availableServers;
        this.serverPool = serverPool;

    }

    @Override
    public void run() {
        while (availableServers != null) {

            for (ProcessServer server : availableServers) {
                if (!server.getWaitingQueue().isEmpty()) {
                    Request request = server.getWaitingQueue().dequeue();
                    try {
                        Thread.sleep((long) request.getProcessingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    request.setStatus("PROCESSED");
                    request.setEndTime(((System.currentTimeMillis())));
                    request.setResponseTime((int) (request.getEndTime() - request.getStartTime()));
                    server.getProcessedRequests().add(request);

                    mainFrame.populateProcessedRequestTable(server, request);
                    server.populateServerTable();

                    System.out.println(request.getRequestName() + " " + request.getStatus());
                }

            }

        }
    }

}
