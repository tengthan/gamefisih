package gremlins;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import processing.core.PImage;

public class MapDisplay{

    public String mapPrint[][];

    public Gremlins[] gremlinsArray;
    public int countGremilins= 0;

    char xCapital ='X';
    char bCapital ='B';
    char gCapital= 'G';

    public MapDisplay(App app){
        mapPrint= new String[33][36];
        loadMap();
        germlinsCreate(app);
        }

    public void loadMap(){
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("level1.txt"));
            int col = 0;
			String line = reader.readLine();
			while (line != null) {
                for (int row = 0; row<line.length(); row++){
                    if(line.charAt(row)== xCapital){ 
                      mapPrint[col][row] = "X";
                    }
                    else if (line.charAt(row) == bCapital){
                        mapPrint[col][row] = "B";
                    }
                    else if (line.charAt(row) == gCapital){
                        mapPrint[col][row] = "G";
                    }
                    else{
                        mapPrint[col][row] = "S";
                    }
                }
                col+=1;
				line = reader.readLine();
			}
			reader.close();
            
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void draw (App app,PImage brickwall,PImage stonewall){
        for(int i=0; i<mapPrint.length; i++) {
            for(int j=0; j<mapPrint[i].length; j++) {
                if(mapPrint[i][j]== "X"){ 
                    app.image(stonewall, j*20 , i*20);
                }
                else if (mapPrint[i][j]== "B"){
                    app.image(brickwall,  j*20 , i*20);
                }
            }
        }
    }
    
    public void germlinsCreate(App app){
        for(int i=0; i<mapPrint.length; i++) {
            for(int j=0; j<mapPrint[i].length; j++) {
                if(mapPrint[i][j]== "G"){ 
                    countGremilins++;
                }
            }
        }
        gremlinsArray= new Gremlins[countGremilins];
        int x=0;
        for(int i=0; i<mapPrint.length; i++) {
            for(int j=0; j<mapPrint[i].length; j++) {
                if(mapPrint[i][j]== "G"){ 
                    gremlinsArray[x]= new Gremlins(i*20, j*20, mapPrint,app);
                    x++;
                }
            }   
        }
    }

}       