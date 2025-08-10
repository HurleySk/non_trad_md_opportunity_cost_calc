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
            System.out.println("2. Set Defaults");
            System.out.println("3. Exit\n");
            System.out.print("Enter: ");
            if (!globalInput.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                globalInput.nextLine(); // consume invalid input
                continue;
            }
            selection = globalInput.nextInt();
            globalInput.nextLine(); // consume newline after the number

            if (selection == 3) {
                shouldExit = true;
                System.out.println("Exiting...");
                globalInput.close();
            } else if (selection == 1) {
                calculateOpportunityCost();
            } else if (selection == 2) {
                setDefaults();
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

    private static void setDefaults() {
        print("\n=== SET DEFAULTS ===");
        print("Press Enter to keep the current value shown in [brackets]. Values marked % expect percentages.");

        // Helper lambdas
        java.util.function.BiFunction<String, Double, Double> askDouble = (label, current) -> {
            System.out.printf("%s [%.4f]: ", label, current);
            String line = globalInput.nextLine().trim();
            if (line.isEmpty()) return current;
            try { return Double.parseDouble(line); } catch (NumberFormatException e) { System.out.println("Invalid, keeping current."); return current; }
        };
        java.util.function.BiFunction<String, Integer, Integer> askInt = (label, current) -> {
            System.out.printf("%s [%d]: ", label, current);
            String line = globalInput.nextLine().trim();
            if (line.isEmpty()) return current;
            try { return Integer.parseInt(line); } catch (NumberFormatException e) { System.out.println("Invalid, keeping current."); return current; }
        };
        java.util.function.BiFunction<String, Boolean, Boolean> askBool = (label, current) -> {
            System.out.printf("%s [%s] (y/n): ", label, current ? "y" : "n");
            String line = globalInput.nextLine().trim().toLowerCase();
            if (line.isEmpty()) return current;
            if (line.startsWith("y")) return true;
            if (line.startsWith("n")) return false;
            System.out.println("Invalid, keeping current.");
            return current;
        };

        // Load current settings (or built-ins if not set yet)
        int medSchoolYears = DefaultsManager.getInt(DefaultsManager.Keys.MED_SCHOOL_YEARS, 4);
        int residencyYears = DefaultsManager.getInt(DefaultsManager.Keys.RESIDENCY_YEARS, 3);
        double residencySalary = DefaultsManager.getDouble(DefaultsManager.Keys.RESIDENCY_SALARY, 60000);
        double physicianStartingSalary = DefaultsManager.getDouble(DefaultsManager.Keys.PHYSICIAN_STARTING_SALARY, 250000);
        double retirementContributionRate = DefaultsManager.getDouble(DefaultsManager.Keys.RETIREMENT_CONTRIB_RATE, 0.15);
        double investmentReturnRate = DefaultsManager.getDouble(DefaultsManager.Keys.INVESTMENT_RETURN_RATE, 0.07);
        double inflationRate = DefaultsManager.getDouble(DefaultsManager.Keys.INFLATION_RATE, 0.03);
        boolean needsFellowship = DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_FELLOWSHIP, false);
        int fellowshipYears = DefaultsManager.getInt(DefaultsManager.Keys.FELLOWSHIP_YEARS, 1);
        double fellowshipSalary = DefaultsManager.getDouble(DefaultsManager.Keys.FELLOWSHIP_SALARY, 90000);
        boolean needsPostBacc = DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_POST_BACC, false);
        int postBaccYears = DefaultsManager.getInt(DefaultsManager.Keys.POST_BACC_YEARS, 2);
        double postBaccCost = DefaultsManager.getDouble(DefaultsManager.Keys.POST_BACC_COST, 60000);
        int yearsUntilPostBacc = DefaultsManager.getInt(DefaultsManager.Keys.YEARS_UNTIL_POST_BACC, 1);
        int yearsUntilMedSchool = DefaultsManager.getInt(DefaultsManager.Keys.YEARS_UNTIL_MED_SCHOOL, 1);
        int loanRepaymentYears = DefaultsManager.getInt(DefaultsManager.Keys.LOAN_REPAYMENT_YEARS, 10);
        double monthlyLoanPayment = DefaultsManager.getDouble(DefaultsManager.Keys.MONTHLY_LOAN_PAYMENT, 0);
        int retirementAge = DefaultsManager.getInt(DefaultsManager.Keys.RETIREMENT_AGE, 65);
        double annualRaise = DefaultsManager.getDouble(DefaultsManager.Keys.ANNUAL_RAISE, 0.03);
        double loanInterestRate = DefaultsManager.getDouble(DefaultsManager.Keys.LOAN_INTEREST_RATE, 0.06);

        // Ask user
        medSchoolYears = askInt.apply("Medical school years", medSchoolYears);
        residencyYears = askInt.apply("Residency years", residencyYears);
        residencySalary = askDouble.apply("Residency salary ($)", residencySalary);
        physicianStartingSalary = askDouble.apply("Physician starting salary ($)", physicianStartingSalary);
        retirementContributionRate = askDouble.apply("Retirement contribution rate (fraction, e.g., 0.15)", retirementContributionRate);
        investmentReturnRate = askDouble.apply("Investment return rate (fraction, e.g., 0.07)", investmentReturnRate);
        inflationRate = askDouble.apply("Inflation rate (fraction, e.g., 0.03)", inflationRate);
        needsFellowship = askBool.apply("Default: needs fellowship?", needsFellowship);
        fellowshipYears = askInt.apply("Fellowship years", fellowshipYears);
        fellowshipSalary = askDouble.apply("Fellowship salary ($)", fellowshipSalary);
        needsPostBacc = askBool.apply("Default: needs post-bacc?", needsPostBacc);
        postBaccYears = askInt.apply("Post-bacc years", postBaccYears);
        postBaccCost = askDouble.apply("Post-bacc total cost ($)", postBaccCost);
        yearsUntilPostBacc = askInt.apply("Years until post-bacc", yearsUntilPostBacc);
        yearsUntilMedSchool = askInt.apply("Years until med school", yearsUntilMedSchool);
        loanRepaymentYears = askInt.apply("Loan repayment years", loanRepaymentYears);
        monthlyLoanPayment = askDouble.apply("Monthly loan payment ($, 0=auto)", monthlyLoanPayment);
        retirementAge = askInt.apply("Retirement age", retirementAge);
        annualRaise = askDouble.apply("Annual raise (fraction, e.g., 0.03)", annualRaise);
        loanInterestRate = askDouble.apply("Loan interest rate (fraction, e.g., 0.06)", loanInterestRate);

        // Save
        DefaultsManager.setInt(DefaultsManager.Keys.MED_SCHOOL_YEARS, medSchoolYears);
        DefaultsManager.setInt(DefaultsManager.Keys.RESIDENCY_YEARS, residencyYears);
        DefaultsManager.setDouble(DefaultsManager.Keys.RESIDENCY_SALARY, residencySalary);
        DefaultsManager.setDouble(DefaultsManager.Keys.PHYSICIAN_STARTING_SALARY, physicianStartingSalary);
        DefaultsManager.setDouble(DefaultsManager.Keys.RETIREMENT_CONTRIB_RATE, retirementContributionRate);
        DefaultsManager.setDouble(DefaultsManager.Keys.INVESTMENT_RETURN_RATE, investmentReturnRate);
        DefaultsManager.setDouble(DefaultsManager.Keys.INFLATION_RATE, inflationRate);
        DefaultsManager.setBoolean(DefaultsManager.Keys.NEEDS_FELLOWSHIP, needsFellowship);
        DefaultsManager.setInt(DefaultsManager.Keys.FELLOWSHIP_YEARS, fellowshipYears);
        DefaultsManager.setDouble(DefaultsManager.Keys.FELLOWSHIP_SALARY, fellowshipSalary);
        DefaultsManager.setBoolean(DefaultsManager.Keys.NEEDS_POST_BACC, needsPostBacc);
        DefaultsManager.setInt(DefaultsManager.Keys.POST_BACC_YEARS, postBaccYears);
        DefaultsManager.setDouble(DefaultsManager.Keys.POST_BACC_COST, postBaccCost);
        DefaultsManager.setInt(DefaultsManager.Keys.YEARS_UNTIL_POST_BACC, yearsUntilPostBacc);
        DefaultsManager.setInt(DefaultsManager.Keys.YEARS_UNTIL_MED_SCHOOL, yearsUntilMedSchool);
        DefaultsManager.setInt(DefaultsManager.Keys.LOAN_REPAYMENT_YEARS, loanRepaymentYears);
        DefaultsManager.setDouble(DefaultsManager.Keys.MONTHLY_LOAN_PAYMENT, monthlyLoanPayment);
        DefaultsManager.setInt(DefaultsManager.Keys.RETIREMENT_AGE, retirementAge);
        DefaultsManager.setDouble(DefaultsManager.Keys.ANNUAL_RAISE, annualRaise);
        DefaultsManager.setDouble(DefaultsManager.Keys.LOAN_INTEREST_RATE, loanInterestRate);
        DefaultsManager.save();

        print("Defaults saved. They will be used as starting values for future calculations.\nPress Enter to continue...");
        globalInput.nextLine();
    }

}



