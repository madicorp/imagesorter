/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package imagesorters;
/**
 *
 * @author sega
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

public class FileHandler {
	
	  private final static String[] okFileExtensions = 
			    new String[] {"jpg", "png", "gif"};
          int i= 0;
          private static final int interval = 1000;
          private Timer timer;
	  private final  ArrayList<String []> repertoire =
			  new ArrayList<String []>() {{
				    add(new String[] {"Carré", "1.00"});
				    add(new String[] {"Standard photo reflex", "1.49"});
				    add(new String[] {"Standard téléphone", "1.33"});
				    add(new String[] {"HD", "1.76"});
				    add(new String[] {"Pano photo reflex", "2.49"});
				    add(new String[] {"Pano iPHONE 6", "2.35"});
				    add(new String[] {"Pano 1", "2.94"});
				    add(new String[] {"Pano 2 ", "5.88"});
				    add(new String[] {"Autre", "0"});
				}}; 
			 
	
	public void fileCopier( String src ,  final String dst) throws IOException, InterruptedException {
		
		
            
                File source = new File(src);
                
		File destination = new File(dst);
                        
		if(!source.exists() || !destination.exists())
		{
                    if(!source.exists())
                    {
                        source.mkdirs();
                        System.out.println(source.getName() + " Créé");
                    }
                    if(!destination.exists())
                    {
                        destination.mkdirs();
                         System.out.println(destination.getName() + " Créé");
                    }
                      
			return;
		}
		
               
		Collection<File> f = FileUtils.listFiles(
				  source, 
				  new RegexFileFilter("^(.*?)"), 
				  DirectoryFileFilter.DIRECTORY
				);
		final File [] files = f.toArray(new File[f.size()]);
                
                
		          Window.bar.setMaximum(files.length);
               
                timer = new Timer(interval, new ActionListener() {
                    
                            public void actionPerformed(ActionEvent ae) {
                                
                                if(i == files.length)
                                {
                                    timer.stop();
                                    Window.btnSend.setEnabled(true);
                                    System.out.println("Terminé!");
                                }
                                else
                                {
                                
                                File child = files[i];
                                if(accept(new File( dst + "/" + child))){
				System.out.println("Traitement de fichier "+child.getName());
				BufferedImage bi=null;
                            try {
                                bi = ImageIO.read(child.getAbsoluteFile());
                                } catch (IOException ex) {
                                    Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
				float w = bi.getWidth();
				float h = bi.getHeight();
                                float r;
                                if(h < w)
                                {
				 r = w / h;
                                }
                                else
                                {
                                  r = h / w;
                                }
                                
				System.out.println("Largeur : "+w + " Hauteur : " + h + " Ratio : " + r);
                                
				String pth = getCorrespFolder(r);
                                
				File realDest = new File( dst + "/" + pth);
                                
				if(! realDest.exists()){
                                    
                                    realDest.mkdirs();
                                    
                                    System.out.println(realDest.getName() + " Créé");
                                    
                                }
				if(! new File( realDest + "/" + child.getName()).exists() ){
                                    try {
                                        
                                        FileUtils.copyFile(child,new File( realDest + "/" + child.getName()));
                                        
                                    } catch (IOException ex) {
                                        
                                        Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                                        
                                    }
                                        System.out.println(child.getName() +" Copiée Dans "+pth+"\n");
				}
                                else
                                {
                                     System.out.println(child.getName() +" Existe Déja  \n");
                                }
                                
                                
                                System.out.println("---------------------------------------------------------\n\n");
				
			}
                        
                   
			i++;
                       Window.bar.setValue(i);
                            }
                                
                            }
                        });
	
               	timer.start();
                        
		
	}
	
        
	
	
	public String getCorrespFolder(float ratio){
		
		String flder = "";
		float diff = 1000;
		
		for(String [] folder : repertoire)
		{
			float d = Math.abs(ratio - Float.parseFloat(folder[1]));
			if(d <= diff)
			{
				flder = folder[0];
				diff = d;
			}
			
		}
		
		return flder;
	}
	
	

	
	
	  public boolean accept(File file)
	  {
	    for (String extension : okFileExtensions)
	    {
	      if (file.getName().toLowerCase().endsWith(extension))
	      {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	  
	
	
}
