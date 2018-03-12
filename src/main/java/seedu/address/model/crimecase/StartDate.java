package seedu.address.model.crimecase;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class StartDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Input date must follow DD/MM/YYYY format, and it should not be blank";

    public static final String DATE_VALIDATION_REGEX= "([0-9]{2})/([0-9]{2})/([0-9]{4})";

    public final String date;

    public static String day;
    public static String month;
    public static String year;

    private static final int DOB_DAY_INDEX = 0;
    private static final int DOB_MONTH_INDEX = 1;
    private static final int DOB_YEAR_INDEX = 2;

    /**
     * Constructs a {@code Name}.
     *
     * @param date A valid date.
     */
    public StartDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Extracts date into their date properties
     */
    private void extractDateProperties() {
        String[] dateProperties = date.split("/");
        this.day = dateProperties[DOB_DAY_INDEX];
        this.month = dateProperties[DOB_MONTH_INDEX];
        this.year = dateProperties[DOB_YEAR_INDEX];
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof StartDate
                && this.date.equals(((StartDate) other).date));
    }

    @Override
    public String toString() {
        extractDateProperties();
        final StringBuilder builder = new StringBuilder();
        builder.append(this.day)
                .append(" ")
                .append(this.month)
                .append(" ")
                .append(this.year);
        return builder.toString();
    }
}
