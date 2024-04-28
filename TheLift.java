import java.util.*;
import java.io.*;
import java.lang.*;

public class TheLift{
    static int totalPassengers;
    public TheLift(){
        try{
            File file = new File("TheLiftFile.txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            String st;
            String[] pieces;
            while((st=input.readLine())!=null){
                pieces = st.split(" ");
                int floors = Integer.parseInt(pieces[1]);
                int[][] queues = new int[floors][0];
                for(int x=0; x<floors; x++){
                    st = input.readLine();
                    if(!st.equals("")){
                        pieces = st.split(",");
                        int[] floorQueue = new int[pieces.length];
                        for(int y=0; y<pieces.length; y++)
                            floorQueue[y] = Integer.parseInt(pieces[y]);
                        queues[x] = floorQueue;
                    }
                }
            st = input.readLine();
            pieces = st.split(" ");
            int cap = Integer.parseInt(pieces[1]);
            totalPassengers = 0;

            int[] stops = liftOperation(queues, cap);

            for(int r = 0; r < stops.length; r++){
                System.out.print(stops.length + " ");
                if(r<stops.length-1)
                    System.out.print(", ");
            }
            System.out.println();
        }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public static class Person{
        private int currFloor;
        private int targetFloor;
        public Person(int currFloor, int targetFloor){
            this.currFloor = currFloor;
            this.targetFloor = targetFloor;
        }
        public boolean isGoingUp(){
            return targetFloor > currFloor;
        }
        public boolean isGoingDown(){
            return targetFloor < currFloor;
        }
        public boolean ifGettingOff(){
            return targetFloor == currFloor;
        }
        public void setCurrFloor(int currFloor){
            this.currFloor = currFloor;
        }
    }

    public int[] liftOperation(final int[][] queues, final int capacity){
        ArrayList<ArrayList<Person>> queuesCopy = new ArrayList<ArrayList<Person>>();
        for(int x=0; x<queues.length; x++){
            ArrayList<Person> queue = new ArrayList<Person>();
            for(int y=0; y<queues[x].length; y++){
                Person person = new Person(x, queues[x][y]);
                queue.add(person);
                totalPassengers++;
            }
            queuesCopy.add(queue);
        }
        Lift lift = new Lift(queuesCopy, capacity);

        while(totalPassengers>0 || lift.getPassengers()){
            lift.stop();
            lift.move();
        }
        return lift.getStops();
    }

    public static class Lift {
        private ArrayList<ArrayList<Person>> queues;
        private int capacity;
        private int currentFloor;
        private boolean goingUp;
        private ArrayList<Person> passengers;
        private ArrayList<Integer> stops;

        public Lift(ArrayList<ArrayList<Person>> queues, int capacity) {
            this.queues = queues;
            this.capacity = capacity;
            this.currentFloor = 0;
            this.goingUp = true;
            this.passengers = new ArrayList<Person>();
            this.stops = new ArrayList<Integer>();
            this.stops.add(0);
        }

        public void move() {
            if(goingUp){
                if(goingUp){
                    currentFloor++;
                    if(currentFloor == queues.size()-1)
                        goingUp = !goingUp;
                }
                else{
                    currentFloor--;
                    if(currentFloor == 0)
                        goingUp = !goingUp;
                }
            }
        }

        public void stop() {
            for(int i = 0; i < passengers.size(); i++){
                Person person = passengers.get(i);
                person.setCurrFloor(currentFloor);
                if(person.ifGettingOff()){
                    passengers.remove(i);
                    i--;
                    stops.add(currentFloor);
                }
            }
            for(int i =0; i < queues.get(currentFloor).size(); i++){
                Person person = queues.get(currentFloor).get(i);
                if ((goingUp && person.isGoingUp()) || (!goingUp && person.isGoingDown())) {
                    if (passengers.size() < capacity) {
                        passengers.add(queues.get(currentFloor).remove(i));
                        i--;
                        totalPassengers--;
                    }
                    stops.add(currentFloor);
                }
                else {
                    queues.add(new ArrayList<>());
                }
            }
        }

        public boolean getPassengers() {
            return passengers.size() > 0;
        }

        public int[] getStops() {
            stops.add(0);
            for(int i=0; i< stops.size()-1;i++){
                if(stops.get(i) == stops.get(i+1)){
                    stops.remove(i);
                    i--;
                }
            }
            int[] arr = new int[stops.size()];
            for(int i =0; i < arr.length; i++)
                arr[i] = stops.get(i);
            return arr;
        }
    }
    public static void main(String[] args){
        TheLift app = new TheLift();
    }
}