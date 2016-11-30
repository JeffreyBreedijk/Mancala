package nl.jb.service;

import java.util.Set;
import java.util.TreeSet;

import nl.jb.domain.Board;
import nl.jb.domain.Pit;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class TurnServiceTest {

	private TurnService turnService = new TurnService();

	@Test
	public void doTurn() throws Exception {
		Board board = new Board(3, 3, 2);
		boolean anotherTurn = turnService.doTurn(board, 0, 1);
		assertThat(anotherTurn, is(false));
		assertThat(board.getPit(0, 0).getSize(), is(3));
		assertThat(board.getPit(0, 1).getSize(), is(0));
		assertThat(board.getPit(0, 2).getSize(), is(4));
		assertThat(board.getPit(0, 3).getSize(), is(1)); //Mancala P0
		assertThat(board.getPit(1, 0).getSize(), is(4));
		assertThat(board.getPit(1, 1).getSize(), is(3));
		assertThat(board.getPit(1, 2).getSize(), is(3));
		assertThat(board.getPit(1, 3).getSize(), is(0)); //Mancala P1
	}

	@Test
	public void doTurn_anotherTurn() throws Exception {
		Board board = new Board(3, 3, 2);
		boolean anotherTurn = turnService.doTurn(board, 0, 0);
		assertThat(anotherTurn, is(true));
		assertThat(board.getPit(0, 0).getSize(), is(0));
		assertThat(board.getPit(0, 1).getSize(), is(4));
		assertThat(board.getPit(0, 2).getSize(), is(4));
		assertThat(board.getPit(0, 3).getSize(), is(1)); //Mancala P0
		assertThat(board.getPit(1, 0).getSize(), is(3));
		assertThat(board.getPit(1, 1).getSize(), is(3));
		assertThat(board.getPit(1, 2).getSize(), is(3));
		assertThat(board.getPit(1, 3).getSize(), is(0)); //Mancala P1
	}

	@Test
	public void doTurn_noOpponentMancala() throws Exception {
		Board board = new Board(10, 3, 2);
		boolean anotherTurn = turnService.doTurn(board, 0, 0);
		assertThat(anotherTurn, is(true));
		assertThat(board.getPit(0, 0).getSize(), is(1));
		assertThat(board.getPit(0, 1).getSize(), is(12));
		assertThat(board.getPit(0, 2).getSize(), is(12));
		assertThat(board.getPit(0, 3).getSize(), is(2)); //Mancala P0
		assertThat(board.getPit(1, 0).getSize(), is(11));
		assertThat(board.getPit(1, 1).getSize(), is(11));
		assertThat(board.getPit(1, 2).getSize(), is(11));
		assertThat(board.getPit(1, 3).getSize(), is(0)); //Mancala P1
	}

	@Test
	public void captureStonesOnEmptyPit() throws Exception {
		Set<Pit> pits = new TreeSet<>();
		pits.add(new Pit(false, 0, 1, 0));
		pits.add(new Pit(false, 0, 0, 1));
		pits.add(new Pit(false, 1, 2, 0));
		pits.add(new Pit(false, 1, 2, 1));
		Board board = new Board(1, 2, 2);
		board.setPits(pits);

		turnService.doTurn(board, 0, 0);

		assertThat(board.getPit(0, 0).getSize(), is(0));
		assertThat(board.getPit(0, 1).getSize(), is(3));
		assertThat(board.getPit(1, 0).getSize(), is(0));
		assertThat(board.getPit(1, 1).getSize(), is(2));
	}

	@Test
	public void doNotCaptureStonesOnOpponentEmptyPit() throws Exception {
		Set<Pit> pits = new TreeSet<>();
		pits.add(new Pit(false, 0, 2, 0));
		pits.add(new Pit(false, 0, 0, 1));
		pits.add(new Pit(false, 1, 0, 0));
		pits.add(new Pit(false, 1, 1, 1));
		Board board = new Board(1, 2, 2);
		board.setPits(pits);

		turnService.doTurn(board, 0, 0);

		assertThat(board.getPit(0, 0).getSize(), is(0));
		assertThat(board.getPit(0, 1).getSize(), is(1));
		assertThat(board.getPit(1, 0).getSize(), is(1));
		assertThat(board.getPit(1, 1).getSize(), is(1));
	}

}