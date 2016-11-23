package nl.jb.domain;

public class Pit {

	private boolean isMancala;
	private int player;
	private int size;

	public Pit(boolean isMancala, int player, int size) {
		this.isMancala = isMancala;
		this.player = player;
		this.size = size;
	}

	public boolean acceptIncement(int player) {
		if (!isMancala || this.player == player) {
			size++;
			return true;
		}
		return false;
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
}
