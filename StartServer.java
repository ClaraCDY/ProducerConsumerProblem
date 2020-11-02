import java.util.*;

public class StartServer
{
    private Buffer b;										  // Creation of buffer object
    private ArrayList<BufferOperator> operators;              // The list of users and servers that will be interacting with the buffer
    private ArrayList<Thread> activeThreads;                  // The threads related to adding to the buffer

    public StartServer(int bufferCapacity, int userCount, int webservers, int elements)
    {												//Creates execution scenario between user and webservers on buffer
        long startTime = System.currentTimeMillis();
        if(bufferCapacity < 0 || userCount < 0 || webservers < 0 || elements < 0)
        {
            System.out.println("Invalid inputs!\nPlease make sure all values are at least 0!");
            return;
        }

        b = new Buffer(bufferCapacity);
        operators = new ArrayList<>();
        activeThreads = new ArrayList<>();
        initialiseUsers(elements, userCount);           // Creates users
        initialiseServers(elements, webservers);        // Creates servers
        System.out.println("-----------------------");
        createAllThreads();                             // Start all threads
        try
        {
            for (Thread thread : activeThreads)         //
            {                                           //
                thread.join();                          // Wait for threads to finish
            }                                           //
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
        System.out.println("-----------------------");
        // Print out elements that were added/consumed
        for (BufferOperator op : operators)
        {
            if(op instanceof User)
            {
                System.out.println("User " + op.getId() + " created a total of "+ op.getMovedElements() +" elements");
            }
            else
            {
                System.out.println("Consumer " + op.getId() + " consumed a total of "+ op.getMovedElements() +" elements");
            }
        }

        System.out.println("-----------------------");
        System.out.println("Buffer has " + b.getSize() + " elements remaining");
        long endTime = System.currentTimeMillis();
        System.out.println("-----------------------");
        System.out.println("Program took " + (endTime - startTime) + " milliseconds to complete");
    }

    /**
     * Creates a new thread for each user and starts it
     */
    private void createAllThreads()
    {
        for (BufferOperator op : operators)
        {
            Thread newThread = new Thread(op);
            activeThreads.add(newThread);
            newThread.start();
        }
    }

    public static void main(String[] args)
    {
        int bufferSize;
        int userCount;
        int webserverCount;
        int totalElements;
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter buffer capacity");					//Insert user inputted values for program execution
        bufferSize = reader.nextInt();
        System.out.println("Enter number of users");
        userCount = reader.nextInt();;
        System.out.println("Enter number of servers");
        webserverCount = reader.nextInt();;
        System.out.println("Enter total number of elements");
        totalElements = reader.nextInt();;
        StartServer start = new StartServer(bufferSize, userCount, webserverCount, totalElements);
    }

    /**
     * Evenly separates the total amount of elements to add across the amount
     * of users that will be adding to the buffer
     * @param totalElements the total amount of elements (for all users)
     * @param userCount the amount of users that will add to the buffer
     */
    private void initialiseUsers(int totalElements, int userCount)
    {
        //Equally subdivide user inputted elements across all user objects
        int remainder = totalElements % userCount;
        int elementPerUser = totalElements/userCount;
        for (int i = 0; i < userCount; i++)
        {
            if(remainder > 0)
            {
                operators.add(new User(i, elementPerUser + 1, b));
                remainder--;
            }
            else
            {
                operators.add(new User(i, elementPerUser, b));
            }
        }
        System.out.println("Elements per user: " + elementPerUser);
    }

    /**
     * Evenly separates the total amount of elements across the
     * amount of servers that will be removing from the buffer
     * @param totalElements the total amount of elements (for all servers)
     * @param serverCount the amount of servers that will take from the buffer
     */
    private void initialiseServers(int totalElements, int serverCount)
    {
        //Equally subdivide user inputted elements across all server objects
        int remainder = totalElements % serverCount;
        int elementPerServer = totalElements/serverCount;
        for (int i = 0; i < serverCount; i++)
        {
            if(remainder > 0)
            {
                operators.add(new Webserver(i, b, elementPerServer + 1));
                remainder--;
            }
            else
            {
                operators.add(new Webserver(i, b, elementPerServer));
            }
        }
        System.out.println("Elements per server: " + elementPerServer);
    }
}
