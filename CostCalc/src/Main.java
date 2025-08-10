import java.util.Scanner;

public class Main {
    private static Scanner globalInput = new Scanner(System.in);
    
    public static void main(String[] args) {
        //Declare vars for menu
        boolean shouldExit = false;
        int selection;

        while (!shouldExit) {
           //Load menu
            System.out.println("\nWelcome! Please enter the number associated with one of the options below to start.\n");
            System.out.println("1. Calculate Opportunity Cost");
            System.out.println("2. Exit\n");
            System.out.print("Enter: ");
            selection = globalInput.nextInt();

            if (selection == 2) {
                shouldExit = true;
                System.out.println("Exiting...");
                globalInput.close();
            } else if (selection == 1) {
                calculateOpportunityCost();
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    //Creating this so I don't have to type out the whole thing
    private static void print(String printThis) {
        System.out.println(printThis);
    }

    private static void calculateOpportunityCost() {
        print("\n=== MEDICAL SCHOOL OPPORTUNITY COST CALCULATOR ===");
        print("This calculator will analyze the total financial impact of pursuing medical school");
        print("including lost wages, retirement savings, and loan costs.\n");
        
        MDCalc calculator = new MDCalc();
        calculator.collectUserInput(globalInput);
        
        MDCalc.OpportunityCostResult result = calculator.calculateOpportunityCost();
        calculator.displayResults(result);
        
        print("\nPress Enter to continue...");
        globalInput.nextLine(); // consume remaining newline
        globalInput.nextLine(); // wait for user input
    }

}



