/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.io.*;
import java.net.*;
import java.util.Date;
/**
 *
 * @author Cosan
 */
public class Server {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {

       ServerSocket servsock = new ServerSocket(9000);
       ServerSocket servsocket = new ServerSocket(10000);
       String put = "PUT";
       String get = "GET";
       String delete = "DELETE";
       String message;
       while (true) {
       
       System.out.println("Port dinleme işlemi başladı.... ");
       Socket socket = servsocket.accept();//10000
       
       InputStream istrm = socket.getInputStream();
       OutputStream ostrm = socket.getOutputStream();
       ObjectInputStream inObject = new ObjectInputStream(istrm);
       ObjectOutputStream outObject=new ObjectOutputStream(ostrm);
       message =(String) inObject.readObject();
       /*
       
            10000 portundan gelen mesaj put mesajı ise bu if bloğu çalışır.
            Bu blok 10000 portundan bir cevap gönderir. Client cevabı aldıktan
            sonra 9000 portundan obje class ı tipinde dosyayı gönderir. Bu 
            blok gönderilen dosyayı alır ve belirlenen adrese gönderir.
       
       */
       if(message.equals(put)){
            
            outObject.writeObject("201 Generated");
            Socket sock = servsock.accept();// 9000 portundan gelen veriyi kabul eden kısım.
            System.out.println(" Bağlantı Açıldı ... : ");
            InputStream is = sock.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is); 
            String length = (String) inObject.readObject();
            outObject.writeObject("HTTP/1.1 201 Generated\r\n"
                    + "Date: " + new Date() + "\r\n" +
                    "Content-Length: " + length + "\r\n" +
                    "Content-Type : text/html; charset=ISO-8859-1\r\n" + "\r\n");
            Obje  Azarf = (Obje)ois.readObject(); // Zarf nesnesinin okunduğu kısım
            File gelendosya = new File("c:\\indirilenler\\"+Azarf.dosyaAdi);
            gelendosya.getParentFile().mkdirs();// Dosya ve Klasörün oluşturuldu kısım.
            FileOutputStream fos = new FileOutputStream(gelendosya, false);
            fos.write(Azarf.dosya);// Byte Array doldurulduğu kısım.
            fos.flush();
            fos.close();
            
       //////////////////////////////////////////////////////////////////////////////////////////////////     
        /*
            
            10000 portundan gelen mesaj get mesajı ise bu blok çalışır.
            Bu blok 10000 portundan bir cevap gönderir.9000 portundan
            Client bu cevaba göre okunmak istenen dosyanın url bilgisini
            gönderir. Gelen url bilgisine göre bu blok dosyayı arar eğer
            bulamazsa bulamadığına dair http formatında mesaj döner. 
            Bulursa dosyayı okur bir stringe atar o stringi client e 
            gönderir.
       
       */    
       }else if(message.equals(get)){
           
           outObject.writeObject("200 OK");
           Socket sock = servsock.accept();// 9000 portundan gelen veriyi kabul eden kısım.
           System.out.println(" Bağlantı Açıldı ... : ");
           
           InputStream is = sock.getInputStream();
           OutputStream os = sock.getOutputStream();
           ObjectOutputStream oss = new ObjectOutputStream(os);
           ObjectInputStream ois = new ObjectInputStream(is);
           String adres = (String) ois.readObject();
           //Obje zarf;
           String icerik = null;
           String satir;
           if(!new File(adres).exists()){
               outObject.writeObject("HTTP/1.1 404 Not Found\r\n");
               
           }
           else{
                outObject.writeObject("HTTP/1.1 200 OK\r\n"
                    + "Date: " + new Date() + "\r\n" +
                    "Content-Length: " /*+ length*/ + "\r\n" +
                    "Content-Type : text/html; charset=ISO-8859-1\r\n" + "\r\n");
                BufferedReader oku = new BufferedReader(new FileReader(adres));
           
                while(true){
                    satir = oku.readLine();
               
                    //System.out.println(satir);
                    if(satir != null){
                        if(icerik != null){
                             icerik =icerik + "\n"+satir;
                        }else{
                            icerik = "\n" + satir;
                         }
                    }else{
                        oss.writeObject(icerik);
                        oku.close();
                        break;
                    }
                }
           }
       
       //////////////////////////////////////////////////////////////////////////////////////////////////    
       /*
       
            10000 portundan gelen mesaj delete mesajı ise bu blok çalışır.
            Bu blok 10000 portundan bir cevap gönderir. 9000 portundan
            client bu cevaba göre silinmek istenen dosyanın url bilgisini
            gönderir. Gelen url bilgisine göre bu blok dosyayı arar eğer
            bulamazsa bulamadığına dair http formatında mesaj döner. 
            Bulursa dosyayı siler.
       
       */
       }else if(message.equals(delete)){
           outObject.writeObject("200 OK");
           Socket sock = servsock.accept();// 9000 portundan gelen veriyi kabul eden kısım.
           System.out.println(" Bağlantı Açıldı ... : ");
           
           InputStream is = sock.getInputStream();
           ObjectInputStream ois = new ObjectInputStream(is);
           String adres = (String) ois.readObject();
           if(!new File(adres).exists()){
               outObject.writeObject("HTTP/1.1 404 Not Found\r\n");
               
           }
           else{
                outObject.writeObject("HTTP/1.1 200 OK\r\n"
                    + "Date: " + new Date() + "\r\n" +
                    "Content-Length: " /*+ length*/ + "\r\n" +
                    "Content-Type : text/html; charset=ISO-8859-1\r\n" + "\r\n");
                File dosya = new File(adres);
                dosya.delete();
           }
       }
       else{
           outObject.writeObject("HTTP/1.1 400 Bad Request\r\n");
       } 
       
        }
       //sock.close();
    }
}
