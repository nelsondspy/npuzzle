package npuzzle;
import java.util.ArrayList;
import java.util.Iterator;


import npuzzle.Problema.Accion;

public class Nodo {
    Nodo padre;
    Tablero estado;
    Accion accion;
    int profundidad = 0;
    
    public Nodo(Tablero estado, Nodo padre, Accion accion){
    	this.estado = estado;
    	this.padre = padre; 
    	this.accion = accion ;
    	
    	if (padre==null){ 
    		this.profundidad=0;
    	}else{
    		this.profundidad = padre.profundidad + 1 
    	;} 
    	    
    }
    
    public Nodo(){
    	
    }
    
    
    /**
     * Metodo que expande un nodo, en la frontera
     * @param nodo a expandir 
     * @param frontera donde se insertaran los nuevos nodos
     * 
     * */
    public static void expandir(Nodo nodoAexp, ArrayList<Nodo>frontera){
    	
    	/*Lista las acciones posibles del estado a excepcion de la accion 
    	 * que condujo al estado por ejemplo 
         * si llego con "subir" , puede volver al mismo estado con "bajar"
         * y nuevamente con bajar puede ir a subir! 
         * entonces se debe evitar el estado "bajar" (evitar un ciclo ).
    	 */
    	    	
    	ArrayList <Accion> acciones = nodoAexp.estado.accionesPosibles(null);
    	
    	
    	//iterador de acciones 
    	Iterator<Accion> itrAcciones = acciones.iterator();
    	while (itrAcciones.hasNext()) {
	    	Accion accion = itrAcciones.next();
	    	
	    	//nuevo estado 
	    	Tablero nuevoEstado = nodoAexp.estado.clonar();
	    	nuevoEstado.mover(accion);

	    	//mapaEstadosgen.put(hashmatriz,nuevoEstado.toStrHash());
	    	
	    	Nodo nuevonodo = new Nodo(nuevoEstado, nodoAexp, accion);
	    	
	    	//inserta en la frontera
	    	frontera.add( nuevonodo );
	    	
    	}
    }
    
}
