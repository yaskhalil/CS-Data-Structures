import javax.swing.tree.TreeNode;
public class TreeSet<E extends Comparable<E>> {
    TreeNode<E> root;
    int size;
    String output;
    TreeNode<E> parent;
    int time = 1;

    public TreeSet() {
        root = null;
    }

    public void add(E val)
    {
        TreeNode<E> temp = new TreeNode<E>(val);
        if (size == 0 || root == null) {
            root = temp;
            size = 1;
        } else {
            add(root, temp);
        }
    }

    public void add(TreeNode<E> e, TreeNode<E> val)
    {
        if (e.compareTo(val) > 0)
        {
            if (e.getLeft() == null)
            {
                e.setLeft(val);
                size++;
            } else {
                add(e.getLeft(), val);
            }
        } else if (e.compareTo(val) < 0)
        {
            if (e.getRight() == null)
            {
                e.setRight(val);
                size++;
            } else {
                add(e.getRight(), val);
            }
        } else {
            System.out.println("Duplicate value");
        }
    }

    public void remove(E val)
    {
        if (root.getValue().equals(val) && root.getLeft() == null && root.getRight() == null)
        {
            size = 0;
            root = null;
        } else {
            remove(root, null, val);
        }
    }

    public void remove(TreeNode<E> curr, TreeNode<E> prev, E val)
    {
        if (curr != null)
        {
            if (val.compareTo(curr.getValue()) > 0)
            {
                prev = curr;
                remove(curr.getRight(), prev, val);
            } else if (val.compareTo(curr.getValue()) < 0)
            {
                prev = curr;
                remove(curr.getLeft(), prev, val);
            } else
            {
                if (curr.getLeft() == null && curr.getRight() == null)
                {
                    if (prev.getLeft() == curr)
                    {
                        prev.setLeft(null);
                        size--;

                    } else if (prev.getRight() == curr)
                    {
                        prev.setRight(null);
                        size--;

                    }
                } else if (curr.getLeft() != null && curr.getRight() == null)
                {
                    if (prev.getLeft() == curr)
                    {
                        prev.setLeft(curr.getLeft());
                        size--;

                    } else if (prev.getRight() == curr)
                    {
                        prev.setRight(curr.getLeft());
                        size--;

                    }
                } else if (curr.getLeft() == null && curr.getRight() != null)
                {
                    if (prev.getLeft() == curr)
                    {
                        prev.setLeft(curr.getRight());
                        size--;

                    } else if (prev.getRight() == curr)
                    {
                        prev.setRight(curr.getRight());
                        size--;

                    }
                } else if (curr.getLeft() != null && curr.getRight() != null)
                {
                    TreeNode<E> left = curr.getLeft();
                    TreeNode<E> right = curr.getRight();

                    if (left.compareTo(right) > 0)
                    {
                        if (prev.getLeft() == curr)
                        {
                            prev.setLeft(left);
                            size--;

                        } else if (prev.getRight() == curr)
                        {
                            prev.setRight(left);
                            size--;

                        }
                    } else if (left.compareTo(right) < 0)
                    {
                        if (prev.getLeft() == curr)
                        {
                            prev.setLeft(right);
                            size--;

                        } else if (prev.getRight() == curr)
                        {
                            prev.setRight(right);
                            size--;

                        }
                    }
                }
            }
        }
    }

    public String preOrder(TreeNode<E> temp) {

        if (size == 0) {
            return "[]";
        }

        if (temp != null) {
            output += temp.getValue() + ", ";
            preOrder(temp.getLeft());
            preOrder(temp.getRight());
        }

        return output.substring(0, output.length() - 2) + "]";
    }

    public String preOrder() {
        output = "[";
        return preOrder(root);
    }

    public boolean contains(TreeNode<E> node, E value) {
        if (node == null) {
            return false;
        }
        if (node.getValue().equals(value)) {
            return true;
        }
        if (value.compareTo(node.getValue()) < 0) {
            return contains(node.getLeft(), value);
        }
        return contains(node.getRight(), value);
    }

    public String inOrder(TreeNode<E> temp) {

        if (size == 0) {
            return "[]";
        }

        if (temp != null) {
            inOrder(temp.getLeft());
            output += temp.getValue() + ", ";
            inOrder(temp.getRight());
        }

        return output.substring(0, output.length() - 1) + "]";
    }

    public String inOrder() {
        output = "[";
        return inOrder(root);
    }

    public String postOrder(TreeNode<E> temp) {

        if (size == 0) {
            return "[]";
        }

        if (temp != null) {
            postOrder(temp.getLeft());
            postOrder(temp.getRight());
            output += temp.getValue() + ", ";
        }

        return output.substring(0, output.length() - 1) + "]";
    }

    public String postOrder() {
        output = "[";
        return postOrder(root);
    }

    public void rotateLeft() {
        if (root.getRight() != null) {
            rotateLeft(root.getRight());
        }
    }

    public void rotateLeft(TreeNode<E> val) {
        if (val.getLeft() == null)
            root.setRight(null);
        else
            root.setRight(val.getLeft());

        val.setLeft(root);
        root = val;

    }

    public void rotateRight() {
        if (root.getLeft() != null) {
            rotateRight(root.getLeft());
        }
    }

    public void rotateRight(TreeNode<E> val) {
        if (val.getRight() == null)
            root.setLeft(null);
        else
            root.setLeft(val.getRight());

        val.setRight(root);
        root = val;
    }

    public int size() {
        return size;
    }

    public class TreeNode<E extends Comparable<E>> {
        E val;
        TreeNode<E> left;
        TreeNode<E> right;

        TreeNode(E val) {
            this.val = val;
            left = null;
            right = null;
        }

        public E getValue() {
            return val;
        }

        public TreeNode<E> getRight() {
            return right;
        }

        public TreeNode<E> getLeft() {
            return left;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public void setval(E val) {
            this.val = val;
        }

        public String toString() {
            return val.toString();
        }

        public int compareTo(TreeNode<E> other) {
            return val.compareTo(other.getValue());
        }
    }
}
