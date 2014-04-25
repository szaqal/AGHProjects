package graphs.editor.domain;

/**
 * Model danych wierzcholka
 * @author malczyk.pawel@gmail.com
 *
 */
public class VertexDataModel implements Cloneable {
	
	/** Okresla czy jest to zrodlo (wykorzystywane przez niektore algorytmy). */
	private boolean isSource;
	
	/** Okresla czy jest to wezel docelowy (wykorzystywane przez niektore algorytmy). */
	private boolean isTarget;
	
	/** Biezacy indeks wezla*/
	private int index;
	
	/** Waga wezla */
	private int weight;
	
	/** Okresla czy wierzcholek jest pokolorowany. */
	private boolean isColoured;
	
	//gettery i settery

	public final boolean isSource() {
		return isSource;
	}

	public final void setSource(boolean isSource) {
		this.isSource = isSource;
	}

	public final boolean isTarget() {
		return isTarget;
	}

	public final void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}

	public final int getIndex() {
		return index;
	}

	public final void setIndex(int index) {
		this.index = index;
	}

	public final int getWeight() {
		return weight;
	}

	public final void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public final boolean isColoured() {
		return isColoured;
	}

	public final void setColoured(boolean isColoured) {
		this.isColoured = isColoured;
	}
}
