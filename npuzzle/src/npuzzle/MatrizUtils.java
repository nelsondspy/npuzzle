package npuzzle;
import java.util.Random;

public class MatrizUtils {
	
	/**
	 * Metodo que recibe una matriz como parametro 
	 * 
	 * */
    public static void cargaDesorden(int[][] matriz){
    	int tamanho = matriz.length ;
        int max = tamanho * tamanho ;
        Random generador = new Random();
        int fila = 0 ;
        int col = 0 ;
        
        for(int v=1; v < max; v++ ){
            fila = generador.nextInt(tamanho) ;
            col = generador.nextInt( tamanho) ;
            /*Si las coordenadas generadas estan libres ( es 0) 
              entonces directamente asigna el valor*/
            if (matriz[fila][col] == 0){
                 matriz[fila] [col]  = v ;
                 continue ;
            }
            while( matriz[fila][col] != 0){
            	//prueba en la misma fila pero en la sigte columna
            	col ++;
            	//controla que no desborde la columna
                if(col >= tamanho ) {
                    col = 0; 
                    fila++ ;
                }
                //controla que no desborde en la fila
                if(fila >= tamanho ) {
                	fila = 0;
                }
                //si es una posicion libre,actualiza con el valor 
                if (matriz[fila][col] == 0){
                    matriz[fila] [col]  = v ;
                    break; //termina el bucle while 
                }
                else{
                    continue ;  //continua buscando 
                } 
                
            }
            
        }
    }
    
    public static String imprimeTablero(int[][] matriz ){
        String strsalida="";
        for(int f=0; f< matriz.length; f++ ){
             for(int c=0; c< matriz.length; c++ ){
            	 strsalida += "\t"+matriz[f][c];
            }
             strsalida +="\n";
        }
        return strsalida;
    }

    public static int[] buscarDato(int [][] matriz, int datobuscado){
        int [] coordenadas = { 0 , 0 } ;
        for(int f=0; f< matriz.length; f++ ){
             for(int c=0; c < matriz.length; c++ ){
                if (matriz[f][c]==datobuscado){
                    coordenadas[0] = f;
                    coordenadas[1] = c;
                }
            }
        }
        return coordenadas;
      }
    
    /**
     * Funcion que copia dos matrices del mismo tamanho
     * 
     * */
	public static void copiarMatrices(int [][] matriz1, int [][] matriz2){
	    for(int f=0; f< matriz1.length; f++ ){
	         for(int c=0; c < matriz1.length; c++ ){
	        	 matriz2[f][c]= matriz1[f][c];
	            
	        }
	    }

	  }
	
	public static String strElementos(int [][] matriz){
		String str="";
	    for(int f=0; f< matriz.length; f++ ){
	         for(int c=0; c < matriz.length; c++ ){
	        	 str = str + matriz[f][c] + ',';
	        }
	    }
	    return str;

	  }
	
	public static int hash(int [][] matriz){
		int h=7;
		
	    for(int f=0; f< matriz.length; f++ ){
	    	 
	         for(int c=0; c < matriz.length; c++ ){
	        	 h = h +((matriz[f][c] * f * 91) +  (matriz[f][c]* c *11)) + matriz[f][c] ;
	        
	        }
	    }
	    return h;

	  }

}



