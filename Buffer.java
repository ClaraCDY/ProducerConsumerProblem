import java.util.*;

/**
 * A buffer that allows for multiple threads to be
 * adding and removing elements from it at the same time
 */
public class Buffer							//Provides data and operations onto the fixed-length buffer
{
    private LinkedList<Object> buf_list;    // The list of arbitrary items that will be added to and removed from
    private int maxBufferSize;              // THe maximum amount of elements in the buf_list
    private Semaphore buffSem;              // The semaphore that ensures only one item can be added/removed at a time
    private Semaphore userSem;              // Semaphore to block users adding if the buffer is full
    private Semaphore serverSem;            // Semaphore to block servers if the buffer is empty

    /**
     * Creates a buffer with the given number of elements
     * @param n the max buffer size
     */
    public Buffer(int n)						//Buffer creation, with n indicating the maximum capacity
    {
        buf_list = new LinkedList<>();
        maxBufferSize = n;
        buffSem = new Semaphore(1);
        userSem = new Semaphore(0);
        serverSem = new Semaphore(0);
    }

    /**
     * Attempts to add an element to the buffer
     * Will put the thread to sleep if it can't
     * Will wake up thread when it can
     * @param adder the user adding to the buffer
     */
    public void add(User adder)
    {
        buffSem.acquire();
        if(!isFull())
        {
            buf_list.add(new Object());
            serverSem.release();
            System.out.println("User " + adder.getId() + " Adds an element " + buf_list.size() + "/" + maxBufferSize);
            buffSem.release();
        }
        else
        {
            System.out.println("Buffer full – User now sleeping");
            buffSem.release();
            userSem.acquire();
            add(adder);
        }
    }

    /**
     * Gets the size of the buffer
     * @return the number of elements in the buffer
     */
    public int getSize()
    {
        return buf_list.size();
    }

    /**
     * Attempts to remove an element from the buffer
     * if the buffer is empty, the thread will be put
     * to sleep until it can remove an element
     * @param remover the server trying to remove an element
     */
    public void removeOne(Webserver remover)
    {
        buffSem.acquire();
        if(!isEmpty())
        {
            buf_list.remove(0);
            userSem.release();
            System.out.println("Serv " + remover.getId() + " removed an element " + buf_list.size() + "/" + maxBufferSize);
            buffSem.release();
        }
        else
        {
            System.out.println("Buffer empty – web server wait");
            buffSem.release();
            serverSem.acquire();
            removeOne(remover);
        }
    }

    /**
     * Gets whether the buffer is full
     * as specified by the max buffer size
     * @return true if full, false otherwise
     */
    public boolean isFull()
    {
        return (buf_list.size() == maxBufferSize);
    }

    /**
     * Gets whether the buffer is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty()
    {
        return buf_list.isEmpty();
    }
}	  
