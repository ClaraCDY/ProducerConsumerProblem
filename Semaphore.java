/**
 * Ensures critical region can be protected from mutliple threads
 * Call acquire() first then release() once finished
 */
public class Semaphore
{
    private int allowedThreads; // Number of threads allowed in the critical region

    /**
     * Creates a semaphore to handle threads in critical regions
     * @param allowedThreads the number of threads allowed in a critical region
     */
    public Semaphore(int allowedThreads)
    {
        this.allowedThreads = allowedThreads;
    }

    /**
     * Attempts to get passed, if the number of threads
     * in the critical section would be exceeded by this
     * new thread, it is put to sleep
     */
    public synchronized void acquire()
    {
        allowedThreads--;
        if(allowedThreads < 0)
        {
            try
            {
                wait();
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Tells the semaphore that another thread can now
     * enter the critical section. This will wake up a thread that
     * was currently waiting due to acquire() (random thread)
     */
    public synchronized void release()
    {
        if(allowedThreads < 1)
        {
            allowedThreads++;
        }
        notify();
    }

}

