package npuzzle;
import java.util.ArrayList;
import java.util.Random;

import npuzzle.Nodo;
import npuzzle.Tablero;

public class Problema {
    
	/**Todos los Tipos Acciones posibles para realizar la transicion de un
	 * estado a otro. 
	 * En el contexto del problema son movimientos de la casilla vacia.
	 */
    public enum Accion { SUBIR, BAJAR, IZQUIERDA, DERECHA };

    
	/**
	 * Tablero meta , necesario para la busqueda A-informada
	 * */
    public static  int[][] MATRIZ_META;
    
    
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
	
	public static boolean testObjetivo_A(int[][] matriz){
		int anterior=0;
		int n = matriz.length  ;
		
		/*si la casilla vacia no esta en estas posiciones
		  no es estado objetivo*/
		if (! ( matriz[n-1][n-1]== 0) ) return false;
		
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
	
	
	/**
	 * @return cantidad de inversiones en la matriz 
	 * */
	public static int cantInversiones(int[][] matriz){
		
		int n = matriz.length  ;
		
		int cantInversiones = 0  ;

		for(int f=0; f < n; f++ ){
             for(int c=0; c < n ; c++ ){
            	 if (matriz[f][c] == 0) continue ;
            	 int valorI = matriz[f][c];
            	 for(int ff = f; ff < n ; ff++ ){
            		 int k = 0;
            		 if(ff == f){
            			 k = c ;
            		 } 
            		 
            		 for(int cc=k ; cc < n ; cc++ ){
        				             			 
        				 int valorAdelante = matriz[ff][cc];
            			 
            			 if(valorAdelante < valorI && ! ( valorAdelante == 0)) {
            				 
            				 cantInversiones ++;
            			 }
            			 
            		 }
            	 }
            }
        }
		
		return  cantInversiones ;
        
	}
	
	/**
	 * Devuelve la lista de inversa de acciones 
	 * del nodo objetivo al nodo inicial.
	 * Si 1,2,3 son estados y a,b,c son acciones 
	 * 1[estado inicial]-(a)->2-(b)->3[estado objetivo]
	 * el programa retornara b,a..
	 * */
	public static ArrayList<Accion> accionesSolucion(Nodo nodoSolucion){
		ArrayList<Accion> acciones = new ArrayList<Accion>();
		
		while (nodoSolucion.padre != null){
			acciones.add(nodoSolucion.accion);
			nodoSolucion = nodoSolucion.padre;
		}
		return acciones ;
	}
	
	
	/**/
    public static int heurManhattan(int[][] actual, int [][] meta){
        int x1=0, x2=0,y1=0,y2=0;        
        int n= 1;
        int res=0;
        int tam = actual.length;
        while(n <= tam*tam-1){
            for(int i=0 ; i < tam; i++){           
                 for(int j=0 ; j < tam; j++){                 
                     if (meta[i][j]== n){
                         x2=i; y2=j;
                     }
                     if (actual[i][j]==n){
                         x1=i; y1=j;
                     }
                 }             
            }    
            res= res+Math.abs(x1-x2)+ Math.abs(y1-y2);
          n++;
        }
        
        //hallar euristica
        return res;
    }
    
    /** 
     * Genera el tablero meta dado el tamanho de filas y columnas
     * @return matriz cuadrada con valores del estado objetivo
     */    
    public static int [][]generarMeta(int tam) {      
        int[][] met= new int[tam][tam];
        for(int i=0 ; i < tam; i++){           
             for(int j=0 ; j < tam; j++){             
                 met[i][j]= tam* i + j + 1;
             }             
        }
        met[tam-1][tam-1]=0; 
       
        return met;
    }
    

    /**
     * @since 1.1
     * @param Problema . es la matriz con la cual se va a comparar.
     * @param Solucion . es la matriz solucion.
     * @return h1. Es el valor de la heuristica. Es decir la cantidad de 
     * piezas fuera de lugar.
     */
    public static int heurPiezasFueradLug(int[][] Problema, int[][] Solucion ){
        
        /*Valor de la h1*/
        int h1 = 0;
  
        for (int x=0; x < Problema.length; x++) 
            for (int y=0; y < Problema[x].length; y++) 
                if (Problema[x][y] !=0 && Problema[x][y] != Solucion[x][y]  )
                   h1++;
        
        return h1;
    }
    
    /**
     * 
     * Metodo que devuelve una intancia de tablero desordenada aleatoriamente
     * nveces.
     * @tablero que debe desordenar
     * @param numero de acciones aplicadas para desordenar el tablero
     * @return instancia de tablero
     * 
     * */
    public static void desordenar(Tablero tablero ,int nveces ){
    	    	
    	Random generador = new Random();
    	
    	for(int i = 1; i <= nveces ; i++){
    		ArrayList <Accion> acciones = tablero.accionesPosibles(null);
    		int indexaccion = generador.nextInt( acciones.size() ) ;
    		tablero.mover(acciones.get(indexaccion ));
    	}
    	
    }
    

	 
}
