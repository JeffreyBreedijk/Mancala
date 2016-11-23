package nl.jb.service;

import java.util.Map;

import nl.jb.domain.Pit;
import nl.jb.domain.Board;
import static nl.jb.domain.Board.generatePitId;
import org.springframework.stereotype.Service;

@Service
public class TurnService {

	public boolean doTurn(Board board, int player, int pitNumber) {
		String pitId = generatePitId(player, pitNumber);
		int stonesInHand = board.getStones(pitId);
		return placeStones(board, pitId, stonesInHand, player);
	}

	private boolean placeStones(Board board, String pitId, int stonesInHand, int player) {
		int remainingStones = stonesInHand;
		boolean anotherTurn = true;
		boolean passed = false;
		for (Map.Entry<String, Pit> entry : board.getPits().entrySet()) {
			if (passed && remainingStones != 0 && entry.getValue().acceptIncement(player)) {
				remainingStones--;
				anotherTurn = entry.getValue().isMancala();
			}
			if (!passed && entry.getKey().equals(pitId)) {
				passed = true;
			}
		}
		while (remainingStones != 0) {
			for (Pit pit : board.getPits().values()) {
				if (remainingStones != 0 && pit.acceptIncement(player)) {
					remainingStones--;
					anotherTurn = pit.isMancala();
				}
			}
		}
		return anotherTurn;
	}

}
