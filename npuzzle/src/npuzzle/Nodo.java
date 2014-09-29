package npuzzle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import npuzzle.Problema.Accion;

public class Nodo {
    Nodo padre;
    Tablero estado;
    Accion accion;
    int profundidad = 0;
    int costo = 0 ;
    int heuristica=0 ;
    
    public Nodo(Tablero estado, Nodo padre, Accion accion){
    	this.estado = estado;
    	this.padre = padre; 
    	this.accion = accion ;
    	
    	if (padre==null){ 
    		this.profundidad=0;
    	}else{
    		this.profundidad = padre.profundidad + 1 ;
    	} 
    	    
    }
    
    
    /* */
    public Nodo(Nodo padre, Tablero estado, Accion accion, int costoHeur) {
        this.padre = padre;
        this.estado = estado;
        this.accion = accion;
        this.heuristica = costoHeur;
        
        if (padre==null){ 
    		
        	this.profundidad=0;
    		
    	
        }else{
        	
    		this.profundidad = padre.profundidad + 1 ;
    		
    	}
        
        this.costo = this.profundidad + costoHeur;
        
        
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
	    	
	    	//Tablero nuevoEstado = new Tablero(m1);
	    	Tablero nuevoEstado = nodoAexp.estado.clonar();
	    	nuevoEstado.mover(accion);

	    	//mapaEstadosgen.put(hashmatriz,nuevoEstado.toStrHash());
	    	
	    	Nodo nuevonodo = new Nodo(nuevoEstado, nodoAexp, accion);
	    	
	    	//inserta en la frontera
	    	frontera.add( nuevonodo );
	    	
    	}
    }
    
    
    /** metodo que expande un nodo segun las acciones que se puedan realizar a partir de el 
     */
     public static void expandirNodo(Nodo nodoAexp , ArrayList<Nodo>frontera ,
    		 HeuristicaInterf heur, HashMap <String, String> mapaEstadosgen ){    
         //listado de nodos posibles al expandir
        ArrayList <Accion> acciones = nodoAexp.estado.accionesPosibles(null);
        Iterator<Accion> itrAcciones = acciones.iterator();

        
        int mejorCosto = -1 ;
        
        ArrayList<Nodo> mejoresNodos = new ArrayList<Nodo>();
        
        int costoTotalNodo = 0 ;
        
    	while (itrAcciones.hasNext()) {
            Accion accion = itrAcciones.next();
            
            Tablero nuevoEstado = nodoAexp.estado.clonar();
            nuevoEstado.mover( accion );    
            
            //control para evitar evaluar heuristicas para estados ya generados
            String hashmatriz = nuevoEstado.toStrHash();
            
            if (  mapaEstadosgen.containsKey( hashmatriz )){
            	continue ;
            }
            
            mapaEstadosgen.put(hashmatriz, hashmatriz);
            
            
            // se obtine el costo  del posible nodo a expandir y se crea un tablero con el estado correspóndiente
            int costoH = heur.heuristica (nuevoEstado.matriz, Problema.MATRIZ_META );
            
            Nodo nuevoNodo = new Nodo(nodoAexp,nuevoEstado,accion,costoH );
            
            costoTotalNodo = nuevoNodo.getCosto();
            
            if ( mejorCosto < 0 ) mejorCosto = costoTotalNodo ;
            
            if (costoTotalNodo <= mejorCosto ){
		
            	mejorCosto = costoTotalNodo ;
            	
            	mejoresNodos.add( nuevoNodo );
            } 
            
            //System.out.println(nuevoNodo.getAccion()+" queda "+nuevoEstado.toString()+" costo "+nuevoNodo.getCosto());
            
    	}
    	int tam = mejoresNodos.size();
    	
    	for(int i = 0 ; i <tam ; i++){
    		if( mejoresNodos.get(i).getCosto() <= mejorCosto )
    			
    			frontera.add(mejoresNodos.get( i ));
    	}

        System.out.println("frontera.size():" + frontera.size());

    }


	public Nodo getPadre() {
		return padre;
	}


	public void setPadre(Nodo padre) {
		this.padre = padre;
	}


	public Tablero getEstado() {
		return estado;
	}


	public void setEstado(Tablero estado) {
		this.estado = estado;
	}


	public Accion getAccion() {
		return accion;
	}


	public void setAccion(Accion accion) {
		this.accion = accion;
	}


	public int getProfundidad() {
		return profundidad;
	}


	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}


	public int getCosto() {
		return costo;
	}


	public void setCosto(int costo) {
		this.costo = costo;
	}
     

    
}
