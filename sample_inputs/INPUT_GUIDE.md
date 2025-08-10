# Input Guide

This guide explains each input parameter and provides examples for different scenarios.

## **NEW**: Set Defaults Feature

The calculator now includes a **Set Defaults** menu option that allows you to:
- Customize default values for all parameters
- Save your preferences to a local file (`mdcalc.defaults.properties`)
- Avoid re-entering common values for multiple calculations
- Easily compare different scenarios with your personalized baseline

**Usage**: Select option `2` from the main menu to configure your defaults before running calculations.

## Input Parameter Explanations

### Basic Information
1. **Menu Selection**: `1` for Calculate Opportunity Cost, `2` for Set Defaults, `3` to Exit
2. **Current Age**: Your age today (e.g., `30`)
3. **Current Annual Salary**: Your current gross annual income (e.g., `80000`)

### Post-Bacc Program
4. **Need Post-Bacc**: `y` if you need prerequisite coursework, `n` if not
5. **Years Until Post-Bacc**: How long until you start post-bacc (e.g., `1`)
6. **Post-Bacc Duration**: How many years the program takes (e.g., `2`)
7. **Post-Bacc Cost**: Total cost including tuition, fees, living expenses (e.g., `60000`)
8. **Gap Before Med School**: Years between post-bacc completion and medical school (e.g., `1`)

### Medical School Planning
9. **Years Until Med School**: If no post-bacc, years until starting medical school (e.g., `1`)
10. **Annual Raise %**: Expected yearly salary increases in current career (e.g., `3` for 3%)
11. **Inflation Rate**: Expected annual inflation rate (e.g., `3` for 3%) - **NEW**
12. **Loan Interest Rate**: Average interest rate on student loans (e.g., `6` for 6%)
13. **Medical School Loans**: Total borrowed for medical school (e.g., `300000`)

### Training Program Details
14. **Residency Years**: Duration of residency program (e.g., `3`)
15. **Residency Salary**: Annual income during residency (default `60000`)
16. **Need Fellowship**: `y` for subspecialty training, `n` for direct practice
17. **Fellowship Duration**: Years of fellowship if applicable (e.g., `2`)
18. **Fellowship Salary**: Annual income during fellowship (default `90000`)
19. **Starting Physician Salary**: Expected first-year attending salary (e.g., `350000`)

### Financial Assumptions
20. **Retirement Contribution %**: Percent of salary saved for retirement (default `15`, enter `0` to keep default)
21. **Investment Return %**: Expected annual return on retirement investments (default `7`, enter `0` to keep default)
22. **Retirement Age**: Your planned retirement age (default `65`, enter `0` to keep default)
23. **Loan Repayment Period**: Years to pay back student loans (default `10`, enter `0` to keep default)
24. **Monthly Loan Payment**: Enter `0` to auto-calculate based on standard loan payment formula
25. **Auto-Align Loan Payoff**: `y` to extend loan term until retirement age, `n` for standard term

## Sample Scenarios

### Traditional Path (traditional_path.txt)
- Age 25, recent college graduate
- $75K current salary in entry-level position  
- No post-bacc needed, strong science background
- Standard 3-year residency, no fellowship
- Conservative estimates for a general practice physician

### Post-Bacc + Fellowship (postbacc_fellowship.txt)
- Age 30, career changer
- $80K current salary in non-medical field
- 2-year post-bacc program required
- 2-year fellowship for subspecialty
- Higher attending salary reflecting subspecialty training
- Standard retirement at age 65, traditional 10-year loan repayment

### Non-Traditional, No Post-Bacc (nontraditional_no_postbacc.txt)
- Age 32, established professional
- $90K current salary, higher current income
- No post-bacc needed (previous science coursework)
- 4-year residency (longer specialty program)
- Higher retirement contribution rate (20%) due to financial sophistication
- More aggressive investment assumptions (8% return)
- **Late retirement at age 70** to maximize earning years
- **Auto-aligned loan repayment** to retirement age for lower monthly payments

### Early Retirement (early_retirement.txt)
- Age 28, high earner planning FIRE (Financial Independence, Retire Early)
- $85K current salary with aggressive savings rate
- No post-bacc, 1-year fellowship
- **Early retirement at age 55** - shows massive opportunity cost impact
- High retirement contribution (18%) and investment return (8%) assumptions
- **Auto-aligned loan repayment** to retirement for comparison

## Tips for Accurate Estimates

### Salary Projections
- Research average salaries in your target specialty and region
- Consider geographic cost of living differences
- Account for potential subspecialty training premium

### Loan Estimates
- Include all educational costs: tuition, fees, living expenses, books
- Research current federal loan rates (typically 5-7%)
- Consider state-specific loan programs with different rates

### Financial Assumptions
- 15% retirement savings is a common recommendation
- 7% long-term stock market return is a historical average
- **3% inflation** is the Federal Reserve's long-term target
- Adjust based on your risk tolerance and investment strategy

### **NEW**: Inflation Rate Impact
The inflation rate has a **critical** impact on calculations:
- **Physician salaries** grow at the **maximum** of your expected raises OR inflation rate
- **Non-MD salaries** grow at your specified raise rate only
- This models the reality that medical careers typically keep pace with or exceed inflation
- Higher inflation assumptions favor the medical career path

### Timeline Considerations
- Be realistic about MCAT preparation and application timing
- Account for potential gap years or delayed starts
- Consider competitive residency match probabilities

### Retirement Planning
- **Early retirement (50-60)**: Dramatically increases opportunity cost due to fewer earning years
- **Standard retirement (65-67)**: Balanced approach for most scenarios
- **Late retirement (70+)**: Maximizes physician earning potential, reduces opportunity cost
- **Auto-align loans**: Compare extended repayment vs. aggressive payoff strategies

## Understanding the Results

The calculator provides several key metrics:

- **Total Opportunity Cost**: Complete financial impact of medical training
- **Break-Even Age**: When physician income recovers all costs
- **Retirement Impact %**: What portion of cost comes from lost retirement savings
- **Timeline**: Clear progression through each training phase

Remember that this is a planning tool - actual results will vary based on individual circumstances, economic conditions, and career decisions.