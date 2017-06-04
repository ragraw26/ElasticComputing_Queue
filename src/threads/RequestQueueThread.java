package threads;

import java.text.SimpleDateFormat;
import java.util.Date;
import request.Request;
import request.RequestQueue;
import userinterface.MainFrame;

/**
 * Created by Juilee on 5/29/2017.
 */
public class RequestQueueThread implements Runnable {

    private RequestQueue requestQueue;
    private int requestRate;
    private int processingTime;
    private int count = 0;
    private MainFrame mainFrame;
    private boolean flag = false;

    public RequestQueueThread(MainFrame mainFrame, RequestQueue requestQueue, int requestRate, int processingTime) {
        this.mainFrame = mainFrame;
        this.requestQueue = requestQueue;
        this.requestRate = requestRate;
        this.processingTime = processingTime;

    }

    @Override
    public void run() {
        while (flag) {

            try {
                Thread.sleep(requestRate * 1000);    //request rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            Request request = new Request("Request " + count);
            request.setStatus("INCOMING");
            request.setProcessingTime(processingTime * 1000);
            request.setStartTime((System.currentTimeMillis()));
            requestQueue.getRequestQueue().add(request);
            mainFrame.populateRequestTable(requestQueue);
        }

        mainFrame.populateRequestTable(requestQueue);

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
