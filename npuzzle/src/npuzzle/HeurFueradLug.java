package npuzzle;

public class HeurFueradLug implements HeuristicaInterf{

		@Override
		public int heuristica(int[][] actual, int[][] meta) {
			
			return Problema.heurPiezasFueradLug(actual, meta);
		}

}
