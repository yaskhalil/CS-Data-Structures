public class ListNode<E> {
    private E value;
    private ListNode<E> next;
    private ListNode<E> previous;
    private ListNode<E> end;
    public ListNode() {
        this.value = value;
        this.next = next;
        this.previous = previous;
        this.end = end;

    }

    public <E> ListNode(E item) {
    }

    public ListNode<E> getEnd()
    {
        return end;
    }

    public E getValue()
    {
        return value;
    }
    public ListNode<E> getPrevious()
    {
        return previous;
    }
    public ListNode<E> getNext()
    {
        return next;
    }

    public void setPrevious()
    {
        this.previous = previous;
    }
    public void setNext()
    {
        this.next = next;
    }
    public boolean hasPrevious()
    {
        return previous != null;
    }
    public boolean hasNext()
    {
        return next != null;
    }


    public void setPrevious(ListNode<E> end) {
    }

    public void setNext(ListNode<E> temp) {
    }
}



