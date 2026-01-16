/*
 * Contestant ID: ##########
 */

/** IMPORTS */
import java.io.File;
import java.io.FileNotFoundException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    /** VARIABLES */

    public static ArrayList<String> Data = new ArrayList<String>();
    public static ArrayList<String> Valid_Data = new ArrayList<String>();
    public static File File = new File("data/input340.txt");

    public static int Total_Processed = 0;
    public static int Invalid_Processed = 0;

    /** MAIN METHOD */
    public static void main (String[] args)
    {
        Get_Data();         // Get the data from the file: "input340.txt"
        Validate_Data();    // Validate the data
        Output_Data();      // output the date to file "output340.txt" and output the counts to the user
    }

    /*
     * Method: Get_Data
     * Usage: Sequentially reads every ISBN number of the file for the program to validate later
     */
    public static void Get_Data ()
    {
        try (Scanner File_Reader = new Scanner(File)) {
            /* While loop to get every line in the file safely */
            while (File_Reader.hasNext())
            {
                String line = File_Reader.nextLine();           // Get line in file

                /* For loop to remove the hyphens to get a raw line */
                for (int i = 0; i < line.length(); i++)
                {
                    String character = line.substring(i, i+1);

                    /* Remove hyphens */
                    if (character.equals("-")) {
                        line = line.substring(0, i) + line.substring(i+1);
                    }
                }

                Data.add(line); // Add the line to the Data to validate
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Error: %s%n", e);
        }
    }

    /*
     * Method: Validate_Data
     * Usage: Validates every ISBN number
     */
    public static void Validate_Data ()
    {
        for (String line : Data)
        {
            int total = 0;          // Total Product (sum of products)
            int remainder = 0;      // Remainder of Product (Valid == 0)

            for (int j = 0; j < line.length(); j++) {
                String character = line.substring(j, j + 1);

                /* If number is X then: number = 10; else: number = character */
                int number = (character.equals("X")) ? 10 : Integer.parseInt(character);
                int weight = 10 - j;        // weight based on index

                total += (number * weight); // Add number with weight to the total
            }

            remainder = (total % 11);   // Modulus the total by 11 to get remainder

            Total_Processed += 1;       // Add count to total processed

            if (remainder == 0) {
                Valid_Data.add(line);   // Add valid number to Valid_Data (Array_List)
            } else {
                Invalid_Processed += 1; // Add count to Invalid Processed
            }
        }
    }

    /*
     * Method: Output_Data
     * Usage: Output count of Total Processed Records and Invalid Processed
     *        Add Valid ISBN numbers to output340.txt (Create File)
     */
    public static void Output_Data ()
    {
        File Output_File = new File("data/output340.txt");  // Create File for Valid ISBN numbers

        /* Go through each line to add hyphens backs */
        for (String Raw_Line : Valid_Data)                           // Go through every line of the Valid Data
        {
            int index = Valid_Data.indexOf(Raw_Line);                // Get index of line

            String line = "";

            /* Update line with hyphens */
            line = Raw_Line.substring(0, 4) + "-" + Raw_Line.substring(4, 8) + "-" + Raw_Line.substring(8);

            Valid_Data.set(index, line);                              // Replace the old line in the data with the new line
        }

        /* Make a BufferedWriter to write to the file: "output340.txt" */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/output340.txt"))) {
            for (String line : Valid_Data)
            {
                writer.write(line); // Write the line
                writer.newLine();   // Go to the next line
            }
        } catch (IOException e) {
            System.out.printf("Error: %s%n", e);
        }

        /**
          * Prints:  Records Processed:  #
          *          Invalid ISBN's:     #
         */

        System.out.printf("Records Processed:\t%d\nInvalid ISBN's:\t\t%d\n"
                , Total_Processed, Invalid_Processed);
    }
}
