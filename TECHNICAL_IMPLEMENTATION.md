# Technical Implementation Guide

This document provides detailed technical information about the implementation of the Medical School Opportunity Cost Calculator, focusing on the sophisticated financial modeling, loan repayment simulation, and system architecture.

## System Architecture

### Core Components

#### 1. Main.java - Application Controller
- **Menu System**: Three-tier menu with calculation, defaults management, and exit options
- **Input Flow Control**: Routes users between quick calculation (defaults) and full input review
- **Defaults Management**: Comprehensive system for modifying and persisting user preferences

#### 2. MDCalc.java - Financial Calculation Engine
- **Opportunity Cost Calculation**: Multi-phase timeline modeling with daily compounding
- **Loan Repayment Simulation**: Sophisticated amortization with monthly payment tracking
- **Retirement Projections**: Daily-compounded growth to configurable retirement age
- **Inflation Modeling**: Realistic salary growth with inflation floors for medical careers

#### 3. DefaultsManager.java - Configuration Persistence
- **Properties File Storage**: Automatic saving/loading of user preferences
- **Input Validation**: Bounds checking for all saved parameters
- **Path Resolution**: Intelligent file location detection for cross-platform compatibility

## Financial Modeling Implementation

### Daily Compounding Calculations

The application uses daily compounding for maximum accuracy in all financial calculations:

```java
// Daily rate calculation
double dailyInvestmentRate = investmentReturnRate / 365.0;
int daysToGrow = yearsToGrow * 365;

// Future value with daily compounding
double futureValue = principal * Math.pow(1 + dailyInvestmentRate, daysToGrow);
```

**Benefits:**
- More accurate than monthly or annual compounding
- Captures the true time value of money
- Industry standard for financial modeling

### Timeline Phase Modeling

The calculator models five distinct phases with proper opportunity cost calculations:

#### Phase 1: Pre-Training Career
```java
// Years working before training begins
int yearsWorking = needsPostBacc ? yearsUntilPostBacc : yearsUntilMedSchool;
for (int year = 1; year <= yearsWorking; year++) {
    currentYear++;
    currentYearSalary *= (1 + annualRaise);
}
```

#### Phase 2: Post-Bacc (if applicable)
```java
if (needsPostBacc) {
    for (int year = 1; year <= postBaccYears; year++) {
        currentYear++;
        double yearlyOpportunityCost = currentYearSalary;
        double missedRetirementContribution = currentYearSalary * retirementContributionRate;
        
        // Project to retirement age with daily compounding
        int yearsToGrow = yearsUntilRetirement - currentYear + 1;
        if (yearsToGrow > 0) {
            double dailyInvestmentRate = investmentReturnRate / 365.0;
            int daysToGrow = yearsToGrow * 365;
            cumulativeLostRetirement += missedRetributionContribution * 
                Math.pow(1 + dailyInvestmentRate, daysToGrow);
        }
        
        totalOpportunityCost += yearlyOpportunityCost;
        currentYearSalary *= (1 + annualRaise);
    }
}
```

#### Phase 3: Medical School
```java
for (int year = 1; year <= medSchoolYears; year++) {
    currentYear++;
    double yearlyOpportunityCost = currentYearSalary;
    double missedRetirementContribution = currentYearSalary * retirementContributionRate;
    
    // Same retirement projection logic
    // ... (similar to post-bacc phase)
    
    totalOpportunityCost += yearlyOpportunityCost;
    currentYearSalary *= (1 + annualRaise);
}
```

#### Phase 4: Residency
```java
for (int year = 1; year <= residencyYears; year++) {
    currentYear++;
    // Net opportunity cost (salary - residency pay)
    double yearlyOpportunityCost = currentYearSalary - residencySalary;
    double netMissedContribution = (currentYearSalary - residencySalary) * retirementContributionRate;
    
    // Project to retirement age
    // ... (similar logic)
    
    totalOpportunityCost += yearlyOpportunityCost;
    currentYearSalary *= (1 + annualRaise);
}
```

#### Phase 5: Fellowship (if applicable)
```java
if (needsFellowship) {
    for (int year = 1; year <= fellowshipYears; year++) {
        currentYear++;
        double yearlyOpportunityCost = currentYearSalary - fellowshipSalary;
        double netMissedContribution = (currentYearSalary - fellowshipSalary) * retirementContributionRate;
        
        // Project to retirement age
        // ... (similar logic)
        
        totalOpportunityCost += yearlyOpportunityCost;
        currentYearSalary *= (1 + annualRaise);
    }
}
```

### Loan Interest Accrual During Training

#### Post-Bacc Loan Interest
```java
if (needsPostBacc) {
    // Calculate total years interest accrues (post-bacc through end of training)
    double yearsForPostBaccInterest = postBaccYears + yearsUntilMedSchool + 
        medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
    
    double dailyRate = loanInterestRate / 365.0;
    int daysForPostBaccInterest = (int)(yearsForPostBaccInterest * 365);
    
    if (daysForPostBaccInterest > 0) {
        postBaccLoanInterest = postBaccCost * 
            Math.pow(1 + dailyRate, daysForPostBaccInterest) - postBaccCost;
    }
}
```

#### Medical School Loan Interest
```java
// Calculate total years interest accrues (med school through end of training)
double yearsForMedSchoolInterest = medSchoolYears + residencyYears + 
    (needsFellowship ? fellowshipYears : 0);

double dailyLoanRate = loanInterestRate / 365.0;
int daysForMedSchoolInterest = (int)(yearsForMedSchoolInterest * 365);

if (daysForMedSchoolInterest > 0) {
    cumulativeLoanInterest = totalLoans * 
        Math.pow(1 + dailyLoanRate, daysForMedSchoolInterest) - totalLoans;
}
```

## Enhanced Loan Repayment Simulation

### Monthly Payment Calculation

The application uses the standard loan payment formula for accurate monthly payment calculation:

```java
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
```

### Monthly Repayment Simulation

The application simulates each monthly payment to track interest accrual during repayment:

```java
private RepaymentSummary simulateRepayment(double startingBalance, double monthlyPayment, 
                                         double annualRate, int yearsPlanned) {
    if (startingBalance <= 0 || monthlyPayment <= 0) {
        return new RepaymentSummary(0, 0, Math.max(0, startingBalance));
    }

    double monthlyRate = annualRate / 12.0;
    int maxMonths = Math.max(1, yearsPlanned * 12 + 120); // Allow extra months, cap runaway

    double balance = startingBalance;
    double totalInterest = 0;
    int months = 0;

    for (; months < maxMonths && balance > 0.005; months++) {
        double interest = balance * monthlyRate;
        totalInterest += interest;

        double principal = monthlyPayment - interest;
        if (principal <= 0) {
            // Payment too low to amortize; break to avoid infinite loop
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
```

### Auto-Alignment to Retirement Age

The application can automatically adjust loan repayment terms to align with retirement:

```java
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
```

## Inflation Modeling

### Salary Growth with Inflation Floors

The application models realistic salary growth that accounts for economic reality:

```java
// Physician salaries grow at max(raises, inflation); alternatives grow at raises only
physicianSalary *= (1 + Math.max(annualRaise, inflationRate));
nonMDSalary *= (1 + annualRaise);
```

**Rationale:**
- Medical careers typically maintain purchasing power over time
- Higher inflation scenarios tend to favor the medical career path
- Reflects the economic reality of professional service industries

## Input Validation System

### Comprehensive Bounds Checking

The DefaultsManager includes validation for all critical parameters:

```java
private static int validateInt(String key, int value, int def) {
    switch (key) {
        case Keys.CURRENT_AGE:
            return (value >= 18 && value <= 70) ? value : def;
        case Keys.MED_SCHOOL_YEARS:
            return (value >= 3 && value <= 5) ? value : def;
        case Keys.RESIDENCY_YEARS:
            return (value >= 3 && value <= 7) ? value : def;
        case Keys.FELLOWSHIP_YEARS:
            return (value >= 1 && value <= 4) ? value : def;
        case Keys.POST_BACC_YEARS:
            return (value >= 1 && value <= 4) ? value : def;
        case Keys.YEARS_UNTIL_POST_BACC:
        case Keys.YEARS_UNTIL_MED_SCHOOL:
            return (value >= 0 && value <= 10) ? value : def;
        case Keys.LOAN_REPAYMENT_YEARS:
            return (value >= 5 && value <= 30) ? value : def;
        case Keys.RETIREMENT_AGE:
            return (value >= 50 && value <= 80) ? value : def;
        default:
            return value;
    }
}
```

### Double Validation

```java
private static double validateDouble(String key, double value, double def) {
    switch (key) {
        case Keys.CURRENT_SALARY:
            return (value >= 20000 && value <= 1000000) ? value : def;
        case Keys.RESIDENCY_SALARY:
            return (value >= 40000 && value <= 80000) ? value : def;
        case Keys.FELLOWSHIP_SALARY:
            return (value >= 60000 && value <= 120000) ? value : def;
        case Keys.PHYSICIAN_STARTING_SALARY:
            return (value >= 150000 && value <= 800000) ? value : def;
        case Keys.POST_BACC_COST:
            return (value >= 10000 && value <= 200000) ? value : def;
        case Keys.TOTAL_LOANS:
            return (value >= 0 && value <= 1500000) ? value : def;
        case Keys.RETIREMENT_CONTRIB_RATE:
            return (value >= 0.05 && value <= 0.50) ? value : def;
        case Keys.INVESTMENT_RETURN_RATE:
            return (value >= 0.03 && value <= 0.15) ? value : def;
        case Keys.INFLATION_RATE:
            return (value >= 0.0 && value <= 0.15) ? value : def;
        case Keys.ANNUAL_RAISE:
            return (value >= 0.0 && value <= 0.20) ? value : def;
        case Keys.LOAN_INTEREST_RATE:
            return (value >= 0.0 && value <= 0.15) ? value : def;
        default:
            return value;
    }
}
```

## Break-Even Analysis

### Cumulative Income Comparison

The break-even calculation accounts for ongoing loan payments and salary growth:

```java
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

        // Physician salaries grow with max(raises, inflation); counterfactual grows with raises only
        physicianSalary *= (1 + Math.max(annualRaise, inflationRate));
        nonMDSalary *= (1 + annualRaise);
    }

    int totalTimeToBecomeMD = (needsPostBacc ? yearsUntilPostBacc + postBaccYears + yearsUntilMedSchool : yearsUntilMedSchool) +
            medSchoolYears + residencyYears + (needsFellowship ? fellowshipYears : 0);
    return currentAge + totalTimeToBecomeMD + yearsAfterTraining;
}
```

## Performance Considerations

### Computational Efficiency

- **Daily compounding**: Uses efficient Math.pow() function for exponential calculations
- **Loop optimization**: Minimizes redundant calculations in timeline phases
- **Memory management**: Efficient object creation and garbage collection
- **Input validation**: Early bounds checking prevents unnecessary calculations

### Scalability

- **Timeline flexibility**: Supports training paths from 7 to 15+ years
- **Age range**: Accommodates students from 18 to 70 years old
- **Salary ranges**: Handles incomes from $20K to $1M+
- **Retirement planning**: Supports retirement ages from 50 to 80

## Error Handling and Robustness

### Graceful Degradation

```java
try {
    // Load saved defaults
    this.currentAge = DefaultsManager.getInt(DefaultsManager.Keys.CURRENT_AGE, this.currentAge);
    // ... other defaults
} catch (Throwable ignored) {
    // If defaults file is missing or malformed, just keep built-in defaults.
}
```

### Input Validation

```java
if (this.currentAge < 18 || this.currentAge > 70) {
    System.out.println("WARNING: Age should be between 18-70. Adjusting to valid range.");
    this.currentAge = Math.max(18, Math.min(70, this.currentAge));
}
```

### Loan Payment Validation

```java
if (principal <= 0) {
    // Payment too low to amortize; bump months and break to avoid infinite loop
    break;
}
```

## Future Enhancement Opportunities

### Potential Improvements

1. **Tax Modeling**: Include federal and state tax considerations
2. **Geographic Cost of Living**: Regional salary and cost adjustments
3. **Specialty-Specific Data**: Real-time salary data from medical associations
4. **Investment Portfolio Modeling**: More sophisticated retirement investment strategies
5. **Loan Refinancing Scenarios**: Compare different loan consolidation options
6. **Part-Time Practice Options**: Model reduced hours and income scenarios
7. **Export Functionality**: Generate reports in PDF or spreadsheet formats

### Technical Enhancements

1. **Database Integration**: Store user profiles and calculation history
2. **Web Interface**: Browser-based version with real-time updates
3. **API Development**: REST endpoints for integration with other financial tools
4. **Mobile Application**: Native iOS/Android apps for on-the-go planning
5. **Cloud Synchronization**: Multi-device access to saved defaults and results

## Conclusion

The Medical School Opportunity Cost Calculator represents a sophisticated financial modeling application that combines:

- **Mathematical rigor**: Daily compounding, proper amortization, and realistic projections
- **User experience**: Comprehensive defaults system, input validation, and clear results
- **Professional features**: TL;DR summaries, professional formatting, and actionable warnings
- **Educational value**: Transparent methodology and comprehensive cost breakdown

The implementation demonstrates best practices in financial software development, including robust error handling, comprehensive validation, and maintainable code structure. The enhanced loan repayment simulation provides users with accurate projections that account for both deferment and repayment phase interest, making it a valuable tool for serious financial planning.
