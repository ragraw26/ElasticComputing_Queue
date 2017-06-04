package server;

import java.util.ArrayList;

/**
 * Created by Juilee on 5/28/2017.
 */
public class ServerPool {

    private int poolSize;
    private ArrayList<ProcessServer> servers;
    private int waitingqueueSize;

    public ServerPool(int poolSize, int waitingqueueSize) {
        this.poolSize = poolSize;
        this.waitingqueueSize = waitingqueueSize;
        servers = new ArrayList<>(poolSize);
        addServers();
    }

    public ArrayList<ProcessServer> getServers() {
        return servers;
    }

    private void addServers() {
        for (int i = 1; i <= poolSize; i++) {
            ProcessServer processServer = new ProcessServer("Server " + i, waitingqueueSize);
            servers.add(processServer);
        }
    }

}
