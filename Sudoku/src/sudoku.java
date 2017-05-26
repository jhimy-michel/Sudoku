import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class sudoku {
 static int N = 9;
 static int tabla[][]=new int[9][9];
 static int convertir_char_to_int(char caracter)
 {
  int auxiliar =0;
  auxiliar = caracter-48;
  return auxiliar;
 } 
 //-----------llenar la matriz con un archivo externo
 //-----------convertir los valore de char -> int
public static void llenar(){
	char matriz[][]= new char[9][9];
	 try{
		 File archivo = new File ("C:/Users/Jhimy-PC/Desktop/inputfile.txt");
		 FileReader fr= new FileReader (archivo);
		 BufferedReader br = new BufferedReader(fr);
		 String linea=br.readLine();
		 String delimiter = "" ;
		 int contador=0; 
		 
		 while((linea)!=null ) {
			 String[] a=linea.split(delimiter);
			 for(int i=0;i<a.length;i++){
				 matriz [ contador ] [ i]=a [ i].charAt(0);
			 } linea=br.readLine();contador++;
		 }
	 }catch(Exception e){}
	 for(int i=0;i<9;i++){
		 for(int j=0;j<9;j++){
			 if(matriz[i][j]=='%'){matriz[i][j]='0';}
			 tabla[i][j]=convertir_char_to_int(matriz[i][j]);
		 }
	 }
}
//-----clase celdas
static class Celdas {
	  int row, col;
	  public Celdas(int row, int col) {
	   super();
	   this.row = row;
	   this.col = col;
	  }
	  public String toString() {
	   return "Cell [row=" + row + ", col=" + col + "]";
	  }
	 };
	 //----validar las celdas
	 static boolean esValido(Celdas cel, int valor) {

	  if (tabla[cel.row][cel.col] != 0) {
	   throw new RuntimeException(
	     "ya se reviso");
	  }
	  // comprobar si se repiten en las columnas
	  for (int r = 0; r < 9; r++) {
	   if (tabla[r][cel.col] == valor)
	    return false;
	  }

	  // comprobar si se repiten en las filas
	  for (int c = 0; c < 9; c++) {
	   if (tabla[cel.row][c] == valor)
	    return false;
	  }
	  //---------
	  int x1 = 3 * (cel.row / 3);
	  int y1 = 3 * (cel.col / 3);
	  int x2 = x1 + 2;
	  int y2 = y1 + 2;

	  for (int x = x1; x <= x2; x++){
	   for (int y = y1; y <= y2; y++){
	    if (tabla[x][y] == valor)
	     return false;}
	  }
	  //----si el valor no esta en la fila y columna retrun=true
	  return true;
	 }

	 static Celdas siguiente(Celdas casilla) {

	  int fil = casilla.row;
	  int colum = casilla.col;

	  // siguiente celda
	  colum++;
	  if (colum > 8) {
	   // siguiente
	   colum = 0;
	   fil++;
	  }

	  if (fil > 8)
	   return null; // fin

	  Celdas next = new Celdas(fil,colum);
	  return next;
	 }
	 //-----------------------
	 static boolean validar(Celdas cur) {
	  if (cur == null)
	   return true;
	  if (tabla[cur.row][cur.col] != 0) {
	   return validar(siguiente(cur));
	  }
	  for (int i = 1; i <= 9; i++) {
	   boolean valid = esValido(cur, i);
	   if (!valid) 
	    continue;


	   tabla[cur.row][cur.col] = i;


	   boolean solved = validar(siguiente(cur));
	   if (solved)
	    return true;
	   else
	    tabla[cur.row][cur.col] = 0; 
	  }
	  return false;
	 }

	 
	 /*** clase principal***/
 public static void main(String[] args) {
	 llenar();
  boolean solved = validar(new Celdas(0, 0));
  if (!solved) {
   System.out.println("Error");
   return;
  }
  imprimirTabla(tabla);
 }

 //---imprimir la tabla
 static void imprimirTabla(int tabla[][]) {
	 int contador=0;
	  System.out.println("+---+---+---+");
	  for (int i = 0; i < N; i++) {
		   for (int j = 0; j < N; j++){
				if(contador%3==0 ){System.out.print("|");}
			    System.out.print(tabla[i][j]);
			    contador++;
		  }System.out.print("|");System.out.println();
	  }	
  System.out.println("+---+---+---+");
 }
 
}



