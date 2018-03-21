package seedu.investigapptor.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.investigapptor.commons.core.Messages;
import seedu.investigapptor.commons.core.index.Index;
import seedu.investigapptor.logic.commands.exceptions.CommandException;
import seedu.investigapptor.model.crimecase.CrimeCase;
import seedu.investigapptor.model.crimecase.exceptions.CrimeCaseNotFoundException;

/**
 * Removes a case identified using it's last displayed index from the investigapptor book.
 */
public class RemoveCaseCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "rem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the case identified by the index number used in the last listing of cases.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_CASE_SUCCESS = "Removed Case: %1$s";

    private final Index targetIndex;

    private CrimeCase caseToRemove;

    public RemoveCaseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(caseToRemove);
        try {
            model.removeCrimeCase(caseToRemove);
        } catch (CrimeCaseNotFoundException pnfe) {
            throw new AssertionError("The target case cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_CASE_SUCCESS, caseToRemove));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<CrimeCase> lastShownList = model.getFilteredCrimeCaseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        caseToRemove = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveCaseCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemoveCaseCommand) other).targetIndex) // state check
                && Objects.equals(this.caseToRemove, ((RemoveCaseCommand) other).caseToRemove));
    }
}
