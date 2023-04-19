/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Raul_515
 */
public class GrafoConColores<T> extends Grafo<T>{
    
    //Atributos
    private HashMap<T,Integer> tablaColores;
    
    //Metodos
    public GrafoConColores() {
        super();
        tablaColores = new HashMap<>();
    }
    
    public void MostrarTablaColores(){   
        System.out.println(tablaColores.toString());     
    }
    
    public int getColor(T v){
        return tablaColores.get(v);
    }
    
    public void asignarColorVertice(T v, int n){
        if(colorValido(v,n)){
            tablaColores.put(v, n);
        }else{
            System.out.println("El color: " + n + " no se puede asignar al vertice: " + v + ".");
        }
        
    }
    
    public void eliminarColorVertice(T v){
        tablaColores.remove(v);
    }
    
    public int informarColorVertice(T v){
        return tablaColores.get(v);
    }
    
    public void eliminarTodosColores(){
        tablaColores.forEach((k,v) -> eliminarColorVertice(k));
    }
    
    public ArrayList<T> listarVerticesColoreados(){
        return (new ArrayList<>(tablaColores.keySet()));
    }
    
    public boolean colorValido(T v, int n){
        
        for (T vertice : listarVerticesAdyacentes(v)){
            if(informarColorVertice(vertice) == n){
                return false;
            }
        }    
        return(true);
       
    }
    
    @Override
    public void añadirArista(T v1, T v2) throws GrafoException{
        if(tablaColores.containsKey(v1) && (tablaColores.containsKey(v2))){
            if(informarColorVertice(v1) != informarColorVertice(v2)){
                super.añadirArista(v1, v2);
            }else{
                System.out.println("No se puede añadir esta arista");
            } 
        }else{
            super.añadirArista(v1, v2);
        }
        
    }
    
    @Override
    public void mostrarGrafo(){
        String grafo = "";
        ArrayList<T> vertices = listarVertices();
        Iterator<T> it = vertices.iterator();
        
        while(it.hasNext()){
            T vertice = it.next();
            grafo += vertice + ", ";
            if(tablaColores.containsKey(vertice)){
                grafo += getColor(vertice) + ", ";
            }else{
                grafo += "Sin color, ";
            }
            ArrayList<T> adyacentes = listarVerticesAdyacentes(vertice);
            grafo += adyacentes.toString() + "\n";
            
        
        }
        System.out.println(grafo);
    }
    
    public boolean colorear(int numColores){
     List<T> listaVertices;
     // lista auxiliar en la que colocaré todos los vértices

     /* Para poder aplicar el algoritmo de coloración de un grafo necesito tener los 
        vértices almacenados en orden. En primer lugar colocaré los vértices que
        tienen ya un color asignado (este color no podrá modificarse). A
        continuación colocaré en la lista el resto de vértices, a los que el algoritmo
        de coloración irá asignando diferentes colores hasta dar con una
        combinación correcta.
     */

    List<T> listaVerticesColoreados=(List<T>) this.listarVerticesColoreados();
    List<T> listaVerticesNoColoreados= super.listarVertices( ); //todos
    listaVerticesNoColoreados.removeAll(listaVerticesColoreados); //quito los
	                                                                  //coloreados
	    
    //Compruebo que los colores que ya están asignados son correctos
    for(T v:listaVerticesColoreados){
    	if (!this.colorValido(v, this.informarColorVertice(v)))
    		return false;
    }
    // el método esColorValido recibe un vértice y un color, 
    // si no cambiar el orden de los parámetros

    // vuelco los vértices en la nueva lista, en el orden correcto
    listaVertices=new ArrayList< >( );
    listaVertices.addAll(listaVerticesColoreados);
    listaVertices.addAll(listaVerticesNoColoreados);

    int k=listaVerticesColoreados.size( );
    boolean b=this.coloreoConRetroceso(listaVertices, k, numColores);

    if (!b) {
      // no se ha podido colorear el grafo
      // vuelvo a la situación inicial

      for (int i = 0, n= listaVerticesNoColoreados.size( );i < n; i++) {
        this.tablaColores.remove(listaVerticesNoColoreados.get(i));
      }
     }
    return b;
 }


 private boolean aceptable(List<T> listaVertices, int color, int posicion){
   /*
      devuelve true si al vértice que ocupa la posición “posicion” en listaVertices
      puedo asignarle el color “color” de modo que no haya ningún vértice en las
      posiciones anteriores que sea adyacente y que tenga el mismo color asignado.
   */

   T vertPos= listaVertices.get(posicion);
   boolean acept=true;
   for (int i=0; i<posicion && acept; i++){
     if (super.verticesAdyacentes(listaVertices.get(i),vertPos) &&
         this.informarColorVertice(listaVertices.get(i))== color)
       acept=false;
   }
   return acept;
 }


 private boolean coloreoConRetroceso(List<T> listaVertices, int k, int numColores){
   /*
    Supongo que a los vértices situados en las posiciones 0..k-1
    de listaVertices ya les he asignado color.
    Busco un color para el vértice en la posición k que sea compatible
    con los anteriores.
   */

    if (k==listaVertices.size( ))
     return true;
   else {
     T vertAux=listaVertices.get(k);
     for (int c=1; c<=numColores; c++){
       if (this.aceptable(listaVertices,c, k)) {
         this.tablaColores.put(vertAux, c);
         boolean b=coloreoConRetroceso(listaVertices,k + 1, numColores);
         if (b)
           return b;
       }
     }
   }
   // he recorrido todas las combinaciones y ninguna es válida, devuelvo falso.
	   return false;

  }


}
