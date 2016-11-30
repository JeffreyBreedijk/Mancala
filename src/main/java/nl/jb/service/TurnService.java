package nl.jb.service;

import nl.jb.domain.Board;
import nl.jb.domain.Pit;
import org.springframework.stereotype.Service;

@Service
public class TurnService {

	public boolean doTurn(Board board, int player, int pitNumber) {
		int stonesInHand = board.getStones(player, pitNumber);
		return placeStones(board, pitNumber, stonesInHand, player);
	}

	private boolean placeStones(Board board, int pitId, int stonesInHand, int player) {
		int remainingStones = stonesInHand;
		boolean anotherTurn = true;
		boolean passed = false;
		for (Pit pit : board.getPits()) {
			if (passed && remainingStones != 0 && pit.acceptIncement(player)) {
				if (shouldCaptureStonesFromOppositePit(remainingStones, pit, player)) {
					captureStonesFromOppositePit(board, player, pit.getPitNumber());
				}
				pit.addStones(1);
				remainingStones--;
				anotherTurn = pit.isMancala();
			}
			if (!passed && pit.getPlayer() == player && pit.getPitNumber() == pitId) {
				passed = true;
			}
		}
		return processRemainingStones(board, player, remainingStones, anotherTurn);
	}

	private boolean processRemainingStones(Board board, int player, int remainingStones, boolean anotherTurn) {
		while (remainingStones != 0) {
			for (Pit pit : board.getPits()) {
				if (remainingStones != 0 && pit.acceptIncement(player)) {
					if (shouldCaptureStonesFromOppositePit(remainingStones, pit, player)) {
						captureStonesFromOppositePit(board, player, pit.getPitNumber());
					}
					pit.addStones(1);
					remainingStones--;
					anotherTurn = pit.isMancala();
				}
			}
		}
		return anotherTurn;
	}

	private static boolean shouldCaptureStonesFromOppositePit(int remainingStones, Pit pit, int player) {
		return !pit.isMancala() && remainingStones == 1 && pit.getSize() == 0 && pit.getPlayer() == player;
	}

	private void captureStonesFromOppositePit(Board board, int player, int pitNumber) {
		int stonesFromOppositePit = board.getStones(player == 0 ? 1 : 0, (getPitsPerPlayer(board) - pitNumber) - 1);
		board.putStones(player, pitNumber, stonesFromOppositePit);
	}

	private int getPitsPerPlayer(Board board) {
		return (int) board.getPits()
				.stream()
				.filter(pit -> !pit.isMancala())
				.count() / 2;
	}

}
