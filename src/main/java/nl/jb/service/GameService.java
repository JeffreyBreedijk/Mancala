package nl.jb.service;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import nl.jb.domain.Board;
import nl.jb.domain.GameStatus;
import nl.jb.domain.Pit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	@Autowired
	private TurnService turnService;

	private int currentPlayer;
	private boolean isGameOver;
	private int players;
	private Board board;

	public GameStatus newGame(int stones, int pits) {
		players = 2;
		currentPlayer = 0;
		isGameOver = false;
		board = new Board(stones, pits, 2);
		return getGameStatus();
	}

	private GameStatus getGameStatus() {
		return GameStatus.builder()
				.currentPlayer(currentPlayer)
				.gameOver(isGameOver)
				.board(board)
				.build();
	}

	public GameStatus play(int player, int pit) {
		checkArgument(currentPlayer == player, "It's player " + currentPlayer + "'s turn!");
		checkArgument(!isGameOver, "The previous game has ended, please start a new game.");
		int nextPlayer = turnService.doTurn(board, player, pit) ? player : (player == players - 1 ? 0 : player + 1);
		isGameOver = gameOverCheck();
		currentPlayer = nextPlayer;
		return getGameStatus();
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public int determineWinner() {
		//TODO: Process stones
		return board.getPits().values().stream()
				.filter(Pit::isMancala)
				.reduce((a,b) -> a.getSize() > b.getSize() ? a : b)
				.get().getPlayer();
	}

	public Board getBoard() {
		return board;
	}

	private boolean gameOverCheck() {
		for (int p = 0; p < players; p++) {
			if (allPitsAreEmpty(board, p)) {
				return true;
			}
		}
		return false;
	}

	private boolean allPitsAreEmpty(Board board, int player) {
		return !board.getPits().entrySet()
				.stream()
				.filter(entry -> entry.getKey().startsWith(player + "."))
				.map(Map.Entry::getValue)
				.filter(pit -> !pit.isMancala())
				.anyMatch(pit -> pit.getSize() > 0);
	}
}
