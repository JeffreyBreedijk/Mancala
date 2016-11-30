package nl.jb.service;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import nl.jb.domain.Board;
import nl.jb.domain.GameStatus;
import nl.jb.domain.Pit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	private final TurnService turnService;
	private int currentPlayer;
	private boolean isGameOver;
	private int players = 2;
	private Board board;

	@Autowired
	public GameService(TurnService turnService) {
		this.turnService = turnService;
	}

	public GameStatus newGame(int stones, int pits) {
		players = 2;
		currentPlayer = 0;
		isGameOver = false;
		board = new Board(stones, pits, 2);
		return getGameStatus();
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
	protected int determineWinner() {
		for (int p = 0; p < players; p++) {
			moveStonesFromPitsToMancala(p);
		}
		if (isDraw()) {
			return -1;
		}
		return board.getPits().stream()
				.filter(Pit::isMancala)
				.reduce((a, b) -> a.getSize() > b.getSize() ? a : b)
				.get().getPlayer();
	}

	protected void setBoard(Board board) {
		this.board = board;
	}

	protected boolean gameOverCheck() {
		for (int p = 0; p < players; p++) {
			if (allPitsAreEmpty(board, p)) {
				return true;
			}
		}
		return false;
	}

	private GameStatus getGameStatus() {
		return GameStatus.builder()
				.currentPlayer(currentPlayer)
				.gameOver(isGameOver)
				.board(board)
				.build();
	}

	private boolean isDraw() {
		List<Pit> mancalas = board.getPits().stream()
				.filter(Pit::isMancala)
				.collect(Collectors.toList());

		int highestValue = mancalas.stream()
				.mapToInt(Pit::getSize)
				.max()
				.getAsInt();

		return mancalas.stream()
				.filter(pit -> pit.getSize() == highestValue)
				.count() > 1;
	}

	private boolean allPitsAreEmpty(Board board, int player) {
		return board.getPits().stream()
				.filter(pit -> !pit.isMancala())
				.filter(pit -> pit.getPlayer() == player)
				.noneMatch(pit -> pit.getSize() > 0);
	}

	private void moveStonesFromPitsToMancala(int player) {
		List<Pit> playerPits = board.getPits().stream()
				.filter(pit -> pit.getPlayer() == player)
				.collect(Collectors.toList());
		Pit mancala = playerPits.stream().filter(Pit::isMancala).findFirst().get();
		playerPits.remove(mancala);
		int stones = playerPits.stream().mapToInt(Pit::getSize).sum();
		mancala.addStones(stones);
		playerPits.forEach(pit -> pit.setSize(0));
	}
}
