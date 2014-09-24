package npuzzle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusquedaArbol {
	
	/*lista de estados generados para evitar volver 
	 * a generar estados repetidos */
	 
	
	public void busquedaAncho(Tablero tablero){
		
		HashMap <String, String> mapaEstadosgen; 
		
		mapaEstadosgen = new HashMap <String, String>();
		
		//Tablero tablero = new Tablero(3, true);
		
		Nodo nodoinicial = new Nodo(tablero, null, null) ;
		
		Nodo nodo = nodoinicial;
		
		ArrayList<Nodo>frontera = new ArrayList<Nodo>();
		
		//crea un nodo con estado inicial del tablero 
		frontera.add( nodoinicial );
		
		int cantgenerado = 0 ;
		int cantRepetidos = 0 ;
		
		while(true){
			
			
			if ( frontera.isEmpty() ){
				
				System.out.println("que mala onda!");
				System.out.println("cantRepetidos:"+ cantRepetidos);
				System.out.println("mapaEstadosgen:"+ mapaEstadosgen.size());
				System.out.println("frontera.size():"+ frontera.size());
				System.out.println(nodoinicial.estado.toString());
				
				return  ;
			}
				
			//cola fifo , obtiene y remueve el primero
			nodo = frontera.remove(0);
			
			if (Problema.testObjetivo(nodo.estado.matriz)){
				System.out.println("solucionado papaa!");
				System.out.println(nodo.estado.toString());
				System.out.println("profundidad:" + nodo.profundidad);
				/*for (Map.Entry<String, String> entry : mapaEstadosgen.entrySet()) {
					System.out.println(entry.getKey()) ; 
				}*/
				
				return ;
			}
			
			
			/*se deben evitar los estados repetidos por eso almacena 
	    	 * en la lista de hashCodes, uno de cada estado*/
	    	
	    	String hashmatriz = nodo.estado.toStrHash();
	    	
			if ( ! mapaEstadosgen.containsKey(hashmatriz)){
				    
				//inserta en la lista de hash de estados
			    mapaEstadosgen.put(hashmatriz,hashmatriz);
			
			    //expande y los nuevos nodos los inserta directamente en la frontera
				Nodo.expandir(nodo, frontera);
			
	    	}		
			
			//estados generados 
			cantgenerado = frontera.size() + 1;
			//System.out.println("Total Estados generados:" + cantgenerado  );
			
			
		}
		
	}
	
	public static void main(String[] args) {
		BusquedaArbol busquedanueva = new BusquedaArbol();
		
		int m1 [][]= {{7,6,1},{4,0,5},{3,2,8}} ;
		
		int m2 [][] = {
		{5,	6	,2},
		{8	,0	,7},
		{3	,1	,4 }
		};

		Tablero tablero = new Tablero( m1 );
		
		busquedanueva.busquedaAncho( tablero );
		
	}
}
