/* -------------------------------------------------------------------
 * Vigenere.java
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
 * Clase que crea el comportamiento del cifrado Vigenere. </p>
 *                            
 * <p>                        
 * Crea el comportamiento para cifrar Vigenere. </p>
 */
public class Vigenere{

    
    private static final int a = 0x41;
    private static final int z = 0x5a;
    private static int s;
    private static ArrayList<Character> claveArray =
	new ArrayList<Character>();
    private static int lugar = 0;
    
    /**
     * Metodo para crear una tabla que contenga el número de elementos.
     * que tiene el abecedario.
     * @return tablaCifrado - regresa la tabla de alafabeto Vigenere.
     */
    public static char[][] creaTabla(){
	char[][] tablaCifrado = new char[26][26];
	for(int i = 0; i < 26; i++){
	    for(int j = 0; j < 26; j++){
		int k = ((i+j) % 26) + 65;
		tablaCifrado[i][j] = (char)k;   
	    }	
	}
	return tablaCifrado;
    }   
    
    /**
     * Metodo que verifica si la palabra clave tiene algún elemento que no sea valido.
     * @param palabraClave - es la palabra que revisara que sean puras letras.
     * @return true - si la palabra clave tiene elementos que no sean letras.
     */
    public static boolean verificaClave(char palabraClave){
	String tmp = new String(new char[] {palabraClave});
	return (a > (s = (int)tmp.toUpperCase().charAt(0))
		|| (s > z));
    }
    
    /**
     * Metodo que cifra un archivo de texto con el metodo de Vigenere.
     * @param clave - es la palabra clave con la que cifra.
     * @param entrada - es el archivo que contiene el texto a cifrar.
     */
    public static void cifra(String clave, String entrada)
	throws IOException{
	String linea = null;
	String lineaClave = null;
	BufferedReader texto = null;
	int k = 0;
	char[][] tabla = creaTabla();
	try{
	    BufferedWriter escritor = new BufferedWriter(new FileWriter("salida.cifrado"));
	    texto = new BufferedReader(new FileReader(entrada));
	    llenaClave(clave);
	    while((linea = texto.readLine()) != null){
		char palClave = sigClave();		
		for(int u = 0; u < linea.length() ; u++){
		    char fila = linea.toUpperCase().charAt(u);
		    if(a > (s = (int)fila) || (s > z)){
			escritor.write((char)s);
			System.out.print((char)s);
		    }else {
			char columna = palClave;
			for(int q = a ; q <= z ; q++){
			    char r = (char)q;
			    if(r == fila){
				System.out.print(tabla[q-a][(int)columna-a]);
				escritor.write(tabla[q-a][(int)columna-a]);
				k++;
				palClave = sigClave();
				break;
			    }
			}
		    }
		    escritor.flush();
		}
	    }
	    System.out.println("");
	}catch (FileNotFoundException e){
	    System.err.println("El archivo de entrada no existe.");
	}finally{
	    if(texto != null){
		texto.close();	    
	    }
	}	
    }
    
    /**
     * Metodo que descifra un archivo de texto con el metodo de Vigenere.
     * @param clave - es la palabra clave con la que se descifra.
     * @param entrada - es el archivo que contiene el texto a descifrar.
     */    
    public static void descifra(String clave, String entrada)
	throws IOException{
	String linea = null;
	BufferedReader texto = null;	 
	int k = 0;
	char[][] tabla = creaTabla();
	try{
	    BufferedWriter escritor = new BufferedWriter(new FileWriter("salida.descifrado"));
	    texto = new BufferedReader(new FileReader(entrada));
	    llenaClave(clave);
	    while((linea = texto.readLine()) != null){
		char palClave = sigClave();
		for(int u = 0; u < linea.length() ; u++){
		    char caracter = linea.toUpperCase().charAt(u);
		    if(a > (s = (int)caracter) || (s > z)){
			System.out.print((char)s);
			escritor.write((char)s);
		    }else {
			char columna = palClave;
			for(int q = a ; q <= z ; q++){
			    if(caracter == tabla[q-a][(int)columna-a]){
				System.out.print((char)q);
				escritor.write((char)q);
				k++;
				palClave = sigClave();
				break;
			    }
			}
		    }
		    escritor.flush();
		}
	    }
	    System.out.println("");
	}catch (FileNotFoundException e){
	    System.err.println("El archivo de entrada no existe.");
	}
	finally{
	    if(texto != null){
		texto.close();	    
	    }	
	}
    }

    public static void llenaClave(String clave) throws IOException{
	/*clave = clave.toUpperCase();
	for(int i = 0; i < clave.length(); i++){
	    if(verificaClave(clave.charAt(i)))
		continue;
	    claveArray.add(clave.charAt(i));
	}*/
	//Es que aqui recibia la clave en un archivo:
	FileReader file = new FileReader(clave);
	int entrada = 0;
	while(entrada != -1){
	    entrada = file.read();
	    if(verificaClave((char)entrada) || entrada == -1)
		continue;
	    String tmp = new String(new char[] {(char)entrada});
	    claveArray.add(tmp.toUpperCase().charAt(0));
	}
	file.close();
    }

    public static char sigClave(){
	return claveArray.get(lugar++ % claveArray.size());	
    }    
} //Fin de Vigenere.java
