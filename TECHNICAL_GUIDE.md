# Technical Implementation Guide

This document provides technical details about the Medical School Opportunity Cost Calculator's architecture, algorithms, and implementation.

## 🏗️ Architecture Overview

### Class Structure
```
Main.java              # Application entry point and user interface
├── MDCalc.java        # Core calculation engine
├── DefaultsManager.java # Configuration and persistence management
└── mdcalc.defaults.properties # User preferences storage
```

### Design Patterns
- **Singleton Pattern**: DefaultsManager for configuration management
- **Strategy Pattern**: Different calculation approaches for various scenarios
- **Builder Pattern**: OpportunityCostResult construction
- **Observer Pattern**: Result display and optimization suggestions

## 🔧 Core Components

### Main.java - User Interface Layer
**Purpose**: Handles user interaction and menu navigation

**Key Methods**:
- `main()`: Application entry point
- `calculateOpportunityCost()`: Main calculation flow
- `setDefaults()`: Configuration management
- `modify*Defaults()`: Specific category modifications

**Input Validation**:
```java
// Age validation with range checking
if (this.currentAge < 18 || this.currentAge > 70) {
    System.out.println("WARNING: Age should be between 18-70. Adjusting to valid range.");
    this.currentAge = Math.max(18, Math.min(70, this.currentAge));
}

// Salary validation
if (tmpSalary > 0) this.currentSalary = tmpSalary;
```

### MDCalc.java - Calculation Engine
**Purpose**: Performs all financial calculations and analysis

**Core Algorithm**:
1. **Timeline Calculation**: Determine key milestones
2. **Lost Wages Calculation**: Project career earnings
3. **Retirement Impact**: Compound growth of savings
4. **Loan Analysis**: Interest and repayment costs
5. **Net Impact**: Total opportunity cost

**Key Methods**:
- `calculateOpportunityCost()`: Main calculation orchestrator
- `calculateLostWages()`: Career earnings projection
- `calculateRetirementImpact()`: Savings compound growth
- `calculateLoanCosts()`: Debt interest analysis
- `displayResults()`: Results presentation

### DefaultsManager.java - Configuration Management
**Purpose**: Manages user preferences and application defaults

**Features**:
- **Persistence**: Saves settings to properties file
- **Validation**: Ensures reasonable value ranges
- **Fallbacks**: Built-in defaults when user settings missing
- **Type Safety**: Strong typing for all configuration values

**Implementation**:
```java
public class DefaultsManager {
    private static final String DEFAULTS_FILE = "mdcalc.defaults.properties";
    private static Properties props = new Properties();
    
    public enum Keys {
        CURRENT_AGE, CURRENT_SALARY, MED_SCHOOL_YEARS,
        RESIDENCY_YEARS, PHYSICIAN_STARTING_SALARY,
        // ... additional keys
    }
    
    public static void setInt(Keys key, int value) {
        props.setProperty(key.name(), String.valueOf(value));
    }
    
    public static int getInt(Keys key, int defaultValue) {
        String value = props.getProperty(key.name());
        return value != null ? Integer.parseInt(value) : defaultValue;
    }
}
```

## 🧮 Calculation Algorithms

### Timeline Calculation
**Purpose**: Determine key milestones in medical training

**Algorithm**:
```java
// Calculate timeline milestones
int ageAtMedSchoolStart = currentAge + yearsUntilMedSchool;
int ageAtResidencyStart = ageAtMedSchoolStart + medSchoolYears;
int ageAtFellowshipStart = needsFellowship ? 
    ageAtResidencyStart + residencyYears : -1;
int ageAtAttendingStart = needsFellowship ? 
    ageAtFellowshipStart + fellowshipYears : ageAtResidencyStart;
```

**Key Considerations**:
- Post-bacc timing affects overall timeline
- Fellowship adds years but may increase earning potential
- Residency length varies by specialty (3-7 years)

### Lost Wages Calculation
**Purpose**: Project earnings if career change not pursued

**Algorithm**:
```java
private double calculateLostWages() {
    double totalLostWages = 0;
    double currentYearSalary = currentSalary;
    
    // Calculate lost wages for each training period
    for (int year = 1; year <= totalTrainingYears; year++) {
        totalLostWages += currentYearSalary;
        currentYearSalary *= (1 + annualRaise);
    }
    
    return totalLostWages;
}
```

**Factors Considered**:
- **Annual raises**: Career progression in current field
- **Inflation**: Cost of living increases
- **Training duration**: Total years of reduced/no income
- **Salary growth**: Compound effect of raises over time

### Retirement Impact Calculation
**Purpose**: Estimate lost retirement savings and compound growth

**Algorithm**:
```java
private double calculateRetirementImpact() {
    double totalLostRetirement = 0;
    double currentYearSalary = currentSalary;
    
    for (int year = 1; year <= totalTrainingYears; year++) {
        double annualContribution = currentYearSalary * retirementContributionRate;
        int yearsUntilRetirement = retirementAge - (currentAge + year);
        double compoundGrowth = Math.pow(1 + investmentReturnRate, yearsUntilRetirement);
        totalLostRetirement += annualContribution * compoundGrowth;
        
        currentYearSalary *= (1 + annualRaise);
    }
    
    return totalLostRetirement;
}
```

**Key Concepts**:
- **Compound growth**: Exponential increase over time
- **Investment returns**: Expected annual return on retirement savings
- **Time horizon**: Years until retirement affects growth potential
- **Contribution rate**: Percentage of salary saved for retirement

### Loan Cost Analysis
**Purpose**: Calculate total cost of medical school debt

**Algorithm**:
```java
private double calculateLoanCosts() {
    // Calculate monthly payment using amortization formula
    double monthlyRate = loanInterestRate / 12;
    int totalPayments = loanRepaymentYears * 12;
    
    if (monthlyRate > 0) {
        monthlyLoanPayment = totalLoans * 
            (monthlyRate * Math.pow(1 + monthlyRate, totalPayments)) /
            (Math.pow(1 + monthlyRate, totalPayments) - 1);
    } else {
        monthlyLoanPayment = totalLoans / totalPayments;
    }
    
    double totalPaid = monthlyLoanPayment * totalPayments;
    return totalPaid - totalLoans; // Return interest only
}
```

**Financial Mathematics**:
- **Amortization**: Equal monthly payments with decreasing interest
- **Compound interest**: Interest on interest over time
- **Payment calculation**: Standard loan payment formula
- **Total cost**: Principal + all interest paid

## 📊 Data Structures

### OpportunityCostResult
**Purpose**: Encapsulate calculation results for display and analysis

**Structure**:
```java
public static class OpportunityCostResult {
    // Timeline information
    public int ageAtMedSchoolStart;
    public int ageAtResidencyStart;
    public int ageAtFellowshipStart;
    public int ageAtAttendingStart;
    
    // Financial impact
    public double lostWages;
    public double lostRetirement;
    public double trainingCosts;
    public double loanInterest;
    public double totalOpportunityCost;
    
    // Optimization data
    public double breakEvenAge;
    public double netWorthAtRetirement;
}
```

### RepaymentSummary
**Purpose**: Track loan repayment details

**Structure**:
```java
public static class RepaymentSummary {
    public double monthlyPayment;
    public double totalInterest;
    public double totalPaid;
    public int repaymentYears;
    public double interestRate;
}
```

## 🔄 Configuration Management

### Properties File Format
**File**: `mdcalc.defaults.properties`

**Format**:
```properties
# Personal Information
CURRENT_AGE=30
CURRENT_SALARY=75000.0
ANNUAL_RAISE=0.03

# Training Path
MED_SCHOOL_YEARS=4
RESIDENCY_YEARS=3
NEEDS_FELLOWSHIP=false

# Financial Assumptions
RETIREMENT_CONTRIB_RATE=0.15
INVESTMENT_RETURN_RATE=0.07
INFLATION_RATE=0.03

# Loan Settings
TOTAL_LOANS=300000.0
LOAN_INTEREST_RATE=0.06
LOAN_REPAYMENT_YEARS=10
```

### Default Value Management
**Built-in Defaults**: Hard-coded fallback values
**User Preferences**: Saved in properties file
**Validation**: Range checking and type safety
**Persistence**: Automatic saving of user changes

## 🚀 Performance Considerations

### Calculation Efficiency
- **Time Complexity**: O(n) where n = training years
- **Space Complexity**: O(1) for most calculations
- **Memory Usage**: Minimal object creation during calculations
- **Caching**: Results cached for optimization suggestions

### Optimization Strategies
- **Lazy Evaluation**: Calculations performed only when needed
- **Efficient Loops**: Single-pass calculations where possible
- **Minimal Object Creation**: Reuse objects during calculations
- **Stream Processing**: Efficient iteration over time periods

## 🧪 Testing and Validation

### Input Validation
```java
// Age validation
if (age < 18 || age > 70) {
    throw new IllegalArgumentException("Age must be between 18-70");
}

// Salary validation
if (salary <= 0) {
    throw new IllegalArgumentException("Salary must be positive");
}

// Percentage validation
if (rate < 0 || rate > 1) {
    throw new IllegalArgumentException("Rate must be between 0-1");
}
```

### Calculation Verification
- **Unit tests**: Individual calculation methods
- **Integration tests**: End-to-end calculation flow
- **Edge cases**: Boundary conditions and error scenarios
- **Mathematical validation**: Cross-check with financial calculators

## 🔧 Extending the Calculator

### Adding New Calculation Types
1. **Extend MDCalc class**: Add new calculation methods
2. **Update OpportunityCostResult**: Include new result fields
3. **Modify display methods**: Show new calculations to users
4. **Add configuration options**: New parameters in DefaultsManager

### Example: Adding Tax Impact
```java
public class MDCalc {
    private double calculateTaxImpact() {
        // Calculate tax implications of career change
        double currentTaxBurden = currentSalary * getTaxRate(currentSalary);
        double physicianTaxBurden = physicianStartingSalary * getTaxRate(physicianStartingSalary);
        return physicianTaxBurden - currentTaxBurden;
    }
    
    private double getTaxRate(double salary) {
        // Implement progressive tax calculation
        if (salary <= 50000) return 0.15;
        if (salary <= 100000) return 0.25;
        return 0.35;
    }
}
```

### Adding New Optimization Strategies
1. **Extend optimization logic**: New strategies in displayOptimizationMenu
2. **Parameter sensitivity**: Analyze impact of changing specific values
3. **Scenario comparison**: Compare different career paths
4. **Risk analysis**: Monte Carlo simulations for uncertainty

## 🐛 Debugging and Troubleshooting

### Common Issues
- **Configuration corruption**: Delete properties file to reset
- **Calculation errors**: Check input validation and ranges
- **Memory issues**: Large time horizons may cause overflow
- **Performance problems**: Very long training periods

### Debug Mode
```java
// Enable debug output
private static final boolean DEBUG = true;

private void debugPrint(String message) {
    if (DEBUG) {
        System.out.println("[DEBUG] " + message);
    }
}
```

### Logging and Monitoring
- **Input validation**: Log all user inputs
- **Calculation steps**: Track intermediate results
- **Performance metrics**: Monitor calculation time
- **Error tracking**: Capture and log exceptions

## 📈 Future Enhancements

### Planned Features
- **Web interface**: Browser-based calculator
- **Data visualization**: Charts and graphs
- **Export functionality**: PDF reports and Excel spreadsheets
- **API integration**: Real-time salary and loan data
- **Mobile app**: iOS and Android versions

### Technical Improvements
- **Database backend**: Persistent storage and user accounts
- **Cloud deployment**: Scalable web service
- **Machine learning**: Predictive optimization suggestions
- **Real-time updates**: Live market data integration
- **Multi-currency**: International medical school support

---

This technical guide provides the foundation for understanding, maintaining, and extending the Medical School Opportunity Cost Calculator. For specific implementation questions, refer to the source code comments and inline documentation.
