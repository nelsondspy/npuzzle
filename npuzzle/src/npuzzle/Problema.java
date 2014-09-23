package npuzzle;

public class Problema {
    
	/**Todos los Tipos Acciones posibles para realizar la transicion de un
	 * estado a otro. 
	 * En el contexto del problema son movimientos de la casilla vacia.
	 */
    public enum Accion { SUBIR, BAJAR, IZQUIERDA, DERECHA };

	
	/**
	 * Test objetivo, donde la casilla vacia puede estar en 
	 * las esquina derecha superior o derecha inferior ejemplo:
	 *  1 2 3    0 1 2 
	 *  4 5 6    3 4 5
	 *  7 8 0    6 7 8 .. son estados-objetivo
	 *  Debemos considerar otros estados-objetivo?
	 * */
	public static boolean testObjetivo(int[][] matriz){
		int anterior=0;
		int n = matriz.length  ;
		
		/*si la casilla vacia no esta en estas posiciones
		  no es estado objetivo*/
		if (! (matriz[0][0]== 0 || matriz[n-1][n-1]== 0) ) return false;
		
		anterior = 0;
		for(int f=0; f < n; f++ ){
             for(int c=0; c < n ; c++ ){
            	 if (matriz[f][c] == 0) continue ;
            	 /* si el valor actual es igual 
            	  * el valor anterior incrementado*/
            	 if (! ( matriz[f][c] == (anterior + 1) ) ){
            		return false; 
            	 }
            	 anterior = matriz[f][c];
            }
        }
        //Pudo iterar sobre todos los valores, pasa la prueba
        return true;
	}
	
	/**
	 * Retorna el opuesto de una accion
	 * */
	public static Accion accionOpuesta(Accion accion){
		if (accion==null) return null; 
		switch (accion) {
		case DERECHA:
			return Accion.IZQUIERDA;
			
		case IZQUIERDA:
			return Accion.DERECHA;		
		case SUBIR:
			return Accion.BAJAR;
		case BAJAR:
			return Accion.SUBIR;
		}
		return null;
	}
}
