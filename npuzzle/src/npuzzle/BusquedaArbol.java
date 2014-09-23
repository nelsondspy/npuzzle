package npuzzle;
import java.util.ArrayList;
import java.util.HashMap;
public class BusquedaArbol {
	
	/*lista de estados generados para evitar volver 
	 * a generar estados repetidos */
	 
	
	public void busquedaAncho(){
		
		HashMap <String, String> mapaEstadosgen; 
		
		mapaEstadosgen = new HashMap <String, String>();
		
		Tablero tablero = new Tablero(3, true);
		
		Nodo nodoinicial = new Nodo(tablero, null, null) ;
		
		Nodo nodo = new Nodo();
		
		
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
				
				int estadoob[][] ={{0,1,2},{3,4,5},{6,7,8}};
				
				int estadoob2[][] ={{1,2,3},{4,5,6},{7,8,0}};
				
				System.out.println("t1:" + mapaEstadosgen.get(MatrizUtils.strElementos(estadoob)));
				System.out.println("t2:" + mapaEstadosgen.get(MatrizUtils.strElementos(estadoob2)));
				System.out.println(nodoinicial.estado.toString());
				return ;
			}
			
			
			//cola fifo , obtiene y remueve el primero
			nodo = frontera.remove(0);
			
			if (Problema.testObjetivo(nodo.estado.matriz)){
				System.out.println("solucionado papaa!");
				System.out.println(nodo.estado.toString());
				System.out.println("profundidad:" + nodo.profundidad);
				return ;
			}
			
			
			/*se deben evitar los estados repetidos por eso almacena 
	    	 * en la lista de hashCodes, uno de cada estado*/
	    	
	    	String hashmatriz = nodo.estado.toStrHash();
	    	
			if (mapaEstadosgen.containsKey(hashmatriz)){
				cantRepetidos ++;
				continue;
	    	}
			
		    //inserta en la lista de hash de estados
		    mapaEstadosgen.put(hashmatriz,hashmatriz);
			
			//expande y los nuevos nodos los inserta directamente en la frontera
			Nodo.expandir(nodo, frontera);
			
			//estados generados 
			cantgenerado = frontera.size() + 1;
			System.out.println("Total Estados generados:" + cantgenerado  );
			
			
		}
		
	}
	
	public static void main(String[] args) {
		BusquedaArbol busquedanueva = new BusquedaArbol();
		busquedanueva.busquedaAncho();
		
	}
}
