# Medical School Opportunity Cost Calculator

A comprehensive Java application that calculates the true financial impact of pursuing medical school, including lost wages, retirement savings, and loan costs over a lifetime.

## 🎯 Purpose

This calculator helps prospective medical students understand the complete financial picture of their career transition by analyzing:

- **Lost Income**: Wages you would have earned if you stayed in your current career
- **Retirement Impact**: Lost retirement savings and compound interest
- **Loan Costs**: Total medical school debt including interest
- **Training Timeline**: Post-bacc, medical school, residency, and fellowship costs
- **Long-term Financial Impact**: Net worth comparison over your working lifetime

## ✨ Key Features

### Comprehensive Financial Analysis
- **Pre-medical school period**: Post-bacc costs and lost income
- **Medical school years**: Tuition, fees, and lost wages
- **Residency period**: Low salary vs. potential career earnings
- **Fellowship years**: Additional training costs and opportunity costs
- **Physician career**: Salary progression and loan repayment
- **Retirement planning**: Impact on long-term wealth accumulation

### Flexible Input Options
- **Quick calculation**: Use sensible defaults for immediate results
- **Custom inputs**: Modify any parameter to match your situation
- **Persistent defaults**: Save your preferences for future calculations
- **Realistic assumptions**: Built-in inflation, investment returns, and salary growth

### Optimization Suggestions
- **Timeline optimization**: Find the best age to start medical school
- **Financial planning**: Strategies to minimize opportunity costs
- **Loan management**: Optimal repayment strategies
- **Career planning**: When to pursue additional training

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- No external dependencies required

### Installation
1. Clone or download this repository
2. Navigate to the `CostCalc/src` directory
3. Compile the Java files:
   ```bash
   javac *.java
   ```
4. Run the application:
   ```bash
   java Main
   ```

### Quick Start
1. **Run the calculator**: Execute `java Main`
2. **Choose option 1**: "Calculate Opportunity Cost"
3. **Select calculation mode**:
   - Option 1: Use defaults for immediate results
   - Option 2: Customize inputs for your situation
4. **Review results**: See detailed breakdown of costs and impacts
5. **Explore optimizations**: Get suggestions for minimizing costs

## 📊 What the Calculator Shows

### Financial Summary
- **Total Opportunity Cost**: Net financial impact of pursuing medicine
- **Lost Wages**: Income you would have earned in your current career
- **Lost Retirement**: Compound growth of retirement savings
- **Loan Costs**: Total debt including interest over repayment period
- **Net Impact**: Your financial position with vs. without medical school

### Timeline Breakdown
- **Pre-medical school**: Post-bacc costs and preparation time
- **Medical school**: 4 years of tuition and lost income
- **Residency**: 3+ years of low salary vs. career earnings
- **Fellowship**: Additional training costs (if applicable)
- **Physician career**: Salary progression and wealth building

### Optimization Insights
- **Best starting age**: When to begin your medical journey
- **Financial strategies**: How to minimize opportunity costs
- **Loan management**: Optimal repayment approaches
- **Career timing**: When additional training makes financial sense

## 🔧 Configuration Options

### Personal Information
- Current age and salary
- Annual raise expectations
- Inflation assumptions

### Training Path
- Post-bacc requirements and costs
- Medical school duration
- Residency length and salary
- Fellowship needs and costs

### Financial Assumptions
- Retirement contribution rates
- Investment return expectations
- Retirement age goals
- Loan terms and interest rates

## 📁 Project Structure

```
CostCalc/
├── src/
│   ├── Main.java              # Main application entry point
│   ├── MDCalc.java            # Core calculation engine
│   ├── DefaultsManager.java   # Configuration management
│   └── mdcalc.defaults.properties  # Default settings
├── sample_inputs/             # Example scenarios
└── README.md                  # This documentation
```

## 💡 Sample Scenarios

The calculator includes several pre-configured scenarios:

- **Traditional Path**: Standard 4-year medical school + 3-year residency
- **Post-bacc + Fellowship**: Non-traditional path with additional training
- **Early Retirement**: Optimizing for financial independence
- **Non-traditional No Post-bacc**: Career changer without additional prerequisites

## 🎓 Who Should Use This

- **Prospective medical students** evaluating the financial impact
- **Career changers** considering medicine as a second career
- **Financial advisors** helping clients plan medical education
- **Medical schools** providing financial planning resources
- **Anyone** interested in understanding the true cost of medical education

## 🔍 Understanding the Results

### Opportunity Cost Components
1. **Lost Wages**: Current salary × years of training × growth rate
2. **Lost Retirement**: Retirement contributions × compound growth
3. **Training Costs**: Tuition, fees, and living expenses
4. **Loan Interest**: Total interest paid over repayment period

### Net Financial Impact
- **Positive numbers** indicate a financial cost
- **Negative numbers** indicate a financial benefit
- **Break-even analysis** shows when medical career becomes profitable

## 🛠️ Technical Details

- **Language**: Java (no external dependencies)
- **Architecture**: Object-oriented design with configuration management
- **Data Persistence**: Properties file for user preferences
- **Input Validation**: Comprehensive error checking and range validation
- **Performance**: Efficient calculations for complex financial scenarios

## 🤝 Contributing

This project is designed to be educational and practical. Contributions are welcome:

1. **Bug reports**: Help improve accuracy and reliability
2. **Feature requests**: Suggest additional calculation methods
3. **Documentation**: Improve clarity and examples
4. **Code improvements**: Enhance performance and maintainability

## 📄 License

This project is provided as-is for educational and personal use. Please ensure compliance with your institution's policies when using this calculator.

## ⚠️ Disclaimer

This calculator provides estimates based on the information you provide. Results are for planning purposes only and should not be considered financial advice. Always consult with qualified financial professionals for your specific situation.

---

**Start calculating your medical school opportunity cost today!** 🩺💰
