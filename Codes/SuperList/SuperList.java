//tasks
// MAKE A REMOVE SO I CAN DO THINGS
// finish add
// finish pop
// finish poll

public class SuperList<E> {
    private ListNode<E> root, end, temp;

    private int size = 0;

    public SuperList() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(E item) {
        add(item);
    }

    public void add(E item){

        if (isEmpty()) {

            root = temp;
            end = root;

        } else {

            temp.setPrevious(end);
            end.setNext(temp);
            end = temp;
        }
        size++; //size increases
    }

    public void add(int position, E item) {
        ListNode<E> temp = new ListNode(item);
        if (position < 0 || position > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (position == size - 1) {
            add(item);
        } else if (position == 0) {
            root.setPrevious(temp);
            temp.setNext(root);
            root = temp;
            size++;
        } else {
            ListNode<E> current = root;
            for (int x = 0; x < position; x++) {
                current = current.getNext();
            }
            try {
                ListNode<E> currentPrevious = current.getPrevious();
                currentPrevious.setNext(temp);
                temp.setPrevious(currentPrevious);
                current.setPrevious(temp);
                temp.setNext(current);

            } catch (Exception e) {
            }
            size++;

        }
    }


    public E stackPeek() {
        if (isEmpty())
            return null;

        else {
            E value = root.getValue();
            return value;
        }
    }

    public E queuePeek() {
        return stackPeek();
    }


    //pop
    public E pop() {
        E temp = end.getValue();
        if (temp == null) {
            //return new EmptyStackException;
        } else if (size == 1) {
            E value = root.getValue();
            clear();
            return value;
        } else {
            E value = root.getValue();
            root = root.getNext();
            root.setPrevious(null);
            size--;
            return value;
        }


        //remove the end value
        return temp;
    }

    //poll
    public E poll() {
        if (size == 0)
            return null;

        return pop();

    }
    public E remove(int position) {
        if (position < 0 || position >= size) //if position is out of bounds
        {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            E temp = root.getValue(); // save val
            clear(); //clear list
            return temp;
        } else if (position == size - 1) //if position is at the end
        {
            E temp = end.getValue(); //saves val of end
            end = end.getPrevious(); //end is now the previous prev
            end.setNext(null); //end does not have a next
            size--; //size decreases
            return temp; //returns val of end
        } else if (position == 0) //if position is at the beginning
        {
            E temp = root.getValue(); //saves val of root
            root = root.getNext(); //root is now the next prev
            root.setPrevious(null); //root does not have xa previous
            size--; //size decreases
            return temp; //returns val of root
        }
        else //if position is in the middle
        {
            ListNode<E> curr = root; //start at first prev
            for (int x = 0; x < position; x++) //find val at intended position
            {
                curr = curr.getNext(); //move to next prev
            }
            E temp = curr.getValue(); //saves val of curr prev
            curr.getPrevious().setNext(curr.getNext()); //prev before curr follows prev after curr
            curr.getNext().setPrevious(curr.getPrevious()); //prev after curr precedes prev before curr
            size--; //size decreases
            return temp; //returns val of curr prev
        }
    }

    public void clear() {
        end = null;
        root = null;
        size = 0;
    }

    public void set(int position, E value)
    {
        if (position < 0 || position >= size) //if position is out of bounds
        {
            throw new IndexOutOfBoundsException();
        }
        else //if position is in bounds
        {
            ListNode<E> temp = root; //start at first prev
            for (int x = 0; x < position; x++) //find val at intended position
            {
                temp = temp.getNext(); //next prev
            }
            temp.setValue(value);
        }
    }
    public E get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            ListNode<E> temp = root;
            for (int x = 0; x < position; x++) {
                temp = temp.getNext();
            }
            return temp.getValue();
        }
    }

    public boolean contains(E val) {
        ListNode<E> temp = root;
        for (int x = 0; x < size; x++) {
            if (temp.getValue().equals(val) || temp.getValue() == val) {
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

    public int size() {
        return size;
    }

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


        public String toString()
        {
            String output = "[";
            for (int x = 0; x < size; x++)
            {
                if (temp != null)
                    output += temp.getValue();
                if(x<size-1)
                    output += ", ";
                temp=temp.getNext();
            }
            output += "]";
            return output;
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
        public void setValue(E value){
            this.value = this.value;
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


}
