package seedu.investigapptor.logic.parser;

import static seedu.investigapptor.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.investigapptor.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.investigapptor.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.investigapptor.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.investigapptor.logic.commands.DeleteInvestigatorCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteInvestigatorCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteInvestigatorCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteInvestigatorCommandParserTest {

    private DeleteInvestigatorCommandParser parser = new DeleteInvestigatorCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteInvestigatorCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteInvestigatorCommand.MESSAGE_USAGE));
    }
}
