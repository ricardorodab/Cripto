/* -------------------------------------------------------------------
 * second.c
 * version 1.0
 * Copyright (C) 2017  Jose Ricardo Rodriguez Abreu.
 * Facultad de Ciencias,
 * Universidad Nacional Autonoma de Mexico, Mexico.
 *
 * Este programa es software libre; se puede redistribuir
 * y/o modificar en los terminos establecidos por la
 * Licencia Publica General de GNU tal como fue publicada
 * por la Free Software Foundation en la version 2 o
 * superior.
 *
 * Este programa es distribuido con la esperanza de que
 * resulte de utilidad, pero SIN GARANTIA ALGUNA; de hecho
 * sin la garantia implicita de COMERCIALIZACION o
 * ADECUACION PARA PROPOSITOS PARTICULARES. Vease la
 * Licencia Publica General de GNU para mayores detalles.
 *
 * Con este programa se debe haber recibido una copia de la
 * Licencia Publica General de GNU, de no ser asi, visite el
 * siguiente URL:
 * http://www.gnu.org/licenses/gpl.html
 * o escriba a la Free Software Foundation Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * -------------------------------------------------------------------
 */

/**
 * @file second.c
 * @author Jose Ricardo Rodriguez Abreu
 * @date 6 Feb 2017
 * @brief File containing the second exercise from the first practice for the
 * "Concurrent computing" class.
 *
 * Este programa utiliza la biblioteca Pthread para crear 50 hilos y modificar
 * una variable que compartan todas en memoria. Tiene como objetivo primario
 * identificar los posibles riesgos de la programacion concurrente.
 *
 * El programa usa el estandar de documentacion que define el uso de 
 * doxygen.
 *
 * @see https://github.com/ricardorodab/concurrente
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define PALABRA "beetlejuicebe"
#define PALABRA_TAMANIO 11


int empareja_archivos(char *argv[])
{
int linea;
  char char_file1[1];
  char char_file2[1];
  int int_file1, int_file2;
  //Abrimos el archivo que nos hayan pasado como argumento.
  FILE *file1;
  FILE *file2;
  file1 = fopen(argv[1],"rb+");
  file2 = fopen(argv[2],"rb+");
  //Revisamos si el archivo abrio bien.
  if(file1 == NULL || file2 == NULL){
    printf("Algun archivo no existe o existe un problema al abrirlo.\n");
    return 1;
  }
  
  int_file1 = fscanf(file1,"%c",char_file1);
  int_file2 = fscanf(file2,"%c",char_file2);
  linea = 0;
  int i = 0;
  while(int_file1 != -1 || int_file2 != -1){    
    char *letra = PALABRA;
    if(int_file1 == -1){
      /*if(linea != -1){
	rewind(file1);
	int tmp = 0;
	while(tmp < linea){
	int_file1 = fscanf(file1,"%c",char_file1);
	fprintf(file1,"%c",char_file1[0]);
	tmp++;
	}
	//Falta tener que guardar en un archivo tmp
	//el texto y luego copiarlo.
	linea = -1;
	}*/
      int_file2 = fscanf(file2,"%c",char_file2);
      if(int_file2 != -1){
	if(char_file2[0] == '\n'){
	  fprintf(file1,"%c",'\n');
	}else{
	  fprintf(file1,"%c",letra[i%(PALABRA_TAMANIO)]);
	}
      }
      i++;
    }else if(int_file2 == -1){
      int_file1 = fscanf(file1,"%c",char_file1);
      if(int_file1 != -1){
	if(char_file1[0] == '\n'){
	  fprintf(file2,"%c",'\n');
	}else{
	  fprintf(file2,"%c",letra[i%(PALABRA_TAMANIO)]);
	}
      }
      i++;
    }else{   
      int_file1 = fscanf(file1,"%c",char_file1);
      int_file2 = fscanf(file2,"%c",char_file2);
      linea++;
    }
  }
  fclose(file1);
  fclose(file2);
  return 0;
}


int realiza_xor(char *archivos[])
{
  FILE *file1;
  FILE *file2;
  FILE *xor;
  char *nombre_archivo = "xor.out";
  unsigned char char_file1[1];
  unsigned char char_file2[1];
  int int_file1, int_file2;

  
  xor = fopen(nombre_archivo,"wb+");
  file1 = fopen(archivos[1],"rb");
  file2 = fopen(archivos[2],"rb");

  //Revisamos si el archivo abrio bien.
  if(file1 == NULL || file2 == NULL || xor == NULL){
    printf("Algun archivo no existe o existe un problema al abrirlo.\n");
    return 1;
  }
  do{
    int_file1 = fscanf(file1,"%c",char_file1);
    int_file2 = fscanf(file2,"%c",char_file2);
    char file_a = char_file1[0];
    char file_b = char_file2[0];
    char result = file_a ^ file_b;
    fprintf(xor,"%c",result);
  }while(int_file1 != -1 || int_file2 != -1);
  
  fclose(file1);
  fclose(file2);
  fclose(xor);
  return 0;
}


int main(int argc, char *argv[])
{
  //Si el parametro no es valido terminamos.
  if(argv[1] == NULL || argv[2] == NULL){
    printf("Favor de ejecutar el programa con \"%s <Ruta de dos archivos>\"\n",argv[0]);
    return 1;
  }
  //Revisamos que ambos archivos tengan el mismo tamanio
  //De lo contrario los emparejamos.
  if(empareja_archivos(argv)){
    printf("Existe un problema al querer encuadrar los archivos");
    printf(" en el procedimiento linea: %d.\n",__LINE__);
  }

  realiza_xor(argv);
  
  return 0;
}
