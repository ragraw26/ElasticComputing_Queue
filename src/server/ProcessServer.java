package server;

import dataStructures.CircularQueueArray;
import request.Request;

import java.util.*;
import javax.swing.table.DefaultTableModel;
import userinterface.MonitoringPanel;

/**
 * Created by Juilee on 5/28/2017.
 */
public class ProcessServer {

    private String name;
    private CircularQueueArray waitingQueue;
    private MonitoringPanel monitoringPan;
    private ArrayList<Request> processedRequests;

    public ProcessServer(String name, int queueSize) {
        this.name = name;
        this.waitingQueue = new CircularQueueArray(queueSize);
        monitoringPan = new MonitoringPanel();
        monitoringPan.nameLbl.setText(name);
        this.processedRequests = new ArrayList<>();
    }

    public ArrayList<Request> getProcessedRequests() {
        return processedRequests;
    }

    public void setProcessedRequests(ArrayList<Request> processedRequests) {
        this.processedRequests = processedRequests;
    }

    public MonitoringPanel getMonitoringPan() {
        return monitoringPan;
    }

    public void setMonitoringPan(MonitoringPanel monitoringPan) {
        this.monitoringPan = monitoringPan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CircularQueueArray getWaitingQueue() {
        return waitingQueue;
    }

    public void populateServerTable() {

        try {

            DefaultTableModel defaultTableModel = (DefaultTableModel) monitoringPan.processedReqsTbl.getModel();
            defaultTableModel.setRowCount(0);
            Object rows[];
            Request serverRequest = null;
            if (!waitingQueue.isEmpty()) {

                for (int i = 0; i < waitingQueue.getSize(); i++) {
                    serverRequest = (Request) waitingQueue.getQueue()[i];
                    if (serverRequest != null && serverRequest.getStatus().equals("WAITING")) {
                        rows = new Object[2];
                        rows[0] = serverRequest.getRequestName();
                        rows[1] = serverRequest.getStatus();

                        defaultTableModel.addRow(rows);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
