package nl.jb.domain;

public class Pit implements Comparable<Pit> {

	private boolean isMancala;
	private Integer player;
	private int size;
	private Integer pitNumber;

	public Pit(boolean isMancala, int player, int size, int pitNumber) {
		this.isMancala = isMancala;
		this.player = player;
		this.size = size;
		this.pitNumber = pitNumber;
	}

	public boolean acceptIncement(int player) {
		return !isMancala || this.player == player;
	}

	public void addStones(int stones) {
		size = size + stones;
	}

	public boolean isMancala() {
		return isMancala;
	}

	public int getPlayer() {
		return player;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPitNumber() {
		return pitNumber;
	}

	@Override
	public int compareTo(Pit o) {
		int c = this.player.compareTo(o.getPlayer());
		if (c == 0) {
			return this.pitNumber.compareTo(o.getPitNumber());
		}
		return c;
	}
}
