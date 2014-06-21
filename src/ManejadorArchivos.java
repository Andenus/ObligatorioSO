import java.io.*;
import java.util.ArrayList;

/**
 * Created by user on 20/06/2014.
 */
public class ManejadorArchivos {


    public void escribirArchivo(ArrayList<ArrayList<String>> listaLineasArchivo){
        FileWriter fw;
        try{
            fw=new FileWriter(new File(System.getProperty("user.dir")+"\\Salida\\SalidaPrograma.txt"), true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(ArrayList<String> cliente:listaLineasArchivo){
                for(String lineaActual:cliente){
                    if(lineaActual!=null) {
                        bw.write(lineaActual);
                        bw.newLine();
                    }
                }
            }
            bw.close();
            fw.close();
        }catch(IOException e){
            System.out.println("Error al escribir el archivo" + System.getProperty("user.dir")+"\\Salida\\SalidaPrograma.txt");
            e.printStackTrace();
        }
    }
}
