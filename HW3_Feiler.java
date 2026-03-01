import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //THIS SOLUTION SATISFIES QUERY A

        //get the file input from command line args
        File file = new File(args[0]);

        //we are going to store all of the lines in the file in an array list
        ArrayList<String> fileLines = new ArrayList<>();

        //create a new scanner using the file we input in the command line args
        try(Scanner fileScnr = new Scanner(file)){
            //while there are lines in the file add them to our arraylist of lines
            while(fileScnr.hasNextLine()){
                String fileLineData = fileScnr.nextLine();
                fileLines.add(fileLineData);
            }
        }catch(FileNotFoundException e){
            System.out.printf("%s not found!", args[0]);
        }

        //get the users query
        Scanner userQueryScanner = new Scanner(System.in);
        System.out.print("Query: ");
        String userQuery = userQueryScanner.nextLine();

        //this is here because the query provided in the homework pdf has weird quotation marks that breaks the query
        //but also this somehow breaks the code when it replaces it so i have no clue why or how this happens
        userQuery = userQuery.replace("“", "\"");
        userQuery = userQuery.replace("”", "\"");

        //we will have an array list that contains the "power words" in the query that will do certain actions
        ArrayList<String> queryPowerWords = new ArrayList<>();

        //build a sort of operation order tree based off the query using some regex
        for(String wordInQuery: userQuery.split(" ")){
            //System.out.println(wordInQuery.toLowerCase());
            //lower case the string to make this easier on recognition
            switch(wordInQuery.toLowerCase()){
                case "search":
                    queryPowerWords.add("select");
                    break;
                case "excluded":
                    queryPowerWords.add("not");
                    break;
                case "names":
                    queryPowerWords.add("Full_Name");
                    break;
                case "employees":
                    queryPowerWords.add("employees");
                    break;
                case "same":
                    queryPowerWords.add("same");
                    break;
                case "department":
                    queryPowerWords.add("Department");
                    break;
                default:
                    if(wordInQuery.toLowerCase().contains("\""))
                        queryPowerWords.add(wordInQuery);
            }
        }

        for(String powerWords: queryPowerWords)
            System.out.println(powerWords);
        System.out.println();

        String param = "";
        String param2 = "";
        String param3 = "";
        String conditions = "";

        for(int i = 0; i < queryPowerWords.size(); i++){
            System.out.println(queryPowerWords.get(i));
            if(queryPowerWords.get(i).equals("select")){
                param = queryPowerWords.get(i+1);
            }
            if(queryPowerWords.get(i).equals("same")){
                param2 = queryPowerWords.get(i+1);
            }
            if(queryPowerWords.get(i).contains("\"")){
                param3 = queryPowerWords.get(i);
                i++;
                while(queryPowerWords.get(i).contains("\"")) {
                    param3 += " " + queryPowerWords.get(i);
                    i++;
                }
            }
            if(queryPowerWords.get(i).equals("not"))
                conditions = "not";
        }

        System.out.println("\n" + param + " " + param2 + " " + param3 + " " + conditions);

        ArrayList<String> columns;
        columns = new ArrayList<>(Arrays.asList(fileLines.getFirst().split(",")));
        System.out.println("\n" + columns);

        String department = "";

        //find the department the employee input works in
        for(int i = 1; i < fileLines.size(); i++){
            if(fileLines.get(i).contains(param3.replace("\"", ""))){
                String[] line = fileLines.get(i).split(",");
                department = line[columns.indexOf("Department")];
            }
        }

        ArrayList<String> employeesFound = new ArrayList<>();

        //get the ones that meet the criteria
        for(int i = 1; i < fileLines.size(); i++){
            String[] lineData = fileLines.get(i).split(",");

            if(lineData[columns.indexOf(param2)].equals(department) && !lineData[columns.indexOf(param)].equals(param3.replace("\"", ""))){
                employeesFound.add(fileLines.get(i));
            }
        }

        for(String employees: employeesFound){
            System.out.println(Arrays.toString(employees.split(",")));
        }
        System.out.println("Found: " + employeesFound.size());
    }
}