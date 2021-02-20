/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.io.*;
/**
 *
 * @author Cosan
 */
/*
    Obje class ı dosya gönderilirken bilgilerinin obje olarak
    gönderebilmek için kullanılmıştır.
*/
public class Obje implements Serializable{
    public String dosyaAdi;
    public long dosyaBoyutu;
    public byte[] dosya;
}
