import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Noah
 */

public class Knapsack {
    
    private int setSize, sackSize;
    private int[] weightArray, valueArray;
    private int[][] knapsackArray;
    private final String outputFilename = "knapsackOutput.txt";
    
    public static void main(String[] args) throws FileNotFoundException{
        if(args.length != 1 || !new File(args[0]).exists()){
            System.out.println("Error: Invalid Filename!");
            System.exit(0);
        }
        
        new Knapsack(new File(args[0]));
    }
    
    private Knapsack(File dataFile) throws FileNotFoundException{
        writeOutput(knapsackSolutionSet(dataFile));
    }
    
    private String knapsackSolutionSet(File data) throws FileNotFoundException{
        createKnapsackArray(data);
        for(int i = 1; i <= setSize; i++)
            for(int j = 0; j <= sackSize; j++){
                if(j - weightArray[i] >= 0){
                    int b = knapsackArray[i-1][j-weightArray[i]]+valueArray[i];
                    if(knapsackArray[i-1][j] < b){
                        knapsackArray[i][j] = b;
                        continue;
                    }
                }
                knapsackArray[i][j] = knapsackArray[i-1][j];
            }
        String solutionSet = "";
        for(int i = setSize, j = sackSize; j > 0;i--)
            if(knapsackArray[i][j] != knapsackArray[i-1][j]){
                solutionSet += valueArray[i]+", ";
                j -= weightArray[i];
            } 
        return solutionSet.replaceAll(", $", " "); //remove last comma
    }
    
    private void createKnapsackArray(File data) throws FileNotFoundException{
        Scanner sc = new Scanner(data);
        while(sc.findInLine("c ") != null)
            sc.nextLine();
        setSize = sc.nextInt();
        sackSize = sc.nextInt();
        weightArray = new int[setSize+1]; 
        valueArray = new int[setSize+1];
        knapsackArray = new int[setSize+1][sackSize+1];
        for(int i = 1; i <= setSize; i++)
            weightArray[i] = sc.nextInt();
        for(int i = 1; i <= setSize; i++)
            valueArray[i] = sc.nextInt();
    }
    
    private void writeOutput(String solutionSet) throws FileNotFoundException{
        PrintWriter writer = new PrintWriter(outputFilename);
        writer.println("------------------------Input------------------------");
        writer.println("Set Size: "+setSize);
        writer.println("Knapsack Size: "+sackSize);
        writer.println("Weight Set: "+printSet(weightArray));
        writer.println("Value Set: "+printSet(valueArray));
        writer.println("-----------------------Solution----------------------");
        writer.println("Optimal Value Set: { "+solutionSet+"}");
        writer.println("Max Value: "+knapsackArray[setSize][sackSize]);
        writer.flush();
    }
    
    private String printSet(int[] array){
        String setString = "{ ";
        for(int i = 1; i <= setSize; i++)
            setString += array[i]+", ";
        return setString.replaceAll(", $"," }");
    }
}