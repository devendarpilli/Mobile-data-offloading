/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.net.*;
/**
 *
 * @author dev
 */
public class Web {


  public static void main(String[] args) throws IOException {
		 
                 Document doc = null;
                 Scanner user_input = new Scanner(System.in);
                 String sitename;
                 System.out.println("Enter the URL....\n");
                 sitename = user_input.next();
                 System.out.println("Connecting to.." +sitename); 
                 System.out.println("Downloading Contents from..." +sitename);        
                 
        try {
            doc = Jsoup.connect(sitename).timeout(0).get();
            String title = doc.title();
            System.out.println(title);
            String route="C:\\Users\\Dev\\Desktop\\"+title;
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
          Document cssdoc = Jsoup.connect(linkarray.get(j)).timeout(0).get();      
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
        }
        catch (IOException ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Finished Downloading...");
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
              
}