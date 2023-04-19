/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author Raul_515
 * @param <T>
 */
public class Grafo<T>{
    
    //Atributos
    private HashMap<T,ArrayList<T>> tabla;
    
    //Metodos
    
    
    public Grafo() {
       tabla = new HashMap<>();
    }

        
    public void añadirVertice(T v) throws GrafoException{
        if(pertenece(v)){
            throw new GrafoException("El vertice " + v + " ya existe.");
        }else{
            ArrayList aristas = new ArrayList();
            tabla.put(v, aristas);
        }
        
    }
    
    /*
    public void añadirVertice(T v, ArrayList<T> adyacencias){
        tabla.put(v, adyacencias);
    }
    */
    
    public void añadirArista(T v1, T v2) throws GrafoException{
        if(!pertenece(v1)){
            throw new GrafoException("El vertice " + v1 + " no existe.");
        }else if(!pertenece(v2)){
            throw new GrafoException("El vertice " + v2 + " no existe.");
        }else{
            tabla.get(v1).add(v2);
            tabla.get(v2).add(v1);
        }
        
    }
    
    public void eliminarVertice(T v1) throws GrafoException{
        if (pertenece(v1)){
            ArrayList<T> vertices = listarVertices();
        Iterator<T> it = vertices.iterator();
        
        while(it.hasNext()){
            T vertice = it.next();
            if(tabla.get(vertice).contains(v1)){
                tabla.get(vertice).remove(v1);
            }
            
            tabla.remove(v1);
        }
        }else{
            throw new GrafoException("No se puede elimimnar el vertice "+ v1 +", no existe");
        }
        
    }
        
    
    public void eliminarArista(T v1, T v2) throws GrafoException{
        if(tabla.get(v1).contains(v2)){
            tabla.get(v1).remove(v2);
            tabla.get(v2).remove(v1);
        }else{
            throw new GrafoException("No se puede eliminar la arista " + v1 +"-" + v2+", no existe");
        }
    }
    
    public int numVertices(){
        
        return tabla.size();
        
    }
    
    public boolean verticesAdyacentes(T v1, T v2){
        boolean adyacente = false;
        
        if(tabla.get(v1) == null){
            return adyacente;
        }else{
            adyacente = tabla.get(v1).contains(v2); 
        }
        
        return adyacente;
        
    }
    
    public boolean pertenece(T v1){
        return tabla.get(v1) != null;
    }
    
    public  ArrayList<T> listarVertices(){
        return (new ArrayList<>(tabla.keySet()));
    }
    
    /*
    
    Version que había hecho yo para listar los Vertices.
    
    public  ArrayList<T> listarVertices(){
        ArrayList<T> vertices = new ArrayList<>();
        Set<T> set = tabla.keySet();
        Iterator<T> it = set.iterator();
        
        while(it.hasNext()){
            T vertice = it.next();
            vertices.add(vertice);
        }
        return vertices;
    }
    */
    
    public ArrayList<T> listarVerticesAdyacentes(T v){
       return tabla.get(v);
        
    }
    
    public void mostrarGrafo(){
        String grafo = "";
        ArrayList<T> vertices = listarVertices();
        Iterator<T> it = vertices.iterator();
        
        while(it.hasNext()){
            T vertice = it.next();
            grafo += vertice + ", ";
            ArrayList<T> adyacentes = listarVerticesAdyacentes(vertice);
            grafo += adyacentes.toString() + "\n";
            
        
        }
        System.out.println(grafo);
    }
    
}
    