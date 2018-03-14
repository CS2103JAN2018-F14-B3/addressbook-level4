package seedu.investigapptor.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.investigapptor.logic.commands.exceptions.CommandException;
import seedu.investigapptor.model.person.Person;
import seedu.investigapptor.model.person.exceptions.DuplicatePersonException;

/**
 * Adds an investigator to investigapptor.
 */
public class AddInvestCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addInvest";
    public static final String COMMAND_ALIAS = "aI";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an investigator to investigapptor. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "teamA "
            + PREFIX_TAG + "new";

    public static final String MESSAGE_SUCCESS = "New investigator added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "Investigator already exists in investigapptor";

    private final Person toAdd;

    /**
     * Creates an AddInvestCommand to add the specified {@code Person}
     */
    public AddInvestCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddInvestCommand // instanceof handles nulls
                && toAdd.equals(((AddInvestCommand) other).toAdd));
    }
}
