import java.util.Scanner;

public class MDCalc {
    private double currentSalary;
    private double annualRaise;
    private double loanInterestRate;
    private double totalLoans;
    private double retirementContributionRate;
    private double investmentReturnRate;
    private double inflationRate;
    private int medSchoolYears;
    private int residencyYears;
    private double residencySalary;
    private double physicianStartingSalary;
    private int currentAge;
    private boolean needsFellowship;
    private int fellowshipYears;
    private double fellowshipSalary;
    private boolean needsPostBacc;
    private int postBaccYears;
    private double postBaccCost;
    private int yearsUntilPostBacc;
    private int yearsUntilMedSchool;
    private int loanRepaymentYears;
    private double monthlyLoanPayment;

    // New: user-selected retirement age and alignment option
    private int retirementAge;
    private boolean alignRepaymentToRetirement;

    public MDCalc() {
        // Built-in defaults
        this.medSchoolYears = 4;
        this.residencyYears = 3;
        this.residencySalary = 60000;
        this.physicianStartingSalary = 250000;
        this.retirementContributionRate = 0.15;
        this.investmentReturnRate = 0.07;
        this.inflationRate = 0.03;
        this.annualRaise = 0.03; // Add missing built-in default
        this.loanInterestRate = 0.06; // Add missing built-in default
        this.needsFellowship = false;
        this.fellowshipYears = 1;
        this.fellowshipSalary = 90000;
        this.needsPostBacc = false;
        this.postBaccYears = 2;
        this.postBaccCost = 60000;
        this.yearsUntilPostBacc = 1;
        this.yearsUntilMedSchool = 1;
        this.loanRepaymentYears = 10;
        this.monthlyLoanPayment = 0; // Will be calculated if not provided
        this.retirementAge = 65;     // Default retirement age (now configurable)
        this.alignRepaymentToRetirement = false;

        // Override with saved defaults (if present)
        try {
            this.medSchoolYears = DefaultsManager.getInt(DefaultsManager.Keys.MED_SCHOOL_YEARS, this.medSchoolYears);
            this.residencyYears = DefaultsManager.getInt(DefaultsManager.Keys.RESIDENCY_YEARS, this.residencyYears);
            this.residencySalary = DefaultsManager.getDouble(DefaultsManager.Keys.RESIDENCY_SALARY, this.residencySalary);
            this.physicianStartingSalary = DefaultsManager.getDouble(DefaultsManager.Keys.PHYSICIAN_STARTING_SALARY, this.physicianStartingSalary);
            this.retirementContributionRate = DefaultsManager.getDouble(DefaultsManager.Keys.RETIREMENT_CONTRIB_RATE, this.retirementContributionRate);
            this.investmentReturnRate = DefaultsManager.getDouble(DefaultsManager.Keys.INVESTMENT_RETURN_RATE, this.investmentReturnRate);
            this.inflationRate = DefaultsManager.getDouble(DefaultsManager.Keys.INFLATION_RATE, this.inflationRate);
            this.needsFellowship = DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_FELLOWSHIP, this.needsFellowship);
            this.fellowshipYears = DefaultsManager.getInt(DefaultsManager.Keys.FELLOWSHIP_YEARS, this.fellowshipYears);
            this.fellowshipSalary = DefaultsManager.getDouble(DefaultsManager.Keys.FELLOWSHIP_SALARY, this.fellowshipSalary);
            this.needsPostBacc = DefaultsManager.getBoolean(DefaultsManager.Keys.NEEDS_POST_BACC, this.needsPostBacc);
            this.postBaccYears = DefaultsManager.getInt(DefaultsManager.Keys.POST_BACC_YEARS, this.postBaccYears);
            this.postBaccCost = DefaultsManager.getDouble(DefaultsManager.Keys.POST_BACC_COST, this.postBaccCost);
            this.yearsUntilPostBacc = DefaultsManager.getInt(DefaultsManager.Keys.YEARS_UNTIL_POST_BACC, this.yearsUntilPostBacc);
            this.yearsUntilMedSchool = DefaultsManager.getInt(DefaultsManager.Keys.YEARS_UNTIL_MED_SCHOOL, this.yearsUntilMedSchool);
            this.loanRepaymentYears = DefaultsManager.getInt(DefaultsManager.Keys.LOAN_REPAYMENT_YEARS, this.loanRepaymentYears);
            this.monthlyLoanPayment = DefaultsManager.getDouble(DefaultsManager.Keys.MONTHLY_LOAN_PAYMENT, this.monthlyLoanPayment);
            this.retirementAge = DefaultsManager.getInt(DefaultsManager.Keys.RETIREMENT_AGE, this.retirementAge);
            this.annualRaise = DefaultsManager.getDouble(DefaultsManager.Keys.ANNUAL_RAISE, this.annualRaise);
            this.loanInterestRate = DefaultsManager.getDouble(DefaultsManager.Keys.LOAN_INTEREST_RATE, this.loanInterestRate);
        } catch (Throwable ignored) {
            // If defaults file is missing or malformed, just keep built-in defaults.
        }
    }

    public void collectUserInput(Scanner input) {
        System.out.print("Enter your current age: ");
        this.currentAge = input.nextInt();
        if (this.currentAge < 0) this.currentAge = 0;

        System.out.print("Enter your current annual salary: $");
        this.currentSalary = input.nextDouble();
        if (this.currentSalary < 0) this.currentSalary = 0;

        System.out.printf("Will you need to do a post-bacc program? [%s] (y/n): ", this.needsPostBacc ? "y" : "n");
        String postBaccResponse = input.next().toLowerCase();
        this.needsPostBacc = postBaccResponse.startsWith("y");

        if (this.needsPostBacc) {
            System.out.printf("How many years until you start post-bacc? [%d] (enter 0 to keep default): ", this.yearsUntilPostBacc);
            int tmpYearsUntilPostBacc = input.nextInt();
            if (tmpYearsUntilPostBacc > 0) this.yearsUntilPostBacc = tmpYearsUntilPostBacc;

            System.out.printf("How many years will post-bacc take? [%d] (enter 0 to keep default): ", this.postBaccYears);
            int tmpPostBaccYears = input.nextInt();
            if (tmpPostBaccYears > 0) this.postBaccYears = tmpPostBaccYears;

            System.out.printf("What is the total cost of post-bacc program? [$%.0f] (enter 0 to keep default): $", this.postBaccCost);
            double tmpPostBaccCost = input.nextDouble();
            if (tmpPostBaccCost > 0) this.postBaccCost = tmpPostBaccCost;
            if (this.postBaccCost < 0) this.postBaccCost = 0;

            System.out.printf("How many years after post-bacc until medical school? [%d] (enter 0 to keep default): ", this.yearsUntilMedSchool);
            int tmpYearsUntilMedAfterPB = input.nextInt();
            if (tmpYearsUntilMedAfterPB > 0) this.yearsUntilMedSchool = tmpYearsUntilMedAfterPB;
        } else {
            System.out.printf("How many years until you start medical school? [%d] (enter 0 to keep default): ", this.yearsUntilMedSchool);
            int tmpYearsUntilMed = input.nextInt();
            if (tmpYearsUntilMed > 0) this.yearsUntilMedSchool = tmpYearsUntilMed;
        }

        System.out.printf("Enter your expected annual raise percentage [%.1f%%] (e.g., 3 for 3%%): ", this.annualRaise * 100);
        double tmpAnnualRaise = input.nextDouble() / 100.0;
        if (tmpAnnualRaise >= 0 && tmpAnnualRaise <= 1) this.annualRaise = tmpAnnualRaise;
        if (this.annualRaise < 0) this.annualRaise = 0;
        if (this.annualRaise > 1) this.annualRaise = 1;

        // New: inflation rate to ensure physician raises at least match inflation
        System.out.printf("Enter expected inflation rate [%.1f%%] (e.g., 3 for 3%%): ", this.inflationRate * 100);
        double tmpInflationRate = input.nextDouble() / 100.0;
        if (tmpInflationRate >= 0 && tmpInflationRate <= 1) this.inflationRate = tmpInflationRate;
        if (this.inflationRate < 0) this.inflationRate = 0;
        if (this.inflationRate > 1) this.inflationRate = 1;

        System.out.printf("Enter average interest rate on medical school loans [%.1f%%] (e.g., 6 for 6%%): ", this.loanInterestRate * 100);
        double tmpLoanInterestRate = input.nextDouble() / 100.0;
        if (tmpLoanInterestRate >= 0 && tmpLoanInterestRate <= 1) this.loanInterestRate = tmpLoanInterestRate;
        if (this.loanInterestRate < 0) this.loanInterestRate = 0;
        if (this.loanInterestRate > 1) this.loanInterestRate = 1;

        System.out.print("Enter total medical school loans: $");
        this.totalLoans = input.nextDouble();
        if (this.totalLoans < 0) this.totalLoans = 0;

        System.out.printf("Enter years of residency [%d] (enter 0 to keep default): ", this.residencyYears);
        int tmpResidencyYears = input.nextInt();
        if (tmpResidencyYears > 0) this.residencyYears = tmpResidencyYears;

        System.out.printf("Confirm residency salary per year [$%.0f] (enter 0 to keep default): $", this.residencySalary);
        double inputResidencySalary = input.nextDouble();
        if (inputResidencySalary > 0) {
            this.residencySalary = inputResidencySalary;
        }

        System.out.printf("Will you need a fellowship? [%s] (y/n): ", this.needsFellowship ? "y" : "n");
        String fellowshipResponse = input.next().toLowerCase();
        this.needsFellowship = fellowshipResponse.startsWith("y");

        if (this.needsFellowship) {
            System.out.printf("Enter fellowship duration in years [%d] (enter 0 to keep default): ", this.fellowshipYears);
            int tmpFellowshipYears = input.nextInt();
            if (tmpFellowshipYears > 0) this.fellowshipYears = tmpFellowshipYears;

            System.out.printf("Confirm fellowship salary per year [$%.0f] (enter 0 to keep default): $", this.fellowshipSalary);
            double inputFellowshipSalary = input.nextDouble();
            if (inputFellowshipSalary > 0) {
                this.fellowshipSalary = inputFellowshipSalary;
            }
        }

        System.out.printf("Enter expected starting physician salary (after residency/fellowship) [$%.0f]: $", this.physicianStartingSalary);
        double tmpPhysicianSalary = input.nextDouble();
        if (tmpPhysicianSalary > 0) this.physicianStartingSalary = tmpPhysicianSalary;
        if (this.physicianStartingSalary < 0) this.physicianStartingSalary = 0;

        System.out.printf("Confirm retirement contribution rate [%.0f%%] of salary (enter 0 to keep default): ",
                this.retirementContributionRate * 100);
        double inputRetirementRate = input.nextDouble();
        if (inputRetirementRate > 0) {
            if (inputRetirementRate < 0) inputRetirementRate = 0;
            if (inputRetirementRate > 100) inputRetirementRate = 100;
            this.retirementContributionRate = inputRetirementRate / 100.0;
        }

        System.out.printf("Confirm expected investment return rate [%.0f%%] annually (enter 0 to keep default): ",
                this.investmentReturnRate * 100);
        double inputInvestmentReturn = input.nextDouble();
        if (inputInvestmentReturn > 0) {
            if (inputInvestmentReturn < 0) inputInvestmentReturn = 0;
            if (inputInvestmentReturn > 100) inputInvestmentReturn = 100;
            this.investmentReturnRate = inputInvestmentReturn / 100.0;
        }

        // New: user-selected retirement age
        System.out.printf("Enter desired retirement age [%d] (enter 0 to keep default): ", this.retirementAge);
        int inputRetirementAge = input.nextInt();
        if (inputRetirementAge > 0) {
            if (inputRetirementAge <= this.currentAge) {
                inputRetirementAge = this.currentAge + 1;
            }
            if (inputRetirementAge > 100) {
                inputRetirementAge = 100;
            }
            this.retirementAge = inputRetirementAge;
        }

        System.out.printf("Enter loan repayment period in years [%d] (enter 0 to keep default): ", this.loanRepaymentYears);
        int inputRepaymentYears = input.nextInt();
        if (inputRepaymentYears > 0) {
            this.loanRepaymentYears = inputRepaymentYears;
        }

        System.out.printf("Enter monthly loan payment amount [$%.0f] (enter 0 to auto-calculate): $", this.monthlyLoanPayment);
        double tmpMonthlyPayment = input.nextDouble();
        if (tmpMonthlyPayment >= 0) this.monthlyLoanPayment = tmpMonthlyPayment;
        if (this.monthlyLoanPayment < 0) this.monthlyLoanPayment = 0;

        // New: ask whether to auto-align payoff to retirement age
        System.out.print("Auto-align loan payoff to your retirement age by adjusting term/payment? (y/n): ");
        String alignResp = input.next().toLowerCase();
        this.alignRepaymentToRetirement = alignResp.startsWith("y");
    }

    public OpportunityCostResult calculateOpportunityCost() {
        double totalOpportunityCost = 0;
        double cumulativeLostRetirement = 0;
        double cumulativeLoanInterest = 0;
        double postBaccLoanInterest = 0;
        double currentYearSalary = currentSalary;

        int yearsUntilRetirement = retirementAge - currentAge; // Use selected retirement age
        if (yearsUntilRetirement < 0) yearsUntilRetirement = 0;

        int currentYear = 0;

        // Phase 1: Years until post-bacc or medical school (working and earning)
        int yearsWorking = needsPostBacc ? yearsUntilPostBacc : yearsUntilMedSchool;
        for (int year = 1; year <= yearsWorking; year++) {
            currentYear++;
            currentYearSalary *= (1 + annualRaise);
        }

        // Phase 2: Post-bacc years (if needed)
        if (needsPostBacc) {
            for (int year = 1; year <= postBaccYears; year++) {
                currentYear++;
                double yearlyOpportunityCost = currentYearSalary;
                double missedRetirementContribution = currentYearSalary * retirementContributionRate;

                // Calculate lost retirement growth to selected retirement age
                int yearsToGrow = yearsUntilRetirement - currentYear + 1;
                if (yearsToGrow > 0) {
                    double dailyInvestmentRate = investmentReturnRate / 365.0;
                    int daysToGrow = yearsToGrow * 365;
                    cumulativeLostRetirement += missedRetirementContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
                }

                totalOpportunityCost += yearlyOpportunityCost;
                currentYearSalary *= (1 + annualRaise);
            }

            // Post-bacc loan interest (starts accruing immediately) - daily compounding
            int yearsForPostBaccInterest = postBaccYears + yearsUntilMedSchool + medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
            double dailyRate = loanInterestRate / 365.0;
            int daysForPostBaccInterest = yearsForPostBaccInterest * 365;
            postBaccLoanInterest = postBaccCost * Math.pow(1 + dailyRate, daysForPostBaccInterest) - postBaccCost;

            // Years between post-bacc and med school (working again)
            for (int year = 1; year <= yearsUntilMedSchool; year++) {
                currentYear++;
                currentYearSalary *= (1 + annualRaise);
            }
        }

        // Phase 3: Medical school years
        for (int year = 1; year <= medSchoolYears; year++) {
            currentYear++;
            double yearlyOpportunityCost = currentYearSalary;
            double missedRetirementContribution = currentYearSalary * retirementContributionRate;

            int yearsToGrow = yearsUntilRetirement - currentYear + 1;
            if (yearsToGrow > 0) {
                double dailyInvestmentRate = investmentReturnRate / 365.0;
                int daysToGrow = yearsToGrow * 365;
                cumulativeLostRetirement += missedRetirementContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
            }

            totalOpportunityCost += yearlyOpportunityCost;
            currentYearSalary *= (1 + annualRaise);
        }

        // Phase 4: Residency years
        for (int year = 1; year <= residencyYears; year++) {
            currentYear++;
            double yearlyOpportunityCost = currentYearSalary - residencySalary;
            double missedRetirementContribution = currentYearSalary * retirementContributionRate;
            double actualRetirementContribution = residencySalary * retirementContributionRate;
            double netMissedContribution = missedRetirementContribution - actualRetirementContribution;

            int yearsToGrow = yearsUntilRetirement - currentYear + 1;
            if (yearsToGrow > 0) {
                double dailyInvestmentRate = investmentReturnRate / 365.0;
                int daysToGrow = yearsToGrow * 365;
                cumulativeLostRetirement += netMissedContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
            }

            totalOpportunityCost += yearlyOpportunityCost;
            currentYearSalary *= (1 + annualRaise);
        }

        // Phase 5: Fellowship years (if needed)
        if (needsFellowship) {
            for (int year = 1; year <= fellowshipYears; year++) {
                currentYear++;
                double yearlyOpportunityCost = currentYearSalary - fellowshipSalary;
                double missedRetirementContribution = currentYearSalary * retirementContributionRate;
                double actualRetirementContribution = fellowshipSalary * retirementContributionRate;
                double netMissedContribution = missedRetirementContribution - actualRetirementContribution;

                int yearsToGrow = yearsUntilRetirement - currentYear + 1;
                if (yearsToGrow > 0) {
                    double dailyInvestmentRate = investmentReturnRate / 365.0;
                    int daysToGrow = yearsToGrow * 365;
                    cumulativeLostRetirement += netMissedContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
                }

                totalOpportunityCost += yearlyOpportunityCost;
                currentYearSalary *= (1 + annualRaise);
            }
        }

        // Calculate medical school loan interest (accrues during med school, residency, and fellowship) - daily compounding
        int yearsForMedSchoolInterest = medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
        double dailyLoanRate = loanInterestRate / 365.0;
        int daysForMedSchoolInterest = yearsForMedSchoolInterest * 365;
        cumulativeLoanInterest = totalLoans * Math.pow(1 + dailyLoanRate, daysForMedSchoolInterest) - totalLoans;

        double totalLoanAmount = totalLoans + (needsPostBacc ? postBaccCost : 0);
        double totalLoanInterest = cumulativeLoanInterest + postBaccLoanInterest;

        // Calculate total loan balance at start of repayment (principal + all accrued interest)
        double totalLoanBalance = totalLoanAmount + totalLoanInterest;

        // Auto-calculate monthly payment if not provided
        if (monthlyLoanPayment <= 0) {
            monthlyLoanPayment = calculateMonthlyPayment(totalLoanBalance, loanInterestRate, loanRepaymentYears);
        }

        // New: Adjust loan repayment term to align with retirement age, if desired
        if (alignRepaymentToRetirement) {
            int yearsUntilPhysician = (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
                    medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
            int yearsToRepay = retirementAge - (currentAge + yearsUntilPhysician);

            if (yearsToRepay > 0) {
                loanRepaymentYears = yearsToRepay;
                monthlyLoanPayment = calculateMonthlyPayment(totalLoanBalance, loanInterestRate, loanRepaymentYears);
            } else {
                System.out.println("\nWARNING: Can't align loan payoff to retirement -- already past retirement age!");
            }
        }

        double totalCost = totalOpportunityCost + cumulativeLostRetirement + totalLoanInterest + totalLoanAmount;

        int breakEvenAge = calculateBreakEvenAge(totalCost, currentYearSalary);

        return new OpportunityCostResult(totalCost, breakEvenAge, totalOpportunityCost,
                cumulativeLostRetirement, totalLoanInterest, totalLoanAmount);
    }

    private double calculateMonthlyPayment(double loanBalance, double annualRate, int years) {
        if (loanBalance <= 0 || years <= 0) return 0;

        double monthlyRate = annualRate / 12.0;
        int totalPayments = years * 12;

        if (monthlyRate == 0) {
            return loanBalance / totalPayments;
        }

        // Standard loan payment formula: P * [r(1+r)^n] / [(1+r)^n - 1]
        double numerator = loanBalance * monthlyRate * Math.pow(1 + monthlyRate, totalPayments);
        double denominator = Math.pow(1 + monthlyRate, totalPayments) - 1;

        return numerator / denominator;
    }

    private int calculateBreakEvenAge(double totalCost, double projectedSalaryAfterTraining) {
        double physicianSalary = physicianStartingSalary;
        double nonMDSalary = projectedSalaryAfterTraining;
        double cumulativeDifference = -totalCost;
        int yearsAfterTraining = 0;
        double annualLoanPayment = monthlyLoanPayment * 12;

        while (cumulativeDifference < 0 && yearsAfterTraining < 50) {
            yearsAfterTraining++;

            double physicianNetIncome = physicianSalary;
            if (yearsAfterTraining <= loanRepaymentYears) {
                physicianNetIncome -= annualLoanPayment;
            }

            double annualDifference = physicianNetIncome - nonMDSalary;
            cumulativeDifference += annualDifference;

            physicianSalary *= (1 + Math.max(annualRaise, inflationRate));
            nonMDSalary *= (1 + annualRaise);
        }

        int totalTimeToBecomeMD = (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
                medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
        return currentAge + totalTimeToBecomeMD + yearsAfterTraining;
    }

    public void displayResults(OpportunityCostResult result) {
        System.out.println("\n=== OPPORTUNITY COST ANALYSIS ===");

        // TL;DR summary block
        System.out.println("\n=== RESULTS (TL;DR) ===");
        System.out.printf("%-34s : $%,.2f%n", "Total Opportunity Cost", result.getTotalCost());
        System.out.printf("%-34s : %d years%n", "Estimated Break-even Age", result.getBreakEvenAge());
        System.out.printf("%-34s : %d years%n", "Selected Retirement Age", retirementAge);
        System.out.printf("%-34s : $%,.2f/mo%n", "Monthly Loan Payment", monthlyLoanPayment);
        System.out.println("----------------------------------------");

        String trainingPath = "";
        if (needsPostBacc) {
            trainingPath += postBaccYears + " years post-bacc + ";
        }
        trainingPath += medSchoolYears + " years med school + " + residencyYears + " years residency";
        if (needsFellowship) {
            trainingPath += " + " + fellowshipYears + " years fellowship";
        }
        System.out.printf("Training path: %s%n", trainingPath);

        int totalTimeToBecomeMD = (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
                medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
        System.out.printf("Age when starting as physician: %d years old%n", currentAge + totalTimeToBecomeMD);

        if (needsPostBacc) {
            System.out.printf("Timeline: Start post-bacc at age %d, med school at age %d%n",
                    currentAge + yearsUntilPostBacc,
                    currentAge + yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool);
        } else {
            System.out.printf("Timeline: Start med school at age %d%n", currentAge + yearsUntilMedSchool);
        }

        System.out.printf("Retirement assumptions: %.0f%% contribution rate, %.0f%% annual return (daily compounding)%n",
                retirementContributionRate * 100, investmentReturnRate * 100);
        System.out.printf("Economic assumption: %.0f%% inflation (MD raises are floored at inflation)%n",
                inflationRate * 100);
        System.out.println("----------------------------------------");
        System.out.printf("Direct opportunity cost (lost wages): $%,.2f%n", result.getDirectOpportunityCost());
        System.out.printf("Lost retirement savings (projected to age %d): $%,.2f%n", retirementAge, result.getLostRetirementGrowth());
        if (needsPostBacc) {
            System.out.printf("Post-bacc loans: $%,.2f%n", postBaccCost);
        }
        System.out.printf("Medical school loans: $%,.2f%n", totalLoans);
        System.out.printf("Total loan interest (daily compounding with deferment): $%,.2f%n", result.getLoanInterest());

        // Calculate and display loan repayment info
        double totalLoanBalance = result.getTotalLoans() + result.getLoanInterest();
        System.out.printf("Total loan balance at repayment start: $%,.2f%n", totalLoanBalance);
        System.out.printf("Monthly loan payment (%d-year repayment): $%,.2f/mo%n", loanRepaymentYears, monthlyLoanPayment);
        System.out.printf("Annual loan payment burden: $%,.2f%n", monthlyLoanPayment * 12);
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL OPPORTUNITY COST: $%,.2f%n", result.getTotalCost());
        System.out.printf("ESTIMATED BREAK-EVEN AGE: %d years old%n", result.getBreakEvenAge());

        // Additional retirement insight
        double yearsOfEarnings = retirementAge - (currentAge + totalTimeToBecomeMD);
        if (yearsOfEarnings < 0) yearsOfEarnings = 0;
        System.out.printf("Years of physician earnings until retirement: %.0f years%n", yearsOfEarnings);

        if (result.getBreakEvenAge() > retirementAge) {
            int diff = result.getBreakEvenAge() - retirementAge;
            String yearsLabel = diff == 1 ? "year" : "years";
            System.out.printf("%nWARNING: Your selected retirement age (%d) is %d %s below the break-even age (%d).%n",
                    retirementAge, diff, yearsLabel, result.getBreakEvenAge());
            System.out.println("Suggestion: Consider postponing retirement, improving earnings, or reducing debt to reach break-even sooner.");
        }

        // Show what percentage of the total cost is retirement losses
        double retirementPercentage = (result.getLostRetirementGrowth() / result.getTotalCost()) * 100;
        System.out.printf("Retirement losses represent %.1f%% of total opportunity cost%n", retirementPercentage);
    }

    public static class OpportunityCostResult {
        private final double totalCost;
        private final int breakEvenAge;
        private final double directOpportunityCost;
        private final double lostRetirementGrowth;
        private final double loanInterest;
        private final double totalLoans;

        public OpportunityCostResult(double totalCost, int breakEvenAge, double directOpportunityCost,
                                   double lostRetirementGrowth, double loanInterest, double totalLoans) {
            this.totalCost = totalCost;
            this.breakEvenAge = breakEvenAge;
            this.directOpportunityCost = directOpportunityCost;
            this.lostRetirementGrowth = lostRetirementGrowth;
            this.loanInterest = loanInterest;
            this.totalLoans = totalLoans;
        }

        public double getTotalCost() { return totalCost; }
        public int getBreakEvenAge() { return breakEvenAge; }
        public double getDirectOpportunityCost() { return directOpportunityCost; }
        public double getLostRetirementGrowth() { return lostRetirementGrowth; }
        public double getLoanInterest() { return loanInterest; }
        public double getTotalLoans() { return totalLoans; }
    }
}