# Quick Reference Guide

Essential information, formulas, and tips for the Medical School Opportunity Cost Calculator.

## 🚀 Quick Start Commands

### Compile and Run
```bash
# Navigate to source directory
cd CostCalc/src

# Compile all Java files
javac *.java

# Run the calculator
java Main
```

### Main Menu Options
```
1. Calculate Opportunity Cost
2. Set Defaults  
3. Exit
```

## 📊 Key Formulas

### Lost Wages Calculation
```
Lost Wages = Current Salary × Training Years × Growth Factor

Where:
- Growth Factor = (1 + Annual Raise) ^ Training Years
- Training Years = Post-bacc + Medical School + Residency + Fellowship
```

### Retirement Impact
```
Lost Retirement = Annual Contribution × Compound Growth

Where:
- Annual Contribution = Salary × Retirement Rate
- Compound Growth = (1 + Investment Return) ^ Years Until Retirement
```

### Loan Interest
```
Total Interest = Monthly Payment × Total Payments - Principal

Where:
- Monthly Payment = P × (r(1+r)^n) / ((1+r)^n - 1)
- P = Principal, r = Monthly Rate, n = Total Payments
```

### Total Opportunity Cost
```
Total Cost = Lost Wages + Lost Retirement + Training Costs + Loan Interest
```

## ⚙️ Default Values

### Personal Information
| Parameter | Default | Range |
|-----------|---------|-------|
| Current Age | 30 | 18-70 |
| Current Salary | $75,000 | > $0 |
| Annual Raise | 3.0% | 0-20% |
| Inflation Rate | 3.0% | 0-10% |

### Training Path
| Parameter | Default | Range |
|-----------|---------|-------|
| Medical School Years | 4 | 3-5 |
| Residency Years | 3 | 3-7 |
| Fellowship Years | 1 | 0-4 |
| Post-bacc Years | 2 | 0-4 |

### Financial Assumptions
| Parameter | Default | Range |
|-----------|---------|-------|
| Retirement Contribution | 15% | 0-50% |
| Investment Return | 7.0% | 3-15% |
| Retirement Age | 65 | 50-75 |
| Loan Interest Rate | 6.0% | 2-12% |

## 🎯 Common Scenarios

### Traditional Path (Age 22-30)
- **Timeline**: College → Medical School → Residency
- **Typical Cost**: $800,000 - $1,200,000
- **Break-even**: Age 35-40

### Career Changer (Age 30+)
- **Timeline**: Career → Post-bacc → Medical School → Residency
- **Typical Cost**: $1,500,000 - $2,500,000
- **Break-even**: Age 40-45

### Non-traditional with Fellowship
- **Timeline**: Post-bacc → Medical School → Residency → Fellowship
- **Typical Cost**: $2,000,000 - $3,000,000
- **Break-even**: Age 45-50

## 💡 Optimization Strategies

### Timeline Optimization
- **Start earlier**: Reduces total opportunity cost
- **Minimize gaps**: Reduce years between stages
- **Consider alternatives**: Some paths may not require post-bacc

### Financial Planning
- **Pre-fund retirement**: Maximize savings before medical school
- **Part-time work**: Consider during post-bacc or summers
- **Loan strategies**: Explore forgiveness programs and refinancing

### Career Planning
- **Specialty choice**: Longer training may mean higher earnings
- **Geographic flexibility**: Some areas offer loan forgiveness
- **Practice setting**: Academic vs. private practice differences

## 🔧 Troubleshooting

### Common Errors
| Error | Cause | Solution |
|-------|-------|----------|
| "Invalid input" | Non-numeric input | Enter only numbers |
| Unexpected results | Wrong percentage format | Use decimals (7.5 for 7.5%) |
| Defaults not saving | File permission issue | Check write permissions |
| Calculation errors | Invalid input ranges | Verify age (18-70), salary (>0) |

### Input Validation Rules
- **Age**: Must be 18-70
- **Salary**: Must be positive
- **Percentages**: Enter as decimals (0.15 for 15%)
- **Years**: Must be positive integers
- **Costs**: Must be non-negative

### Reset to Defaults
```bash
# Delete configuration file
rm mdcalc.defaults.properties

# Restart application
java Main
```

## 📈 Result Interpretation

### Financial Metrics
- **Positive numbers**: Financial cost to pursue medicine
- **Negative numbers**: Financial benefit (rare)
- **Break-even age**: When medical career becomes profitable
- **Net worth**: Long-term financial outcome

### Key Insights
- **Lost wages**: Usually largest component
- **Retirement impact**: Can exceed lost wages due to compound growth
- **Loan interest**: Adds 20-40% to total cost
- **Training costs**: Direct expenses, separate from loans

## 🎓 Sample Inputs

### Quick Calculation Template
```
Current age: 28
Current salary: 80000
Annual raise: 0.04
Retirement contribution: 0.15
Years until med school: 2
Medical school years: 4
Residency years: 3
Fellowship: false
Total loans: 300000
Loan interest rate: 0.06
```

### Career Changer Template
```
Current age: 32
Current salary: 65000
Post-bacc needed: true
Post-bacc years: 2
Post-bacc cost: 60000
Years until med school: 3
Medical school years: 4
Residency years: 3
Fellowship: false
Total loans: 350000
```

## 🔄 Configuration Management

### Setting Defaults
1. **Main menu** → Option 2
2. **Choose modification type**:
   - Modify all defaults
   - Modify specific categories
   - View current defaults
3. **Enter new values** or press Enter to keep current
4. **Save automatically** when complete

### Default Categories
1. **Personal Information**: Age, salary, raises, inflation
2. **Training Path**: Post-bacc, med school, residency, fellowship
3. **Financial Assumptions**: Retirement, investment, inflation
4. **Loan Settings**: Amount, interest, repayment terms

### Persistence
- **Automatic saving**: Changes saved immediately
- **File location**: `mdcalc.defaults.properties`
- **Backup**: Copy file before major changes
- **Reset**: Delete file to restore built-in defaults

## 📱 Usage Tips

### First Time Users
1. **Set defaults first**: Configure your personal situation
2. **Use custom input**: Review and modify all values
3. **Run multiple scenarios**: Compare different paths
4. **Save results**: Note down key numbers for comparison

### Regular Users
1. **Quick calculation**: Use saved defaults for speed
2. **Update annually**: Review and adjust as situation changes
3. **Track progress**: Monitor how decisions affect timeline
4. **Optimize strategy**: Use suggestions to improve outcomes

### Advanced Users
1. **Parameter sensitivity**: Test impact of changing specific values
2. **Scenario comparison**: Run side-by-side analysis
3. **Custom optimization**: Modify code for specific needs
4. **Data export**: Copy results for external analysis

## 🆘 Getting Help

### Built-in Help
- **Input validation**: Automatic range checking
- **Default values**: Sensible fallbacks for all parameters
- **Error messages**: Clear explanations of problems
- **Optimization suggestions**: Built-in improvement ideas

### Documentation
- **README.md**: Project overview and installation
- **USER_GUIDE.md**: Step-by-step usage instructions
- **TECHNICAL_GUIDE.md**: Code architecture and algorithms
- **EXAMPLES_AND_SCENARIOS.md**: Real-world examples

### Common Questions
- **Q**: How accurate are the calculations?
- **A**: Estimates based on your inputs, consult professionals for advice

- **Q**: Can I save multiple scenarios?
- **A**: Currently supports one set of defaults, plan to add scenario saving

- **Q**: What if my situation changes?
- **A**: Update defaults and recalculate, review annually

- **Q**: How do I compare different paths?
- **A**: Run calculations with different inputs and compare results

---

**Quick Tip**: Press Enter to keep default values, type new values to change them. Use `0` to keep defaults when prompted for custom input.
