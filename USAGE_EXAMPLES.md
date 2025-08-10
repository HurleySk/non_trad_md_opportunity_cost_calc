# Usage Examples

## Quick Start

```bash
# Navigate to source directory
cd CostCalc/src

# Compile
javac *.java

# Run interactively
java Main

# Or use sample input
java Main < ../../sample_inputs/traditional_path.txt
```

## Sample Results Comparison

### Traditional Path (Age 25, Standard Retirement at 65)
```
Training path: 4 years med school + 3 years residency
Age when starting as physician: 33 years old
Total Opportunity Cost: $1,605,193
Break-even age: 43 years old
Retirement losses: 50.6% of total cost
Years of physician earnings until retirement: 32 years
```

### Early Retirement FIRE (Age 28, Retire at 55)
```
Training path: 4 years med school + 3 years residency + 1 years fellowship
Age when starting as physician: 37 years old
Total Opportunity Cost: $2,800,000+ (estimated)
Break-even age: 50+ years old  
Retirement losses: 60%+ of total cost
Years of physician earnings until retirement: 18 years
WARNING: Break-even point may exceed retirement age!
```

### Late Retirement (Age 32, Retire at 70, Auto-Aligned Loans)
```
Training path: 4 years med school + 4 years residency
Age when starting as physician: 38 years old
Total Opportunity Cost: $1,400,000+ (estimated)
Break-even age: 45 years old
Retirement losses: 35% of total cost  
Years of physician earnings until retirement: 32 years
Loan term extended to retirement (32 years) - lower monthly payments
```

### Key Insights from Comparison
- **Retirement age is critical**: Early retirement (55) vs standard (65) can double opportunity cost
- **Age matters significantly**: Starting at 30 vs 25 adds ~$620K in opportunity cost  
- **Training length compounds**: Each extra year has exponential impact due to compounding
- **Retirement losses**: Represent 35-60% of total opportunity cost depending on retirement age
- **Loan strategy**: Auto-aligned loans reduce monthly burden but may increase total cost
- **Career length**: More earning years dramatically reduces opportunity cost

## Interactive vs Batch Mode

### Interactive Mode
Best for exploring different scenarios and understanding input impact:
```bash
java Main
# Follow prompts to input your specific situation
```

### Batch Mode  
Best for comparing multiple scenarios or automated analysis:
```bash
# Test all scenarios to compare retirement age impact
java Main < ../../sample_inputs/traditional_path.txt > standard_retirement.txt
java Main < ../../sample_inputs/early_retirement.txt > early_retirement.txt  
java Main < ../../sample_inputs/nontraditional_no_postbacc.txt > late_retirement.txt
java Main < ../../sample_inputs/postbacc_fellowship.txt > complex_path.txt
```

## Customization Tips

### Creating Your Own Input File
1. Copy a sample input file closest to your situation
2. Modify the values line by line according to INPUT_GUIDE.md
3. Save with a descriptive name
4. Test with: `java Main < your_custom_input.txt`

### Common Modifications
- **Retirement age**: Early retirement (50-60) dramatically increases opportunity cost
- **Higher starting age**: Increases opportunity cost significantly
- **Higher current salary**: Increases lost wages and retirement impact  
- **Auto-aligned loans**: Compare extended repayment vs standard 10-year terms
- **Investment assumptions**: Higher returns increase retirement opportunity cost
- **Specialty training**: Longer residency/fellowship vs higher attending salary

## Understanding Output Sensitivity

The calculator is most sensitive to:
1. **Retirement age** (early retirement can double opportunity cost)
2. **Starting age** (exponential impact due to compounding)
3. **Current salary** (directly affects opportunity cost)
4. **Training length** (each additional year compounds losses)
5. **Investment return assumptions** (affects 35-60% of total cost)

Moderately sensitive to:
- **Loan repayment strategy** (auto-aligned vs standard terms)
- **Annual raise assumptions** (affects projected salary growth)

Less sensitive to:
- **Loan interest rates** (important but smaller portion of total)
- **Residency/fellowship pay** (reduces but doesn't eliminate opportunity cost)