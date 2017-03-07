/* -------------------------------------------------------------------
 * Cifrado.java
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

import java.io.IOException;
import Cifrados.*;

/**
 * @author Jose Ricardo Rodriguez Abreu, Jimenez Mendez Ricardo
 * @version 2.0               
 * @since Mar 6 2017.        
 * <p>                        
 * Clase principal para poder hacer uso de diversos tipos de cifrado. </p>
 *                            
 * <p>                        
 * Crea el comportamiento para cifrar de metodos Cesar, Afin, Mezclado y
 * Vigenere. </p>
 */
public class Cifrado{
    
    /**
     * Metodo que imprime el mensaje de uso de el programa.
     */
    public static void msgUso(){
	System.err.println("Para iniciar el cifrado/descifrado del texto, se "
			   +"debe invocar el programa de la siguiente manera:"
			   +"\njava Cifrado [c|d] [cesar | afin | mezclado | "
			   +"vigenere] archivoClave archivoEntrada");
    }
    
    /**
     * El metodo principal de el programa.
     *
     * @param args - son los argumentos de el programa.
     */
    public static void main(String[] args)
	throws IOException{
	if(args.length != 4){
	    msgUso();
	}else{
	    String procedimiento = args[0].toLowerCase().trim();;
	    String cifrado = args[1].toLowerCase().trim();
	    String clave = args[2].toLowerCase().trim();
	    String entrada = args[3].toLowerCase().trim();
	    switch(cifrado){
		
	    case "vigenere":
		if(procedimiento.equals("c")){
		    Vigenere.cifra(clave,entrada);
		}else if(procedimiento.equals("d")){
		    Vigenere.descifra(clave,entrada);		
		}else{ 
		    msgUso();
		}
		break;
	    case "cesar":
		if(procedimiento.equals("c")){
		    Cesar.cifra(clave,entrada);
		}else if(procedimiento.equals("d")){
		    Cesar.descifra(clave,entrada);		
		}else{ 
		    msgUso();
		}
		break;
	    case "afin":
		if(procedimiento.equals("c")){
		    Afin.cifra(clave,entrada);
		}else if(procedimiento.equals("d")){
		    Afin.descifra(clave,entrada);		
		}else{ 
		    msgUso();
		}
		break;
	    case "mezclado":
		if(procedimiento.equals("c")){
		    Mezclado.cifra(clave,entrada);
		}else if(procedimiento.equals("d")){
		    Mezclado.descifra(clave,entrada);		
		}else{ 
		    msgUso();
		}
		break;
	    default:
		msgUso();
	    }
	}
    }    
} //Fin de Main.java
