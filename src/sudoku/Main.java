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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            GrafoConColores grafo = new GrafoConColores();
            grafo.añadirVertice("a");
            grafo.añadirVertice("b");
            grafo.añadirVertice("c");
            grafo.añadirVertice("d");
            grafo.añadirVertice("e");
            grafo.añadirVertice("f");
            grafo.MostrarTablaColores();
            grafo.añadirArista("a", "b");
            grafo.añadirArista("b", "c");
            grafo.añadirArista("b", "d");
            grafo.añadirArista("c", "e");
            grafo.añadirArista("d", "e");
            grafo.añadirArista("e", "f");
            grafo.colorear(3);
            grafo.mostrarGrafo();
            
            Sudoku su = new Sudoku(4);
            su.AñadirEntero(0, 1, 2);
            su.AñadirEntero(1, 1, 3);
            su.AñadirEntero(2, 2, 1);
            su.AñadirEntero(3, 2, 2);
            su.mostrarSudoku();
            
            
        } catch (GrafoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
