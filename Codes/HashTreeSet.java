
import java.util.ArrayList;
import java.util.*;

public class HashTreeSet {
    public HashTreeSet() {
        //fill list with 10 randoms 1-20
        ArrayList<HashSet> list = new ArrayList<HashSet>();
        int s = (int)(Math.random()*12)+2; //number of sets
        for (int x = 0; x < s; x++) 
        {
            HashSet<Integer> set = new HashSet<Integer>();
            while (set.size() != 10) //10 vals per set
            {
                set.add((int)(Math.random()*20)+1); //random 1-20
            }
            list.add(set);
        }
        //print list
        System.out.println("List: ");
        for (HashSet<Integer> si : list)
            System.out.println(si);
        //print intersection
        Set<Integer> i = intersection(list.get(0), list.get(1));
        System.out.println("Intersection 0-1: " + i);
        for (int x = 2; x < s; x++)
        {
            i = intersection(i, list.get(x));
            System.out.println("Intersection 0-"+ x + ": " + i);
        }
        System.out.println("Intersection Total: " + i);
        //print union
        Set<Integer> u = union(list.get(0), list.get(1));
        System.out.println("Union 0-1: " + u);
        for (int x = 2; x < s; x++)
        {
            u = union(u, list.get(x));
            System.out.println("Union 0-"+ x + ": " + u);
        }
        System.out.println("Union Total: " + u);
        //print even intersection
        Set<Integer> ei = evenIntersection(list.get(0), list.get(1));
        System.out.println("Even Intersection 0-1: " + ei);
        for (int x = 2; x < s; x++)
        {
            ei = evenIntersection(ei, list.get(x));
            System.out.println("Even Intersection 0-"+ x + ": " + ei);
        }
        System.out.println("Even Intersection Total: " + ei);
        //print even union
        Set<Integer> eu = evenUnion(list.get(0), list.get(1));
        System.out.println("Even Union 0-1: " + eu);
        for (int x = 2; x < s; x++)
        {
            eu = evenUnion(eu, list.get(x));
            System.out.println("Even Union 0-"+ x + ": " + eu);
        }
        System.out.println("Even Union Total: " + eu);
    }
    //only common vals between two sets
    public Set<Integer> intersection(Set<Integer> s1, Set<Integer> s2) {
        Set<Integer> s3 = new HashSet<Integer>();
        Iterator<Integer> it = s1.iterator(); //iterator for s1
        while (it.hasNext()) { //while there are more elements in s1
            int x = it.next();
            if (s2.contains(x)) { //if s2 also contains the element
                s3.add(x);
            }
        }
        return s3;
    }
    //all unique vals from both sets
    public Set<Integer> union(Set<Integer> s1, Set<Integer> s2) {
        Set<Integer> s3 = new HashSet<Integer>();
        Iterator<Integer> it = s1.iterator(); //iterator for s1
        while (it.hasNext()) { //while there are more elements in s1
            int x = it.next();
            s3.add(x); //add all
        }
        it = s2.iterator(); //iterator for s2
        while (it.hasNext()) {
            int x = it.next();
            s3.add(x); //add all (sets don't allow duplicates and will deal with it)
        }
        return s3;
    }
    //all common even vals between two sets
    public Set<Integer> evenIntersection(Set<Integer> s1, Set<Integer> s2)
    {
        Set<Integer> s3 = new HashSet<Integer>();
        Iterator<Integer> it = s1.iterator(); //iterator for s1
        while (it.hasNext()) { //while there are more elements in s1
            int x = it.next();
            if (s2.contains(x) && x % 2 == 0) { //if s2 also contains the element and element is even
                s3.add(x);
            }
        }
        return s3;
    }
    //all unique even vals between two sets
    public Set<Integer> evenUnion(Set<Integer> s1, Set<Integer> s2)
    {
        Set<Integer> s3 = new HashSet<Integer>();
        Iterator<Integer> it = s1.iterator(); //iterator for s1
        while (it.hasNext()) { //while there are more elements in s1
            int x = it.next();
            if (x % 2 == 0) { //if element is even
                s3.add(x);
            }
        }
        it = s2.iterator(); //iterator for s2
        while (it.hasNext()) {
            int x = it.next();
            if (x % 2 == 0) { //if element is even
                s3.add(x);
            }
        }
        return s3;
    }
    public static void main (String[] args) {
        HashTreeSet hts = new HashTreeSet();
    }
}