package npuzzle;

public class HeurManhattan implements HeuristicaInterf{

	@Override
	public int heuristica(int[][] actual, int[][] meta) {
		
		return Problema.heurManhattan(actual, meta);
	}
	
	
}
