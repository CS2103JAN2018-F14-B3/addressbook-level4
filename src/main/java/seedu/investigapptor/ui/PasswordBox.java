package seedu.investigapptor.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.investigapptor.commons.core.LogsCenter;
import seedu.investigapptor.commons.events.ui.InvalidFileFormatEvent;
import seedu.investigapptor.commons.events.ui.NewResultAvailableEvent;
import seedu.investigapptor.commons.events.ui.ValidPasswordEvent;
import seedu.investigapptor.commons.exceptions.DataConversionException;
import seedu.investigapptor.commons.exceptions.WrongPasswordException;
import seedu.investigapptor.logic.commands.CommandResult;
import seedu.investigapptor.model.Model;
import seedu.investigapptor.model.Password;
import seedu.investigapptor.storage.Storage;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class PasswordBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "PasswordBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Storage storage;
    private final Model model;

    @FXML
    private PasswordField passwordField;

    public PasswordBox(Storage storage, Model model) {
        super(FXML);

        this.storage = storage;
        this.model = model;
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handlePasswordInput() {
        String input = passwordField.getText();
        try {
            storage.readInvestigapptor(new Password(input));
            raise(new ValidPasswordEvent());
        } catch (WrongPasswordException wpe) {
            CommandResult passwordResult = new CommandResult("An invalid password has been entered");
            passwordField.setText("");
            logger.info("Result: " + passwordResult.feedbackToUser);
            raise(new NewResultAvailableEvent(passwordResult.feedbackToUser));
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Investigapptor");
            raise(new InvalidFileFormatEvent());
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Investigapptor");
            raise(new InvalidFileFormatEvent());
        }
    }

}