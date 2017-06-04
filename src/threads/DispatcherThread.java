package threads;

import request.Request;
import request.RequestQueue;
import server.ProcessServer;
import server.ServerPool;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import userinterface.MainFrame;

/**
 * Created by Juilee on 5/28/2017.
 */
public class DispatcherThread implements Runnable {

    private RequestQueue requestQueue;
    private CopyOnWriteArrayList<ProcessServer> availableServers;
    private ServerPool serverPool;
    private MainFrame mainFrame;

    public DispatcherThread(MainFrame mainFrame, RequestQueue requestQueue, ServerPool serverPool, CopyOnWriteArrayList<ProcessServer> availableServers) {
        this.mainFrame = mainFrame;
        this.requestQueue = requestQueue;
        this.serverPool = serverPool;
        this.availableServers = availableServers;
        ProcessServer processServer = serverPool.getServers().get(0);
        availableServers.add(processServer);
        serverPool.getServers().remove(processServer);

    }

    @Override
    public void run() {
        try {

            Queue<Request> rQueue = requestQueue.getRequestQueue();
            while (true) {

                synchronized (rQueue) {

                    if (rQueue.peek() != null) {
                        Thread.sleep(1000);
                        Request req = rQueue.peek();                      

                        CopyOnWriteArrayList<ProcessServer> serverList = availableServers;

                        for (int i = serverList.size() - 1; i >= 0; i--) {
                            System.out.println("Available server size" + serverList.size());
                            if (!req.getStatus().equals("WAITING")) {
                                if (!serverList.get(i).getWaitingQueue().isFull()) {
                                    mainFrame.getMonitoringTabs().addTab(serverList.get(i).getName(), serverList.get(i).getMonitoringPan());
                                    req.setStatus("WAITING");
                                    serverList.get(i).getWaitingQueue().enqueue(req);
                                    rQueue.poll();
                                    mainFrame.populateRequestTable(requestQueue);
                                    System.out.println(req.getRequestName() + " " + req.getStatus());
                                    mainFrame.populateServerPoolTable();
                                    serverList.get(i).populateServerTable();
                                } else if (i == 0 && (!serverPool.getServers().isEmpty())) {
                                    ProcessServer server = serverPool.getServers().get(0);
                                    availableServers.add(server);
                                    server.getWaitingQueue().enqueue(req);
                                    req.setStatus("WAITING");
                                    rQueue.poll();
                                    mainFrame.populateRequestTable(requestQueue);
                                    System.out.println(server.getName());
                                    mainFrame.getMonitoringTabs().addTab(server.getName(), server.getMonitoringPan());
                                    serverPool.getServers().remove(server);
                                    mainFrame.populateServerPoolTable();
                                    server.populateServerTable();
                                    break;
                                }

                            }
                        }

                    } else if (availableServers.size() > 1) {
                        for (ProcessServer pserver : availableServers) {

                            if (pserver.getWaitingQueue().isEmpty()) {
                                serverPool.getServers().add(pserver);
                                availableServers.remove(pserver);
                                mainFrame.populateServerPoolTable();
                                pserver.populateServerTable();
                                mainFrame.getMonitoringTabs().remove(pserver.getMonitoringPan());
                                System.out.println("Server Removed " + pserver.getName());

                            }

                        }
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void startProcessing() throws InterruptedException {

        Thread requestProcessThread = new Thread(new RequestProcessThread(mainFrame, availableServers, serverPool));
        requestProcessThread.start();
        System.out.println("Process started");
    }
}
