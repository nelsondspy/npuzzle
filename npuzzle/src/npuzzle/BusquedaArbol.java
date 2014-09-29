package npuzzle;
import java.util.ArrayList;
import java.util.HashMap;




public class BusquedaArbol {
		 
	
   /**
     * Realiza la busqueda por ancho , generando todos los estados posibles
     * evitando repetirlos.
     * @param tablero con el estado inicial
     * @param @return datos extra de la busqueda realizada, estadisticas
     * */

	public static Nodo busquedaAncho(Tablero tablero, 
			HashMap <String, String> extradat){
		
		/*Lista de estados generados para evitar volver 
		 * a generar estados repetidos */
		HashMap <String, String> mapaEstadosgen; 
		mapaEstadosgen = new HashMap <String, String>();
		
		//Nodo inicial 
		Nodo nodoinicial = new Nodo(tablero, null, null) ;
		Nodo nodo = nodoinicial;
		
		//Frontera: Lista de nodos a expandir 
		ArrayList<Nodo>frontera = new ArrayList<Nodo>();
		
		//agrega el estado inicial a la frontera
		frontera.add( nodoinicial );
	
		while(true){
				
			if ( frontera.isEmpty() ){
				extradat.put("CANT_ESTGEN",Integer.toString(mapaEstadosgen.size()));
				return null ;
			}
				
			//obtiene y remueve el primero
			nodo = frontera.remove(0);
			
			if (Problema.testObjetivo(nodo.estado.matriz)){
				
				extradat.put("CANT_ESTGEN",Integer.toString(mapaEstadosgen.size()));
				
				return nodo ;
			}
			
			//Hash del estado, osea la matriz
	    	String hashmatriz = nodo.estado.toStrHash();
	    	
			if ( ! mapaEstadosgen.containsKey(hashmatriz)){
				    
				//inserta en la lista de hash de estados
			    mapaEstadosgen.put(hashmatriz,hashmatriz);
			
			    //expande y los nuevos nodos los inserta directamente en la frontera
				Nodo.expandir(nodo, frontera);
	    	}					
		}
	}
	
	
    /**
     * Busqueda en profundidad con informacion A*
     * @param tablero con el estado inicial
     * @param funcion heuristica a aplicar
     * @param @return datos extra de la busqueda realizada, estadisticas
     * */
	public static Nodo busquedaAmas(Tablero tablero, HeuristicaInterf heur, 
			HashMap <String, String> extradat){
		
		
		/*Lista de estados generados para evitar volver 
		 * a generar estados repetidos */
		HashMap <String, String> mapaEstadosgen; 
		mapaEstadosgen = new HashMap <String, String>();
		
		//Nodo inicial 
		
		int costoH = heur.heuristica(tablero.matriz,Problema.MATRIZ_META);
		
		Nodo nodoinicial = new Nodo(null,tablero, null, costoH) ;
		
		Nodo nodo = nodoinicial;
		
		//Frontera: Lista de nodos a expandir 
		ArrayList<Nodo>frontera = new ArrayList<Nodo>();
		
		//agrega el estado inicial a la frontera
		frontera.add( nodoinicial );
	
		while(true){
				
			if ( frontera.isEmpty() ){
				extradat.put("CANT_ESTGEN",Integer.toString(mapaEstadosgen.size()));
				return null ;
			}
				
			//obtiene y remueve el ultimo insertado,comportamiento de pila 
			nodo = frontera.remove( frontera.size() - 1 );
			
			if (Problema.testObjetivo(nodo.estado.matriz)){
				extradat.put("CANT_ESTGEN",Integer.toString(mapaEstadosgen.size()));
				return nodo ;
			}
			
			//expande y los nuevos nodos los inserta directamente en la frontera
			Nodo.expandirNodo(nodo, frontera,  heur, mapaEstadosgen);
	    						
		}
	}
}
