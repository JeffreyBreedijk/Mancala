package nl.jb.domain;

public class GameStatus {

	private boolean gameOver;
	private Integer winner;
	private int currentPlayer;
	private Board board;

	private GameStatus(Builder builder) {
		setGameOver(builder.gameOver);
		setWinner(builder.winner);
		setCurrentPlayer(builder.currentPlayer);
		setBoard(builder.board);
	}

	public static Builder builder() {
		return new Builder();
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Integer getWinner() {
		return winner;
	}

	public void setWinner(Integer winner) {
		this.winner = winner;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}


	public static final class Builder {

		private boolean gameOver;
		private Integer winner;
		private int currentPlayer;
		private Board board;

		private Builder() {
		}

		public Builder gameOver(boolean val) {
			gameOver = val;
			return this;
		}

		public Builder winner(Integer val) {
			winner = val;
			return this;
		}

		public Builder currentPlayer(int val) {
			currentPlayer = val;
			return this;
		}

		public Builder board(Board val) {
			board = val;
			return this;
		}

		public GameStatus build() {
			return new GameStatus(this);
		}
	}
}
