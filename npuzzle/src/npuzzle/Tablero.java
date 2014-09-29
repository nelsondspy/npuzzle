/* Tablero
 * 21/09/2014
 * 
 * 
 * */
package npuzzle;

import java.util.ArrayList; 

import npuzzle.Problema.Accion;

public class Tablero {


	public int [][] matriz ;

    private int XcasillaVacia;
    private int YcasillaVacia;
    private int tamanho = 0 ;
    ArrayList<Accion> movimPosibles; 

    
    public Tablero(int n, boolean desorden){
        tamanho = n ;
        matriz = new int [n][n];
        
       //LLena la matriz con valores aleatorios si le paso verdadero
        if ( desorden ) MatrizUtils.cargaDesorden(matriz);
        
        //Obtiene la posicion de la casilla vacia (valor 0)
        int[] pos = MatrizUtils.buscarDato(matriz, 0);
        XcasillaVacia = pos [1];
        YcasillaVacia = pos [0];
    }
    
    
    public Tablero(int[][] matriz){
    	
    	/*Instancia un tablero, con una matriz */
    	
    	this.matriz = matriz;
    	
    	//Obtiene la posicion de la casilla vacia (valor 0)
    	int[] pos = MatrizUtils.buscarDato(matriz, 0);
    	XcasillaVacia = pos [1];
        YcasillaVacia = pos [0];
        tamanho = matriz.length  ;
    }
    
    
    public int getTamanho(){
         return tamanho;
    }

   /**
    * Lista los movimientos posibles de la casilla vacia,
    *  recibe como parametro la accion cuya accion debe excluir
    *  Si se le pasa un null devuelve todos los movimientos posibles
    **/
    public ArrayList <Accion> accionesPosibles(Accion movimExcluir){
    	ArrayList<Accion> movPos = new ArrayList<Accion>();
        //movimientos sobre fila 
    	int puedeIzq = XcasillaVacia - 1;
        int puedeDer = XcasillaVacia + 1;
       //movimientos sobre columna
        int puedeAbajo = YcasillaVacia + 1;
        int puedeArriba = YcasillaVacia - 1;
        
        /*El objetivo es que no caiga en indices invalidos
         * si cae dentro del array entonces el movim. es posible*/
        if ( puedeIzq >= 0 ){
            movPos.add(Accion.IZQUIERDA);
        }
        if ( puedeDer < tamanho  ){
            movPos.add(Accion.DERECHA);
        }
        if ( puedeArriba >= 0 ){
            movPos.add(Accion.SUBIR);
        }
        if ( puedeAbajo < tamanho ){
            movPos.add(Accion.BAJAR);
        }
        /*Si recibe el parametro un no nulo , elimina
         * de la lista de movimietos posibles */
        if (movPos != null){
        	movPos.remove(movimExcluir);
        }
        
        movimPosibles = movPos ;
        return movPos ;
    }
    
    

    /**
     * Metodo que mueve la pieza 
     * Asume que el movimiento es valido, es decir que no desbordara 
     * 
     * */
    public void mover(Accion mov ){
    	int x=0, y=0;
    	switch (mov) {
		case DERECHA:
			x=1; y=0;
			break;
		case IZQUIERDA:
			x=-1; y=0;
			break;
		case SUBIR:
		    x=0; y=-1;
		    break;
		case BAJAR:
		    x=0; y=1;
		    break;
		default:
			x=0; y=0;
			break;
		}
    	/* Hace un swap entre los valores que contiene la casilla vacia 
    	 * y la casilla a mover */
    	int fila , col ;
    	fila = YcasillaVacia + y;
    	col = XcasillaVacia + x ;
    	int aux = matriz[ fila ][ col ];
    	matriz[ fila ][ col ] =  0;
    	//las filas son coordenadas Y
    	matriz[YcasillaVacia][XcasillaVacia] = aux;
    	
    	/*Actualiza la Posicion de la casilla vacia
    	 *Importante recordar la dif entre posicion valor*/
    	XcasillaVacia = XcasillaVacia  + x;
    	YcasillaVacia = YcasillaVacia  + y ;
    }
    
    /*Muestra el tablero, la posicion de la casilla vacia*/
    public String toString(){
    	String ret;
    	ret = MatrizUtils.imprimeTablero(this.matriz);
    	ret += "\n XcasillaVacia:" + XcasillaVacia;
    	ret += "\nYcasillaVacia:" + YcasillaVacia ;
    	return ret ;
    }
    
    /**
     * Por algun motivo no funciona adecuadamente clone() con las matrices
     * entonces implementamos nosotros mismos una funcion
     * */
    public Tablero clonar(){
    	Tablero nuevoTablero = new Tablero(tamanho, false);
    	MatrizUtils.copiarMatrices(this.matriz, nuevoTablero.matriz);
    	nuevoTablero.XcasillaVacia = this.XcasillaVacia ;
    	nuevoTablero.YcasillaVacia = this.YcasillaVacia;
    	return nuevoTablero ;
    }
    
    

    public String toStrHash(){
    	return MatrizUtils.strElementos(matriz);
    }
    
}
