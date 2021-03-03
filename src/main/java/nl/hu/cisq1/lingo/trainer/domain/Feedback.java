package nl.hu.cisq1.lingo.trainer.domain;

import lombok.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedBackException;

import java.util.List;

@RequiredArgsConstructor
@ToString @EqualsAndHashCode
public class Feedback {
    public final String attempt;
    private final List<Mark> mark;

    //setting list correctness externally
    public boolean isWordGuessed() throws InvalidFeedBackException {
        for (Mark m : mark) if (m != Mark.CORRECT) return false;
        return true;
        //if(!marks.isEmpty());
        //return this.marks.stream().allMatch(m-> m==Mark.CORRECT);
    }

    public boolean isGuessValid() {
        return attempt.length() >= 5 && attempt.length() <= 7;
    }

    @NonNull
    public List<Character> giveHint(List<Character> prevHint, String wordToGuess) {
        char[] at = attempt.toCharArray();
        char[] g = wordToGuess.toCharArray();

        if (!isWordGuessed() && isGuessValid()) {
            for (int i = 0; i < at.length; i++) {
                prevHint.add(i, at[i] == g[i] ? at[i] : '.');
            }
        }
        return prevHint;
    }
}