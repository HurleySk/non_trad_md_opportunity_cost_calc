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
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static double getDouble(String key, double def) {
        String v = getProps().getProperty(key);
        if (v == null) return def;
        try {
            return Double.parseDouble(v.trim());
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
        public static final String LOAN_REPAYMENT_YEARS = "loanRepaymentYears";
        public static final String MONTHLY_LOAN_PAYMENT = "monthlyLoanPayment";
        public static final String RETIREMENT_AGE = "retirementAge";
        public static final String ANNUAL_RAISE = "annualRaise"; // fraction
        public static final String LOAN_INTEREST_RATE = "loanInterestRate"; // fraction
    }
}
