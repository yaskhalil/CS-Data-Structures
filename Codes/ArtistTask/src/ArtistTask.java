import java.util.HashMap;
import java.util.*;
import java.lang.*;
import java.io.*;

public class ArtistTask
{
    HashMap<Artist, HashSet<Edge>> artistMap;
    Artist start;
    Artist end;
    Boolean temp,temp2;
    Graph graph;
    Stack<Artist> currentPath;
    HashSet<Artist> visited;

    public ArtistTask()
    {
        artistMap  = new HashMap<Artist,HashSet<Edge>>();
        graph = new Graph();

        File file = new File("artistFile");

        try
        {
            BufferedReader reader=new BufferedReader(new FileReader(file));
            String text;
            while((text=reader.readLine())!=null)
            {
                String[] info = text.split(", ");

                Artist a1 = new Artist(info[0]);
                Artist a2 = new Artist(info[1]);

                graph.addArtist(a1);
                graph.addArtist(a2);


                graph.addEdge(a1,a2);
                graph.addEdge(a2,a1);

            }
        }catch(Exception IOException){}

        System.out.println("Edges -- Connecting artists with similar");

        for(Artist a1 : graph.artistSet)
        {
            start = a1;

            System.out.println("Starting Arist: " + start);

            for(Artist a2 : graph.artistSet)
            {
                end = a2;

                currentPath = new Stack<Artist>();
                visited = new HashSet<Artist>();

                dft(start, end);

            }
        }
    }

    public void dft(Artist currentArtist, Artist destination)
    {
        currentPath.push(currentArtist);

        visited.add(currentArtist);

        if(currentArtist == destination)
        {
            printCurrentPath();
        }

        for(Edge e : graph.edgeSet)
        {
            Artist artist = e.getArtist();
            Artist similar = e.getSimilar();

            if(visited.contains(artist) && !(visited.contains(similar)))
            {
                dft(similar,destination);
            }

            if(visited.contains(similar) && !(visited.contains(artist)))
            {
                dft(artist,destination);
            }
        }
    }

    public void printCurrentPath()
    {
        String output = "";

        while(!currentPath.isEmpty())
        {
            output = currentPath.pop() + output;

            if(!currentPath.isEmpty())
            {
                output = "  -  " + output;
            }
        }

        System.out.println("\t" + "\t" + output);
    }

    public class Artist
    {
        private String name;
        private int id;

        public Artist(String name)
        {
            this.name = name;
            this.id = name.hashCode();
        }

        public String getName()
        {
            return name;
        }

        public int getID()
        {
            return id;
        }

        public String toString()
        {
            return "Artist Name: " + name;
        }

        public boolean equals(Object obj)
        {
            if(obj == null || this.getClass() != obj.getClass())
                return false;

            else
            {
                Artist otherArtist = (Artist) obj;

                return this.hashCode() == otherArtist.hashCode();
            }
        }
    }


    public class Edge
    {
        private Artist artist;
        private Artist similar;
        private int uniqueID;

        public Edge(Artist artist, Artist similar)
        {
            this.artist = artist;
            this.similar = similar;
            uniqueID = artist.hashCode() + similar.hashCode();
        }

        public Artist getArtist()
        {
            return artist;
        }

        public Artist getSimilar()
        {
            return similar;
        }

        public int getUniqueID()
        {
            return uniqueID;
        }

        public String toString()
        {
            return artist + "issimilar to" + similar;
        }

        public boolean equals(Object obj)
        {
            if(obj == null || this.getClass() != obj.getClass())
                return false;

            else
            {

                Edge otherEdge = (Edge) obj;

                return this.hashCode() == otherEdge.hashCode();
            }

        }
    }



    public class Graph
    {
        private HashSet<Artist> artistSet = new HashSet<Artist>();
        private HashSet<Edge> edgeSet = new HashSet<Edge>();

        public HashSet<Artist> getArtistSet()
        {
            return artistSet;
        }

        public HashSet<Edge> getEdgeSet()
        {
            return edgeSet;
        }

        public void addArtist(Artist artist)
        {
            artistSet.add(artist);
        }

        public void addEdge(Artist a1,Artist a2)
        {
            edgeSet.add(new Edge(a1,a2));
        }
    }

    public static void main(String[] args)
    {
        ArtistTask app = new ArtistTask();
    }
}
