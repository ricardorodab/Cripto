/* -------------------------------------------------------------------
 * bytes.c
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
 * @file bytes.c
 * @author Jose Ricardo Rodriguez Abreu
 * @date 13 Feb 2017
 * @brief File containing the second exercise from the first practice for the
 * "Cryptography and security" class.
 *
 * Este programa recibe dos archivos para generar dos archivos mas que
 * sirven para practicar los metodos xor y producto de bytes.
 *
 * El programa usa el estandar de documentacion que define el uso de 
 * doxygen.
 *
 * @see https://github.com/ricardorodab/Cripto
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef max
#define max(a,b) ((a) > (b) ? (a) : (b))
#endif

/**
 * @brief Constante char.
 *
 * Usamos esta palabra para rellenar los espacios vacios del archivo
 * mas chico.
 */
#define PALABRA "beetlejuicebe"

/**
 * @brief Constante int.
 *
 * Este es el tamanio de la palabra que usaremos para rellenar el archivo
 * mas chico. Esta variable sirve por si deseamos cambiar la palabra.
 */
#define PALABRA_TAMANIO 11

/**
 * @brief Constante int-byte.
 *
 * Esta constante se usa para realizar la operacion de producto. 
 * Este es el binario equivalente a 10101010
 */
#define BYTE_CONSTANTE 0xAA

/**
 * @brief Empareja archivos de entrada.
 *
 * Funcion que escribe en el archivo mas chico si es necesario
 * para que tengan las mismas dimensiones.
 *
 * @param argv - Es la entrada de los archivos que deseamos comparar.
 * @return 0 si es que no tuvo ningun problema, 1 en otro caso.
 */
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
      //rewind(file1); //Esto es para regresar la marca de lectura.
      int_file2 = fscanf(file2,"%c",char_file2);
      if(int_file2 == -1){
	fprintf(file1,"%c",letra[i%(PALABRA_TAMANIO)]);
	break;
      }
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
      if(int_file1 == -1){
	fprintf(file2,"%c",letra[i%(PALABRA_TAMANIO)]);
	break;
      }
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

int get_grado(unsigned int *polinomio)
{
  int grado = 0;
  unsigned int poli = *polinomio;
  while(poli){
    poli = poli >> 1;
    grado++;
  }
  return grado-1;
}

/**
 * @brief XOR entre dos archivos.
 *
 * Funcion que escribe un archivo llamado xor.out que contiene
 * bytes resultantes de la funcion XOR dado dos archivos de entrada.
 *
 * @param archivos - Es la entrada de los archivos que deseamos hacer XOR.
 * @return 0 si es que no tuvo ningun problema, 1 en otro caso.
 */
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
    if(int_file1 == -1 && int_file2 == -1)
      break;
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

unsigned int multiplica(unsigned int f, unsigned int g)
{
  unsigned int respuesta = 0;
  unsigned int resultado = 0;
  unsigned int recorrimiento = 0X01;
  int i;
  for(i = 0; i < 8; i++){
    if(g & recorrimiento){
      resultado ^= f;
    }
    f = f << 0X01;
    recorrimiento = recorrimiento << 0X01;
  }
  return resultado;
}

unsigned int divide(unsigned int f, unsigned int m)
{
  unsigned int v = f;
  unsigned int k = 0;
  unsigned int z = 0;
  unsigned int temp = 0;
  unsigned int w = 0;
  while(get_grado(&v) >= 8)
    {
      k = max(8, get_grado(&v)-3);
      w = (((0X01 << k) - 0X01) & v);
      z = v >> k;
      temp = multiplica(z, (m ^ 256));
      temp = multiplica(temp, 1 << (k-8));
      v = w ^ temp;      
    }
  return v;
}

/**
 * @brief Realiza multiplicacion por un byte constante.
 *
 * Funcion que escribe un archivo llamado multiplicacion.out que contiene
 * bytes resultantes de la funcion multiplicacion definida como el producto
 * de dos polinomios en base 2 dado un byte.
 *
 * @param entrada - Es la entrada de el archivo a realizar el producto.
 * @return 0 si es que no tuvo ningun problema, 1 en otro caso.
 */
int realiza_multiplicacion(char *entrada)
{
  FILE *file;
  FILE *multi;
  char *nombre_archivo = "multiplicacion.out";
  unsigned char char_file[1];
  unsigned char salida_multi[1];
  int int_file;
  unsigned int int_resultado = 0;
  unsigned int cast_entrada;
  
  multi = fopen(nombre_archivo,"wb+");
  file = fopen(entrada,"rb");
  if(file == NULL || multi == NULL){
    printf("Algun archivo no existe o existe un problema al abrirlo.\n");
    return 1;
  }
  do{
    int_file = fscanf(file,"%c",char_file);
    if(int_file == -1)
      break;
    cast_entrada = (char_file[0] ^ 0x00);
    int_resultado = multiplica(cast_entrada, BYTE_CONSTANTE);  
    // 283 en binario es 100011011;
    // que es el polinomio = X^8+X^4+X^3+X+1
    unsigned int POLINOMIO = 283;
    printf("%d \n",int_resultado);
    int_resultado = divide(int_resultado,POLINOMIO);
    printf("%d \n",int_resultado);
    unsigned int cota = 255;
    //salida_multi = int_resultado & cota;
    salida_multi[0] = int_resultado;
    fprintf(multi,"%c",salida_multi[0]);
  }while(int_file != -1);
  return 0;
}

/**
 * @brief Funcion principal.
 *
 * Es la funcion principal del programa y que define su comportamiento.
 *
 * @param argc - Es el numero de argumentos que recibe el programa.
 * @param argv - Son los argumentos desde la linea de comandos que recibe.
 * @return 0 si es que no tuvo ningun problema, 1 en otro caso.
 */
int main(int argc, char *argv[])
{
  /*unsigned int pp1 = 20250;
  unsigned int pp2 = 255;
  printf("%d \n",divide(pp1,pp2));
  exit(0);*/
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
    return 1;
  }

  if(realiza_xor(argv)){
    printf("Existe un problema al querer realizar el xor");
    printf(" en el procedimiento linea: %d.\n",__LINE__);
    return 1;
  }
  if(realiza_multiplicacion(argv[1])){
    printf("Existe un problema al querer relizar la multiplicacion");
    printf(" en el procedimiento linea: %d.\n",__LINE__);
    return 1;
  }
  return 0;
} //Fin de bytes.c
