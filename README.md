# Medical School Opportunity Cost Calculator

A comprehensive Java console application that calculates the true financial opportunity cost of pursuing medical school as a non-traditional student.

## Overview

This calculator provides a detailed financial analysis for aspiring physicians, accounting for:
- **Lost wages** during training years
- **Retirement savings opportunity costs** with compound growth projections
- **Student loan interest** with daily compounding and proper deferment periods
- **Post-bacc program costs** and timeline impacts
- **Break-even analysis** to determine when physician income recovers total costs

## Features

### Comprehensive Training Path Support
- **Post-bacc programs**: Optional pre-medical coursework with customizable duration and cost
- **Medical school**: 4-year program with configurable loan amounts
- **Residency**: Customizable duration (default 3 years) with salary considerations
- **Fellowship**: Optional subspecialty training with extended timeline

### Advanced Financial Modeling
- **Daily compounding**: Both loan interest and investment returns use daily compounding for accuracy
- **Timeline-based calculations**: Accounts for actual matriculation timing and career progression
- **Retirement projections**: Projects missed contributions to age 65 with compound growth
- **Salary growth modeling**: Annual raises applied throughout career timeline

### Detailed Cost Breakdown
- Direct opportunity cost (lost wages)
- Lost retirement savings (projected to retirement)
- Student loan principal and interest
- Post-bacc program costs
- Total opportunity cost with break-even age analysis

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
The application will prompt for the following information:

1. **Personal Information**
   - Current age
   - Current annual salary

2. **Post-Bacc Program (if applicable)**
   - Years until starting post-bacc
   - Duration of post-bacc program
   - Total cost of post-bacc program
   - Gap years between post-bacc and medical school

3. **Medical School Planning**
   - Years until starting medical school (if no post-bacc)
   - Expected annual salary raises
   - Student loan interest rate
   - Total medical school loan amount

4. **Residency and Fellowship**
   - Years of residency training
   - Residency salary confirmation
   - Fellowship requirements and duration
   - Fellowship salary confirmation
   - Expected starting physician salary

5. **Financial Assumptions**
   - Retirement contribution rate (default: 15%)
   - Investment return rate (default: 7%)

### Sample Input Files
Three sample scenarios are provided in the `sample_inputs/` directory:

- `traditional_path.txt`: Young student (25), no post-bacc, no fellowship
- `postbacc_fellowship.txt`: Career changer (30) with post-bacc and fellowship
- `nontraditional_no_postbacc.txt`: Older career changer (32), higher income, no post-bacc

To use sample inputs:
```bash
java Main < sample_inputs/traditional_path.txt
```

## Sample Output

```
=== OPPORTUNITY COST ANALYSIS ===
Training path: 2 years post-bacc + 4 years med school + 3 years residency + 2 years fellowship
Age when starting as physician: 43 years old
Timeline: Start post-bacc at age 31, med school at age 34
Retirement assumptions: 15% contribution rate, 7% annual return (daily compounding)
----------------------------------------
Direct opportunity cost (lost wages): $722,005.08
Lost retirement savings (projected to age 65): $865,292.96
Post-bacc loans: $60,000.00
Medical school loans: $300,000.00
Total loan interest (daily compounding with deferment): $278,037.91
----------------------------------------
TOTAL OPPORTUNITY COST: $2,225,335.94
ESTIMATED BREAK-EVEN AGE: 52 years old
Years of physician earnings until retirement: 22 years
Retirement losses represent 38.9% of total opportunity cost
```

## Financial Methodology

### Interest Calculations
All interest calculations use **daily compounding** for maximum accuracy:
- **Student loans**: Interest accrues daily from loan origination through deferment periods
- **Investment returns**: Retirement contributions compound daily until retirement at age 65

### Timeline Phases
The calculator models five distinct phases:

1. **Pre-training career**: Working and earning normal salary with raises
2. **Post-bacc years**: Full opportunity cost, post-bacc loans begin accruing interest
3. **Medical school**: Full opportunity cost, medical school loans begin accruing interest
4. **Residency**: Reduced opportunity cost (salary - residency pay), all loans continue accruing
5. **Fellowship**: Reduced opportunity cost (salary - fellowship pay), all loans continue accruing

### Retirement Opportunity Cost
Each missed retirement contribution is projected to retirement age (65) using daily compounding:
```
Future Value = Missed_Contribution × (1 + daily_rate)^days_until_retirement
```

### Break-Even Analysis
The calculator determines when cumulative physician earnings exceed the non-MD career path plus total opportunity costs, accounting for continued salary growth in both scenarios.

## Key Assumptions

- **Retirement age**: 65 years old
- **Loan deferment**: Interest accrues but no payments during training
- **Daily compounding**: 365 days per year for all financial calculations
- **Salary growth**: Applied consistently throughout timeline
- **Career progression**: Immediate transition between training phases

## Educational Purpose

This calculator is designed for **educational and planning purposes only**. Financial decisions should be made in consultation with qualified financial advisors. Individual circumstances may vary significantly from model assumptions.

## Project Structure

```
non_trad_md_opportunity_cost_calc/
├── CostCalc/
│   └── src/
│       ├── Main.java          # Application entry point and menu system
│       └── MDCalc.java        # Core calculation engine and financial modeling
├── sample_inputs/
│   ├── traditional_path.txt
│   ├── postbacc_fellowship.txt
│   └── nontraditional_no_postbacc.txt
└── README.md                  # This file
```

## Version History

- **v1.0**: Initial implementation with basic opportunity cost calculation
- **v2.0**: Added retirement savings projections and enhanced loan interest modeling
- **v3.0**: Implemented post-bacc program support and timeline-based calculations
- **v4.0**: Enhanced with daily compounding for all financial calculations

## Contributing

This is an educational project. Suggestions for improving accuracy or adding features are welcome through standard GitHub contribution processes.

## License

This project is provided as-is for educational purposes. No warranty is provided regarding the accuracy of financial projections.