package npuzzle;



import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane; // 

import npuzzle.Problema.Accion;

public class GuiPuzzle implements ActionListener{//implementando el listener de eventos
 
    JButton bCrear, bEjecutar, bReporte ;//creando variables globales de los botones
    JLabel ln, lmetodo, lheuristica, lv, lnodos, ltiempo;//creando variables globales para las etiquetas
    JTextField tn, nodos, tiempo;//creando variables globales para los campos de texto
    JFrame frame;//creacion de ventana con el titulo
    JPanel paneln, panelba, panelhu, panelppal, panelbot, panelpuzz, panelres, panelnum;
    JRadioButton rdBa, rdA, rdHu1, rdHu2;
    ButtonGroup grAlg, grHu;
    JScrollPane scrPane = new JScrollPane(frame);
    
    //contenedor e interprete html
    JEditorPane jep ;
    
    int [][] puzz;
    
    //Es el tablero inicial con el problema a resolver
    Tablero tableroProblema;
    //Nodo resultado de la busqueda
    Nodo nodoResultado;
    //datos complementarios de la busqueda
    HashMap <String, String> extradat = new HashMap <String, String>();
    //Tipos de busqueda  manhattan
    private enum TipoBusqueda {ANCHO, A_HMANHAT, A_PIEZFD };
    
    public GuiPuzzle(){//constructor de la clase        
 
        //Definir decoradores de la ventana.
        JFrame.setDefaultLookAndFeelDecorated(true);
        //Crear y configurar la ventana
        frame = new JFrame("N-Puzzle");
    	frame.setIconImage (new ImageIcon("puzzle.jpg").getImage());
    	frame.setSize(700, 500);
    	frame.setLayout(new FlowLayout());//Configurar como se dispondra el espacio del jframe
                
        Dimension d = new Dimension();//objeto para obtener el ancho de la pantalla
        
        //Instanciando etiquetas
        ln = new JLabel(" N:");
        lmetodo = new JLabel(" Algoritmo:");
        lheuristica = new JLabel(" Heuristica:");
        lv = new JLabel("");
        lnodos = new JLabel("Nodos:");
        ltiempo = new JLabel("Tiempo(ms):");
        
        //Instanciando cuadros de texto
        tn = new JTextField(3);
        tn.setText("3");
        nodos = new JTextField(10);
        tiempo = new JTextField(10);
        
        //Instanciando boton con texto
        bCrear = new JButton("Generar Puzzle");
        bEjecutar = new JButton("Buscar solucion");
        bReporte = new JButton("reporte");
        
        paneln  = new JPanel();
        panelba  = new JPanel();
        panelhu  = new JPanel();
        panelppal  = new JPanel();
        panelbot  = new JPanel();
        panelpuzz  = new JPanel();
        panelres  = new JPanel();
        panelnum  = new JPanel();
        
        jep = new JEditorPane();
        jep.setEditable(false); 
        
        grAlg = new ButtonGroup();
        grHu = new ButtonGroup();
        
        rdBa = new JRadioButton("Búsqueda en Ancho");
        grAlg.add(rdBa);
        
        rdA = new JRadioButton("Búsqueda A*");
        grAlg.add(rdA);
        rdBa.setSelected(true);
        
        rdHu1 = new JRadioButton("Distancia de Manhattan");
        grHu.add(rdHu1);
        
        rdHu2 = new JRadioButton("Nro. de piezas en lugar incorrecto");
        grHu.add(rdHu2);
        
        paneln.setLayout(new GridLayout(0,3));
        paneln.add(ln);
        paneln.add(tn);
        
        panelba.setLayout(new GridLayout(3,0));
        panelba.add(lmetodo);
        panelba.add(rdBa);
        panelba.add(rdA);
        
        panelhu.setLayout(new GridLayout(3,0));
        panelhu.add(lheuristica);
        panelhu.add(rdHu1);
        panelhu.add(rdHu2);
        
        panelppal.setLayout(new GridLayout(0,2));
        panelppal.add(panelba);
        panelppal.add(panelhu);
        
        panelbot.setLayout(new FlowLayout());
        panelbot.setPreferredSize(new Dimension(200,100));
        panelbot.add(bCrear);
        panelbot.add(bEjecutar);
        panelbot.add(bReporte);
        
        panelnum.setLayout(new GridLayout(4,0));
        panelnum.add(lnodos);
        panelnum.add(nodos);
        panelnum.add(ltiempo);
        panelnum.add(tiempo);
        
        //añadiendo objetos a la ventana
        frame.add(paneln);
        frame.add(panelppal);
        frame.add(panelbot);
        frame.add(panelpuzz);
        frame.add(panelres);
        frame.add(panelnum);
       
        frame.add(jep);
        
        //añadiendo el listener a los botones para manipular los eventos del click
        bCrear.addActionListener(this);        
        bEjecutar.addActionListener(this);
        bReporte.addActionListener(this);
        
        frame.add(scrPane);
        scrPane.setLayout(new ScrollPaneLayout());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//finaliza el programa cuando se da click en la X        
        frame.setResizable(true);//para configurar si se redimensiona la ventana
        frame.setLocation((int) ((d.getWidth()/2)+290), 50);//para ubicar inicialmente donde se muestra la ventana (x, y)
        frame.setVisible(true);//configurando visualización de la venta
        //codigo de prueba
        
    }
 
    public static void main(String[] args) {        
        GuiPuzzle gj = new GuiPuzzle();//uso de constructor para la ventana
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {//sobreescribimos el metodo del listener
    	int n;
    	TipoBusqueda tipobusq = TipoBusqueda.ANCHO;
    	
    	if(e.getSource()==bCrear){//podemos comparar por el contenido del boton   		
            
    		
    		//Valida que ingrese N
    		if ((tn.getText().length()) == 0){ 
    			JOptionPane.showMessageDialog( null, "Ingrese N:" );
    			tn.requestFocus();
    			return ;
    		};
    		
    		n = Integer.parseInt(tn.getText());
            
            //crear puzzle
            panelpuzz.removeAll();
            
            //Crea la matriz meta 
            Problema.MATRIZ_META = Problema.generarMeta( n );
            
            //Instancia el tablero con la matriz meta
            int[][] estadoIni = new int [n][n];
            
            //int[][] estadoIni = {{2,4,3},{1,0,5},{7,8,6}};
            
            MatrizUtils.copiarMatrices(Problema.MATRIZ_META, estadoIni);
            
            tableroProblema = new Tablero(estadoIni);
            
            //desordena el tablero aletaroriamente
            Problema.desordenar(tableroProblema, 50 );
            
            //hallar la cantidad de inversiones 
            int inversiones = Problema.cantInversiones(tableroProblema.matriz);
            
            if (inversiones % 2 != 0){
            	
            	JOptionPane.showMessageDialog(null,"NO ES RESOLUBLE!.");
            }
            
            puzz  = tableroProblema.matriz;
            
            jep.setContentType("text/html");
            jep.setText("<bold>Problema:</bold>" + tablaHTML(puzz) );
            
        }
    	if (e.getSource()==bEjecutar){
    		
    		//valida que haya generado el problema
    		if(tableroProblema==null){
    			JOptionPane.showMessageDialog( null, "Genere el tablero problema antes de buscar" );
    			return ;
    		} 
    		
    		//Establece el tipo de busqueda seleccionado
    		
    		if (rdBa.isSelected()) tipobusq = TipoBusqueda.ANCHO ;
    		if (rdA.isSelected() && rdHu1.isSelected()) tipobusq =TipoBusqueda.A_HMANHAT ;
    		if (rdA.isSelected() && rdHu2.isSelected())tipobusq = TipoBusqueda.A_PIEZFD ;
    		
    		long time_start = 0 , time_end =0;
    		
    		switch (tipobusq) {
    		
			case ANCHO :
    			time_start = System.currentTimeMillis();
    			/*Llama al metodo de busqueda A-Ancho*/	
    			nodoResultado = BusquedaArbol.busquedaAncho(tableroProblema, extradat);
    			time_end = System.currentTimeMillis();
				break;
				
			case A_HMANHAT:				
    			time_start = System.currentTimeMillis();
    			/*Llamar al metodo de busqueda*/
    			HeurManhattan heurMan = new HeurManhattan();
    			nodoResultado = BusquedaArbol.busquedaAmas(tableroProblema, heurMan , extradat );
    			time_end = System.currentTimeMillis();
    		
				break;
				
			case A_PIEZFD:	
    			time_start = System.currentTimeMillis();
    			/*Llamar al metodo de busqueda*/
    			HeurFueradLug heurPFL = new HeurFueradLug();
    			nodoResultado = BusquedaArbol.busquedaAmas(tableroProblema, heurPFL, extradat);
    			time_end = System.currentTimeMillis();
				break;
			
			default:
				break;
			}
    		
    		//verifica el resultado
    		if (nodoResultado == null){
				JOptionPane.showMessageDialog(null,"fallo al resolver!.");
			}else{
				jep.setText("<p>Probl:</p>"+tablaHTML(puzz)+
					"<p>Sol:</p>"+tablaHTML(nodoResultado.estado.matriz));
				
				
			}
			
			panelres.removeAll();
			tiempo.setText(""+(time_end - time_start)); //completa el tiempo
			//cantidad de nodos
			String cantnodos = extradat.get("CANT_ESTGEN");
			if (cantnodos != null) nodos.setText(cantnodos);
			
			System.out.println("El algoritmo Busqueda "+ tipobusq + "tardo "+ 
					( time_end - time_start ) +" milisegundos ");		
    	}
    	
    	//genera y abre el reporte
    	if (e.getSource()==bReporte){
    		if (nodoResultado != null) reporteSolucion(tipobusq);
    		
    	}
    }
    
    
    public static String tablaHTML(int[][] matriz){
    	  String strsalida="<table fontsize=\"10\" border=\"1\">" ;
    	  
          for(int f=0; f< matriz.length; f++ ){
        	  
        	  strsalida += "<tr>";
        	  
               for(int c=0; c< matriz.length; c++ ){
            	   if (matriz[f][c] != 0 ){
            		   strsalida += "<td>"+matriz[f][c] + "</td>";   
            	   }else{ 
            		   strsalida += "<td>&nbsp;</td>"; 
            	   }
               }
               
               strsalida += "</tr>";
               strsalida +="\n";
          }
          strsalida +="</table>" ;
          return strsalida;
          
    }
    
    /*Metodo que muestra un reporte html con la lista de acciones a seguir*/
    private void reporteSolucion(TipoBusqueda tipobusq ){
    	//salida a achivo
    	PrintWriter out = null ;
    	
    	try {
			out = new PrintWriter("resultado_busq.html");
		}
    	catch (Exception e) {
		
    		e.printStackTrace();
    	}
    	
    	//this.nodoSolucion
    	ArrayList<Accion> listaac = Problema.accionesSolucion(nodoResultado);
    	
    	int size = listaac.size()- 1;
    	
    	String salida ="<p style=\"{color:red;}\">Problema:" +
    			tablaHTML(tableroProblema.matriz)  + "</p>";

    	salida += "<p style=\"{color:green;}\"> Tipo de busqueda: " + tipobusq + "</p>";
    	salida += "<p>ACCIONES A APLICAR= " + size  + " acciones :</p>";
    	
    	Tablero tableroresul = tableroProblema.clonar();
    	
    	out.println(salida);
    	salida="";
    	int c = 0 ;
    	for(int i=size ; i>=0 ; i--){
    		
    		Accion accionaplicar = listaac.get(i);
    		tableroresul.mover(accionaplicar);
    		
    		salida += "<div style=\"{ float:left; margin-left:20px; "
    				+ "border: 1px solid;border-color:grey;}\"><p>" + 
    				accionaplicar + tablaHTML(tableroresul.matriz) +  "</p></div>";
    		
    		c++;
    		
    		if (c==20){
    			out.println(salida);
    			salida="";
    			c = 0;
    		}
    	}
    	
    	out.println(salida);
    		
		File file = new File ("resultado_busq.html");
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
    	
    }
}