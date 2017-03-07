/* -------------------------------------------------------------------
 * Cesar.java
 * versión 2.0
 * Copyright (C) 2016  José Ricardo Rodríguez Abreu, Jimenez Mendez R..
 * Facultad de Ciencias,
 * Universidad Nacional Autónoma de México, Mexico.
 * Este programa es software libre; se puede redistribuir
 * y/o modificar en los términos establecidos por la
 * Licencia Pública General de GNU tal como fue publicada
 * por la Free Software Foundation en la versión 2 o
 * superior.                  
 *                            
 * Este programa es distribuido con la esperanza de que
 * resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho
 * sin la garantía implícita de COMERCIALIZACIÓN o
 * ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la
 * Licencia Pública General de GNU para mayores detalles.
 *
 * Con este programa se debe haber recibido una copia de la
 * Licencia Pública General de GNU, de no ser así, visite el
 * siguiente URL:             
 * http://www.gnu.org/licenses/gpl.html
 * o escriba a la Free Software Foundation Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * -------------------------------------------------------------------
 */

package Cifrados;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * @author Jose Ricardo Rodriguez Abreu, Jimenez Mendez Ricardo
 * @version 2.0               
 * @since Mar 6 2017.        
 * <p>                        
 * Clase que crea el comportamiento del cifrado Cesar. </p>
 *                            
 * <p>                        
 * Crea el comportamiento para cifrar Cesar. </p>
 */
public class Cesar{
    
    private static final int a = 0x41;
    private static final int z = 0x5a;
    private static int s;
    private static int lugares = 0;

    /**
     * Metodo para crear una tabla que contenga el número de elementos.
     * que tiene el abecedario.
     * @return tablaCifrado - regresa la tabla de alafabeto Cesar.
     */    
    public static char[] creaTabla(){
	char[] tablaCifrado = new char[26];
	for(int i = 0; i < 26; i++){	    
	    int k = (i % 26) + 65;
	    tablaCifrado[i] = (char)k;
	}
	return tablaCifrado;
    }

    /**
     * Metodo que cifra un archivo de texto con el metodo de Cesar.
     * @param clave - es la palabra clave con la que cifra.
     * @param entrada - es el archivo que contiene el texto a cifrar.
     */
    public static void cifra(String clave, String entrada) throws IOException{
	String linea = null;
	BufferedReader texto = null;
	char[] tabla = creaTabla();
	try{
	    lugares = Integer.parseInt(clave);
	    texto = new BufferedReader(new FileReader(entrada));
	    while((linea = texto.readLine()) != null){
		for(int i = 0; i < linea.length(); i++){
		    char fila = linea.toUpperCase().charAt(i);
		    if(a > (s = (int)fila) || (s > z)){
			System.out.print((char)s);
		    }else {
			System.out.print(tabla[((s-65)+lugares)%tabla.length]);
			
		    }
		}
	    }
	    System.out.println("");
	}catch(FileNotFoundException e){
	    System.err.println("El archivo de entrada no existe.");
	}catch(NumberFormatException n){
	    System.err.println("Se debe pasar un numero.");
	}
    } 


    /**
     * Metodo que descifra un archivo de texto con el metodo de Cesar.
     * @param clave - es la palabra clave con la que se descifra.
     * @param entrada - es el archivo que contiene el texto a descifrar.
     */    
    public static void descifra(String clave, String entrada) throws IOException{
	String linea = null;
	BufferedReader texto = null;
	char[] tabla = creaTabla();
	try{
	    lugares = Integer.parseInt(clave);
	    texto = new BufferedReader(new FileReader(entrada));
	    while((linea = texto.readLine()) != null){
		for(int i = 0; i < linea.length(); i++){
		    char fila = linea.toUpperCase().charAt(i);
		    if(a > (s = (int)fila) || (s > z)){
			System.out.print((char)s);
		    }else {
			int x = ((s-65)-lugares) % tabla.length;
			if(x < 0)
			    x += tabla.length;
			System.out.print(tabla[x]);
		    }
		}
	    }
	    System.out.println("");
	}catch(FileNotFoundException e){
	    System.err.println("El archivo de entrada no existe.");
	}catch(NumberFormatException n){
	    System.err.println("Se debe pasar un numero.");
	}
    }
} //Fin de Cesar.java
