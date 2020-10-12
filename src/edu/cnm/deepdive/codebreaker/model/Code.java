package edu.cnm.deepdive.codebreaker.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Creates a random code that the user has to guess. The code is generated using pool, length, and
 * rng.
 *
 * @version 1.0
 * @author  Robert Dominguez
 */
public class Code {

  private final char[] secret;

  /**
   * Initializes this instance by generating a random {@link String}, of length {@code length} with
   * characters taken from {@code pool}. A source of randomness must be provided in {@code rng}.
   *
   * @param pool Characters are available
   * @param length Number of how long the code is.
   * @param rng Used to randomly set the code.
   */
  public Code(String pool, int length, Random rng) {
    secret = new char[length];
    for (int i = 0; i < secret.length; i++) {
      secret[i] = pool.charAt(rng.nextInt(pool.length()));
    }
  }

  @Override
  public String toString() {
    return new String(secret);
  }

  /**
   * Determines whether the user submitted guess was correct or how close the guess was to being correct.
   */
  public class Guess {

    private static final String STRING_FORMAT = "{text: \"%s\", correct: %d, close: %d}";

    private final String text;
    private final int correct;
    private final int close;

    /**
     * Determines whether the user submitted guess was correct or how close the guess was to being correct
     * by checking if characters of text are the same as code and in the same position.
     *
     * @param text User's guess.
     */
    public Guess(String  text) {
      this.text = text;
      int correct = 0;
      int close = 0;

      Map<Character, Set<Integer>> letterMap = getLetterMap(text);

      char[] work = Arrays.copyOf(secret, secret.length);

      for (int i = 0; i < work.length; i++) {
        char letter = work[i];
        Set<Integer> positions = letterMap.getOrDefault(letter, Collections.emptySet());
        if (positions.contains(i)) {
          correct++;
          positions.remove(i);
          work[i] = 0;
        }
      }

      for (char letter : work) {
        if (letter != 0) {
          Set<Integer> positions = letterMap.getOrDefault(letter, Collections.emptySet());
          if (positions.size() > 0) {
            close++;
            Iterator<Integer> iter = positions.iterator();
            iter.next();
            iter.remove();
          }
        }
      }

      this.correct = correct;
      this.close = close;
    }

    private Map<Character, Set<Integer>> getLetterMap(String text) {
      Map<Character, Set<Integer>> letterMap = new HashMap<>();
      char[] letters = text.toCharArray();
      for (int i =0; i < letters.length; i++){
        char letter= letters[i];
        Set<Integer> positions = letterMap.getOrDefault(letter, new HashSet<>());
        positions.add(i);
        letterMap.putIfAbsent(letter, positions);
      }
      return letterMap;
    }

    @Override
    public String toString() {
      return String.format(STRING_FORMAT, text, correct, close);
    }

    /**
     * Returns the text of guess.
     */
    public String getText() {
      return text;
    }

    /**
     * Returns that the text was the same as the code.
     */
    public int getCorrect() {
      return correct;
    }

    /**
     *  Returns the number of characters that are in code but not in the correct place.
     */
    public int getClose() {
      return close;
    }

  }

}
