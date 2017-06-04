package dataStructures;

import request.Request;

/**
 * Created by Juilee on 5/29/2017.
 */
public class CircularQueueArray {

    private int rear, front;
    private int size;
    private Request[] queue;
    private int len;

    public CircularQueueArray(int size) {
        this.size = size;
        this.queue = new Request[this.size];
        front = 0;
        rear = 0;
        len = 0;
    }

    public boolean isFull() {

        return (len == queue.length);
    }

    public boolean isEmpty() {

        return (len == 0);
    }

    public void enqueue(Request i) {
        if (isFull()) {
            System.out.println("Queue is full");
        } else {

            queue[rear] = i;
            rear = (rear + 1) % queue.length;
            len++;

        }

    }

    public Request dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is Empty");
        }

        Request data = queue[front];
        front = (front + 1) % queue.length;
        len--;
        return data;
    }

    public Request peek() {
        if (!isEmpty()) {
            return queue[front];
        } else {
            return null;
        }
    }

    public int getSize() {
        return size;
    }

    public Request[] getQueue() {
        return queue;
    }

}
