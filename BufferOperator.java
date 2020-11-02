/**
 * A class to standardise objects that interact with a buffer
 * It does no buffer actions on its own, it is just a utility class
 */
public abstract class BufferOperator implements Runnable
{
    private int id;             // The ID of this buffer operator
    private int totalElements;  // The total amount of elements to move in the buffer
    private int elementsMoved;  // The number of elements that have been moved so far
    private Buffer buffer;      // The buffer to move elements in

    /**
     * Creates a BufferOperator with the given data
     * @param id the ID of the buffer operator
     * @param elements the amount of elements that it will move in the buffer
     * @param buff the buffer that elements will be moved in
     */
    public BufferOperator(int id, int elements, Buffer buff)
    {
        this.id = id;
        totalElements = elements;
        elementsMoved = 0;
        buffer = buff;
    }

    /**
     * Gets the ID of this operator
     * @return the ID of the operator
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gets the total amount of elements that will be moved
     * @return the total elements to be moved
     */
    public int getTotalElements()
    {
        return totalElements;
    }

    /**
     * Gets how many elements have already been moved
     * @return the amount of moved elements
     */
    public int getMovedElements()
    {
        return elementsMoved;
    }

    /**
     * Gets the buffer that elements will be moved in
     * @return the buffer elements are moved in
     */
    public Buffer getBuffer()
    {
        return buffer;
    }

    /**
     * Increases how many elements have been moved
     * It will not increment past the total amount of elements
     */
    public void incrementMoves()
    {
        if(elementsMoved < totalElements)
        {
            elementsMoved++;
        }
    }
}
