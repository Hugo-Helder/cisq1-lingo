package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.data.dto.GameDTOStrategy;
import nl.hu.cisq1.lingo.data.dto.GameProgressDTO;
import nl.hu.cisq1.lingo.data.repositories.SpringGameRepository;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.exceptions.GameNotFoundException;
import nl.hu.cisq1.lingo.exceptions.WordNotFoundException;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.stereotype.Service;


@Service
public class GameService {
	private final SpringGameRepository gameRepo;
	private final SpringWordRepository wordRepo;

	public GameService(SpringGameRepository gameRepo, SpringWordRepository wordRepo) {
		this.gameRepo = gameRepo;
		this.wordRepo = wordRepo;
	}

	public GameDTOStrategy newGame(){
		LingoGame game = new LingoGame();
		Word wordToGuess = this.wordRepo.findRandomWordByLength(LingoGame.getWordLengths().get(0))
				.orElseThrow(() -> new WordNotFoundException());
		game.nextRound(wordToGuess.getValue());
		LingoGameDM gameDM = new LingoGameDM(game);
		gameRepo.save(gameDM);
		return new GameProgressDTO(gameDM);
	}

	public GameDTOStrategy getGameById(Long id){
		LingoGameDM gameDM = this.gameRepo.findById(id)
				.orElseThrow(() -> new GameNotFoundException());
		return new GameProgressDTO(gameDM);
	}
}
