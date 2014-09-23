package npuzzle;
import java.util.Iterator;
import java.util.ArrayList;
import npuzzle.Problema.Accion;
import java.util.Arrays;



public class TestVarios {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tablero t = new Tablero(3,true);
		
		ArrayList <Accion> movimienPosib = t.accionesPosibles(null);
	    Iterator<Accion> itr = movimienPosib.iterator();
	    System.out.println(t.toString());
	    //listar movimientos posibles 
	    while (itr.hasNext()) {
	    	Accion mov = itr.next();
	        
	        System.out.println("despues de mover " + mov.toString() );
	        t.mover(mov);
	        //prueba de test objetivo
	        System.out.println("T.O:" + Problema.testObjetivo(t.matriz));
	        
	        System.out.println(t.toString());
	        System.out.println("-----");
	      }
	    
	    //test objetivo 1. debe dar true 
	    int estadoob[][] ={{0,1,2},{3,4,5},{6,7,8}};
	    System.out.println("T.O:" + Problema.testObjetivo(estadoob));
	    
	    //test objetivo 2 debe dar true 
	    int estadoob2[][] ={{1,2,3},{4,5,6},{7,8,0}};
	    System.out.println("T.O:" + Problema.testObjetivo(estadoob2));
	    
	    int estadoob3[][] ={{0,1,2},{3,4,5},{6,7,8}};
	    
	    /*7	 5	8
		  0	 1	2
		  3	 4	6
	    */
	    
	    System.out.println("hash1:" + Arrays.hashCode(estadoob[1]));
	    System.out.println("hash2:" + Arrays.hashCode(estadoob2));
	    System.out.println("hash3:" + Arrays.hashCode(estadoob3[1]));
	    
	}
}
