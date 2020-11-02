/**
 * Represents a user in a system who wants to add something
 * to a buffer for a server to deal with later
 */
public class User extends BufferOperator implements Runnable
{
    /**
     * Creates a user
     * @param id the id of the user
     * @param elements the number of elements to add to the buffer
     * @param buff the buffer to add items to
     */
    public User(int id, int elements, Buffer buff)
    {
        super(id, elements, buff);
    }

    @Override
    public void run()
    {
        while (getMovedElements() < getTotalElements())
        {
            getBuffer().add(this);
            incrementMoves();
        }
    }
}