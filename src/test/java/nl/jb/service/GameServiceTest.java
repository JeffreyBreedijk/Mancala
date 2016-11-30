package nl.jb.service;

import java.util.Set;
import java.util.TreeSet;

import nl.jb.domain.Board;
import nl.jb.domain.GameStatus;
import nl.jb.domain.Pit;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import org.hamcrest.core.Is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private TurnService turnService = new TurnService();
	private GameService gameService = new GameService(turnService);

	@Test
	public void play() throws Exception {
		gameService.newGame(6, 6);
		GameStatus gameStatus = gameService.play(0, 1);
		assertThat(gameStatus.isGameOver(), is(false));
		assertThat(gameStatus.getWinner(), is(nullValue()));
	}

	@Test
	public void play_wrongPlayer() throws Exception {
		gameService.newGame(6, 6);
		expectedException.expect(IllegalArgumentException.class);
		gameService.play(1, 1);
	}

	@Test
	public void determineWinner() throws Exception {
		Set<Pit> pits = new TreeSet<>();
		pits.add(new Pit(false, 0, 0, 0));
		pits.add(new Pit(false, 0, 0, 1));
		pits.add(new Pit(true, 0, 3, 2));
		pits.add(new Pit(false, 1, 0, 0));
		pits.add(new Pit(false, 1, 1, 1));
		pits.add(new Pit(true, 1, 1, 2));
		Board board = new Board(1, 2, 2);
		board.setPits(pits);

		gameService.setBoard(board);

		int winner = gameService.determineWinner();

		assertThat(winner, is(0));
		assertThat(board.getPit(0, 0).getSize(), Is.is(0));
		assertThat(board.getPit(0, 1).getSize(), Is.is(0));
		assertThat(board.getPit(0, 2).getSize(), Is.is(3));
		assertThat(board.getPit(1, 0).getSize(), Is.is(0));
		assertThat(board.getPit(1, 1).getSize(), Is.is(0));
		assertThat(board.getPit(1, 2).getSize(), Is.is(2));
	}

	@Test
	public void determineWinner_draw() throws Exception {
		Set<Pit> pits = new TreeSet<>();
		pits.add(new Pit(false, 0, 0, 0));
		pits.add(new Pit(false, 0, 0, 1));
		pits.add(new Pit(true, 0, 3, 2));
		pits.add(new Pit(false, 1, 1, 0));
		pits.add(new Pit(false, 1, 1, 1));
		pits.add(new Pit(true, 1, 1, 2));
		Board board = new Board(1, 2, 2);
		board.setPits(pits);

		gameService.setBoard(board);

		int winner = gameService.determineWinner();

		assertThat(winner, is(-1));
		assertThat(board.getPit(0, 0).getSize(), Is.is(0));
		assertThat(board.getPit(0, 1).getSize(), Is.is(0));
		assertThat(board.getPit(0, 2).getSize(), Is.is(3));
		assertThat(board.getPit(1, 0).getSize(), Is.is(0));
		assertThat(board.getPit(1, 1).getSize(), Is.is(0));
		assertThat(board.getPit(1, 2).getSize(), Is.is(3));
	}

	@Test
	public void gameOverCheck_true() throws Exception {
		Set<Pit> pits = new TreeSet<>();
		pits.add(new Pit(false, 0, 0, 0));
		pits.add(new Pit(false, 0, 0, 1));
		pits.add(new Pit(true, 0, 3, 2));
		pits.add(new Pit(false, 1, 1, 0));
		pits.add(new Pit(false, 1, 1, 1));
		pits.add(new Pit(true, 1, 1, 2));
		Board board = new Board(1, 2, 2);
		board.setPits(pits);

		gameService.setBoard(board);
		assertThat(gameService.gameOverCheck(), is(true));
	}

	@Test
	public void gameOverCheck_false() throws Exception {
		Set<Pit> pits = new TreeSet<>();
		pits.add(new Pit(false, 0, 0, 0));
		pits.add(new Pit(false, 0, 2, 1));
		pits.add(new Pit(true, 0, 3, 2));
		pits.add(new Pit(false, 1, 1, 0));
		pits.add(new Pit(false, 1, 1, 1));
		pits.add(new Pit(true, 1, 1, 2));
		Board board = new Board(1, 2, 2);
		board.setPits(pits);

		gameService.setBoard(board);
		assertThat(gameService.gameOverCheck(), is(false));
	}


}