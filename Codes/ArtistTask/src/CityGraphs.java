import java.util.HashSet;
import java.util.*;
public class CityGraphs {


    public static class City {
        private String name;
        private int id;
        public City(String name)
        {
            this.name = name;
            id = name.hashCode();
        }
        public String getName()
        {
            return name;
        }
        public int hashCode()
        {
            return id;
        }
        public String toString()
        {
            return name;
        }
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            City other = (City) o;
            return this.hashCode()==other.hashCode();
        }
    }


    public static class Edge {
        private City start, destination;
        private int dist, id;
        public Edge(City start, City destination, int dist)
        {
            this.start = start;
            this.destination = destination;
            this.dist = dist;
            id = start.hashCode() + destination.hashCode();
        }
        public City getStart()
        {
            return start;
        }
        public City getDestination()
        {
            return destination;
        }
        public int getDist()
        {
            return dist;
        }
        public int hashCode()
        {
            return id;
        }
        public String toString()
        {
            return start + " to " + destination + ": " + dist;
        }
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            Edge other = (Edge) o;
            return this.hashCode()==other.hashCode();
        }
    }
    public static class Graph
    {
        private HashSet<City> cities;
        private HashSet<Edge> edges;

        public Graph(HashSet<City> cities, HashSet<Edge> edges)
        {
            this.cities = cities;
            this.edges = edges;
        }
        public HashSet getCities()
        {
            return cities;
        }
        public HashSet getEdges()
        {
            return edges;
        }

    }


    public static class DijkstrasAlgo {
        ArrayList<City> cities;
        ArrayList<Edge> edges;
        HashSet<City> visited;
        HashSet<City> unvisited;
        HashMap<City, City> prev;
        HashMap<City, Integer> dist;
        public DijkstrasAlgo(Graph g)
        {
            this.cities =new ArrayList<City>(g.getCities());
            this.edges = new ArrayList<Edge>(g.getEdges());
        }
        public void createTravelsPaths(City source)
        {
            //initialize all the variables
            visited = new HashSet<City>(); //visited cities
            unvisited = new HashSet<City>(); //unvisited cities
            dist = new HashMap<City, Integer>(); //distances from the source to each city
            prev = new HashMap<City, City>(); //previous city in the path
            dist.put(source, 0); //distance from the source to itself is 0
            unvisited.add(source);
            while(unvisited.size()>0) //while there are still unvisited nodes
            {
                City u = getMin(unvisited); //get the node with the smallest distance
                visited.add(u); //add it to the visited nodes
                unvisited.remove(u); //remove it from the unvisited nodes
                findMinDist(u); //find the minimum distance from the node to all its neighbors
            }
        }
        public HashMap<City, City> getPrev()
        {
            return prev;
        }
        public void findMinDist(City tempCity)
        {
            ArrayList<City> adjacentNodes = getNeighbors(tempCity);
            for(City targetCity: adjacentNodes) //for each neighbor
            {
                int distance = getShortestDist(targetCity);
                //if the distance is less than the current distance
                if(distance > getShortestDist(tempCity)+getDistance(tempCity, targetCity))
                {
                    dist.put(targetCity, getShortestDist(tempCity)+getDistance(tempCity, targetCity)); //update the distance
                    prev.put(targetCity, tempCity);
                    unvisited.add(targetCity);
                }
            }
        }
        public int getDistance(City tempCity, City targetCity)
        {
            for(Edge e: edges) //iterate through edges
            {
                //if the edge is between the current node and the neighbor
                if ((e.getStart().equals(tempCity) && e.getDestination().equals(targetCity)) || (e.getStart().equals(targetCity) && e.getDestination().equals(tempCity)))
                {
                    return e.getDist(); //return the distance
                }
            }
            throw new RuntimeException("Should not happen");
        }
        public ArrayList<City> getNeighbors(City tempCity)
        {
            ArrayList<City> neighbors = new ArrayList<City>();
            //for each edge
            for(Edge e: edges)
            {
                //if the edge is between the current node and the neighbor
                if(e.getStart().equals(tempCity) && !wasVisited(e.getDestination()))
                {
                    neighbors.add(e.getDestination()); //add the neighbor to the list of neighbors
                }
                //if the edge is between the neighbor and the current node
                if(e.getDestination().equals(tempCity) && !wasVisited(e.getStart()))
                {
                    neighbors.add(e.getStart()); //add the neighbor to the list of neighbors
                }
            }
            return neighbors;
        }
        public City getMin(HashSet<City> cities)
        {
            City min = null;
            for(City c: cities) //for each city
            {
                if(min==null)
                {
                    min = c; //first city is the min
                }
                else
                {
                    //if the distance from the source to the current node is less than the distance from the source to the node with the smallest distance
                    if(getShortestDist(c)<getShortestDist(min))
                    {
                        min = c; //set the current node to the min
                    }
                }
            }
            return min;
        }
        public boolean wasVisited(City c)
        {
            return visited.contains(c);
        }
        public int getShortestDist(City destination)
        {
            Integer d = dist.get(destination); //get the distance from the source to the destination
            if(d==null)
            {
                return Integer.MAX_VALUE; //if the distance is null, return the max value
            }
            else
            {
                return d; //otherwise return the distance
            }
        }
        public ArrayList<City> getShortestPath(City target)
        {
            ArrayList<City> path = new ArrayList<City>(); //the path
            City step = target; //start at the target
            //check if a path does not exist, by seeing if nothing precedes it in the path
            if(prev.get(step)==null)
            {
                return null;
            }
            path.add(step); //add the target to the path
            //check if a path exists, by seeing if something precedes it in the path
            while(prev.get(step)!=null)
            {
                step = prev.get(step); //set the current node to the previous node
                path.add(step); //add it to the path
            }
            // Put it into the correct order
            Collections.reverse(path);
            return path;
        }
    }
}
