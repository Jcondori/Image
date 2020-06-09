package com.image.process;

import net.coobird.thumbnailator.Thumbnails;
import org.primefaces.model.UploadedFile;

import java.io.*;

public class UploadFileLogica {

    //Solo para manejar Imagenes
    public String saveImage(UploadedFile file, String carpeta) throws Exception {
        try {

            String type = file.getContentType().split("/")[1]; //Extraemos la Extencion generica de la imagen
            type = "png".equals(type) ? "jpeg" : type;//Si es png lo convertimos a jpeg

            if (type.equals("jpeg")) {

                String nombre = file.getFileName().replace(".", "/").split("/")[0]; //Estraemos en nombre del archivo

                String NuevoNombre = nombre + "." + type; //Generamos un nombre generico

                //Metodo para guardar la imagen
                Thumbnails.of(file.getInputstream()).forceSize(800, 800).toFile(new File("C:/Users/JCondori/Pictures/" + carpeta + "/" + NuevoNombre));
                return NuevoNombre;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw e;
        }
    }

    //Solo para manejar documentos
    public String saveDocument(UploadedFile file) throws Exception {
        String documentos[] = {"doc", "docx", "pdf", "xlsx", "xls", "pptx", "ppt"};
        try {
            String[] temp = file.getFileName().replace(".", "/").split("/");
            String document = temp[temp.length - 1];

            if (buscar(documentos, document)) {
                //Metodo para guardar la imagen
//                saveFile(file, "C:/Users/JCondori/AppData/Roaming/NetBeans/8.2/config/GF_4.1.1/domain1/docroot/doc/" + file.getFileName());
                File destino = new File("C:/Users/JCondori/Pictures/@/" + file.getFileName());
                destino.delete();
                InputStream in = file.getInputstream();
                OutputStream out = new FileOutputStream(destino);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return file.getFileName();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw e;
        }
    }

    //Validar si el domumento es de formato correcto
    public boolean buscar(String[] lista, String ext) {
        for (String string : lista) {
            if (string.equals(ext.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
