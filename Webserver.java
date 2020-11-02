/**
 * Represents a web-server which will take a set amount of items out of a buffer
 * to be "processed"
 */
public class Webserver extends BufferOperator implements Runnable
{
    /**
     * Creates a webserver
     * @param id the ID of the webserver
     * @param b the buffer to take items from
     * @param elements the number of elements to take
     */
    public Webserver(int id, Buffer b, int elements)
    {
        super(id, elements, b);
    }

    @Override
    public void run()
    {
        while(getMovedElements() < getTotalElements())
        {
            getBuffer().removeOne(this);
            incrementMoves();
        }
    }
}