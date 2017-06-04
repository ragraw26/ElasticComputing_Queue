package dataStructures;

import request.Request;

import java.util.NoSuchElementException;

/**
 * Created by Juilee on 5/28/2017.
 */
public class QueueArray {
    protected Request Queue[] ;
    protected int front, rear, size, len;
    /* Constructor */
    public QueueArray(int n)
    {
        size = n;
        len = 0;
        Queue = new Request[size];
        front = -1;
        rear = -1;
    }
    /*  Function to check if queue is empty */
    public boolean isEmpty()
    {
        return front == -1;
    }
    /*  Function to check if queue is full */
    public boolean isFull()
    {
        return front==0 && rear == size -1 ;
    }
    /*  Function to get the size of the queue */
    public int getSize()
    {
        return len ;
    }
    /*  Function to check the front element of the queue */
    public Request peek()
    {
        if (isEmpty())
            throw new NoSuchElementException("Underflow Exception");
        return Queue[front];
    }
    /*  Function to insert an element to the queue */
    public void insert(Request i)
    {
        if (rear == -1)
        {
            front = 0;
            rear = 0;
            Queue[rear] = i;
        }
        else if (rear + 1 >= size)
            throw new IndexOutOfBoundsException("Overflow Exception");
        else if ( rear + 1 < size)
            Queue[++rear] = i;
        len++ ;
    }
    /*  Function to remove front element from the queue */
    public Request remove()
    {
        if (isEmpty())
            throw new NoSuchElementException("Underflow Exception");
        else
        {
            len-- ;
            Request ele = Queue[front];
            if ( front == rear)
            {
                front = -1;
                rear = -1;
            }
            else
                front++;
            return ele;
        }
    }
    /*  Function to display the status of the queue */
    public void display()
    {
        System.out.print("\nQueue = ");
        if (len == 0)
        {
            System.out.print("Empty\n");
            return ;
        }
        for (int i = front; i <= rear; i++)
            System.out.print(Queue[i]+" ");
        System.out.println();
    }
}
