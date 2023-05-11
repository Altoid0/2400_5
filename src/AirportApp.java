//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class AirportApp {
    public static void main(String args[]) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        File airportsFile = new File("airports.csv");
        File distancesFile = new File("distances.csv");

        if (!airportsFile.exists() || !distancesFile.exists()) {
            if (!airportsFile.exists()) {
                throw new FileNotFoundException("airports.csv not found.");
            }
            if (!distancesFile.exists()) {
                throw new FileNotFoundException("distances.csv not found.");
            }
        }

        Scanner airports = new Scanner(airportsFile);
        Scanner distances = new Scanner(distancesFile);
    

        DictionaryInterface<String,String> airportDict = new HashedDictionary<>(); // TODO is this right?
        GraphInterface<String> airportGraph = new DirectedGraph<>();

        String[] airportArray;
        String[] distanceArray;

        while (airports.hasNextLine()) {
            airportArray = airports.nextLine().split(",");
            airportDict.add(airportArray[0], airportArray[1]);
            airportGraph.addVertex(airportArray[0]);
        }

        while (distances.hasNextLine()) {
            distanceArray = distances.nextLine().split(",");
            airportGraph.addEdge(distanceArray[0], distanceArray[1], Double.parseDouble(distanceArray[2]));
        }

        airports.close();
        distances.close();

        System.out.println("Airports v0.1 by T. Shah");



        System.out.print("Command? ");
        String command = input.nextLine();
        while (!command.equals("E")) {
            switch (command) {
                case "H": 
                    System.out.println("Q Query the airport information by entering the airport code.");
                    System.out.println("D Find the minimum distance between two airports.");
                    System.out.println("I Insert a new connection by entering two airport codes and distance.");
                    System.out.println("R Remove an existing connection by entering two airport codes.");
                    System.out.println("H Display this message.");
                    System.out.println("E Exit.");
                    break;
                case "Q":
                    System.out.print("Airport code: ");
                    String iata = input.nextLine();
                    System.out.println("Airport information: " + airportDict.getValue(iata.toUpperCase()));
                    break;
                case "D":
                    System.out.print("Airport codes: ");
                    String[] iatas = input.nextLine().split(" ");
                    StackInterface<String> path = new LinkedStack<>();
                    System.out.println("The minimum distance between " + airportDict.getValue(iatas[0]) + " and " + airportDict.getValue(iatas[1]) + " is " + airportGraph.getCheapestPath(iatas[0], iatas[1], path) + " through the route:");
                    while (!path.isEmpty()) {
                        System.out.println(airportDict.getValue(path.pop()));
                    }
                    break;
                case "I":
                    System.out.print("Airport codes and distance: ");
                    String[] iataDistance = input.nextLine().split(" ");
                    airportGraph.addEdge(iataDistance[0], iataDistance[1], Double.parseDouble(iataDistance[2])); // TODO add in error handling here
                    System.out.println("You have inserted a flight from " + airportDict.getValue(iataDistance[0]) + " to " + airportDict.getValue(iataDistance[1]) + " with a distance of " + iataDistance[2] + ".");
                    break;
                case "R":
                    System.out.print("Airport codes: ");
                    String[] iatasRemove = input.nextLine().split(" ");
                    airportGraph.removeEdge(iatasRemove[0], iatasRemove[1]); // TODO: Error handling
                    System.out.println("The connection from " + airportDict.getValue(iatasRemove[0]) + " to " + airportDict.getValue(iatasRemove[1]) + " removed.");
                    break;
            }
            System.out.print("Command? ");
            command = input.nextLine();
        }
    }
}

