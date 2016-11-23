package nl.jb.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Board {

	private int players;
	private int numberOfPlayerPits;
	private Map<String, Pit> pits;

	public Board(int stones, int numberOfPlayerPits, int players) {
		this.players = players;
		this.numberOfPlayerPits = numberOfPlayerPits;
		pits = new LinkedHashMap<String, Pit>();
		for (int p = 0; p < players; p++) {
			for (int b = 0; b < numberOfPlayerPits; b++) {
				pits.put(generatePitId(p, b), new Pit(false, p, stones));
			}
			pits.put(p + "." + "m", new Pit(true, p, 0));
		}
	}

	public Map<String, Pit> getPits() {
		return pits;
	}

	public int getStones(String pitId) {
		Pit pit = pits.get(pitId);
		int stones = pit.getSize();
		pit.setSize(0);
		return stones;
	}


	public static String generatePitId(int player, int bin) {
		return player + "." + bin;
	}




}
