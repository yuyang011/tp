package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPersonsPredicate;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsNumbersPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindCommand.FindPersonsPredicate findPersonsPredicate = new FindPersonsPredicate();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            findPersonsPredicate.setNamePredicate(new NameContainsKeywordsPredicate(
                    ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME))));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            findPersonsPredicate.setPhonePredicate(new PhoneContainsNumbersPredicate(
                    ParserUtil.parsePhones(argMultimap.getAllValues(PREFIX_PHONE))));
        }

        if (!findPersonsPredicate.isAnyPredicateSet()) {
            throw new ParseException(FindCommand.MESSAGE_NOT_FOUND);
        }

        return new FindCommand(findPersonsPredicate);
    }

}
