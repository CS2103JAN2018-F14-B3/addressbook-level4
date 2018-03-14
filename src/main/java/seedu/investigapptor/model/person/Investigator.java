package seedu.investigapptor.model.person;

import seedu.investigapptor.model.crimecase.CrimeCase;
import seedu.investigapptor.model.crimecase.UniqueCrimeCaseList;
import seedu.investigapptor.model.tag.Tag;

import java.util.Set;

/**
 * Represents a Investigator in the investigapptor book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Investigator extends Person {

    UniqueCrimeCaseList crimeCases;

    public Investigator(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        crimeCases = new UniqueCrimeCaseList();
    }

    public void addCrimeCase(CrimeCase caseToAdd) throws UniqueCrimeCaseList.DuplicateCrimeCaseException {
        crimeCases.add(caseToAdd);
    }

    public void removeCrimeCase(CrimeCase caseToRemove) {
        crimeCases.remove(caseToRemove);
    }
}