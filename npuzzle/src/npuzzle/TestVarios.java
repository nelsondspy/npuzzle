package npuzzle;
import java.util.Iterator;
import java.util.ArrayList;

import npuzzle.Problema.Accion;

import java.util.Arrays;



public class TestVarios {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][]matriz = new int [5][5];
		System.out.println("length:" + matriz.length);
		
		/*int m2 [][] = {
	    		{5,	6	,2},
	    		{8	,0	,7},
	    		{3	,1	,4 }
	    		};
		*/
		int m1 [][] = {
	    		{1,	0	,2},
	    		{4	,3	,5},
	    		{6	,7	,8 }
	    		};
		Tablero t = new Tablero(m1);
		
		ArrayList <Accion> movimienPosib = t.accionesPosibles(null);
	    Iterator<Accion> itr = movimienPosib.iterator();
	    System.out.println(t.toString());
	    //listar movimientos posibles 
	    while (itr.hasNext()) {
	    	Accion mov = itr.next();
	        
	    	System.out.println("===============================");
	        System.out.println("despues de mover " + mov.toString() );
	        
	        //Tablero nuevot = new Tablero(t.matriz);
	        Tablero nuevot = t.clonar();
	        
	        nuevot.mover(mov);
	        //prueba de test objetivo
	        System.out.println("T.O:" + Problema.testObjetivo(nuevot.matriz));
	        
	        System.out.println(nuevot.toString());
	        System.out.println("-----");
	      }
	    
	    
	    
	    
	    
	}
}
