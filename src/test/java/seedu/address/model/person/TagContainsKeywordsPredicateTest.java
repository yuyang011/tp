package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("friend");
        List<String> secondPredicateKeywordList = Arrays.asList("friend", "colleague");

        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friend", "colleague"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friend", "criminal"));
        assertTrue(predicate.test(new PersonBuilder().withTags("criminal", "enemy").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("FriEnd", "cOLLEAgue"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));
    }

    @Test
    public void test_tagsDoNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("enemy"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Keywords match name, phone, email, address, but not tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "MainStreet"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withAddress("MainStreet").withTags("friend").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("friend", "colleague");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
