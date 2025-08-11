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
                // Fix: Use more realistic retirement contribution assumption during training
                // Assume they would contribute a fixed percentage of their pre-training salary
                double missedRetirementContribution = currentYearSalary * retirementContributionRate * 0.5; // Reduced during training

                // Calculate lost retirement growth to selected retirement age
                int yearsToGrow = yearsUntilRetirement - currentYear + 1;
                if (yearsToGrow > 0) {
                    double dailyInvestmentRate = investmentReturnRate / 365.0;
                    int daysToGrow = yearsToGrow * 365;
                    cumulativeLostRetirement += missedRetirementContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
                }

                totalOpportunityCost += yearlyOpportunityCost;
                // Fix: Apply inflation-adjusted salary growth consistently
                currentYearSalary *= (1 + Math.max(annualRaise, inflationRate));
            }

            // Fix: More realistic post-bacc loan interest calculation
            // Assume 6-month grace period after post-bacc before interest starts
            double yearsForPostBaccInterest = Math.max(0, yearsUntilMedSchool - 0.5) + medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
            double dailyRate = loanInterestRate / 365.0;
            int daysForPostBaccInterest = (int)(yearsForPostBaccInterest * 365);
            if (daysForPostBaccInterest > 0) {
                postBaccLoanInterest = postBaccCost * Math.pow(1 + dailyRate, daysForPostBaccInterest) - postBaccCost;
            }

            // Years between post-bacc and med school (working again)
            for (int year = 1; year <= yearsUntilMedSchool; year++) {
                currentYear++;
                currentYearSalary *= (1 + Math.max(annualRaise, inflationRate));
            }
        }

        // Phase 3: Medical school years
        for (int year = 1; year <= medSchoolYears; year++) {
            currentYear++;
            double yearlyOpportunityCost = currentYearSalary;
            // Fix: Use more realistic retirement contribution assumption during training
            double missedRetirementContribution = currentYearSalary * retirementContributionRate * 0.5; // Reduced during training

            int yearsToGrow = yearsUntilRetirement - currentYear + 1;
            if (yearsToGrow > 0) {
                double dailyInvestmentRate = investmentReturnRate / 365.0;
                int daysToGrow = yearsToGrow * 365;
                cumulativeLostRetirement += missedRetirementContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
            }

            totalOpportunityCost += yearlyOpportunityCost;
            // Fix: Apply inflation-adjusted salary growth consistently
            currentYearSalary *= (1 + Math.max(annualRaise, inflationRate));
        }

        // Phase 4: Residency years
        for (int year = 1; year <= residencyYears; year++) {
            currentYear++;
            double yearlyOpportunityCost = currentYearSalary - residencySalary;
            // Fix: More realistic retirement contribution during residency
            // Assume they contribute based on actual income, not missed opportunity
            double actualRetirementContribution = residencySalary * retirementContributionRate;
            double missedRetirementContribution = currentYearSalary * retirementContributionRate * 0.3; // Reduced opportunity cost
            double netMissedContribution = missedRetirementContribution - actualRetirementContribution;

            int yearsToGrow = yearsUntilRetirement - currentYear + 1;
            if (yearsToGrow > 0) {
                double dailyInvestmentRate = investmentReturnRate / 365.0;
                int daysToGrow = yearsToGrow * 365;
                cumulativeLostRetirement += netMissedContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
            }

            totalOpportunityCost += yearlyOpportunityCost;
            // Fix: Apply inflation-adjusted salary growth consistently
            currentYearSalary *= (1 + Math.max(annualRaise, inflationRate));
        }

        // Phase 5: Fellowship years (if needed)
        if (needsFellowship) {
            for (int year = 1; year <= fellowshipYears; year++) {
                currentYear++;
                double yearlyOpportunityCost = currentYearSalary - fellowshipSalary;
                // Fix: More realistic retirement contribution during fellowship
                double actualRetirementContribution = fellowshipSalary * retirementContributionRate;
                double missedRetirementContribution = currentYearSalary * retirementContributionRate * 0.3; // Reduced opportunity cost
                double netMissedContribution = missedRetirementContribution - actualRetirementContribution;

                int yearsToGrow = yearsUntilRetirement - currentYear + 1;
                if (yearsToGrow > 0) {
                    double dailyInvestmentRate = investmentReturnRate / 365.0;
                    int daysToGrow = yearsToGrow * 365;
                    cumulativeLostRetirement += netMissedContribution * Math.pow(1 + dailyInvestmentRate, daysToGrow);
                }

                totalOpportunityCost += yearlyOpportunityCost;
                // Fix: Apply inflation-adjusted salary growth consistently
                currentYearSalary *= (1 + Math.max(annualRaise, inflationRate));
            }
        }

        // Fix: More realistic medical school loan interest calculation
        // Assume 6-month grace period after graduation before interest starts
        double yearsForMedSchoolInterest = Math.max(0, residencyYears - 0.5) + (needsFellowship ? fellowshipYears : 0);
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