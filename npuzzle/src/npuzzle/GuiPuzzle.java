package npuzzle;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class GuiPuzzle implements ActionListener{//implementando el listener de eventos
 
    JButton bCrear, bEjecutar;//creando variables globales de los botones
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
        nodos = new JTextField(10);
        tiempo = new JTextField(10);
        
        //Instanciando boton con texto
        bCrear = new JButton("Generar Puzzle");
        bEjecutar = new JButton("Ejecutar");
        
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
        panelbot.setPreferredSize(new Dimension(200,80));
        panelbot.add(bCrear);
        panelbot.add(bEjecutar);
        
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
    	if(e.getSource()==bCrear){//podemos comparar por el contenido del boton   		
            //Los campos de texto son de tipo string, asi que tomamos la cadena con el metodo .getText()
            //y lo almacenamos en la variable.
            n = Integer.parseInt(tn.getText());
            //crear puzzle
            panelpuzz.removeAll();
            
            //Crea la matriz meta 
            Problema.MATRIZ_META = Problema.generarMeta( n );
            
            //Instancia el tablero con la matriz meta
            tableroProblema = new Tablero(Problema.MATRIZ_META);
            
            //desordena el tablero aletaroriamente
            Problema.desordenar(tableroProblema , 2);
            
            //hallar la cantidad de inversiones 
            int inversiones = Problema.cantInversiones(tableroProblema.matriz);
            
            if (inversiones % 2 != 0){
            	System.out.println("NO ES RESOLUBLE!");
            	JOptionPane.showMessageDialog(null,"NO ES RESOLUBLE!.");
            }
            
            puzz  = tableroProblema.matriz;
            
            jep.setContentType("text/html");
            jep.setText("<bold>Problema:</bold>" + tablaHTML(puzz) );
            
            //completar(puzz, panelpuzz);
        }
    	if (e.getSource()==bEjecutar){
    		
    		//Establece el tipo de busqueda seleccionado
    		TipoBusqueda tipobusq = TipoBusqueda.ANCHO;
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
    }
    
    private void completar(int[][] puzz, JPanel panel)
	{
    	int filas = puzz.length;
		int columnas = puzz[0].length;
		Font fuente=new Font("Dialog", Font.BOLD, 15);
		//frame.getContentPane().setLayout(new GridLayout(filas,columnas));
		panel.setLayout(new GridLayout(filas,columnas));
		JTextField [][] textField = new JTextField [filas][columnas];
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				textField[i][j] = new JTextField(2);		
				if (puzz[i][j]==0) {
					textField[i][j].setBackground(Color.BLACK);
				} else {
					textField[i][j].setText(Integer.toString(puzz[i][j]));
					textField[i][j].setFont(fuente);
					textField[i][j].setHorizontalAlignment(JTextField.CENTER); 
				}	
				textField[i][j].setEditable(false);
				panel.add(textField[i][j]);
			}
		}
		frame.setVisible(true);
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
}