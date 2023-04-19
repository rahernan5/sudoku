/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raul_515
 */
public class Sudoku {
    
    //Atributos
    private GrafoConColores resolver;
    private int[][] sudoku;
    private int[][] auxInicio;
    private int N;
    //Metodos

    public Sudoku(int[][] sudoku) {
        this.resolver = new GrafoConColores();
        this.sudoku = sudoku;
        this.N = sudoku.length;
    }
    
 
    public Sudoku(int n){
        resolver = new GrafoConColores();
        sudoku = new int[n][n];
        N = n;
    }
    
    public void construirAuxInicial(){ 
        auxInicio = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                auxInicio[i][j] = sudoku[i][j];
            }
        }
    }
    
    
    private void construirGrafoInicial( ){
// sudoku es el nombre del atributo de Sudoku que contiene los números (vector bidimensional)
// resolver es el nombre del atributo de Sudoku que contiene el grafo con colores, que tiene que haber sido inicializado antes
	int numFilas= this.sudoku.length; // o int numFilas= this.numFilas; si tenéis el atributo numFilas
	int numVertices=numFilas*numFilas;
	for (int v=1; v<=numVertices; v++)
	    try {
                this.resolver.añadirVertice(v);
            } catch (GrafoException ex) {
                Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
            }
	 	
	//Añado aristas para todas las parejas de vértices que están en la misma fila
	  for (int i = 0; i < numFilas ; i++) {
	    for (int j = 0; j < numFilas; j++) {
	      for (int k = j + 1; k < numFilas ; k++) {
	        int v1=numFilas*i + j+1;
	        int v2=numFilas*i + k+1;
                  try {
                      this.resolver.añadirArista(v1, v2);
                  } catch (GrafoException ex) {
                      Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
                  }
	      }
	    }
	  }
	
	  //Añado aristas para todas las parejas de vértices que están en la misma
	  // columna
	  for (int j = 0; j < numFilas; j++) {
	    for (int i = 0; i < numFilas ; i++) {
	      for (int k = i + 1; k < numFilas ; k++) {
	        int v1=numFilas*i + j+1;
	        int v2=numFilas*k + j+1;
                  try {
                      this.resolver.añadirArista(v1, v2);
                  } catch (GrafoException ex) {
                      Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
                  }
	      }
	    }
	  }
	
	  //Añado aristas para todas las parejas de vértices que están en la misma región
	  int n = (int)Math.sqrt(numFilas);
	  for (int i = 0; i < n ; i++) {
		  for (int j = 0; j < n; j++) {
			int i0 = i * n;
			int j0 = j * n;
			// (i0,j0) es la esquina superior izquierda de la región
			for (int i1 = i0; i1 < i0 + n; i1++) {
				for (int j1 = j0; j1 < j0 + n; j1++) {
		          for (int i2 =i0; i2<i0+n; i2++){
		            for (int j2 = j0; j2 < j0 + n; j2++) {
		              int v1 = numFilas * i1 + j1 + 1;
		              int v2 = numFilas * i2 + j2 + 1;
		              if ((v2 != v1) && !this.resolver.verticesAdyacentes(v1, v2))
		                try {
                                    this.resolver.añadirArista(v1, v2);
                              } catch (GrafoException ex) {
                                  Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
                              }
		            }
		          }
		        }
		      }
	   }
	 }
	
	 // Por último añado los colores a los vértices correspondientes a los
	 // valores iniciales del sudoku
	 for (int i=0; i<numFilas; i++){
	   for (int j=0; j<numFilas; j++){
	     if (this.valorInicial(i,j)!=0) // Los parámetros del método valorInicial tienen que empezar en 0,
		    	 // si no cambiar en esta llamada i y j por i+1, j+1
	       this.resolver.asignarColorVertice(i*numFilas+j+1,this.valorInicial(i,j));
// el método asignarColor recibe un vértice y un color, 
// si no cambiar el orden de los parámetros
	   }
	 }
}

    
    public void AñadirEntero(int f,int c,int num){
        if(f<0 || f>N ){
            System.out.println("Valor incorrecto de fila");
        }else if(c<0 || c>N){
            System.out.println("Valor incorrecto de columna");
        }else if(num<=0 || num>N){
            System.out.println("valor incorrecto del numero a introducir.");
        }else{
            sudoku[f][c] = num;
        }
    }
    
    public void EliminarNumero(int f,int c){
        sudoku[f][c] = 0;
        
    }
    
    public void InformarValorCasilla(int f,int c){
        System.out.println(sudoku[f][c]);
    }
    
    public boolean NumeroValidoAñadir(int f,int c,int num){
        return resolver.colorValido(N, N);
    }
    
    public void mostrarSudoku(){
        for(int f=0; f < sudoku.length; f++) {
            System.out.print("|");
            for (int c=0; c < sudoku[f].length; c++) {
                System.out.print (sudoku[f][c]);
                if (c!=sudoku[f].length-1) System.out.print("\t");
            }
        System.out.println("|");
        }
        System.out.println();
    }
    
    public void resolverSudoku(){
        construirGrafoInicial();
        resolver.colorear(N);
        for (int i=0; i<N; i++){
            for (int j=0; j<N; j++){
	      sudoku[i][j] = this.resolver.getColor(i*N+j+1);
            }
        }
    }
    
    public int valorInicial(int i, int j) {
        return auxInicio[i][j];
    }
    
    public void reset(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sudoku[i][j] = auxInicio[i][j];
            }
        }
    }
    
}
