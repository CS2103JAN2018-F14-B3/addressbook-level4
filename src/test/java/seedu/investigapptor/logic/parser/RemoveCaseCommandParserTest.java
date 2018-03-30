package seedu.investigapptor.logic.parser;

import static seedu.investigapptor.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.investigapptor.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.investigapptor.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.investigapptor.testutil.TypicalIndexes.INDEX_FIRST_CASE;

import org.junit.Test;

import seedu.investigapptor.logic.commands.RemoveCaseCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RemoveCaseCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemoveCaseCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveCaseCommandParserTest {

    private RemoveCaseCommandParser parser = new RemoveCaseCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveCommand() {
        assertParseSuccess(parser, "1", new RemoveCaseCommand(INDEX_FIRST_CASE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveCaseCommand.MESSAGE_USAGE));
    }
}
