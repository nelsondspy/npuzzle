package npuzzle;
import java.util.ArrayList;
import java.util.HashMap;




public class BusquedaArbol {
		 
	/**
	 * Metodo que realiza la busqueda por ancho
	 * @return un nodo objetivo  o null si se produjo un fallo
	 * */
	public static Nodo busquedaAncho(Tablero tablero){
		
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
				return null ;
			}
				
			//obtiene y remueve el primero
			nodo = frontera.remove(0);
			
			if (Problema.testObjetivo(nodo.estado.matriz)){							
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
	
	
	public static void main(String[] args) {
		
		//Genera una matriz con los datos del estado meta
		Problema.MATRIZ_META = Problema.generarMeta( 3 );
				
				
		BusquedaArbol busqueda = new BusquedaArbol();
		
		Tablero tablero = new Tablero(3,true);
				
		//Nodo resultado = busqueda.busquedaAncho( tablero );
		//Nodo resultado = busqueda.busquedaAmas( tablero );
		//si el resultado es null entonces fallo la busqueda
		/*if (resultado != null){	
			
			System.out.println(resultado.estado.toString());
			
			System.out.println(Problema.accionesSolucion(resultado).toString());
			
			System.out.println(tablero.toString());
			
			
		}
		else{
			System.out.println("Fallo la busqueda");
		}
		*/
	}
	
	
	public static Nodo busquedaAmas(Tablero tablero, HeuristicaInterf heur ){
		
		
		/*Lista de estados generados para evitar volver 
		 * a generar estados repetidos */
		HashMap <String, String> mapaEstadosgen; 
		mapaEstadosgen = new HashMap <String, String>();
		
		//Nodo inicial 
		
		int costoH = Problema.heurManhattan(tablero.matriz,Problema.MATRIZ_META);
		
		Nodo nodoinicial = new Nodo(null,tablero, null, costoH) ;
		
		Nodo nodo = nodoinicial;
		
		//Frontera: Lista de nodos a expandir 
		ArrayList<Nodo>frontera = new ArrayList<Nodo>();
		
		//agrega el estado inicial a la frontera
		frontera.add( nodoinicial );
	
		while(true){
				
			if ( frontera.isEmpty() ){
				System.out.println("mapaEstadosgen.size():" + mapaEstadosgen.size());
				return null ;
			}
				
			//obtiene y remueve el ultimo insertado,comportamiento de pila 
			nodo = frontera.remove( frontera.size() - 1 );
			
			
			
			if (Problema.testObjetivo(nodo.estado.matriz)){							
				return nodo ;
			}
			
			//Hash del estado, osea la matriz
	    	String hashmatriz = nodo.estado.toStrHash();
	    	
			if ( ! mapaEstadosgen.containsKey( hashmatriz )){
				
				System.out.println(nodo.estado.toString());
				System.out.println("  getCosto()" +nodo.getCosto() );
				System.out.println("...............................");
				   
				//inserta en la lista de hash de estados
			    mapaEstadosgen.put(hashmatriz, hashmatriz);
			
			    //expande y los nuevos nodos los inserta directamente en la frontera
				Nodo.expandirNodo(nodo, frontera,  heur);
	    	}					
		}
	}
}
