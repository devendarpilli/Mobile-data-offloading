/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static web.Web.DownloadHtml;
/**
 *
 * @author Dev
 */
public class server extends Thread
{
   private ServerSocket serverSocket;
   
   public server(int port) throws IOException
   {
      serverSocket = new ServerSocket(6550);
      //serverSocket.setSoTimeout(10000);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Connected to "
                  + server.getRemoteSocketAddress());
            DataInputStream in =
                  new DataInputStream(server.getInputStream());
            String SiteToDownload=in.readUTF();
           Document doc = Jsoup.connect(SiteToDownload).get();
            String title = doc.title();
            String route="C:\\Users\\Dev\\Desktop\\Downloaded\\"+title;
            new File(route).mkdir();
            Elements links = doc.select("a[href]");
List<String> linkarray=new ArrayList<String>();
            for(Element e: links)
            {
              
                String linkurl=e.attr("abs:href");
                  linkarray.add(linkurl);
                  
                System.out.println(linkurl);
                if(linkurl.endsWith("html"))
                DownloadHtml(route,linkurl);
            }
            System.out.println(linkarray);
          
            
            for(int j=0;j<linkarray.size();j++)
            {
          Document cssdoc = Jsoup.connect(linkarray.get(j)).get();      
          Elements css = cssdoc.select("link[href]");
            for(Element c: css)
            {
                
                String cssurl=c.attr("abs:href");
                System.out.println(cssurl);
                if(cssurl.endsWith("css"))
                DownloadHtml(route,cssurl);
            }
             Elements jpgs = doc.select("src");
             for(Element i: jpgs)
            {
                
                String jpgurl=i.attr("abs:src");
                System.out.println(jpgurl);
                if(jpgurl.endsWith("jpg"))
                DownloadHtml(route,jpgurl);
            }
            Elements js = doc.select("script"); 
            for(Element s: js)
            {
              
                String jsurl=s.attr("abs:src");
                if(jsurl.endsWith("js"))
                DownloadHtml(route,jsurl);
            }
            
            }
             System.out.println("Finished Downloading...");
       
 
            DataOutputStream out =
                 new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to "
              + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
         
      }
      
        }
       
public static void DownloadHtml(String route,String url) throws IOException
          {
            BufferedInputStream in = null;
    FileOutputStream fout = null;
    try {
        String names[]=url.split("/");
        String page=names[names.length-1];
        in = new BufferedInputStream(new URL(url).openStream());
        fout = new FileOutputStream(route+"/"+page);

        final byte data[] = new byte[2048];
        int count;
        while ((count = in.read(data, 0, 2048)) != -1) {
            fout.write(data, 0, count);
        }
    } finally {
        if (in != null) {
            in.close();
        }
        if (fout != null) {
            fout.close();
        }
    }
}   
   public static void main(String [] args)
   {
      int port = 6550;
      try
      {
         Thread t = new server(port);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
