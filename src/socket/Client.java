/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author Cosan
 */
public class Client {
    /*
        dosyaOku fonksiyonu kullanıcıdan okunmak istenen dosyanın url 
        bilgisini alır. 10000 portu üzerinden server a mesaj gönderir.
        Gelen cevaba göre 9000 portundan url i gönderir. Serverdan gelen
        bilgileri ekrana bastırır.
    */
    public static void dosyaOku() throws IOException, ClassNotFoundException{
        Scanner s = new Scanner(System.in);
        String get = "200 OK";
        String readed;
        System.out.println("Okunacak dosyanın URL bilgisini girin");
        String adres = s.nextLine();
        
        
        /////////////////////////server ile bağlantı//////////////////////
        Socket socket = new Socket("localhost",10000);
        OutputStream oss = socket.getOutputStream();
        InputStream iss = socket.getInputStream();
        ObjectOutputStream outObjectt=new ObjectOutputStream(oss);
        ObjectInputStream inObjectt = new ObjectInputStream(iss);
        ///////////////////////////////////////////////////////////////////
        
        outObjectt.writeObject("GET");
        readed = (String) inObjectt.readObject();
        if(readed.equals(get)){
            Socket sock = new Socket("localhost",9000);//9000 portuna gönderme işlemi
            OutputStream os = sock.getOutputStream();
            InputStream is = sock.getInputStream();
            ObjectOutputStream outObject=new ObjectOutputStream(os);
            ObjectInputStream inObject = new ObjectInputStream(is);
            outObject.writeObject(adres);
            readed = (String) inObjectt.readObject();//gelen mesaj
            if(readed.equals("HTTP/1.1 404 Not Found\r\n")){
                //mesaj eğer bulunamadığına yönelikse
                System.out.println(readed);
            }else{
                //mesaj bulunduğuna yönelikse
                System.out.println(readed);//gelen mesajı bastır
                String icerik =(String) inObject.readObject();
                System.out.println(icerik);//gelen iceriği bastır
                outObject.flush();
                os.flush();
                os.close();
                sock.close();
                is.close();
                inObject.close();
            }
            
        }else{
            //serverdan gelen cevap mesajın anlaşılmadığına yönelikse
            System.out.println(readed);
        }
        
        iss.close();
        oss.close();
        socket.close();
    }
    /*
        dosyaSil fonksiyonu kullanıcıdan silinmek istenen dosyanın 
        url bilgisini alır. 10000 portu üzerinden server a mesaj
        gönderir. Gelen cevaba göre 9000 portundan url i gönderir.
        Serverdan gelen bilgileri ekrana bastırır.
    */
    public static void dosyaSil() throws IOException, ClassNotFoundException{
        Scanner s = new Scanner(System.in);
        String delete = "200 OK";
        String readed;
        System.out.println("Silinecek dosyanın URL bilgisini girin");
        String adres = s.nextLine();
        
        
        ///////////////////////server ile bağlantı///////////////////////
        Socket socket = new Socket("localhost",10000);
        OutputStream oss = socket.getOutputStream();
        InputStream iss = socket.getInputStream();
        ObjectOutputStream outObjectt=new ObjectOutputStream(oss);
        ObjectInputStream inObjectt = new ObjectInputStream(iss);
        /////////////////////////////////////////////////////////////////
        
        outObjectt.writeObject("DELETE");
        readed = (String) inObjectt.readObject();
        if(readed.equals(delete)){
            Socket sock = new Socket("localhost",9000);//9000 portuna gönderme işlemi
            OutputStream os = sock.getOutputStream();
            ObjectOutputStream outObject=new ObjectOutputStream(os);
            outObject.writeObject(adres);
            readed = (String) inObjectt.readObject();//gelen mesaj
            if(readed.equals("HTTP/1.1 404 Not Found\r\n")){
                //mesaj eğer bulunamadığına yönelikse
                System.out.println(readed);
            }else{
                //mesaj bulunduğuna yönelikse
                System.out.println(readed);
                outObject.flush();
                os.flush();
                os.close();
                sock.close();
            }
            
        }else{
            //serverdan gelen cevap mesajın anlaşılmadığına yönelikse
            System.out.println(readed);
        }
        
        iss.close();
        oss.close();
        socket.close();
    }
    /*
        dosyaGonder fonksiyonu kullanıcıdan gönderilmek istenen dosyanın
        url bilgisini alır. 10000 portu üzerinden server a mesaj
        gönderir. Gelen cevaba göre 9000 portundan url i gönderir.
        Serverdan gelen bilgileri ekrana bastırır.
    */
    public static void dosyaGonder() throws IOException, ClassNotFoundException{
        
        Scanner s = new Scanner(System.in);
        String put = "201 Generated";
        String readed;
        System.out.println("Gönderilecek dosyanın URL bilgisini girin");
        String adres = s.nextLine();
        
        if(new File(adres).exists()){
        
        ///////////////////////server ile bağlantı/////////////////////
        Socket socket = new Socket("localhost",10000);
        OutputStream oss = socket.getOutputStream();
        InputStream iss = socket.getInputStream();
        ObjectOutputStream outObjectt=new ObjectOutputStream(oss);
        ObjectInputStream inObjectt = new ObjectInputStream(iss);
        ///////////////////////////////////////////////////////////////
        
        
        
        
        
        outObjectt.writeObject("PUT");
        readed =(String) inObjectt.readObject();
        if(readed.equals(put)){
            Socket sock = new Socket("localhost",9000);// Kendi hostunda  9000 portuna gönderme işlemi
            OutputStream os = sock.getOutputStream();// Soketle gönderilme işleminin başladığı kısım
            ObjectOutputStream outObject=new ObjectOutputStream(os);
            System.out.println("Gönderliyor...");
            File dosya =new File(adres);// İlgili dosya okunacak
            
            FileInputStream dosyabyte = new FileInputStream(dosya);// Gönderilecek dosyanın byte çevirme işlemi
            Obje zrf = new Obje();//Gönderlecek neseneni oluştuğu kısım.
            zrf.dosya = new byte[(int) dosya.length()]; // Byte arrayın uzunluğu
            zrf.dosyaAdi=dosya.getName();// Dosyanın Adı dolduruluyor
            zrf.dosyaBoyutu=dosya.length();//Dosyanın Boyutu Dolduruluyor
            outObjectt.writeObject(""+zrf.dosyaBoyutu);
            readed =(String) inObjectt.readObject();
            System.out.println(readed);
            System.out.println ("Dosya Adı : "+zrf.dosyaAdi);
            System.out.println("Dosya Boyutu  : "+zrf.dosyaBoyutu/1024+" Kilobyte");
            dosyabyte.read(zrf.dosya);// Byte Array Doduruluyor.
            outObject.writeObject(zrf);// Nesne gönderilme işlemi yapıldı.
            System.out.println("Gönderme Başarılı...");
            System.out.println("==========================================\n\n");
            // İşlem sonu Stream ve Soket kapatılma işlemi.
            outObject.flush();
            os.flush();
            os.close();
            sock.close();
            
        }else{
            
            System.out.println(readed);
            
        }
        iss.close();
        oss.close();
        socket.close();
        }else{
            System.out.println("Gönderilmek istenilen dosya bulunamadı.");
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int secim;
        Scanner s = new Scanner(System.in);
        do{
            System.out.println("HTTP AĞ İSTEMCİSİ ARAYÜZÜ");
            System.out.println("1. Dosya Oku");
            System.out.println("2. Dosya Sil");
            System.out.println("3. Dosya Gönder");
            System.out.print("Tercih>>");
            
            secim = s.nextInt();
            switch(secim){
                case 1:
                    dosyaOku();
                    break;
                case 2:
                    dosyaSil();
                    break;
                case 3:
                    dosyaGonder();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Hatalı seçim..");
                    break;
        }
        }while(secim != 4);
    }
    
}
