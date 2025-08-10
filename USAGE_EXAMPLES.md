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

### Traditional Path (Age 25, No Post-Bacc, No Fellowship)
```
Training path: 4 years med school + 3 years residency
Age when starting as physician: 33 years old
Total Opportunity Cost: $1,605,193
Break-even age: 41 years old
Retirement losses: 50.6% of total cost
```

### Post-Bacc + Fellowship (Age 30, Career Changer)
```
Training path: 2 years post-bacc + 4 years med school + 3 years residency + 2 years fellowship
Age when starting as physician: 43 years old
Total Opportunity Cost: $2,225,336
Break-even age: 52 years old  
Retirement losses: 38.9% of total cost
```

### Key Insights from Comparison
- **Age matters significantly**: Starting at 30 vs 25 adds ~$620K in opportunity cost
- **Post-bacc + fellowship**: Adds 4 extra training years with massive compound impact
- **Retirement losses**: Represent 40-50% of total opportunity cost
- **Break-even timing**: Traditional path breaks even 11 years earlier
- **Career length**: Traditional path has 10 more earning years until retirement

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
# Test all three scenarios
java Main < ../../sample_inputs/traditional_path.txt > traditional_results.txt
java Main < ../../sample_inputs/postbacc_fellowship.txt > postbacc_results.txt  
java Main < ../../sample_inputs/nontraditional_no_postbacc.txt > nontraditional_results.txt
```

## Customization Tips

### Creating Your Own Input File
1. Copy a sample input file closest to your situation
2. Modify the values line by line according to INPUT_GUIDE.md
3. Save with a descriptive name
4. Test with: `java Main < your_custom_input.txt`

### Common Modifications
- **Higher starting age**: Increases opportunity cost significantly
- **Higher current salary**: Increases lost wages and retirement impact  
- **Lower loan rates**: Reduces interest burden
- **Higher investment returns**: Increases retirement opportunity cost
- **Specialty training**: Longer residency/fellowship vs higher attending salary

## Understanding Output Sensitivity

The calculator is most sensitive to:
1. **Starting age** (exponential impact due to compounding)
2. **Current salary** (directly affects opportunity cost)
3. **Training length** (each additional year compounds losses)
4. **Investment return assumptions** (affects 40-50% of total cost)

Less sensitive to:
- **Loan interest rates** (important but smaller portion of total)
- **Residency/fellowship pay** (reduces but doesn't eliminate opportunity cost)
- **Annual raise assumptions** (gradual impact over time)