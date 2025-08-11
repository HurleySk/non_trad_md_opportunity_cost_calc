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
        
        // Ask user if they want to use defaults or enter custom values
        print("Would you like to:");
        print("1. Use current defaults and calculate immediately");
        print("2. Review and confirm/modify values");
        print("3. Go back to main menu");
        System.out.print("Enter choice (1-3): ");
        
        if (!globalInput.hasNextInt()) {
            print("Invalid input. Using defaults and calculating immediately.");
            globalInput.nextLine(); // consume invalid input
            calculateWithDefaults();
            return;
        }
        
        int choice = globalInput.nextInt();
        globalInput.nextLine(); // consume newline
        
        if (choice == 1) {
            // Use defaults directly
            calculateWithDefaults();
        } else if (choice == 2) {
            // Go through full input process
            calculateWithCustomInput();
        } else if (choice == 3) {
            // Go back to main menu
            return;
        } else {
            print("Invalid choice. Using defaults and calculating immediately.");
            calculateWithDefaults();
        }
    }

    private static void calculateWithDefaults() {
        print("\nUsing current defaults for calculation...");
        
        MDCalc calculator = new MDCalc();
        // Calculator already loads defaults in constructor, so we can proceed directly
        
        MDCalc.OpportunityCostResult result = calculator.calculateOpportunityCost();
        calculator.displayResults(result);
        
        print("\nPress Enter to continue...");
        globalInput.nextLine(); // consume remaining newline
        globalInput.nextLine(); // wait for user input
    }

    private static void calculateWithCustomInput() {
        print("\nPlease review and confirm/modify the following values:");
        
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
        
        // Show menu for default modification options
        while (true) {
            print("\nWhat would you like to do?");
            print("1. Modify all defaults (go through each one)");
            print("2. Modify specific default categories");
            print("3. View current defaults");
            print("4. Go back to main menu");
            System.out.print("Enter choice (1-4): ");
            
            if (!globalInput.hasNextInt()) {
                print("Invalid input. Please enter a number.");
                globalInput.nextLine(); // consume invalid input
                continue;
            }
            
            int choice = globalInput.nextInt();
            globalInput.nextLine(); // consume newline
            
            if (choice == 1) {
                modifyAllDefaults();
                break;
            } else if (choice == 2) {
                modifySpecificDefaults();
                break;
            } else if (choice == 3) {
                viewCurrentDefaults();
                continue; // Show menu again
            } else if (choice == 4) {
                return; // Go back to main menu
            } else {
                print("Invalid choice. Please enter 1-4.");
            }
        }
    }

    private static void modifyAllDefaults() {
        print("\n=== MODIFY ALL DEFAULTS ===");
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
        int currentAge = DefaultsManager.getInt(DefaultsManager.Keys.CURRENT_AGE, 30);
        double currentSalary = DefaultsManager.getDouble(DefaultsManager.Keys.CURRENT_SALARY, 75000);
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

        // Ask user for all values
        currentAge = askInt.apply("Current age", currentAge);
        currentSalary = askDouble.apply("Current annual salary ($)", currentSalary);
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

        // Save all values
        saveAllDefaults(currentAge, currentSalary, medSchoolYears, residencyYears, residencySalary,
                physicianStartingSalary, retirementContributionRate, investmentReturnRate, inflationRate,
                needsFellowship, fellowshipYears, fellowshipSalary, needsPostBacc, postBaccYears, postBaccCost,
                yearsUntilPostBacc, yearsUntilMedSchool, loanRepaymentYears, monthlyLoanPayment,
                retirementAge, annualRaise, loanInterestRate);
    }

    private static void modifySpecificDefaults() {
        print("\n=== MODIFY SPECIFIC DEFAULTS ===");
        
        while (true) {
            print("\nWhich category would you like to modify?");
            print("1. Personal Information (age, salary, raises)");
            print("2. Training Path (post-bacc, med school, residency, fellowship)");
            print("3. Financial Assumptions (retirement, investment, inflation)");
            print("4. Loan Settings (interest rates, repayment terms)");
            print("5. Go back to defaults menu");
            System.out.print("Enter choice (1-5): ");
            
            if (!globalInput.hasNextInt()) {
                print("Invalid input. Please enter a number.");
                globalInput.nextLine(); // consume invalid input
                continue;
            }
            
            int choice = globalInput.nextInt();
            globalInput.nextLine(); // consume newline
            
            if (choice == 1) {
                modifyPersonalDefaults();
                break;
            } else if (choice == 2) {
                modifyTrainingDefaults();
                break;
            } else if (choice == 3) {
                modifyFinancialDefaults();
                break;
            } else if (choice == 4) {
                modifyLoanDefaults();
                break;
            } else if (choice == 5) {
                return; // Go back to defaults menu
            } else {
                print("Invalid choice. Please enter 1-5.");
            }
        }
    }

    private static void viewCurrentDefaults() {
        print("\n=== CURRENT DEFAULTS ===");
        print("Personal Information:");
        print("  Current age: " + DefaultsManager.getInt(DefaultsManager.Keys.CURRENT_AGE, 30) + " years");
        print("  Current salary: $" + String.format("%,.0f", DefaultsManager.getDouble(DefaultsManager.Keys.CURRENT_SALARY, 75000)));
        print("  Annual raise: " + String.format("%.1f", DefaultsManager.getDouble(DefaultsManager.Keys.ANNUAL_RAISE, 0.03) * 100) + "%");
        print("  Inflation rate: " + String.format("%.1f", DefaultsManager.getDouble(DefaultsManager.Keys.INFLATION_RATE, 0.03) * 100) + "%");
        
        print("\nTraining Path:");
        print("  Post-bacc needed: " + (DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_POST_BACC, false) ? "Yes" : "No"));
        if (DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_POST_BACC, false)) {
            print("  Post-bacc years: " + DefaultsManager.getInt(DefaultsManager.Keys.POST_BACC_YEARS, 2));
            print("  Post-bacc cost: $" + String.format("%,.0f", DefaultsManager.getDouble(DefaultsManager.Keys.POST_BACC_COST, 60000)));
        }
        print("  Medical school years: " + DefaultsManager.getInt(DefaultsManager.Keys.MED_SCHOOL_YEARS, 4));
        print("  Residency years: " + DefaultsManager.getInt(DefaultsManager.Keys.RESIDENCY_YEARS, 3));
        print("  Fellowship needed: " + (DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_FELLOWSHIP, false) ? "Yes" : "No"));
        if (DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_FELLOWSHIP, false)) {
            print("  Fellowship years: " + DefaultsManager.getInt(DefaultsManager.Keys.FELLOWSHIP_YEARS, 1));
        }
        
        print("\nFinancial Assumptions:");
        print("  Retirement contribution: " + String.format("%.1f", DefaultsManager.getDouble(DefaultsManager.Keys.RETIREMENT_CONTRIB_RATE, 0.15) * 100) + "%");
        print("  Investment return: " + String.format("%.1f", DefaultsManager.getDouble(DefaultsManager.Keys.INVESTMENT_RETURN_RATE, 0.07) * 100) + "%");
        print("  Retirement age: " + DefaultsManager.getInt(DefaultsManager.Keys.RETIREMENT_AGE, 65) + " years");
        
        print("\nLoan Settings:");
        print("  Loan interest rate: " + String.format("%.1f", DefaultsManager.getDouble(DefaultsManager.Keys.LOAN_INTEREST_RATE, 0.06) * 100) + "%");
        print("  Loan repayment years: " + DefaultsManager.getInt(DefaultsManager.Keys.LOAN_REPAYMENT_YEARS, 10));
        
        print("\nPress Enter to continue...");
        globalInput.nextLine();
    }

    private static void saveAllDefaults(int currentAge, double currentSalary, int medSchoolYears, int residencyYears, 
            double residencySalary, double physicianStartingSalary, double retirementContributionRate, 
            double investmentReturnRate, double inflationRate, boolean needsFellowship, int fellowshipYears, 
            double fellowshipSalary, boolean needsPostBacc, int postBaccYears, double postBaccCost, 
            int yearsUntilPostBacc, int yearsUntilMedSchool, int loanRepaymentYears, double monthlyLoanPayment, 
            int retirementAge, double annualRaise, double loanInterestRate) {
        
        // Save all values
        DefaultsManager.setInt(DefaultsManager.Keys.CURRENT_AGE, currentAge);
        DefaultsManager.setDouble(DefaultsManager.Keys.CURRENT_SALARY, currentSalary);
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

    private static void modifyPersonalDefaults() {
        print("\n=== MODIFY PERSONAL DEFAULTS ===");
        print("Press Enter to keep the current value shown in [brackets].");
        
        int currentAge = DefaultsManager.getInt(DefaultsManager.Keys.CURRENT_AGE, 30);
        double currentSalary = DefaultsManager.getDouble(DefaultsManager.Keys.CURRENT_SALARY, 75000);
        double annualRaise = DefaultsManager.getDouble(DefaultsManager.Keys.ANNUAL_RAISE, 0.03);
        double inflationRate = DefaultsManager.getDouble(DefaultsManager.Keys.INFLATION_RATE, 0.03);
        
        System.out.printf("Current age [%d]: ", currentAge);
        String input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { currentAge = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Current annual salary [$%.0f]: $", currentSalary);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { currentSalary = Double.parseDouble(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Annual raise [%.1f%%]: ", annualRaise * 100);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { annualRaise = Double.parseDouble(input) / 100.0; } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Inflation rate [%.1f%%]: ", inflationRate * 100);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { inflationRate = Double.parseDouble(input) / 100.0; } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        // Save personal defaults
        DefaultsManager.setInt(DefaultsManager.Keys.CURRENT_AGE, currentAge);
        DefaultsManager.setDouble(DefaultsManager.Keys.CURRENT_SALARY, currentSalary);
        DefaultsManager.setDouble(DefaultsManager.Keys.ANNUAL_RAISE, annualRaise);
        DefaultsManager.setDouble(DefaultsManager.Keys.INFLATION_RATE, inflationRate);
        DefaultsManager.save();
        
        print("Personal defaults saved. Press Enter to continue...");
        globalInput.nextLine();
    }

    private static void modifyTrainingDefaults() {
        print("\n=== MODIFY TRAINING DEFAULTS ===");
        print("Press Enter to keep the current value shown in [brackets].");
        
        boolean needsPostBacc = DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_POST_BACC, false);
        int postBaccYears = DefaultsManager.getInt(DefaultsManager.Keys.POST_BACC_YEARS, 2);
        double postBaccCost = DefaultsManager.getDouble(DefaultsManager.Keys.POST_BACC_COST, 60000);
        int yearsUntilPostBacc = DefaultsManager.getInt(DefaultsManager.Keys.YEARS_UNTIL_POST_BACC, 1);
        int yearsUntilMedSchool = DefaultsManager.getInt(DefaultsManager.Keys.YEARS_UNTIL_MED_SCHOOL, 1);
        int medSchoolYears = DefaultsManager.getInt(DefaultsManager.Keys.MED_SCHOOL_YEARS, 4);
        int residencyYears = DefaultsManager.getInt(DefaultsManager.Keys.RESIDENCY_YEARS, 3);
        double residencySalary = DefaultsManager.getDouble(DefaultsManager.Keys.RESIDENCY_SALARY, 60000);
        boolean needsFellowship = DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_FELLOWSHIP, false);
        int fellowshipYears = DefaultsManager.getInt(DefaultsManager.Keys.FELLOWSHIP_YEARS, 1);
        double fellowshipSalary = DefaultsManager.getDouble(DefaultsManager.Keys.FELLOWSHIP_SALARY, 90000);
        double physicianStartingSalary = DefaultsManager.getDouble(DefaultsManager.Keys.PHYSICIAN_STARTING_SALARY, 250000);
        
        System.out.printf("Needs post-bacc [%s] (y/n): ", needsPostBacc ? "y" : "n");
        String input = globalInput.nextLine().trim().toLowerCase();
        if (!input.isEmpty()) {
            needsPostBacc = input.startsWith("y");
        }
        
        if (needsPostBacc) {
            System.out.printf("Post-bacc years [%d]: ", postBaccYears);
            input = globalInput.nextLine().trim();
            if (!input.isEmpty()) {
                try { postBaccYears = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
            }
            
            System.out.printf("Post-bacc cost [$%.0f]: $", postBaccCost);
            input = globalInput.nextLine().trim();
            if (!input.isEmpty()) {
                try { postBaccCost = Double.parseDouble(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
            }
            
            System.out.printf("Years until post-bacc [%d]: ", yearsUntilPostBacc);
            input = globalInput.nextLine().trim();
            if (!input.isEmpty()) {
                try { yearsUntilPostBacc = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
            }
        }
        
        System.out.printf("Years until med school [%d]: ", yearsUntilMedSchool);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { yearsUntilMedSchool = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Medical school years [%d]: ", medSchoolYears);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { medSchoolYears = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Residency years [%d]: ", residencyYears);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { residencyYears = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Residency salary [$%.0f]: $", residencySalary);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { residencySalary = Double.parseDouble(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Needs fellowship [%s] (y/n): ", needsFellowship ? "y" : "n");
        input = globalInput.nextLine().trim().toLowerCase();
        if (!input.isEmpty()) {
            needsFellowship = input.startsWith("y");
        }
        
        if (needsFellowship) {
            System.out.printf("Fellowship years [%d]: ", fellowshipYears);
            input = globalInput.nextLine().trim();
            if (!input.isEmpty()) {
                try { fellowshipYears = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
            }
            
            System.out.printf("Fellowship salary [$%.0f]: $", fellowshipSalary);
            input = globalInput.nextLine().trim();
            if (!input.isEmpty()) {
                try { fellowshipSalary = Double.parseDouble(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
            }
        }
        
        System.out.printf("Physician starting salary [$%.0f]: $", physicianStartingSalary);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { physicianStartingSalary = Double.parseDouble(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        // Save training defaults
        DefaultsManager.setBoolean(DefaultsManager.Keys.NEEDS_POST_BACC, needsPostBacc);
        DefaultsManager.setInt(DefaultsManager.Keys.POST_BACC_YEARS, postBaccYears);
        DefaultsManager.setDouble(DefaultsManager.Keys.POST_BACC_COST, postBaccCost);
        DefaultsManager.setInt(DefaultsManager.Keys.YEARS_UNTIL_POST_BACC, yearsUntilPostBacc);
        DefaultsManager.setInt(DefaultsManager.Keys.YEARS_UNTIL_MED_SCHOOL, yearsUntilMedSchool);
        DefaultsManager.setInt(DefaultsManager.Keys.MED_SCHOOL_YEARS, medSchoolYears);
        DefaultsManager.setInt(DefaultsManager.Keys.RESIDENCY_YEARS, residencyYears);
        DefaultsManager.setDouble(DefaultsManager.Keys.RESIDENCY_SALARY, residencySalary);
        DefaultsManager.setBoolean(DefaultsManager.Keys.NEEDS_FELLOWSHIP, needsFellowship);
        DefaultsManager.setInt(DefaultsManager.Keys.FELLOWSHIP_YEARS, fellowshipYears);
        DefaultsManager.setDouble(DefaultsManager.Keys.FELLOWSHIP_SALARY, fellowshipSalary);
        DefaultsManager.setDouble(DefaultsManager.Keys.PHYSICIAN_STARTING_SALARY, physicianStartingSalary);
        DefaultsManager.save();
        
        print("Training defaults saved. Press Enter to continue...");
        globalInput.nextLine();
    }

    private static void modifyFinancialDefaults() {
        print("\n=== MODIFY FINANCIAL DEFAULTS ===");
        print("Press Enter to keep the current value shown in [brackets].");
        
        double retirementContributionRate = DefaultsManager.getDouble(DefaultsManager.Keys.RETIREMENT_CONTRIB_RATE, 0.15);
        double investmentReturnRate = DefaultsManager.getDouble(DefaultsManager.Keys.INVESTMENT_RETURN_RATE, 0.07);
        int retirementAge = DefaultsManager.getInt(DefaultsManager.Keys.RETIREMENT_AGE, 65);
        
        System.out.printf("Retirement contribution rate [%.1f%%]: ", retirementContributionRate * 100);
        String input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { retirementContributionRate = Double.parseDouble(input) / 100.0; } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Investment return rate [%.1f%%]: ", investmentReturnRate * 100);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { investmentReturnRate = Double.parseDouble(input) / 100.0; } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Retirement age [%d]: ", retirementAge);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { retirementAge = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        // Save financial defaults
        DefaultsManager.setDouble(DefaultsManager.Keys.RETIREMENT_CONTRIB_RATE, retirementContributionRate);
        DefaultsManager.setDouble(DefaultsManager.Keys.INVESTMENT_RETURN_RATE, investmentReturnRate);
        DefaultsManager.setInt(DefaultsManager.Keys.RETIREMENT_AGE, retirementAge);
        DefaultsManager.save();
        
        print("Financial defaults saved. Press Enter to continue...");
        globalInput.nextLine();
    }

    private static void modifyLoanDefaults() {
        print("\n=== MODIFY LOAN DEFAULTS ===");
        print("Press Enter to keep the current value shown in [brackets].");
        
        double loanInterestRate = DefaultsManager.getDouble(DefaultsManager.Keys.LOAN_INTEREST_RATE, 0.06);
        int loanRepaymentYears = DefaultsManager.getInt(DefaultsManager.Keys.LOAN_REPAYMENT_YEARS, 10);
        double monthlyLoanPayment = DefaultsManager.getDouble(DefaultsManager.Keys.MONTHLY_LOAN_PAYMENT, 0);
        
        System.out.printf("Loan interest rate [%.1f%%]: ", loanInterestRate * 100);
        String input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { loanInterestRate = Double.parseDouble(input) / 100.0; } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Loan repayment years [%d]: ", loanRepaymentYears);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { loanRepaymentYears = Integer.parseInt(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        System.out.printf("Monthly loan payment [$%.0f]: $", monthlyLoanPayment);
        input = globalInput.nextLine().trim();
        if (!input.isEmpty()) {
            try { monthlyLoanPayment = Double.parseDouble(input); } catch (NumberFormatException e) { print("Invalid, keeping current."); }
        }
        
        // Save loan defaults
        DefaultsManager.setDouble(DefaultsManager.Keys.LOAN_INTEREST_RATE, loanInterestRate);
        DefaultsManager.setInt(DefaultsManager.Keys.LOAN_REPAYMENT_YEARS, loanRepaymentYears);
        DefaultsManager.setDouble(DefaultsManager.Keys.MONTHLY_LOAN_PAYMENT, monthlyLoanPayment);
        DefaultsManager.save();
        
        print("Loan defaults saved. Press Enter to continue...");
        globalInput.nextLine();
    }
}



