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
    

        DictionaryInterface<String,String> airportDict = new HashedDictionary<>();
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
                    
                    String airportName = airportDict.getValue(iata.toUpperCase());
                    if (airportName == null) {
                        System.out.println("Airport code unknown, " + iata.toUpperCase() + " not found.");
                    } else {
                        System.out.println("Airport information: " + airportName);
                    }
                    break;
                case "D":
                    System.out.print("Airport codes: ");
                    String[] iatas = input.nextLine().split(" ");
                    if (iatas.length != 2 || airportDict.getValue(iatas[0].toUpperCase()) == null || airportDict.getValue(iatas[1].toUpperCase()) == null) {
                        System.out.println("Airport code unknown, one or more of the airports does not exist.");
                    } else {
                        StackInterface<String> path = new LinkedStack<>();
                        Double cost = airportGraph.getCheapestPath(iatas[0], iatas[1], path);
                        if (cost != -1) {
                            System.out.println("The minimum distance between " + airportDict.getValue(iatas[0]) + " and " + airportDict.getValue(iatas[1]) + " is " + cost + " through the route:");
                            while (!path.isEmpty()) {
                                System.out.println(airportDict.getValue(path.pop()));
                            }
                        } else {
                            System.out.println("There is no path between " + airportDict.getValue(iatas[0]) + " and " + airportDict.getValue(iatas[1]) + ".");
                        }
                    }
                    
                    break;
                case "I":
                    System.out.print("Airport codes and distance: ");
                    String[] iataDistance = input.nextLine().split(" ");
                    if (iataDistance.length != 3 || airportDict.getValue(iataDistance[0]) == null || airportDict.getValue(iataDistance[1]) == null || Double.parseDouble(iataDistance[2]) < 0) {
                        System.out.println("Airport code unknown, invalid edge input.");
                    } else {
                        if (!airportGraph.hasEdge(iataDistance[0], iataDistance[1])) {
                            airportGraph.addEdge(iataDistance[0], iataDistance[1], Double.parseDouble(iataDistance[2])); // TODO add in error handling here
                            System.out.println("You have inserted a flight from " + airportDict.getValue(iataDistance[0]) + " to " + airportDict.getValue(iataDistance[1]) + " with a distance of " + iataDistance[2] + ".");
                        } else {
                            System.out.println("Skipping addition, the connection from " + airportDict.getValue(iataDistance[0]) + " to " + airportDict.getValue(iataDistance[1]) + " already exists.");
                        }
                    }
                    break;
                case "R":
                    System.out.print("Airport codes: ");
                    String[] iatasRemove = input.nextLine().split(" ");
                    if (iatasRemove.length != 2 || airportDict.getValue(iatasRemove[0]) == null || airportDict.getValue(iatasRemove[1]) == null) {
                        System.out.println("Airport code unknown, one or more of the airport IATAs does not exist.");
                    } else {
                        if (airportGraph.hasEdge(iatasRemove[0], iatasRemove[1])) {
                            airportGraph.removeEdge(iatasRemove[0], iatasRemove[1]); // TODO: Error handling
                            System.out.println("The connection from " + airportDict.getValue(iatasRemove[0]) + " to " + airportDict.getValue(iatasRemove[1]) + " has been removed.");
                        } else {
                            System.out.println("Airports not connected, the connection from " + airportDict.getValue(iatasRemove[0]) + " to " + airportDict.getValue(iatasRemove[1]) + " does not exist.");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid Command");
            }
            System.out.print("Command? ");
            command = input.nextLine();
        }
    }
}

