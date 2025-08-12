# Medical School Opportunity Cost Calculator

A comprehensive Java console application that calculates the true financial opportunity cost of pursuing medical school as a non-traditional student, featuring persistent defaults, inflation modeling, professional-grade financial analysis, and sophisticated loan repayment simulation.

## Overview

This calculator provides a detailed financial analysis for aspiring physicians, accounting for:
- **Lost wages** during training years
- **Retirement savings opportunity costs** with compound growth projections to configurable retirement age
- **Student loan interest** with daily compounding, proper deferment periods, and **amortized repayment-phase interest**
- **Loan repayment burden** with configurable repayment terms, monthly payments, and **auto-alignment to retirement age**
- **Post-bacc program costs** and timeline impacts
- **Break-even analysis** accounting for ongoing loan payments until loans are paid off
- **Inflation modeling** that recognizes economic reality in medical career progression

## Features

### Comprehensive Training Path Support
- **Post-bacc programs**: Optional pre-medical coursework with customizable duration and cost
- **Medical school**: 4-year program with configurable loan amounts
- **Residency**: Customizable duration (default 3 years) with salary considerations
- **Fellowship**: Optional subspecialty training with extended timeline

### Advanced Financial Modeling
- **Daily compounding**: Both loan interest and investment returns use daily compounding for maximum accuracy
- **Inflation modeling**: Physician salaries grow at max(raises, inflation) while alternatives grow at raise rate only
- **Timeline-based calculations**: Accounts for actual matriculation timing and career progression
- **Configurable retirement age**: Projects missed contributions to your selected retirement age (default: 65)
- **Flexible loan repayment**: Standard terms or auto-aligned to retirement age for optimal planning
- **Salary growth modeling**: Annual raises applied throughout career timeline with inflation floors
- **Input validation**: Robust error handling and bounds checking for all user inputs

### **NEW**: Enhanced Loan Repayment Simulation
- **Amortized interest calculations**: Properly models interest accrual during repayment phase
- **Monthly payment simulation**: Detailed month-by-month loan payoff tracking
- **Auto-aligned repayment**: Option to extend loan term to retirement age for lower monthly burden
- **Repayment burden analysis**: Warnings when loan payments exceed 50% of first-year physician income
- **Complete interest tracking**: Captures both deferment and repayment phase interest
- **Post-calculation optimization menu**: Interactive suggestions for reaching break-even earlier

### Professional Features
- **Persistent defaults system**: Save and customize all default values for repeated use with validation
- **TL;DR results summary**: Key metrics displayed upfront for quick analysis
- **Professional formatting**: Currency formatting with thousands separators and proper percentage display
- **Actionable warnings**: Specific guidance when break-even exceeds retirement age
- **Economic sophistication**: Inflation-adjusted salary growth modeling with realistic assumptions
- **Comprehensive menu system**: Easy navigation between calculation, defaults management, and exit
- **Optimization suggestions**: Post-calculation menu with loan repayment and salary optimization strategies

### Detailed Cost Breakdown
- Direct opportunity cost (lost wages)
- Lost retirement savings (projected to selected retirement age)
- Student loan principal and interest (deferment + repayment phases)
- Post-bacc program costs
- Total opportunity cost with break-even age analysis
- Loan repayment burden and monthly payment details

## How to Use

### Compilation and Execution
```bash
# Navigate to source directory
cd CostCalc/src

# Compile the application
javac *.java

# Run the application
java Main
```

### Interactive Input
The application features a main menu with three options:
1. **Calculate Opportunity Cost** - Run the financial analysis with choice of defaults or custom input
2. **Set Defaults** - Customize default values that persist between sessions with comprehensive validation
3. **Exit** - Quit the application

For calculations, the application offers two modes:
- **Quick calculation** using current defaults
- **Full input review** with ability to modify any parameter

### **NEW**: Post-Calculation Optimization Menu

After displaying the calculation results, the application now presents an **Optimization Suggestions** menu that helps users understand how to reach break-even earlier:

#### 1. Loan Repayment Optimization Strategies
- **Current situation analysis**: Shows break-even age, age when starting practice, and years to break-even
- **Aggressive repayment scenarios**: Compares 5-year and 7-year repayment terms vs. current strategy
- **Custom target analysis**: Allows users to specify a target break-even age
- **Payment burden warnings**: Alerts when monthly payments exceed 50% of first-year physician income
- **Years saved calculation**: Shows how many years earlier break-even can be achieved

#### 2. Target Salary Recommendations
- **Salary optimization scenarios**: Calculates target salaries needed for 5-year, 10-year, or retirement-age break-even
- **Percentage increase analysis**: Shows how much salary increase is needed to reach targets
- **Realistic salary validation**: Warns when calculated targets exceed realistic ranges
- **Strategic recommendations**: Provides actionable advice for salary negotiation and career planning

#### 3. Skip Optimization Suggestions
- Option to proceed without optimization analysis

**Example Optimization Output:**
```
=== LOAN REPAYMENT OPTIMIZATION ===
Current situation:
- Break-even age: 45 years old
- Age when starting practice: 33 years old
- Years to break-even after starting: 12 years
- Current monthly payment: $3,456.78 (10-year term)

=== AGGRESSIVE REPAYMENT SCENARIOS ===
5-year aggressive repayment:
- Monthly payment: $6,789.12
- Annual burden: $81,469.44
- New break-even age: 41 years old
- Years saved: 4

=== RECOMMENDATIONS ===
✅ Consider accelerating loan repayment to reach break-even sooner.
✅ Higher monthly payments reduce total interest and accelerate wealth building.
⚠️  Ensure payment levels are sustainable with your lifestyle and other expenses.
```

The application will prompt for the following information:

1. **Personal Information**
   - Current age (18-70, with validation)
   - Current annual salary ($20K-$1M, with validation)

2. **Post-Bacc Program (if applicable)**
   - Years until starting post-bacc (0-10, with validation)
   - Duration of post-bacc program (1-4 years, with validation)
   - Total cost of post-bacc program ($10K-$200K, with validation)
   - Gap years between post-bacc and medical school (0-5, with validation)

3. **Medical School Planning**
   - Years until starting medical school (if no post-bacc, 0-10, with validation)
   - Expected annual salary raises (0-20%, with validation)
   - **Expected inflation rate** (0-15%, with validation) - critical for realistic modeling
   - Student loan interest rate (0-15%, with validation)
   - Total medical school loan amount ($50K-$500K, with validation)

4. **Residency and Fellowship**
   - Years of residency training (3-7 years, with validation)
   - Residency salary confirmation ($40K-$80K, with validation)
   - Fellowship requirements and duration (1-4 years, with validation)
   - Fellowship salary confirmation ($60K-$120K, with validation)
   - Expected starting physician salary ($150K-$800K, with validation)

5. **Financial Assumptions**
   - Retirement contribution rate (5-50%, default: 15%, with validation)
   - Investment return rate (3-15%, default: 7%, with validation)
   - Retirement age (50-80, default: 65, fully configurable, with validation)
   - Loan repayment period (5-30 years, default: 10 years, with validation)
   - Monthly loan payment (auto-calculated using standard loan payment formula)
   - Auto-align loan payoff to retirement age (optional feature for optimal planning)

### Sample Input Files
Four sample scenarios are provided in the `sample_inputs/` directory:

- `traditional_path.txt`: Young student (25), no post-bacc, no fellowship, standard retirement
- `postbacc_fellowship.txt`: Career changer (30) with post-bacc and fellowship, standard retirement
- `nontraditional_no_postbacc.txt`: Older career changer (32), higher income, late retirement (70), auto-aligned loans
- `early_retirement.txt`: FIRE planner (28), early retirement (55), shows massive opportunity cost impact

To use sample inputs:
```bash
java Main < sample_inputs/traditional_path.txt
java Main < sample_inputs/early_retirement.txt  # See impact of early retirement
```

## Sample Output

```
=== RESULTS (TL;DR) ===
Total Opportunity Cost             : $2,225,336
Estimated Break-even Age           : 55 years
Selected Retirement Age            : 65 years
Monthly Loan Payment               : $7,084/mo
----------------------------------------

Training path: 2 years post-bacc + 4 years med school + 3 years residency + 2 years fellowship
Age when starting as physician: 43 years old
Timeline: Start post-bacc at age 31, med school at age 34
Retirement assumptions: 15% contribution rate, 7% annual return (daily compounding)
Economic assumption: 3% inflation (MD raises are floored at inflation)
----------------------------------------
Direct opportunity cost (lost wages): $722,005
Lost retirement savings (projected to age 65): $865,293
Post-bacc loans: $60,000
Medical school loans: $300,000
Total loan interest (deferment + repayment): $278,038
Total loan balance at repayment start: $638,038
Monthly loan payment (10-year repayment): $7,084/mo
Annual loan payment burden: $85,002
----------------------------------------
TOTAL OPPORTUNITY COST: $2,225,336
ESTIMATED BREAK-EVEN AGE: 55 years old
Years of physician earnings until retirement: 22 years
Retirement losses represent 38.9% of total opportunity cost
```

## Financial Methodology

### Interest Calculations
All interest calculations use **daily compounding** for maximum accuracy:
- **Student loans**: Interest accrues daily from loan origination through deferment periods
- **Investment returns**: Retirement contributions compound daily until selected retirement age
- **Repayment phase**: Monthly loan payments are simulated with proper amortization

### Timeline Phases
The calculator models five distinct phases:

1. **Pre-training career**: Working and earning normal salary with raises
2. **Post-bacc years**: Full opportunity cost, post-bacc loans begin accruing interest
3. **Medical school**: Full opportunity cost, medical school loans begin accruing interest
4. **Residency**: Reduced opportunity cost (salary - residency pay), all loans continue accruing
5. **Fellowship**: Reduced opportunity cost (salary - fellowship pay), all loans continue accruing

### **NEW**: Enhanced Loan Repayment Modeling
- **Monthly simulation**: Tracks each payment with proper principal/interest allocation
- **Amortization accuracy**: Uses standard loan payment formula with daily compounding
- **Auto-alignment**: Option to extend repayment term to retirement age for lower monthly burden
- **Burden analysis**: Warns when annual loan payments exceed 50% of first-year physician income

### Retirement Opportunity Cost
Each missed retirement contribution is projected to selected retirement age using daily compounding:
```
Future Value = Missed_Contribution × (1 + daily_rate)^days_until_retirement
```

### Break-Even Analysis
The calculator determines when cumulative physician earnings exceed the non-MD career path plus total opportunity costs, accounting for:
- Continued salary growth in both career paths with **inflation floors for physician salaries**
- **Monthly loan payments** reducing net physician income during repayment period
- Complete loan payoff after the specified repayment period
- **Configurable retirement age** affecting total earning years and retirement projections

### Inflation Modeling
A sophisticated feature that recognizes economic reality:
- **Physician salaries** grow at `max(expected_raises, inflation_rate)` 
- **Alternative career salaries** grow at `expected_raises` only
- Reflects that medical careers typically maintain purchasing power over time
- Higher inflation scenarios tend to favor the medical career path

## Key Assumptions

- **Retirement age**: User-configurable (default: 65 years old, range: 50-80)
- **Loan deferment**: Interest accrues but no payments during training
- **Daily compounding**: 365 days per year for all financial calculations
- **Salary growth**: Applied consistently throughout timeline with inflation floors
- **Career progression**: Immediate transition between training phases
- **Input validation**: Automatic bounds checking prevents invalid entries
- **Loan repayment**: Standard amortization with option for retirement-age alignment

## Educational Purpose

This calculator is designed for **educational and planning purposes only**. Financial decisions should be made in consultation with qualified financial advisors. Individual circumstances may vary significantly from model assumptions.

## Project Structure

```
non_trad_md_opportunity_cost_calc/
├── CostCalc/
│   └── src/
│       ├── Main.java          # Application entry point and comprehensive menu system
│       ├── MDCalc.java        # Core calculation engine and financial modeling
│       └── DefaultsManager.java # Persistent defaults system with validation
├── sample_inputs/
│   ├── INPUT_GUIDE.md         # Detailed parameter explanations & Set Defaults guide
│   ├── traditional_path.txt   # Standard retirement (65), with inflation modeling
│   ├── postbacc_fellowship.txt # Complex training path, inflation-adjusted
│   ├── nontraditional_no_postbacc.txt # Late retirement (70), auto-aligned loans
│   └── early_retirement.txt   # Early retirement (55), high opportunity cost
├── mdcalc.defaults.properties # Persistent user defaults (auto-generated)
├── README.md                  # This file
├── USAGE_EXAMPLES.md          # Practical usage examples and scenarios
└── TECHNICAL_IMPLEMENTATION.md # Detailed technical implementation guide
```

## Version History

- **v1.0**: Initial implementation with basic opportunity cost calculation
- **v2.0**: Added retirement savings projections and enhanced loan interest modeling
- **v3.0**: Implemented post-bacc program support and timeline-based calculations
- **v4.0**: Enhanced with daily compounding for all financial calculations
- **v5.0**: Added configurable retirement age, auto-aligned loan repayment, robust input validation
- **v6.0**: **Major release** - Persistent defaults system, inflation modeling, TL;DR results, professional formatting
- **v6.1**: **Enhanced loan modeling** - Amortized repayment-phase interest, monthly payment simulation, repayment burden analysis
- **v6.2**: **Optimization suggestions menu** - Post-calculation loan repayment and salary optimization strategies

## Contributing

This is an educational project. Suggestions for improving accuracy or adding features are welcome through standard GitHub contribution processes.

## License

This project is provided as-is for educational purposes. No warranty is provided regarding the accuracy of financial projections.