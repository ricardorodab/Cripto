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
import java.util.Hashtable;
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
public class Mezclado{

    private static String linea1;
    private static String linea2;    
    private static Hashtable<Character,Character> conversion
	= new Hashtable<Character,Character>();
    
    public static void cifra(String clave, String entrada) throws IOException{
	String linea = null;
	BufferedReader texto = null;

	try{
	    BufferedWriter escritor = new BufferedWriter(new FileWriter("salida.cifrado"));
	    BufferedReader claves = new BufferedReader(new FileReader(clave));
	    linea1 = claves.readLine().toUpperCase();
	    linea2 = claves.readLine().toUpperCase();
	    putHash(linea1,linea2);
	    texto = new BufferedReader(new FileReader(entrada));
	    while((linea = texto.readLine()) != null){
		for(int i = 0; i < linea.length(); i++){
		    char fila = linea.toUpperCase().charAt(i);
		    if(!conversion.containsKey(fila)){
			System.out.print(fila);
			escritor.write(fila);
		    }else{
			System.out.print(conversion.get(fila));
			escritor.write(conversion.get(fila));
		    }
		    escritor.flush();
		}
	    }		    
	}catch(FileNotFoundException e){
	    System.err.println("El archivo de entrada no existe.");
	}       
    }

    public static void descifra(String clave, String entrada) throws IOException{
	String linea = null;
	BufferedReader texto = null;

	try{
	    BufferedWriter escritor = new BufferedWriter(new FileWriter("salida.descifrado"));
	    BufferedReader claves = new BufferedReader(new FileReader(clave));
	    linea1 = claves.readLine().toUpperCase();
	    linea2 = claves.readLine().toUpperCase();
	    putHash(linea2,linea1);
	    texto = new BufferedReader(new FileReader(entrada));
	    while((linea = texto.readLine()) != null){
		for(int i = 0; i < linea.length(); i++){
		    char fila = linea.toUpperCase().charAt(i);
		    if(!conversion.containsKey(fila)){
			System.out.print(fila);
			escritor.write(fila);
		    }else{
			System.out.print(conversion.get(fila));
			escritor.write(conversion.get(fila));
		    }
		    escritor.flush();
		}
	    }		    
	}catch(FileNotFoundException e){
	    System.err.println("El archivo de entrada no existe.");
	}       
    }


    public static void putHash(String l1, String l2){
	for(int i = 0; i < l1.length(); i++){
	    conversion.put(l1.charAt(i),l2.charAt(i));
	}
    }
    
}
