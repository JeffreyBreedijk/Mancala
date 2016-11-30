package nl.jb.domain;

import java.util.Set;
import java.util.TreeSet;

public class Board {


	private int players;
	private int numberOfPlayerPits;
	private Set<Pit> pits = new TreeSet<>();

	public Board(int stones, int numberOfPlayerPits, int players) {
		this.players = players;
		this.numberOfPlayerPits = numberOfPlayerPits;
		for (int p = 0; p < players; p++) {
			for (int b = 0; b < numberOfPlayerPits; b++) {
				pits.add(new Pit(false, p, stones, b));
			}
			pits.add(new Pit(true, p, 0, numberOfPlayerPits));
		}
	}

	public Set<Pit> getPits() {
		return pits;
	}

	public void setPits(Set<Pit> pits) {
		this.pits = pits;
	}

	public int getStones(int player, int pitId) {
		Pit pit = getPit(player, pitId);
		int stones = pit.getSize();
		pit.setSize(0);
		return stones;
	}

	public void putStones(int player, int pitId, int stones) {
		Pit pit = getPit(player, pitId);
		pit.addStones(stones);
	}

	public Pit getPit(int player, int pitId) {
		return pits.stream()
				.filter(pit -> pit.getPlayer() == player)
				.filter(pit -> pit.getPitNumber() == pitId)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Not a valid pit"));
	}



}
