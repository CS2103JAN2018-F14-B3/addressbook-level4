package seedu.investigapptor.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.investigapptor.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.investigapptor.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.investigapptor.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.investigapptor.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.investigapptor.testutil.TypicalCrimeCases.getTypicalInvestigapptor;
import static seedu.investigapptor.testutil.TypicalIndexes.INDEX_FIRST_CASE;
import static seedu.investigapptor.testutil.TypicalIndexes.INDEX_SECOND_CASE;

import org.junit.Test;

import seedu.investigapptor.commons.core.Messages;
import seedu.investigapptor.commons.core.index.Index;
import seedu.investigapptor.logic.CommandHistory;
import seedu.investigapptor.logic.UndoRedoStack;
import seedu.investigapptor.model.Model;
import seedu.investigapptor.model.ModelManager;
import seedu.investigapptor.model.UserPrefs;
import seedu.investigapptor.model.crimecase.CrimeCase;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemoveCaseCommand}.
 */
public class RemoveCaseCommandTest {

    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        CrimeCase crimeCaseToDelete = model.getFilteredCrimeCaseList().get(INDEX_FIRST_CASE.getZeroBased());
        RemoveCaseCommand removeCaseCommand = prepareCommand(INDEX_FIRST_CASE);

        String expectedMessage = String.format(RemoveCaseCommand.MESSAGE_REMOVE_CASE_SUCCESS, crimeCaseToDelete);

        ModelManager expectedModel = new ModelManager(model.getInvestigapptor(), new UserPrefs());
        expectedModel.removeCrimeCase(crimeCaseToDelete);

        assertCommandSuccess(removeCaseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCrimeCaseList().size() + 1);
        RemoveCaseCommand removeCaseCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(removeCaseCommand, model, Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        CrimeCase crimeCaseToDelete = model.getFilteredCrimeCaseList().get(INDEX_FIRST_CASE.getZeroBased());
        RemoveCaseCommand removeCaseCommand = prepareCommand(INDEX_FIRST_CASE);
        Model expectedModel = new ModelManager(model.getInvestigapptor(), new UserPrefs());

        // remove -> first crimeCase removed
        removeCaseCommand.execute();
        undoRedoStack.push(removeCaseCommand);

        // undo -> reverts investigapptor back to previous state and filtered crimeCase list to show all crimeCases
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first crimeCase removed again
        expectedModel.removeCrimeCase(crimeCaseToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCrimeCaseList().size() + 1);
        RemoveCaseCommand removeCaseCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> removeCaseCommand not pushed into undoRedoStack
        assertCommandFailure(removeCaseCommand, model, Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        RemoveCaseCommand removeFirstCommand = prepareCommand(INDEX_FIRST_CASE);
        RemoveCaseCommand removeSecondCommand = prepareCommand(INDEX_SECOND_CASE);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveCaseCommand removeFirstCommandCopy = prepareCommand(INDEX_FIRST_CASE);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        removeFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different crimeCase -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * Returns a {@code RemoveCaseCommand} with the parameter {@code index}.
     */
    private RemoveCaseCommand prepareCommand(Index index) {
        RemoveCaseCommand removeCaseCommand = new RemoveCaseCommand(index);
        removeCaseCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCaseCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCrimeCase(Model model) {
        model.updateFilteredCrimeCaseList(p -> false);

        assertTrue(model.getFilteredCrimeCaseList().isEmpty());
    }
}
