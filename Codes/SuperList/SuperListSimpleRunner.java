

public class SuperListSimpleRunner {
    public SuperListSimpleRunner() {
        SuperList<Integer> list = new SuperList<Integer>();
        for (int x = 0; x < 30; x++) {
            list.add((int) (Math.random() * 1000) + 1);
        }
        System.out.println("List: " + list);
        System.out.println("The size is " + list.size());
        SuperList<Integer> stack = new SuperList<Integer>();
        while (!list.isEmpty())
            stack.push(list.remove(0));
        System.out.println("Stack: " + stack);
        SuperList<Integer> queue = new SuperList<Integer>();
        while (!stack.isEmpty())
            queue.add(stack.pop());
        System.out.println("Queue: " + queue);
        while (!queue.isEmpty()) {
            if (list.isEmpty())
                list.add(queue.poll());
            else
                list.add((int) (Math.random() * list.size()), queue.poll());
        }
        System.out.println("Randomized List: " + list);
        int sum = 0;
        for (int x = 0; x < list.size(); x++) {
            sum += list.get(x);
        }
        System.out.println("The sum is " + sum);
        int sumeven = 0;
        for (int x = 0; x < list.size(); x += 2) {
            sumeven += list.get(x);
        }
        System.out.println("The sum of even indices is " + sumeven);
        int sumodd = 0;
        for (int x = 1; x < list.size(); x += 2) {
            sumodd += list.get(x);
        }
        System.out.println("The sum of odd indices is " + sumodd);
        for (int x = 0; x < list.size(); x += 2) {
            list.add(list.get(x));
        }
        System.out.println("Doubled List: " + list);
        for (int x = 0; x < list.size(); x++) {
            if (list.get(x) % 3 == 0) {
                list.remove(x);
                x--;
            }
        }
        System.out.println("List with multiples of 3 removed: " + list);
        list.add(3, 55555);
        for (int i = 1; i < list.size(); ++i) {
            int key = list.get(i);
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their curr position */
            while (j >= 0 && list.get(j) < key) {
                list.add(j + 1, list.remove(j));
                j--;
            }
            list.set(j + 1, key);
        }
        System.out.println("Descending List: " + list);
        int median = 0;
        if (list.size() % 2 == 0)
            median = (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2;
        else
            median = list.get(list.size() / 2);
        System.out.println("The median is " + median);
        System.out.print("The numbers after the median are ");
        for (int x = 0; x < list.size() / 2; x++) {
            System.out.print(list.get(x) + " ");
        }
        System.out.print("\nThe numbers before the median are ");
        for (int x = list.size() / 2; x < list.size(); x++) {
            System.out.print(list.get(x) + " ");
        }

        SuperList<String> sentence = new SuperList<String>();
        String s = "The quick brown fox jumped over the lazy dog";
        String[] split = s.split(" ");
        for (int x = 0; x < split.length; x++) {
            sentence.add(split[x]);
        }
        for (int x = 0; x < sentence.size(); x++) {
            if (sentence.get(x).length() <= 3) {
                sentence.remove(x);
                x--;
            }
        }
        for (int i = 1; i < sentence.size(); ++i) {
            String key = sentence.get(i);
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their curr position */
            while (j >= 0 && sentence.get(j).compareTo(key) > 0) {
                sentence.add(j + 1, sentence.remove(j));
                j--;
            }
            sentence.set(j + 1, key);
        }

        System.out.println("Sentence Ascending: " + sentence);

        int suml = 0;
        for (int x = 0; x < sentence.size(); x++) {
            suml += sentence.get(x).length();
        }
        int avgl = suml / sentence.size();
        System.out.println("The average word length is " + avgl);
    }

    public static void main(String[] args) {
        SuperListSimpleRunner app = new SuperListSimpleRunner();
    }
}

