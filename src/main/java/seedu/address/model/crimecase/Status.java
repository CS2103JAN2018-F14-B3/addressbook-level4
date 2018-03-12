package seedu.address.model.crimecase;

public class Status {

    public String status;

    /**
     * Constructs a {@code Status}.
     *
     */
    public Status() {
        this.status = "open";
    }

    /**
     * Sets status to "close"
     *
     */
    public void closeCase() {
        this.status = "close";
    }

    @Override
    public String toString() {
        return this.status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
