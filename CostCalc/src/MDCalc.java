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
        this.currentAge = 30; // Add built-in default for age
        this.currentSalary = 75000; // Add built-in default for salary
        this.medSchoolYears = 4;
        this.residencyYears = 3;
        this.residencySalary = 60000;
        this.physicianStartingSalary = 250000;
        this.retirementContributionRate = 0.15;
        this.investmentReturnRate = 0.07;
        this.inflationRate = 0.03;
        this.annualRaise = 0.03; // Add missing built-in default
        this.loanInterestRate = 0.06; // Add missing built-in default
        this.totalLoans = 300000; // Default medical school loan amount
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
            this.currentAge = DefaultsManager.getInt(DefaultsManager.Keys.CURRENT_AGE, this.currentAge);
            this.currentSalary = DefaultsManager.getDouble(DefaultsManager.Keys.CURRENT_SALARY, this.currentSalary);
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
            this.totalLoans = DefaultsManager.getDouble(DefaultsManager.Keys.TOTAL_LOANS, this.totalLoans);
            this.totalLoans = DefaultsManager.getDouble(DefaultsManager.Keys.TOTAL_LOANS, this.totalLoans);
            this.loanRepaymentYears = DefaultsManager.getInt(DefaultsManager.Keys.LOAN_REPAYMENT_YEARS, this.loanRepaymentYears);
            // monthlyLoanPayment is derived; no default to load
            this.retirementAge = DefaultsManager.getInt(DefaultsManager.Keys.RETIREMENT_AGE, this.retirementAge);
            this.annualRaise = DefaultsManager.getDouble(DefaultsManager.Keys.ANNUAL_RAISE, this.annualRaise);
            this.loanInterestRate = DefaultsManager.getDouble(DefaultsManager.Keys.LOAN_INTEREST_RATE, this.loanInterestRate);
        } catch (Throwable ignored) {
            // If defaults file is missing or malformed, just keep built-in defaults.
        }
    }

    public void collectUserInput(Scanner input) {
        System.out.printf("Enter your current age [%d] (enter 0 to keep default): ", this.currentAge);
        int tmpAge = input.nextInt();
        if (tmpAge > 0) this.currentAge = tmpAge;
        if (this.currentAge < 18 || this.currentAge > 70) {
            System.out.println("WARNING: Age should be between 18-70. Adjusting to valid range.");
            this.currentAge = Math.max(18, Math.min(70, this.currentAge));
        }

        System.out.printf("Enter your current annual salary [$%.0f] (enter 0 to keep default): $", this.currentSalary);
        double tmpSalary = input.nextDouble();
        if (tmpSalary > 0) this.currentSalary = tmpSalary;
        if (this.currentSalary < 20000 || this.currentSalary > 1000000) {
            System.out.println("WARNING: Salary should be between $20K-$1M. Adjusting to valid range.");
            this.currentSalary = Math.max(20000, Math.min(1000000, this.currentSalary));
        }

        System.out.printf("Will you need to do a post-bacc program? [%s] (y/n, 0=keep default): ", this.needsPostBacc ? "y" : "n");
        String postBaccResponse = input.next().toLowerCase();
        if (!postBaccResponse.equals("0")) {
            this.needsPostBacc = postBaccResponse.startsWith("y");
        }

        if (this.needsPostBacc) {
            System.out.printf("How many years until you start post-bacc? [%d] (enter 0 to keep default): ", this.yearsUntilPostBacc);
            int tmpYearsUntilPostBacc = input.nextInt();
            if (tmpYearsUntilPostBacc > 0) this.yearsUntilPostBacc = tmpYearsUntilPostBacc;
            if (this.yearsUntilPostBacc < 0 || this.yearsUntilPostBacc > 10) {
                System.out.println("WARNING: Years until post-bacc should be 0-10. Adjusting to valid range.");
                this.yearsUntilPostBacc = Math.max(0, Math.min(10, this.yearsUntilPostBacc));
            }

            System.out.printf("How many years will post-bacc take? [%d] (enter 0 to keep default): ", this.postBaccYears);
            int tmpPostBaccYears = input.nextInt();
            if (tmpPostBaccYears > 0) this.postBaccYears = tmpPostBaccYears;
            if (this.postBaccYears < 1 || this.postBaccYears > 4) {
                System.out.println("WARNING: Post-bacc duration should be 1-4 years. Adjusting to valid range.");
                this.postBaccYears = Math.max(1, Math.min(4, this.postBaccYears));
            }

            System.out.printf("What is the total cost of post-bacc program? [$%.0f] (enter 0 to keep default): $", this.postBaccCost);
            double tmpPostBaccCost = input.nextDouble();
            if (tmpPostBaccCost > 0) this.postBaccCost = tmpPostBaccCost;
            if (this.postBaccCost < 10000 || this.postBaccCost > 200000) {
                System.out.println("WARNING: Post-bacc cost should be $10K-$200K. Adjusting to valid range.");
                this.postBaccCost = Math.max(10000, Math.min(200000, this.postBaccCost));
            }

            System.out.printf("How many years after post-bacc until medical school? [%d] (enter 0 to keep default): ", this.yearsUntilMedSchool);
            int tmpYearsUntilMedAfterPB = input.nextInt();
            if (tmpYearsUntilMedAfterPB > 0) this.yearsUntilMedSchool = tmpYearsUntilMedAfterPB;
            if (this.yearsUntilMedSchool < 0 || this.yearsUntilMedSchool > 5) {
                System.out.println("WARNING: Gap between post-bacc and med school should be 0-5 years. Adjusting to valid range.");
                this.yearsUntilMedSchool = Math.max(0, Math.min(5, this.yearsUntilMedSchool));
            }
        } else {
            System.out.printf("How many years until you start medical school? [%d] (enter 0 to keep default): ", this.yearsUntilMedSchool);
            int tmpYearsUntilMed = input.nextInt();
            if (tmpYearsUntilMed > 0) this.yearsUntilMedSchool = tmpYearsUntilMed;
            if (this.yearsUntilMedSchool < 0 || this.yearsUntilMedSchool > 10) {
                System.out.println("WARNING: Years until med school should be 0-10. Adjusting to valid range.");
                this.yearsUntilMedSchool = Math.max(0, Math.min(10, this.yearsUntilMedSchool));
            }
        }

        System.out.printf("Enter your expected annual raise percentage [%.1f%%] (e.g., 3 for 3%%, enter 0 to keep default): ", this.annualRaise * 100);
        double tmpAnnualRaisePct = input.nextDouble();
        if (tmpAnnualRaisePct > 0) {
            double tmpAnnualRaise = tmpAnnualRaisePct / 100.0;
            if (tmpAnnualRaise < 0) tmpAnnualRaise = 0;
            if (tmpAnnualRaise > 0.20) {
                System.out.println("WARNING: Annual raise should be 0-20%. Capping at 20%.");
                tmpAnnualRaise = 0.20;
            }
            this.annualRaise = tmpAnnualRaise;
        }

        // New: inflation rate to ensure physician raises at least match inflation
        System.out.printf("Enter expected inflation rate [%.1f%%] (e.g., 3 for 3%%, enter 0 to keep default): ", this.inflationRate * 100);
        double tmpInflationPct = input.nextDouble();
        if (tmpInflationPct > 0) {
            double tmpInflationRate = tmpInflationPct / 100.0;
            if (tmpInflationRate < 0) tmpInflationRate = 0;
            if (tmpInflationRate > 0.15) {
                System.out.println("WARNING: Inflation rate should be 0-15%. Capping at 15%.");
                tmpInflationRate = 0.15;
            }
            this.inflationRate = tmpInflationRate;
        }

        System.out.printf("Enter average interest rate on medical school loans [%.1f%%] (e.g., 6 for 6%%, enter 0 to keep default): ", this.loanInterestRate * 100);
        double tmpLoanInterestPct = input.nextDouble();
        if (tmpLoanInterestPct > 0) {
            double tmpLoanInterestRate = tmpLoanInterestPct / 100.0;
            if (tmpLoanInterestRate < 0) tmpLoanInterestRate = 0;
            if (tmpLoanInterestRate > 0.15) {
                System.out.println("WARNING: Loan interest rate should be 0-15%. Capping at 15%.");
                tmpLoanInterestRate = 0.15;
            }
            this.loanInterestRate = tmpLoanInterestRate;
        }

        System.out.printf("Enter total medical school loans [$%.0f] (enter 0 to keep default): $", this.totalLoans);
        double tmpTotalLoans = input.nextDouble();
        if (tmpTotalLoans > 0) this.totalLoans = tmpTotalLoans;
        if (this.totalLoans < 50000 || this.totalLoans > 500000) {
            System.out.println("WARNING: Medical school loans should be $50K-$500K. Adjusting to valid range.");
            this.totalLoans = Math.max(50000, Math.min(500000, this.totalLoans));
        }

        System.out.printf("Enter years of residency [%d] (enter 0 to keep default): ", this.residencyYears);
        int tmpResidencyYears = input.nextInt();
        if (tmpResidencyYears > 0) this.residencyYears = tmpResidencyYears;
        if (this.residencyYears < 3 || this.residencyYears > 7) {
            System.out.println("WARNING: Residency duration should be 3-7 years. Adjusting to valid range.");
            this.residencyYears = Math.max(3, Math.min(7, this.residencyYears));
        }

        System.out.printf("Confirm residency salary per year [$%.0f] (enter 0 to keep default): $", this.residencySalary);
        double inputResidencySalary = input.nextDouble();
        if (inputResidencySalary > 0) {
            this.residencySalary = inputResidencySalary;
        }
        if (this.residencySalary < 40000 || this.residencySalary > 80000) {
            System.out.println("WARNING: Residency salary should be $40K-$80K. Adjusting to valid range.");
            this.residencySalary = Math.max(40000, Math.min(80000, this.residencySalary));
        }

        System.out.printf("Will you need a fellowship? [%s] (y/n, 0=keep default): ", this.needsFellowship ? "y" : "n");
        String fellowshipResponse = input.next().toLowerCase();
        if (!fellowshipResponse.equals("0")) {
            this.needsFellowship = fellowshipResponse.startsWith("y");
        }

        if (this.needsFellowship) {
            System.out.printf("Enter fellowship duration in years [%d] (enter 0 to keep default): ", this.fellowshipYears);
            int tmpFellowshipYears = input.nextInt();
            if (tmpFellowshipYears > 0) this.fellowshipYears = tmpFellowshipYears;
            if (this.fellowshipYears < 1 || this.fellowshipYears > 4) {
                System.out.println("WARNING: Fellowship duration should be 1-4 years. Adjusting to valid range.");
                this.fellowshipYears = Math.max(1, Math.min(4, this.fellowshipYears));
            }

            System.out.printf("Confirm fellowship salary per year [$%.0f] (enter 0 to keep default): $", this.fellowshipSalary);
            double inputFellowshipSalary = input.nextDouble();
            if (inputFellowshipSalary > 0) {
                this.fellowshipSalary = inputFellowshipSalary;
            }
            if (this.fellowshipSalary < 60000 || this.fellowshipSalary > 120000) {
                System.out.println("WARNING: Fellowship salary should be $60K-$120K. Adjusting to valid range.");
                this.fellowshipSalary = Math.max(60000, Math.min(120000, this.fellowshipSalary));
            }
        }

        System.out.printf("Enter expected starting physician salary (after residency/fellowship) [$%.0f] (enter 0 to keep default): $", this.physicianStartingSalary);
        double tmpPhysicianSalary = input.nextDouble();
        if (tmpPhysicianSalary > 0) this.physicianStartingSalary = tmpPhysicianSalary;
        if (this.physicianStartingSalary < 150000 || this.physicianStartingSalary > 800000) {
            System.out.println("WARNING: Physician starting salary should be $150K-$800K. Adjusting to valid range.");
            this.physicianStartingSalary = Math.max(150000, Math.min(800000, this.physicianStartingSalary));
        }

        System.out.printf("Confirm retirement contribution rate [%.0f%%] of salary (enter 0 to keep default): ",
                this.retirementContributionRate * 100);
        double inputRetirementRate = input.nextDouble();
        if (inputRetirementRate > 0) {
            if (inputRetirementRate < 5) {
                System.out.println("WARNING: Retirement contribution should be at least 5%. Setting to 5%.");
                inputRetirementRate = 5;
            }
            if (inputRetirementRate > 50) {
                System.out.println("WARNING: Retirement contribution should be 5-50%. Capping at 50%.");
                inputRetirementRate = 50;
            }
            this.retirementContributionRate = inputRetirementRate / 100.0;
        }

        System.out.printf("Confirm expected investment return rate [%.0f%%] annually (enter 0 to keep default): ",
                this.investmentReturnRate * 100);
        double inputInvestmentReturn = input.nextDouble();
        if (inputInvestmentReturn > 0) {
            if (inputInvestmentReturn < 3) {
                System.out.println("WARNING: Investment return should be at least 3%. Setting to 3%.");
                inputInvestmentReturn = 3;
            }
            if (inputInvestmentReturn > 15) {
                System.out.println("WARNING: Investment return should be 3-15%. Capping at 15%.");
                inputInvestmentReturn = 15;
            }
            this.investmentReturnRate = inputInvestmentReturn / 100.0;
        }

        // New: user-selected retirement age
        System.out.printf("Enter desired retirement age [%d] (enter 0 to keep default): ", this.retirementAge);
        int inputRetirementAge = input.nextInt();
        if (inputRetirementAge > 0) {
            if (inputRetirementAge <= this.currentAge) {
                System.out.println("WARNING: Retirement age must be after current age. Setting to current age + 1.");
                inputRetirementAge = this.currentAge + 1;
            }
            if (inputRetirementAge > 80) {
                System.out.println("WARNING: Retirement age should be 80 or less. Capping at 80.");
                inputRetirementAge = 80;
            }
            this.retirementAge = inputRetirementAge;
        }

        System.out.printf("Enter loan repayment period in years [%d] (enter 0 to keep default): ", this.loanRepaymentYears);
        int inputRepaymentYears = input.nextInt();
        if (inputRepaymentYears > 0) this.loanRepaymentYears = inputRepaymentYears;
        if (this.loanRepaymentYears < 5 || this.loanRepaymentYears > 30) {
            System.out.println("WARNING: Loan repayment period should be 5-30 years. Adjusting to valid range.");
            this.loanRepaymentYears = Math.max(5, Math.min(30, this.loanRepaymentYears));
        }

        System.out.printf("Enter monthly loan payment amount [$%.0f] (enter 0 to auto-calculate): $", this.monthlyLoanPayment);
        double tmpMonthlyPayment = input.nextDouble();
        if (tmpMonthlyPayment >= 0) this.monthlyLoanPayment = tmpMonthlyPayment;
        if (this.monthlyLoanPayment < 0) this.monthlyLoanPayment = 0;

        // New: ask whether to auto-align payoff to retirement age
        System.out.printf("Auto-align loan payoff to your retirement age by adjusting term/payment? [%s] (y/n, 0=keep default): ", this.alignRepaymentToRetirement ? "y" : "n");
        String alignResp = input.next().toLowerCase();
        if (!alignResp.equals("0")) {
            this.alignRepaymentToRetirement = alignResp.startsWith("y");
        }
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
                // Missed retirement contributions equal what would have been contributed on the alternative salary
                double missedRetirementContribution = currentYearSalary * retirementContributionRate;

                // Calculate lost retirement growth to selected retirement age
                int yearsToGrow = yearsUntilRetirement - currentYear + 1;
                if (yearsToGrow > 0) {
                    double dailyInvestmentRate = investmentReturnRate / 365.0;
                    int daysToGrow = yearsToGrow * 365;
                    cumulativeLostRetirement += missedRetirementContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
                }

                totalOpportunityCost += yearlyOpportunityCost;
                // Alternative career salary grows by expected raises only
                currentYearSalary *= (1 + annualRaise);
            }

            // Post-bacc loan interest accrues during post-bacc through end of training path
            double yearsForPostBaccInterest = postBaccYears + yearsUntilMedSchool + medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
            double dailyRate = loanInterestRate / 365.0;
            int daysForPostBaccInterest = (int)(yearsForPostBaccInterest * 365);
            if (daysForPostBaccInterest > 0) {
                postBaccLoanInterest = postBaccCost * Math.pow(1 + dailyRate, daysForPostBaccInterest) - postBaccCost;
            }

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
            // Missed retirement contributions equal what would have been contributed on the alternative salary
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
            // Net missed retirement contributions are the contribution gap between paths
            double netMissedContribution = (currentYearSalary - residencySalary) * retirementContributionRate;

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
                // Net missed retirement contributions are the contribution gap between paths
                double netMissedContribution = (currentYearSalary - fellowshipSalary) * retirementContributionRate;

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

        // Medical school loan interest accrues during med school through end of training path
        double yearsForMedSchoolInterest = medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
        double dailyLoanRate = loanInterestRate / 365.0;
        int daysForMedSchoolInterest = (int)(yearsForMedSchoolInterest * 365);
        if (daysForMedSchoolInterest > 0) {
            cumulativeLoanInterest = totalLoans * Math.pow(1 + dailyLoanRate, daysForMedSchoolInterest) - totalLoans;
        }

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

        // Simulate amortization during repayment so interest compounds until payoff
        RepaymentSummary repayment = simulateRepayment(totalLoanBalance, monthlyLoanPayment, loanInterestRate, loanRepaymentYears);
        double repaymentPhaseInterest = repayment.totalInterest;

        // Include repayment-phase interest in total loan interest
        totalLoanInterest += repaymentPhaseInterest;

        double totalCost = totalOpportunityCost + cumulativeLostRetirement + totalLoanInterest + totalLoanAmount;

        int breakEvenAge = calculateBreakEvenAge(totalCost, currentYearSalary);

        return new OpportunityCostResult(totalCost, breakEvenAge, totalOpportunityCost,
                cumulativeLostRetirement, totalLoanInterest, totalLoans);
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

    private static class RepaymentSummary {
        final double totalInterest;
        final int monthsTaken;
        final double finalBalance;

        RepaymentSummary(double totalInterest, int monthsTaken, double finalBalance) {
            this.totalInterest = totalInterest;
            this.monthsTaken = monthsTaken;
            this.finalBalance = finalBalance;
        }
    }

    private RepaymentSummary simulateRepayment(double startingBalance, double monthlyPayment, double annualRate, int yearsPlanned) {
        if (startingBalance <= 0 || monthlyPayment <= 0) {
            return new RepaymentSummary(0, 0, Math.max(0, startingBalance));
        }

        double monthlyRate = annualRate / 12.0;
        int maxMonths = Math.max(1, yearsPlanned * 12 + 120); // allow extra months due to rounding, cap runaway

        double balance = startingBalance;
        double totalInterest = 0;
        int months = 0;

        for (; months < maxMonths && balance > 0.005; months++) {
            double interest = balance * monthlyRate;
            totalInterest += interest;

            double principal = monthlyPayment - interest;
            if (principal <= 0) {
                // Payment too low to amortize; bump months and break to avoid infinite loop
                break;
            }

            if (principal >= balance) {
                // Final payment this month
                principal = balance;
                balance = 0;
                months++;
                break;
            }

            balance -= principal;
        }

        return new RepaymentSummary(totalInterest, months, Math.max(0, balance));
    }

    private int calculateBreakEvenAge(double totalCost, double projectedSalaryAfterTraining) {
        // Use end-of-training alternative salary as baseline for counterfactual path
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

            // Physician salaries grow with max(raises, inflation); counterfactual grows with raises only
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
        System.out.printf("Total loan interest (deferment + repayment): $%,.2f%n", result.getLoanInterest());

        // Calculate and display loan repayment info
        double startingRepaymentBalance = computeStartingLoanBalanceAtRepaymentStart();
        System.out.printf("Total loan balance at repayment start: $%,.2f%n", startingRepaymentBalance);
        System.out.printf("Monthly loan payment (%d-year repayment): $%,.2f/mo%n", loanRepaymentYears, monthlyLoanPayment);
        double annualLoanPayment = monthlyLoanPayment * 12;
        System.out.printf("Annual loan payment burden: $%,.2f%n", annualLoanPayment);
        // Warn if annual loan repayment exceeds half of first-year physician income
        double firstYearPhysicianIncome = physicianStartingSalary;
        if (annualLoanPayment > 0.5 * firstYearPhysicianIncome) {
            double percentOfIncome = (annualLoanPayment / firstYearPhysicianIncome) * 100.0;
            System.out.printf("WARNING: Annual loan payment is %.1f%% of your first-year physician income (>%s).%n",
                    percentOfIncome, "50% threshold");
            System.out.println("Consider extending the repayment timeline, lowering interest rate, or adjusting debt to reduce burden.");
        }
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

    /**
     * Displays optimization suggestions menu after calculation results
     */
    public void displayOptimizationMenu(OpportunityCostResult result, Scanner input) {
        System.out.println("\n=== OPTIMIZATION SUGGESTIONS ===");
        System.out.println("Would you like to see suggestions for reaching break-even earlier?");
        System.out.println("1. Loan repayment optimization strategies");
        System.out.println("2. Target salary recommendations");
        System.out.println("3. Skip optimization suggestions");
        System.out.print("Enter choice (1-3): ");
        
        if (!input.hasNextInt()) {
            System.out.println("Invalid input. Skipping optimization suggestions.");
            input.nextLine(); // consume invalid input
            return;
        }
        
        int choice = input.nextInt();
        input.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                displayLoanOptimizationSuggestions(result, input);
                break;
            case 2:
                displayTargetSalaryRecommendations(result, input);
                break;
            case 3:
                System.out.println("Skipping optimization suggestions.");
                break;
            default:
                System.out.println("Invalid choice. Skipping optimization suggestions.");
                break;
        }
    }

    /**
     * Displays loan repayment optimization suggestions
     */
    private void displayLoanOptimizationSuggestions(OpportunityCostResult result, Scanner input) {
        System.out.println("\n=== LOAN REPAYMENT OPTIMIZATION ===");
        
        // Use the correct starting balance at the beginning of repayment
        double totalLoanBalance = computeStartingLoanBalanceAtRepaymentStart();
        double principalSum = this.totalLoans + (needsPostBacc ? postBaccCost : 0);
        double defermentInterest = Math.max(0, totalLoanBalance - principalSum);
        int totalTimeToBecomeMD = (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
                medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
        int ageWhenStarting = currentAge + totalTimeToBecomeMD;
        
        // Check if aggressive repayment can help reach break-even earlier
        boolean canOptimizeLoans = result.getBreakEvenAge() > ageWhenStarting;
        
        if (!canOptimizeLoans) {
            System.out.println("Your current loan repayment strategy is already optimal!");
            System.out.println("Break-even age (" + result.getBreakEvenAge() + ") is at or before you start practicing.");
            return;
        }
        
        System.out.println("Current situation:");
        System.out.printf("- Break-even age: %d years old%n", result.getBreakEvenAge());
        System.out.printf("- Age when starting practice: %d years old%n", ageWhenStarting);
        System.out.printf("- Years to break-even after starting: %d years%n", result.getBreakEvenAge() - ageWhenStarting);
        System.out.printf("- Current monthly payment: $%,.2f (%d-year term)%n", monthlyLoanPayment, loanRepaymentYears);
        
        System.out.println("\n=== AGGRESSIVE REPAYMENT SCENARIOS ===");
        
        // Scenario 1: 5-year aggressive repayment
        int aggressiveYears = 5;
        double aggressiveMonthlyPayment = calculateMonthlyPayment(totalLoanBalance, loanInterestRate, aggressiveYears);
        RepaymentSummary aggressiveRepay = simulateRepayment(totalLoanBalance, aggressiveMonthlyPayment, loanInterestRate, aggressiveYears);
        double aggressiveTotalCost = result.getDirectOpportunityCost() + result.getLostRetirementGrowth() + (defermentInterest + aggressiveRepay.totalInterest) + principalSum;
        int aggressiveBreakEvenAge = calculateBreakEvenAgeWithCustomPayment(aggressiveTotalCost, aggressiveMonthlyPayment, aggressiveYears);
        
        if (aggressiveBreakEvenAge < result.getBreakEvenAge()) {
            System.out.printf("5-year aggressive repayment:%n");
            System.out.printf("- Monthly payment: $%,.2f%n", aggressiveMonthlyPayment);
            System.out.printf("- Annual burden: $%,.2f%n", aggressiveMonthlyPayment * 12);
            System.out.printf("- New break-even age: %d years old%n", aggressiveBreakEvenAge);
            System.out.printf("- Years saved: %d%n", result.getBreakEvenAge() - aggressiveBreakEvenAge);
            
            double percentOfIncome = (aggressiveMonthlyPayment * 12 / physicianStartingSalary) * 100;
            if (percentOfIncome > 50) {
                System.out.printf("⚠️  WARNING: This represents %.1f%% of first-year physician income%n", percentOfIncome);
                System.out.println("   Consider if this payment level is sustainable.");
            }
        } else {
            System.out.println("5-year repayment would not improve break-even age.");
        }
        
        // Scenario 2: 7-year moderate repayment
        int moderateYears = 7;
        double moderateMonthlyPayment = calculateMonthlyPayment(totalLoanBalance, loanInterestRate, moderateYears);
        RepaymentSummary moderateRepay = simulateRepayment(totalLoanBalance, moderateMonthlyPayment, loanInterestRate, moderateYears);
        double moderateTotalCost = result.getDirectOpportunityCost() + result.getLostRetirementGrowth() + (defermentInterest + moderateRepay.totalInterest) + principalSum;
        int moderateBreakEvenAge = calculateBreakEvenAgeWithCustomPayment(moderateTotalCost, moderateMonthlyPayment, moderateYears);
        
        if (moderateBreakEvenAge < result.getBreakEvenAge()) {
            System.out.printf("\n7-year moderate repayment:%n");
            System.out.printf("- Monthly payment: $%,.2f%n", moderateMonthlyPayment);
            System.out.printf("- Annual burden: $%,.2f%n", moderateMonthlyPayment * 12);
            System.out.printf("- New break-even age: %d years old%n", moderateBreakEvenAge);
            System.out.printf("- Years saved: %d%n", result.getBreakEvenAge() - moderateBreakEvenAge);
            
            double percentOfIncome = (moderateMonthlyPayment * 12 / physicianStartingSalary) * 100;
            if (percentOfIncome > 50) {
                System.out.printf("⚠️  WARNING: This represents %.1f%% of first-year physician income%n", percentOfIncome);
            }
        }
        
        // Scenario 3: Custom repayment term
        System.out.println("\n=== CUSTOM REPAYMENT ANALYSIS ===");
        System.out.println("Enter a target break-even age (or 0 to skip): ");
        
        if (input.hasNextInt()) {
            int targetBreakEvenAge = input.nextInt();
            input.nextLine(); // consume newline
            
            if (targetBreakEvenAge > 0 && targetBreakEvenAge < result.getBreakEvenAge()) {
                int yearsToTarget = targetBreakEvenAge - ageWhenStarting;
                if (yearsToTarget > 0) {
                    double customMonthlyPayment = calculateMonthlyPayment(totalLoanBalance, loanInterestRate, yearsToTarget);
                    RepaymentSummary customRepay = simulateRepayment(totalLoanBalance, customMonthlyPayment, loanInterestRate, yearsToTarget);
                    double customTotalCost = result.getDirectOpportunityCost() + result.getLostRetirementGrowth() + (defermentInterest + customRepay.totalInterest) + principalSum;
                    System.out.printf("\nTo reach break-even at age %d:%n", targetBreakEvenAge);
                    System.out.printf("- Required repayment term: %d years%n", yearsToTarget);
                    System.out.printf("- Monthly payment: $%,.2f%n", customMonthlyPayment);
                    System.out.printf("- Annual burden: $%,.2f%n", customMonthlyPayment * 12);
                    int customBreakEvenAge = calculateBreakEvenAgeWithCustomPayment(customTotalCost, customMonthlyPayment, yearsToTarget);
                    System.out.printf("- Projected break-even age with this plan: %d years old%n", customBreakEvenAge);
                    
                    double percentOfIncome = (customMonthlyPayment * 12 / physicianStartingSalary) * 100;
                    if (percentOfIncome > 50) {
                        System.out.printf("⚠️  WARNING: This represents %.1f%% of first-year physician income%n", percentOfIncome);
                        System.out.println("   This payment level may not be sustainable.");
                    }
                } else {
                    System.out.println("Target break-even age is too early - already past when you start practicing.");
                }
            } else if (targetBreakEvenAge > 0) {
                System.out.println("Target break-even age is not earlier than current projection.");
            }
        } else {
            System.out.println("Invalid input. Skipping custom analysis.");
            input.nextLine(); // consume invalid input
        }
        
        System.out.println("\n=== RECOMMENDATIONS ===");
        if (aggressiveBreakEvenAge < result.getBreakEvenAge() || moderateBreakEvenAge < result.getBreakEvenAge()) {
            System.out.println("✅ Consider accelerating loan repayment to reach break-even sooner.");
            System.out.println("✅ Higher monthly payments reduce total interest and accelerate wealth building.");
            System.out.println("⚠️  Ensure payment levels are sustainable with your lifestyle and other expenses.");
        } else {
            System.out.println("✅ Your current loan strategy is well-optimized for your situation.");
            System.out.println("✅ Focus on other optimization strategies like increasing income or reducing costs.");
        }
    }

    /**
     * Displays target salary recommendations
     */
    private void displayTargetSalaryRecommendations(OpportunityCostResult result, Scanner input) {
        System.out.println("\n=== TARGET SALARY RECOMMENDATIONS ===");
        
        int totalTimeToBecomeMD = (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
                medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
        int ageWhenStarting = currentAge + totalTimeToBecomeMD;
        double startingRepaymentBalance = computeStartingLoanBalanceAtRepaymentStart();
        
        System.out.println("Current situation:");
        System.out.printf("- Starting physician salary: $%,.2f%n", physicianStartingSalary);
        System.out.printf("- Break-even age: %d years old%n", result.getBreakEvenAge());
        System.out.printf("- Years to break-even after starting: %d years%n", result.getBreakEvenAge() - ageWhenStarting);
        
        if (result.getBreakEvenAge() <= ageWhenStarting) {
            System.out.println("\n✅ Congratulations! You're already projected to break even immediately or before starting practice.");
            System.out.println("Your current salary projections are excellent for your situation.");
            return;
        }
        
        System.out.println("\n=== SALARY OPTIMIZATION SCENARIOS ===");
        
        // Calculate what salary would be needed to break even in 5 years
        // Use a 5-year payoff to reflect more realistic burden during those years
        double fiveYearMonthly = calculateMonthlyPayment(startingRepaymentBalance, loanInterestRate, 5);
        double targetSalary5Years = calculateTargetSalaryForBreakEvenWithCustomPayment(
                result.getTotalCost(), 5, ageWhenStarting, fiveYearMonthly, 5);
        if (targetSalary5Years > physicianStartingSalary) {
            System.out.printf("To break even in 5 years after starting practice:%n");
            System.out.printf("- Target starting salary: $%,.2f%n", targetSalary5Years);
            System.out.printf("- Salary increase needed: $%,.2f%n", targetSalary5Years - physicianStartingSalary);
            System.out.printf("- Percentage increase: %.1f%%%n", ((targetSalary5Years / physicianStartingSalary) - 1) * 100);
        }
        
        // Calculate what salary would be needed to break even in 10 years
        double tenYearMonthly = calculateMonthlyPayment(startingRepaymentBalance, loanInterestRate, 10);
        double targetSalary10Years = calculateTargetSalaryForBreakEvenWithCustomPayment(
                result.getTotalCost(), 10, ageWhenStarting, tenYearMonthly, 10);
        if (targetSalary10Years > physicianStartingSalary) {
            System.out.printf("\nTo break even in 10 years after starting practice:%n");
            System.out.printf("- Target starting salary: $%,.2f%n", targetSalary10Years);
            System.out.printf("- Salary increase needed: $%,.2f%n", targetSalary10Years - physicianStartingSalary);
            System.out.printf("- Percentage increase: %.1f%%%n", ((targetSalary10Years / physicianStartingSalary) - 1) * 100);
        }
        
        // Calculate what salary would be needed to break even at retirement age
        int yearsToRetirement = retirementAge - ageWhenStarting;
        if (yearsToRetirement > 0) {
            double retirementMonthly = calculateMonthlyPayment(startingRepaymentBalance, loanInterestRate, yearsToRetirement);
            double targetSalaryRetirement = calculateTargetSalaryForBreakEvenWithCustomPayment(
                    result.getTotalCost(), yearsToRetirement, ageWhenStarting, retirementMonthly, yearsToRetirement);
            if (targetSalaryRetirement > physicianStartingSalary) {
                System.out.printf("\nTo break even by retirement age (%d):%n", retirementAge);
                System.out.printf("- Target starting salary: $%,.2f%n", targetSalaryRetirement);
                System.out.printf("- Salary increase needed: $%,.2f%n", targetSalaryRetirement - physicianStartingSalary);
                System.out.printf("- Percentage increase: %.1f%%%n", ((targetSalaryRetirement / physicianStartingSalary) - 1) * 100);
            }
        }
        
        // Custom target analysis
        System.out.println("\n=== CUSTOM TARGET ANALYSIS ===");
        System.out.println("Enter target years to break-even after starting practice (or 0 to skip): ");
        
        if (input.hasNextInt()) {
            int targetYears = input.nextInt();
            input.nextLine(); // consume newline
            
            if (targetYears > 0 && targetYears < (result.getBreakEvenAge() - ageWhenStarting)) {
                double customMonthly = calculateMonthlyPayment(startingRepaymentBalance, loanInterestRate, targetYears);
                double customTargetSalary = calculateTargetSalaryForBreakEvenWithCustomPayment(
                        result.getTotalCost(), targetYears, ageWhenStarting, customMonthly, targetYears);
                System.out.printf("\nTo break even in %d years after starting practice:%n", targetYears);
                System.out.printf("- Target starting salary: $%,.2f%n", customTargetSalary);
                System.out.printf("- Salary increase needed: $%,.2f%n", customTargetSalary - physicianStartingSalary);
                System.out.printf("- Percentage increase: %.1f%%%n", ((customTargetSalary / physicianStartingSalary) - 1) * 100);
                
                if (customTargetSalary > 800000) {
                    System.out.println("⚠️  WARNING: This salary level may be unrealistic for most specialties.");
                    System.out.println("   Consider combining salary increases with other optimization strategies.");
                }
            } else if (targetYears > 0) {
                System.out.println("Target years is not shorter than current projection.");
            }
        } else {
            System.out.println("Invalid input. Skipping custom analysis.");
            input.nextLine(); // consume invalid input
        }
        
        System.out.println("\n=== RECOMMENDATIONS ===");
        System.out.println("✅ Research salaries in your target specialty and geographic region.");
        System.out.println("✅ Consider subspecialty training that commands higher compensation.");
        System.out.println("✅ Negotiate aggressively for your first attending position.");
        System.out.println("✅ Explore opportunities in underserved areas that may offer higher pay.");
        System.out.println("✅ Consider combining salary increases with accelerated loan repayment.");
    }

    /**
     * Calculates break-even age with a custom monthly loan payment
     */
    private int calculateBreakEvenAgeWithCustomPayment(double totalCost, double customMonthlyPayment, int customRepaymentYears) {
        double physicianSalary = physicianStartingSalary;
        double nonMDSalary = currentSalary * Math.pow(1 + annualRaise, 
            (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
            medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0));
        
        double cumulativeDifference = -totalCost;
        int yearsAfterTraining = 0;
        double annualLoanPayment = customMonthlyPayment * 12;

        while (cumulativeDifference < 0 && yearsAfterTraining < 50) {
            yearsAfterTraining++;

            double physicianNetIncome = physicianSalary;
            if (yearsAfterTraining <= customRepaymentYears) {
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

    /**
     * Calculates target starting salary needed to break even in specified years
     */
    private double calculateTargetSalaryForBreakEven(double totalCost, int targetYears, int ageWhenStarting) {
        double baseNonMDSalary = currentSalary * Math.pow(1 + annualRaise,
            (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
            medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0));

        double low = 150000; // realistic minimum
        double high = 2000000; // cap for search

        for (int i = 0; i < 40; i++) { // binary search
            double mid = (low + high) / 2.0;
            boolean meets = salaryMeetsTarget(totalCost, targetYears, mid, baseNonMDSalary, monthlyLoanPayment, loanRepaymentYears);
            if (meets) {
                high = mid;
            } else {
                low = mid;
            }
        }

        return Math.max(high, 150000);
    }

    private double calculateTargetSalaryForBreakEvenWithCustomPayment(double totalCost, int targetYears,
                                                                      int ageWhenStarting,
                                                                      double customMonthlyPayment,
                                                                      int customRepaymentYears) {
        double baseNonMDSalary = currentSalary * Math.pow(1 + annualRaise,
            (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
            medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0));

        double low = 150000;
        double high = 2000000;

        for (int i = 0; i < 40; i++) {
            double mid = (low + high) / 2.0;
            boolean meets = salaryMeetsTarget(totalCost, targetYears, mid, baseNonMDSalary, customMonthlyPayment, customRepaymentYears);
            if (meets) {
                high = mid;
            } else {
                low = mid;
            }
        }

        return Math.max(high, 150000);
    }

    private boolean salaryMeetsTarget(double totalCost,
                                      int targetYears,
                                      double startingPhysicianSalary,
                                      double baseNonMDSalary,
                                      double monthlyPayment,
                                      int repaymentYears) {
        double cumulativeDifference = -totalCost;
        double physicianSalary = startingPhysicianSalary;
        double nonMDSalary = baseNonMDSalary;

        for (int year = 1; year <= targetYears; year++) {
            double physicianNetIncome = physicianSalary;
            if (year <= repaymentYears) {
                physicianNetIncome -= (monthlyPayment * 12);
            }

            double annualDifference = physicianNetIncome - nonMDSalary;
            cumulativeDifference += annualDifference;

            physicianSalary *= (1 + Math.max(annualRaise, inflationRate));
            nonMDSalary *= (1 + annualRaise);
        }

        return cumulativeDifference >= 0;
    }

    private double computeStartingLoanBalanceAtRepaymentStart() {
        double yearsForMedSchoolInterest = medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
        double dailyLoanRate = loanInterestRate / 365.0;
        int daysForMedSchoolInterest = (int) (yearsForMedSchoolInterest * 365);
        double medSchoolAccrued = totalLoans;
        if (daysForMedSchoolInterest > 0) {
            medSchoolAccrued = totalLoans * Math.pow(1 + dailyLoanRate, daysForMedSchoolInterest);
        }

        double postBaccAccrued = needsPostBacc ? postBaccCost : 0;
        if (needsPostBacc) {
            double yearsForPostBaccInterest = postBaccYears + yearsUntilMedSchool + medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
            int daysForPostBaccInterest = (int) (yearsForPostBaccInterest * 365);
            if (daysForPostBaccInterest > 0) {
                postBaccAccrued = postBaccCost * Math.pow(1 + dailyLoanRate, daysForPostBaccInterest);
            }
        }

        return medSchoolAccrued + postBaccAccrued;
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