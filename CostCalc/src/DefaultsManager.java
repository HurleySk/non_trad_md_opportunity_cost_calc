import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Simple manager to persist and load default configuration for MDCalc.
 * Stores values in a properties file in the current working directory.
 */
public class DefaultsManager {
    private static final String FILE_NAME = "mdcalc.defaults.properties";

    private static Properties props = null;

    private static synchronized Properties getProps() {
        if (props == null) {
            props = new Properties();
            Path p = Paths.get(FILE_NAME);
            if (Files.exists(p)) {
                try (FileInputStream fis = new FileInputStream(p.toFile())) {
                    props.load(fis);
                } catch (IOException ignored) {
                }
            }
        }
        return props;
    }

    public static void save() {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            getProps().store(fos, "MDCalc default settings");
        } catch (IOException ignored) {
        }
    }

    // Generic getters/setters with defaults
    public static int getInt(String key, int def) {
        String v = getProps().getProperty(key);
        if (v == null) return def;
        try {
            int value = Integer.parseInt(v.trim());
            // Validate bounds for critical values
            return validateInt(key, value, def);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static double getDouble(String key, double def) {
        String v = getProps().getProperty(key);
        if (v == null) return def;
        try {
            double value = Double.parseDouble(v.trim());
            // Validate bounds for critical values
            return validateDouble(key, value, def);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static boolean getBoolean(String key, boolean def) {
        String v = getProps().getProperty(key);
        if (v == null) return def;
        return Boolean.parseBoolean(v.trim());
    }

    public static void setInt(String key, int value) {
        getProps().setProperty(key, Integer.toString(value));
    }

    public static void setDouble(String key, double value) {
        getProps().setProperty(key, Double.toString(value));
    }

    public static void setBoolean(String key, boolean value) {
        getProps().setProperty(key, Boolean.toString(value));
    }

    // Keys used by MDCalc
    public static class Keys {
        public static final String CURRENT_AGE = "currentAge";
        public static final String CURRENT_SALARY = "currentSalary";
        public static final String MED_SCHOOL_YEARS = "medSchoolYears";
        public static final String RESIDENCY_YEARS = "residencyYears";
        public static final String RESIDENCY_SALARY = "residencySalary";
        public static final String PHYSICIAN_STARTING_SALARY = "physicianStartingSalary";
        public static final String RETIREMENT_CONTRIB_RATE = "retirementContributionRate"; // fraction
        public static final String INVESTMENT_RETURN_RATE = "investmentReturnRate"; // fraction
        public static final String INFLATION_RATE = "inflationRate"; // fraction
        public static final String NEEDS_FELLOWSHIP = "needsFellowship";
        public static final String FELLOWSHIP_YEARS = "fellowshipYears";
        public static final String FELLOWSHIP_SALARY = "fellowshipSalary";
        public static final String NEEDS_POST_BACC = "needsPostBacc";
        public static final String POST_BACC_YEARS = "postBaccYears";
        public static final String POST_BACC_COST = "postBaccCost";
        public static final String YEARS_UNTIL_POST_BACC = "yearsUntilPostBacc";
        public static final String YEARS_UNTIL_MED_SCHOOL = "yearsUntilMedSchool";
        public static final String TOTAL_LOANS = "totalLoans";
        public static final String LOAN_REPAYMENT_YEARS = "loanRepaymentYears";
        public static final String MONTHLY_LOAN_PAYMENT = "monthlyLoanPayment";
        public static final String RETIREMENT_AGE = "retirementAge";
        public static final String ANNUAL_RAISE = "annualRaise"; // fraction
        public static final String LOAN_INTEREST_RATE = "loanInterestRate"; // fraction
    }

    // Validation methods to ensure saved values are within realistic bounds
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
                return (value >= 50000 && value <= 500000) ? value : def;
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
            case Keys.MONTHLY_LOAN_PAYMENT:
                return (value >= 0.0) ? value : def;
            default:
                return value;
        }
    }
}
