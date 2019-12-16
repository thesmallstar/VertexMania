
/*

     Manthan Surkar
    IIT2018040


*/

import java.awt.event.*; 
import javax.swing.*; 
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;

class Vertex{
      
      String name;
      double x;
      double y;
      Vertex(String name,double x,double y){
          this.name= name;
          this.x=x;
          this.y=y;
      }
}

class Edge{
    String from;
    String to;
    double w;
    Edge(String from,String to,double w){
        this.from = from;
        this.to=to;
        this.w=w;
        
    }
}

class aException extends Exception{
    String msg;
    aException(String msg){
        this.msg = msg;
    }
    
    protected void showMsg(){
          JOptionPane.showMessageDialog(ApplicationManager.f,
   msg,
    "Opps! Something went Wrong",
    JOptionPane.ERROR_MESSAGE);
    }
    
}

class GraphHandler{
    ArrayList<Vertex> myVertex;
    ArrayList<ArrayList<Edge>> myEdges;
    int nEdges;
    String currentPathRequest;
   
    GraphHandler(){
        myVertex  = new ArrayList<Vertex>();
        myEdges = new ArrayList<ArrayList<Edge>>();
    } 
    
      protected void addVertex(String name,String x,String y){
        try{
            if(findVertex(name)!=-1) 
                throw new aException("Vertex with same name already exists");
     
     System.out.println(findVertex(name));
        Vertex newVertex = new Vertex(name,Double.parseDouble(x),Double.parseDouble(y));
        myVertex.add(newVertex);
        System.out.println("Added Vertex");
        myEdges.add(new ArrayList<Edge>());
        ApplicationManager.k.repaint();
        }
        catch(aException s){
            s.showMsg();
        }
        catch(java.lang.NumberFormatException s){
            new aException("Please enter real values of X and Y cordinates").showMsg();
        }
        
        
    }
    
      protected int findVertex(String name){
       
         for(int i = 0 ; i <myVertex.size();i++)
               if(name.equals(myVertex.get(i).name))
                    return i;
            
       
        return -1;
        
        
    }

      protected void showVertexDetails(int index) {
       JOptionPane.showMessageDialog(ApplicationManager.f,
    "Name: "+myVertex.get(index).name+ " X:" + myVertex.get(index).x +" Y:"+myVertex.get(index).y,
    "Vertex Found",
    JOptionPane.INFORMATION_MESSAGE);
    }
 protected void showEdgeDetails(String from,String to) {
     int index1 = findVertex(from);
      //  int index2 = findVertex(to);
      
      int eindex1 = SearchEdge(from,to);
//        int eindex2  = SearchEdge(to,from);
        try{
       JOptionPane.showMessageDialog(ApplicationManager.f,
    "from: "+myEdges.get(index1).get(eindex1).from + " To: " + myEdges.get(index1).get(eindex1).to +" Weight: "+myEdges.get(index1).get(eindex1).w,
    "Vertex Found",
    JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception s){
            new aException("Cannot Find any Edge").showMsg();
            
        }
    }
      protected  void deleteVertex(int index) {
       String name = myVertex.get(index).name;
        nEdges = nEdges - myEdges.get(index).size();
       myVertex.remove(index);
       myEdges.remove(index);
      
       for(int i=0;i<myEdges.size();i++){
              for(int j=0;j<myEdges.get(i).size();j++){
                  if(myEdges.get(i).get(j).to.equals(name)){
                      myEdges.get(i).remove(j);
                      nEdges--;
                    break;
                  }
                
              }
       }
       
        ApplicationManager.k.repaint();
       
       

      }
    
      protected void modifyVertex(int index,String name, String x,String y){
          
           try{
               double a=0,b=0;
                if(!(x.equals("")))
             a =  Double.parseDouble(x);
                if(!(y.equals("")))
             b = Double.parseDouble(y);
          if(!(name.equals("")))
         myVertex.get(index).name = name;
          if(!(x.equals("")))
         myVertex.get(index).x=a;
          if(!(y.equals("")))
         myVertex.get(index).y=b;
          
          ApplicationManager.k.repaint();
           }
           catch(java.lang.NumberFormatException s){
             new aException("Enter real values from x and y cordinates").showMsg();
           }
         

    }
      protected void addEdge(String from,String to,String w){
        
       // Edge newEdge2 = new Edge(to,from,Double.parseDouble(w));
        int index1 = findVertex(from);
        int index2= findVertex(to);
        try{
            Edge newEdge = new Edge(from,to,Double.parseDouble(w));
            if(index1==-1 || index2==-1){
                throw new aException("Please check name of the vertex you  entered");
            }
            if(SearchEdge(from,to)!=-1){
                throw new aException("Edge Already Exists");
            }
            
         myEdges.get(index1).add(newEdge);
         nEdges = nEdges+1;
                ApplicationManager.k.repaint();
        }
        catch(aException s)
        {
            s.showMsg();
        }
        catch(java.lang.NumberFormatException s){
            new aException("Enter a real weight").showMsg();
        }
        
         
        // myEdges.get(index2).add(newEdge2);
    }
      protected int SearchEdge(String from,String to){
       
        int index1 = findVertex(from);
          ArrayList<Edge> findhere;
        try{
     findhere  = myEdges.get(index1);
        }
        catch(Exception s){
            new aException("Invalid Input").showMsg();
            return -1;
        }
        
        for(int i = 0 ; i< findhere.size();i++){
             if(findhere.get(i).to.equals(to)){
                 return i;
             }
        }
         return -1;
         
    }
 
      protected void deleteEdge(String from,String to){
        int index1 = findVertex(from);
      //  int index2 = findVertex(to);
        int eindex1 = SearchEdge(from,to);
       // int eindex2  = SearchEdge(to,from);
       try{
       if(index1==-1 || eindex1==-1) 
           throw new aException("Cannot Delete edge, No edge Exists");
       
        myEdges.get(index1).remove(eindex1);
       // myEdges.get(index2).remove(eindex2); 
       nEdges--;
        ApplicationManager.k.repaint();
       }
       catch(aException s){
           s.showMsg();
       }
       return;
      }
      
      protected void modifyEdge(String from,String to,String w){
          
        int index1 = findVertex(from);
        int index2 = findVertex(to);
       // System.out.println();
        int eindex1 = SearchEdge(from,to);
        int eindex2  = SearchEdge(to,from);
        
         try{
       if(index1==-1 || eindex1==-1)
           throw new aException("Cannot Modify edge, No edge Exists");
        //System.out.println(index1 + " " + index2);
        myEdges.get(index1).get(eindex1).w=Double.parseDouble(w);
       // myEdges.get(index2).get(eindex2).w=Double.parseDouble(w);
        ApplicationManager.k.repaint();
        return;
         }
         catch(aException s){
             s.showMsg();
         }
         catch(java.lang.NumberFormatException s){
             new aException("Enter Real Value of weight").showMsg();
         }
      }
      protected int findVertex(int x, int y){
          
           for(int i = 0 ; i < myVertex.size();i++)
               if(Math.abs(myVertex.get(i).x-x)<=8 && Math.abs(myVertex.get(i).y-y)<=8)
                    return i;
                     
         return -1;
          
      }
      
   
      protected String findIfClickOnEdge(int x,int y){
          
          
          for(int i=0;i<myEdges.size();i++){
              for(int j=0;j<myEdges.get(i).size();j++){
                  int index1 = findVertex(myEdges.get(i).get(j).from);
                  int index2 = findVertex(myEdges.get(i).get(j).to);
                   int x1= (int) myVertex.get(index1).x;
                   int y1= (int) myVertex.get(index1).y;
                   int x2= (int) myVertex.get(index2).x;
                   int y2= (int) myVertex.get(index2).y;
                  System.out.println(x+ " " + y + " "+x1+ " "+ y1 + " "+ x2 + " " + y2);
                   if( x>Math.min(x1, x2)+4 && x<Math.max(x1,x2)-4&& y>Math.min(y1,y2)+4 && y<Math.max(y1, y2)-4){
                       
                       double slope1  = Math.abs((y2-y1)/(double)(x2-x1));
                       double slope2 = Math.abs((y2-y)/(double)(x2-x));
                       double slope3 = Math.abs((y1-y)/(double)(x1-x));
                         System.out.println(slope1+ " " + slope2 + " "+slope3);
                       if(Math.abs(slope1-slope2) <=0.3 || Math.abs(slope1-slope3) <=0.3 ){
                           return (myVertex.get(index1).name + " " + myVertex.get(index2).name);
                       }
                       
                       
                   }
                   
                   
                   
              }
          }
          
          return "no";
      }
      
   protected int dJfind(int v,int vi[],double d[]){
    double min=Integer.MAX_VALUE;
    int k=0;
    for(int i=0;i<v;i++)
    {
        if(vi[i]==0 && d[i]<min){
            k=i;
            min=d[i];
           
        }
    
    }
    return k;   
}
void dJgetpath(int i,int mm,int parent[]){
    if(i==mm){
       currentPathRequest+= myVertex.get(i).name+"\n";
        return;
    }
    dJgetpath(parent[i],mm,parent);
      currentPathRequest+=myVertex.get(i).name+"\n";   
}

void dj(String from,String to){
       
    // vi[0]=1;
    double[] d  = new double[100000];
    int vi[]= new int[100000];
    int parent[]=new int[100000];
        for(int i=0;i<100000;i++){
                d[i]=Integer.MAX_VALUE;
                vi[i]=0;
                parent[i]=0;
          }
         
     d[findVertex(from)]=0;


      for(int i=0;i<myVertex.size();i++){
       
              int k=dJfind(myVertex.size(),vi,d);
              ArrayList<Edge> M= myEdges.get(k);
              System.out.printf("%d\n",vi[k]);
              vi[k]=1;
            for(int j=0;j<M.size();j++){        
                 if(myEdges.get(k).get(j).w+d[k]<d[findVertex(myEdges.get(k).get(j).to)])
                 {
                    d[findVertex(myEdges.get(k).get(j).to)]=myEdges.get(k).get(j).w+d[k];
                     parent[findVertex(myEdges.get(k).get(j).to)]=k;   
                 }else if(myEdges.get(k).get(j).w+d[k]==d[findVertex(myEdges.get(k).get(j).to)]){
                     
                     System.out.print("No need to do anything\n Not mentioned in Questions");
                     
                 }
                 
            }  
        
      }
      
      currentPathRequest="No Path Exists";
      //System.out.println(parent);
        if(!(d[findVertex(to)]==Integer.MAX_VALUE))
        { 
            currentPathRequest="";
            dJgetpath(findVertex(to),findVertex(from),parent);

        }
   
}
  
           
}


 class GraphDrawer extends JPanel   {
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> points;
    String nameVertexSelected;
    String edgeSelected;
    int initial;
    int finalPosition;
    static int grid=1;
    
    public GraphDrawer(){
        nameVertexSelected="";
        edgeSelected="";   
   this.requestFocus();
               
        points = new ArrayList<Point>();
        setBackground(Color.WHITE);
        mouseListen();
          repaint();
    }
    
    private void mouseListen(){
          
        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
              
                 ApplicationManager.k.requestFocus();
              String m=     ApplicationManager.myGraph.findIfClickOnEdge(e.getX(), e.getY());
              System.out.println(m);
               if(!m.equals("no")){
                     String kk[] = m.split(" ");
                  ApplicationManager.eee.setText(kk[0]+" --> "+kk[1]);
                
                  
                   edgeSelected=m;
            return;
               }
              else{
             //   ApplicationManager.k.requestFocus();
              int y= ApplicationManager.myGraph.findVertex(e.getX(),e.getY());
              System.out.println(y);
                
              
                if(y==-1)
                {
                String name = JOptionPane.showInputDialog(ApplicationManager.f, "Name of Vertex?");
try{
                if(!name.equals(""))
                ApplicationManager.myGraph.addVertex(name,Integer.toString(e.getX()),Integer.toString(e.getY()));
}
catch(Exception E){
    
}
                repaint();
                }
                else{
                   nameVertexSelected=ApplicationManager.myGraph.myVertex.get(y).name;
                    ApplicationManager.vvv.setText(nameVertexSelected);
                }
                
                      }
                
            }
           
            public void mousePressed(MouseEvent e){
            initial = -1;
                 int y= ApplicationManager.myGraph.findVertex(e.getX(),e.getY());
                 if(y==-1)
                 {
                     initial =-1;
                     return;
                 }
                 initial =y;
                
            }
            public void mouseReleased(MouseEvent e){
              
                if(initial == -1)
                    return;
                  int y= ApplicationManager.myGraph.findVertex(e.getX(),e.getY());
               if(y==-1 )
               {
                   ApplicationManager.myGraph.modifyVertex(initial, "", Integer.toString(e.getX()),  Integer.toString(e.getY()));
               }
               else if(y!=initial){
                   String cost = JOptionPane.showInputDialog(ApplicationManager.f, "Weight of Edge?");
                   ApplicationManager.myGraph.addEdge(ApplicationManager.myGraph.myVertex.get(initial).name, ApplicationManager.myGraph.myVertex.get(y).name, cost);
                   
               }
            }
            
        });
        
        addKeyListener(new KeyAdapter() {
                @Override public void keyPressed(KeyEvent e) {
                   
                  if(e.getKeyChar()=='D' ||e.getKeyChar()=='d'){
        
           int x = ApplicationManager.myGraph.findVertex(nameVertexSelected);
           System.out.println(x);
           if(x!=-1){
           ApplicationManager.myGraph.deleteVertex(x);
           ApplicationManager.vvv.setText("No Vertex Selected");
           }
            
        }
                  
                       if(e.getKeyChar()=='M' ||e.getKeyChar()=='m'){
        handleMod();
         
        }
                       
                       
                       if(e.getKeyChar()=='x' ||e.getKeyChar()=='X'){
                           if(!edgeSelected.equals(""))
                           {
                       String nn[] = edgeSelected.split(" ");
                       
                Object[] options = {"Yes, please",
                    "No way!"};
int n = JOptionPane.showOptionDialog(ApplicationManager.f,
    "Are you sure and want to delete this Edge",
    "You sure?",
    JOptionPane.YES_NO_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,     //do not use a custom Icon
    options,  //the titles of buttons
    options[0]); //default button title  

if(n==0){
   ApplicationManager.l.setText("Edge Deleted from" + nn[0] + " " + nn[1]);
  ApplicationManager.myGraph.deleteEdge(nn[0],nn[1]);  
  ApplicationManager.eee.setText("No Edge Selected");
}
                           }
        }
                     
                       if(e.getKeyChar()=='c' ||e.getKeyChar()=='C'){
               String n[] = edgeSelected.split(" ");
                 JLabel note = new JLabel();
       note.setText("You are editing edgre from "+n[0] + " to " + n[1]);
       JTextField newWtext;
        newWtext = new JTextField();
        
     Object[] message = {
     "new Weight",newWtext,
     "Note:",note
     };

int option = JOptionPane.showConfirmDialog(null, message, "Modify ", JOptionPane.OK_CANCEL_OPTION);
if (option == JOptionPane.OK_OPTION) {

    ApplicationManager.myGraph.modifyEdge(n[0],n[1],newWtext.getText());
     ApplicationManager.l.setText("Edge Edited");
              }
else{
    ApplicationManager.l.setText("Edge not Modified");
}
                           
        }
                    
                       
        }   
        });
        
    }
    
   void handleMod(){
          int x = ApplicationManager.myGraph.findVertex(nameVertexSelected);
           System.out.println(x);
          
         
            JLabel note = new JLabel();
         note.setText("Leave Blank if you don't want to change that field");
             if(x>=0){ 
        JTextField newNameText = new JTextField();
      JTextField  newXtext = new JTextField();
       JTextField newYtext = new JTextField();
        
Object[] message = {
   // "new Name",newNameText,
    "new X:", newXtext,
    "new Y:", newYtext,
    "Note:",note
};

int option = JOptionPane.showConfirmDialog(null, message, "Modify ", JOptionPane.OK_CANCEL_OPTION);
if (option == JOptionPane.OK_OPTION) {
    
      ApplicationManager.myGraph.modifyVertex(x,"",newXtext.getText(),newYtext.getText());
      
    
} else {
  ApplicationManager.l.setText("Vertex was not modified");
}   }  
else{
                 JOptionPane.showMessageDialog(ApplicationManager.f,
    "The vertex you want to modify  was not found :(",
    "Vertex not found",
    JOptionPane.ERROR_MESSAGE);
                 
             }
    }
private void drawGrid(Graphics g, int gridSpace) {
      Insets insets = getInsets();
      
      int firstX = insets.left;
      int firstY =insets.top;
      int lastX = getWidth() - insets.right;
      int lastY = getHeight() - insets.bottom;
      g.setColor(Color.BLACK);
      // Draw vertical lines.
      int x = firstX;
      while (x < lastX) {
        g.drawLine(x, firstY, x, lastY);
        x += gridSpace;
      }

      // Draw horizontal lines.
      int y = firstY;
      while (y < lastY) {
        g.drawLine(firstX, y, lastX, y);
        y += gridSpace;
      }
    }

    @Override
    public void paintComponent(Graphics g) {
       
        super.paintComponent(g);
          initial=-1;
          if(GraphDrawer.grid ==1)
       drawGrid(g, 20);
        Graphics2D g2 = (Graphics2D) g;
        Shape s;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       for(int j=0;j<ApplicationManager.myGraph.myEdges.size();j++){
         //ArrayList<Edge> s =  ApplicationManager.myGraph.myEdges.get(j);
         for(int ii=0;ii<ApplicationManager.myGraph.myEdges.get(j).size();ii++){ 
              g2.setColor(Color.BLUE);
          int x1= ApplicationManager.myGraph.findVertex(ApplicationManager.myGraph.myEdges.get(j).get(ii).from);
          int x2= ApplicationManager.myGraph.findVertex(ApplicationManager.myGraph.myEdges.get(j).get(ii).to);
          
           g2.setStroke(new BasicStroke(3));
           if(x1!=-1 && x2!=-1)
          g2.drawLine((int)ApplicationManager.myGraph.myVertex.get(x1).x, (int)ApplicationManager.myGraph.myVertex.get(x1).y, (int)ApplicationManager.myGraph.myVertex.get(x2).x, (int)ApplicationManager.myGraph.myVertex.get(x2).y);
    
          System.out.println(x1 + " " + x2);
         }
          g2.setColor(Color.red);
      for(int x=0;x<ApplicationManager.myGraph.myVertex.size();x++) {
            g2.fillRect((int)ApplicationManager.myGraph.myVertex.get(x).x,(int) ApplicationManager.myGraph.myVertex.get(x).y, 8,8);
        }
       
      }
     
    }
 static javax.swing.Timer timer;
       void  animateThis(String m[]) throws InterruptedException{
    try{
           if(timer.isRunning()){
          //  timer.stop();   
          //  ApplicationManager.k.repaint();
           }
    }catch(Exception E){
        
    }
//        String m[] = ApplicationManager.myGraph.currentPathRequest.split("\n");
       // System.out.print("asdad");
         int i=0;
         final int a=4000;
         int n = m.length;
          
        timer = new javax.swing.Timer(200, new ActionListener() {
            int x=0;
            int o=0;
             int a1,b1;
            @Override
          
            public void actionPerformed(ActionEvent ae) {
                 //  i=1+i;
                 //ApplicationManager.k.repaint(); 
               System.out.println(x);
               x+=200;
             
//                  ApplicationManager.k.removeAll(); 
//                   ApplicationManager.k.revalidate(); 
//                  ApplicationManager.k.repaint(); 
//                 
                             //      ApplicationManager.k.repaint();   
                                     //  this.getDelay();
           Graphics g   =  ApplicationManager.k.getGraphics();
          Graphics2D g2 = (Graphics2D) g;
         //   g2.fillRect((int)(Math.random()*1000),(int) (Math.random()*1000), 8,8);
          int index1=ApplicationManager.myGraph.findVertex(m[o]);
          int index2=ApplicationManager.myGraph.findVertex(m[o+1]);
         Vertex a=  ApplicationManager.myGraph.myVertex.get(index1);
         Vertex b = ApplicationManager.myGraph.myVertex.get(index2);
         g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
         g2.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
         if(x!=200)
             g2.fillOval(a1,b1, 7,7);
          g2.setColor(Color.RED);
          g2.fillOval((int)((x*b.x+(4000-x)*a.x)/(4000)),(int)(x*b.y+(4000-x)*a.y)/(4000), 7,7);
           a1 = (int)(x*b.x+(4000-x)*a.x)/(4000);
           b1=(int)(x*b.y+(4000-x)*a.y)/(4000);
          //   
if(x==4000){
    x=0;
     ApplicationManager.k.repaint(); 
       o++;
       if(o==n-1){
           o=0;
       }
       
       
       
}
            }
           
           
       });
       
          timer.start();
     
        
                   //while(timer.isRunning());
     }
    
    void ShowDJpathAnimated(String from,String to) throws InterruptedException{
        ApplicationManager.myGraph.dj(from, to);
         String m[] = ApplicationManager.myGraph.currentPathRequest.split("\n");
          animateThis(m);
        
    }
   static void stopAnimation(){
       GraphDrawer.grid=1;
       ApplicationManager.k.repaint();
    try{
      // if(timer.isRunning()){
        timer.stop();
          ApplicationManager.k.repaint(); 
       // }
       }
    catch(Exception e){
        
    }
    }
}

abstract class AnimateMyShape{
    String name;
     AnimateMyShape(String name){
        this.name = name;
     }
     void ShowDJpathAnimated(String from,String to) throws InterruptedException{
        ApplicationManager.myGraph.dj(from, to);
         String m[] = ApplicationManager.myGraph.currentPathRequest.split("\n");
         GraphDrawer.grid=ApplicationManager.grid;
          ApplicationManager.k.repaint();
          animateThis(m);
       }
      
     abstract void animateThis(String m[]) throws InterruptedException;
}

class AnimateCircle extends AnimateMyShape{
     
      AnimateCircle(String name){
          super(name);
      }

    @Override
          void  animateThis(String m[]) throws InterruptedException{
    try{
           if(GraphDrawer.timer.isRunning()){
            //GraphDrawer.timer.stop();   
            //ApplicationManager.k.repaint();
           }
    }catch(Exception E){
        
    }
//        String m[] = ApplicationManager.myGraph.currentPathRequest.split("\n");
       // System.out.print("asdad");
         int i=0;
         final int a=4000;
         int n = m.length;
          
        GraphDrawer.timer = new javax.swing.Timer(200, new ActionListener() {
            int x=0;
            int o=0;
             int a1,b1;
            @Override
          
            public void actionPerformed(ActionEvent ae) {
                 //  i=1+i;
                 //ApplicationManager.k.repaint(); 
               System.out.println(x);
               x+=200;
             
//                  ApplicationManager.k.removeAll(); 
//                   ApplicationManager.k.revalidate(); 
//                  ApplicationManager.k.repaint(); 
//                 
                             //      ApplicationManager.k.repaint();   
                                     //  this.getDelay();
           Graphics g   =  ApplicationManager.k.getGraphics();
          Graphics2D g2 = (Graphics2D) g;
         //   g2.fillRect((int)(Math.random()*1000),(int) (Math.random()*1000), 8,8);
          int index1=ApplicationManager.myGraph.findVertex(m[o]);
          int index2=ApplicationManager.myGraph.findVertex(m[o+1]);
         Vertex a=  ApplicationManager.myGraph.myVertex.get(index1);
         Vertex b = ApplicationManager.myGraph.myVertex.get(index2);
         g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
         g2.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
         if(x!=200)
             g2.fillOval(a1,b1, 7,7);
          g2.setColor(Color.RED);
          g2.fillOval((int)((x*b.x+(4000-x)*a.x)/(4000)),(int)(x*b.y+(4000-x)*a.y)/(4000), 7,7);
           a1 = (int)(x*b.x+(4000-x)*a.x)/(4000);
           b1=(int)(x*b.y+(4000-x)*a.y)/(4000);
          //   
if(x==4000){
    x=0;
     ApplicationManager.k.repaint(); 
       o++;
       if(o==n-1){
           o=0;
       }
       
       
       
}
            }
           
           
       });
       
          GraphDrawer.timer.start();
     
        
                   //while(timer.isRunning());
     }

    }
    
class AnimateSqaure extends AnimateMyShape{
     
      AnimateSqaure(String name){
          super(name);
      }

    @Override
          void  animateThis(String m[]) throws InterruptedException{
    try{
           if(GraphDrawer.timer.isRunning()){
//            GraphDrawer.timer.stop();   
//            ApplicationManager.k.repaint();
           }
    }catch(Exception E){
        
    }
//        String m[] = ApplicationManager.myGraph.currentPathRequest.split("\n");
       // System.out.print("asdad");
         int i=0;
         final int a=4000;
         int n = m.length;
          
        GraphDrawer.timer = new javax.swing.Timer(200, new ActionListener() {
            int x=0;
            int o=0;
             int a1,b1;
            @Override
          
            public void actionPerformed(ActionEvent ae) {
                 //  i=1+i;
                 //ApplicationManager.k.repaint(); 
               System.out.println(x);
               x+=200;
             
//                  ApplicationManager.k.removeAll(); 
//                   ApplicationManager.k.revalidate(); 
//                  ApplicationManager.k.repaint(); 
//                 
                             //      ApplicationManager.k.repaint();   
                                     //  this.getDelay();
           Graphics g   =  ApplicationManager.k.getGraphics();
          Graphics2D g2 = (Graphics2D) g;
         //   g2.fillRect((int)(Math.random()*1000),(int) (Math.random()*1000), 8,8);
          int index1=ApplicationManager.myGraph.findVertex(m[o]);
          int index2=ApplicationManager.myGraph.findVertex(m[o+1]);
         Vertex a=  ApplicationManager.myGraph.myVertex.get(index1);
         Vertex b = ApplicationManager.myGraph.myVertex.get(index2);
         g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
         g2.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
         if(x!=200)
             g2.fillRect(a1,b1, 7,7);
          g2.setColor(Color.RED);
          g2.fillRect((int)((x*b.x+(4000-x)*a.x)/(4000)),(int)(x*b.y+(4000-x)*a.y)/(4000), 7,7);
           a1 = (int)(x*b.x+(4000-x)*a.x)/(4000);
           b1=(int)(x*b.y+(4000-x)*a.y)/(4000);
          //   
if(x==4000){
    x=0;
     ApplicationManager.k.repaint(); 
       o++;
       if(o==n-1){
           o=0;
       }
       
       
       
}
            }
           
           
       });
       
          GraphDrawer.timer.start();
     
        
                   //while(timer.isRunning());
     }

    }
    



    
class AnimateTriangle extends AnimateMyShape{
     
      AnimateTriangle(String name){
          super(name);
      }

    @Override
          void  animateThis(String m[]) throws InterruptedException{
    try{
           if(GraphDrawer.timer.isRunning()){
//            GraphDrawer.timer.stop();   
//            ApplicationManager.k.repaint();
           }
    }catch(Exception E){
        
    }
//        String m[] = ApplicationManager.myGraph.currentPathRequest.split("\n");
       // System.out.print("asdad");
         int i=0;
         final int a=4000;
         int n = m.length;
          
        GraphDrawer.timer = new javax.swing.Timer(200, new ActionListener() {
            int x=0;
            int o=0;
             int a1,b1;
            @Override
          
            public void actionPerformed(ActionEvent ae) {
                 //  i=1+i;
                 //ApplicationManager.k.repaint(); 
               System.out.println(x);
               x+=200;
             
//                  ApplicationManager.k.removeAll(); 
//                   ApplicationManager.k.revalidate(); 
//                  ApplicationManager.k.repaint(); 
//                 
                             //      ApplicationManager.k.repaint();   
                                     //  this.getDelay();
           Graphics g   =  ApplicationManager.k.getGraphics();
          Graphics2D g2 = (Graphics2D) g;
         //   g2.fillRect((int)(Math.random()*1000),(int) (Math.random()*1000), 8,8);
          int index1=ApplicationManager.myGraph.findVertex(m[o]);
          int index2=ApplicationManager.myGraph.findVertex(m[o+1]);
         Vertex a=  ApplicationManager.myGraph.myVertex.get(index1);
         Vertex b = ApplicationManager.myGraph.myVertex.get(index2);
         g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
         g2.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
         if(x!=200){
               int xxx[]={a1,a1-10,a1};
           int yyy[] = {b1,b1-10,b1+10};
          g2.fillPolygon(xxx,yyy,3);
           //  g2.fillRect(a1,b1, 7,7);
         }
          g2.setColor(Color.RED);
          
           a1 = (int)(x*b.x+(4000-x)*a.x)/(4000);
           b1=(int)(x*b.y+(4000-x)*a.y)/(4000);
              int xx[]={a1,a1-10,a1};
           int yy[] = {b1,b1-10,b1+10};
          g2.fillPolygon(xx,yy,3);
         
          //   
if(x==4000){
    x=0;
     ApplicationManager.k.repaint(); 
       o++;
       if(o==n-1){
           o=0;
       }
       
       
       
}
            }
           
           
       });
       
          GraphDrawer.timer.start();
     
        
                   //while(timer.isRunning());
     }

    }
    
    
class AnimateCross extends AnimateMyShape{
     
      AnimateCross(String name){
          super(name);
      }

    @Override
          void  animateThis(String m[]) throws InterruptedException{
    try{
           if(GraphDrawer.timer.isRunning()){
//            GraphDrawer.timer.stop();   
//            ApplicationManager.k.repaint();
           }
    }catch(Exception E){
        
    }
//        String m[] = ApplicationManager.myGraph.currentPathRequest.split("\n");
       // System.out.print("asdad");
         int i=0;
         final int a=4000;
         int n = m.length;
          
        GraphDrawer.timer = new javax.swing.Timer(200, new ActionListener() {
            int x=0;
            int o=0;
             int a1,b1;
            @Override
          
            public void actionPerformed(ActionEvent ae) {
                 //  i=1+i;
                 //ApplicationManager.k.repaint(); 
               System.out.println(x);
               x+=200;
             
//                  ApplicationManager.k.removeAll(); 
//                   ApplicationManager.k.revalidate(); 
//                  ApplicationManager.k.repaint(); 
//                 
                             //      ApplicationManager.k.repaint();   
                                     //  this.getDelay();
           Graphics g   =  ApplicationManager.k.getGraphics();
          Graphics2D g2 = (Graphics2D) g;
         //   g2.fillRect((int)(Math.random()*1000),(int) (Math.random()*1000), 8,8);
          int index1=ApplicationManager.myGraph.findVertex(m[o]);
          int index2=ApplicationManager.myGraph.findVertex(m[o+1]);
         Vertex a=  ApplicationManager.myGraph.myVertex.get(index1);
         Vertex b = ApplicationManager.myGraph.myVertex.get(index2);
         g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
         g2.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
         if(x!=200){
                 g2.drawLine(a1-3,b1-3,a1+3,b1+3);
          g2.drawLine(a1-3,b1+3,a1+3,b1-3);
           //  g2.fillRect(a1,b1, 7,7);
         }
          g2.setColor(Color.RED);
          
           a1 = (int)(x*b.x+(4000-x)*a.x)/(4000);
           b1=(int)(x*b.y+(4000-x)*a.y)/(4000);
            
          g2.drawLine(a1-3,b1-3,a1+3,b1+3);
          g2.drawLine(a1-3,b1+3,a1+3,b1-3);
         
          //   1
if(x==4000){
    x=0;
     ApplicationManager.k.repaint(); 
       o++;
       if(o==n-1){
           o=0;
       }
       
       
       
}
            }
           
           
       });
       
          GraphDrawer.timer.start();
     
        
                   //while(timer.isRunning());
     }

    }
    class AnimatePlus extends AnimateMyShape{
     
      AnimatePlus(String name){
          super(name);
      }

    @Override
          void  animateThis(String m[]) throws InterruptedException{
    try{
           if(GraphDrawer.timer.isRunning()){
//            GraphDrawer.timer.stop();   
//            ApplicationManager.k.repaint();
           }
    }catch(Exception E){
        
    }
//        String m[] = ApplicationManager.myGraph.currentPathRequest.split("\n");
       // System.out.print("asdad");
         int i=0;
         final int a=4000;
         int n = m.length;
          
        GraphDrawer.timer = new javax.swing.Timer(200, new ActionListener() {
            int x=0;
            int o=0;
             int a1,b1;
            @Override
          
            public void actionPerformed(ActionEvent ae) {
                 //  i=1+i;
                 //ApplicationManager.k.repaint(); 
               System.out.println(x);
               x+=200;
             
//                  ApplicationManager.k.removeAll(); 
//                   ApplicationManager.k.revalidate(); 
//                  ApplicationManager.k.repaint(); 
//                 
                             //      ApplicationManager.k.repaint();   
                                     //  this.getDelay();
           Graphics g   =  ApplicationManager.k.getGraphics();
          Graphics2D g2 = (Graphics2D) g;
         //   g2.fillRect((int)(Math.random()*1000),(int) (Math.random()*1000), 8,8);
          int index1=ApplicationManager.myGraph.findVertex(m[o]);
          int index2=ApplicationManager.myGraph.findVertex(m[o+1]);
         Vertex a=  ApplicationManager.myGraph.myVertex.get(index1);
         Vertex b = ApplicationManager.myGraph.myVertex.get(index2);
         g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
         g2.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
         if(x!=200){
                 g2.drawLine(a1,b1-4,a1,b1+4);
          g2.drawLine(a1-4,b1,a1+4,b1);
           //  g2.fillRect(a1,b1, 7,7);
         }
          g2.setColor(Color.RED);
          
           a1 = (int)(x*b.x+(4000-x)*a.x)/(4000);
           b1=(int)(x*b.y+(4000-x)*a.y)/(4000);
            
         g2.drawLine(a1,b1-3,a1,b1+3);
          g2.drawLine(a1-3,b1,a1+3,b1);
         
          //   1
if(x==4000){
    x=0;
     ApplicationManager.k.repaint(); 
       o++;
       if(o==n-1){
           o=0;
       }
       
       
       
}
            }
           
           
       });
       
          GraphDrawer.timer.start();
     
        
                   //while(timer.isRunning());
     }

    }
    






class TextInputOutputHandler{

   
    
     TextInputOutputHandler(){
         
     }
      static void doExport(String s) throws FileNotFoundException {
         
             if(s.equals("")|| s.equals("Import or Export From(Path)"))
             s="src/output.txt";
    FileOutputStream  fout;
             try{
         fout=new FileOutputStream(s); 
             }
             catch(Exception e){
                 new aException("Cannot make file").showMsg();
                 return;
             }
        PrintStream out=new PrintStream(fout); 
      
        ArrayList<Vertex> copythis = new ArrayList<Vertex>(ApplicationManager.myGraph.myVertex);
           out.println(copythis.size());
        Collections.sort(copythis, new Comparator<Vertex>(){
           public int compare(Vertex u1, Vertex u2) {
                        return u1.name.compareTo(u2.name);
           }
        });
       // System.out.println("da");
        for(int i =0 ; i<copythis.size();i++){
            Vertex x=copythis.get(i);
            out.println(x.name+" " + (int)x.x + " " +(int)x.y);
        }
        out.println(ApplicationManager.myGraph.nEdges);
        for(int i=0;i<copythis.size();i++){
                String y = copythis.get(i).name;
                int yy = ApplicationManager.myGraph.findVertex(y);
                ArrayList<Edge> toPrint= new ArrayList<Edge>(ApplicationManager.myGraph.myEdges.get(yy));
                  Collections.sort(toPrint, new Comparator<Edge>(){
           public int compare(Edge u1, Edge u2) {
                        return u1.to.compareTo(u2.to);
           }
        });
                  for(int ii =0 ; ii<toPrint.size();ii++){
            Edge xx= toPrint.get(ii);
            out.println(xx.from+" " + xx.to+ " " +(int)xx.w);
                  }   
        }
        
       JOptionPane.showMessageDialog(ApplicationManager.f,
    "Graph Was Successfilly Exported to "+s,"Success",
    JOptionPane.INFORMATION_MESSAGE); 
        
    }
      
     static void takeInput(String s) throws FileNotFoundException, IOException{
         
         if(s.equals("")|| s.equals("Import or Export From(Path)"))
             s="src/input.txt";
       BufferedReader br;
             File file = new File(s); 
  try{
         br = new BufferedReader(new FileReader(file)); 
             }
             catch(Exception e){
                 new aException("File Not Found").showMsg();
                 return;
             }
 
  
  String st; 
      st = br.readLine();
      try{
     for(int i=0;i<Integer.parseInt(st);i++){
         String st1 = br.readLine();
         String x[] = st1.split(" ");
        ApplicationManager.myGraph.addVertex(x[0], x[1], x[2]);
     }
   st = br.readLine();
     for(int i=0;i<Integer.parseInt(st);i++){
         String st1 = br.readLine();
         String x[] = st1.split(" ");
         ApplicationManager.myGraph.addEdge(x[0], x[1], x[2]);
     }
      }     
     catch(Exception E){
         new aException("Invalid File Format :( -  Partially Accepted Only.").showMsg();
     }
     }

}



class ApplicationManager extends JFrame implements ActionListener{
    static int grid;
    JButton b,searchButton,deleteButton,modifyButton,addEdgeButtton; 
    JTextField xText,yText,nameText,searchText,deleteText,modifyText,newXtext,newYtext,newNameText;  
    JTextField  addEdgeFrom,addEdgeTo,addEdgeWeight,actionFrom,actionTo,ImportExportText;
    JButton searchEdgeButton,deleteEdgeButton,modifyEdgeButton;
    JButton ImportGraphButton;
    JButton ExportGraphButton;
    JTextField froms,tos;
    static JFrame f; 
    JTextArea c;
    static JLabel l; 
    static GraphHandler myGraph;
    static GraphDrawer k;
    static JTextArea mm;
   static JLabel vvv,eee;
    
       ApplicationManager() throws IOException{
        f = new JFrame("OOM Assignment IIT2018040");  
        l = new JLabel("(development Purpose Only)Message: "); 
        JLabel vv = new JLabel("Selected Vertex:");
        JLabel ee = new JLabel("Selected Edge:");
        vvv = new JLabel("No Vertex Selected");
        eee = new JLabel("No Edge Selected");
        
       
        
        mm = new JTextArea("Instructions:\nClick to Select Vertex,\nM to modify,\nD to delete Selected Vertex\nClick To Select Edge\nC to modify,\nX to delete selected edge\n\nNote For Evaluation:\nQuestion 1,2,3,4,5 \nare completely Solved\nMutiple Animation are also Supported\nThe Program is entirely Written in\nJava Swing and 'NO' Scene Builder \nis used\n\n");
        mm.setEditable(false);
        mm.setBounds(370,60,300,320);
        f.add(mm);
     
      
         myGraph = new GraphHandler();
          k = new GraphDrawer();
         k.setBounds(700,50,800,600);
         f.add(k);
          k.setBorder(BorderFactory.createMatteBorder(1, 1,0, 0, Color.RED));
         
       
        l.setBounds(100,500,1000,20);
        vv.setBounds(750,500,200,400);
        ee.setBounds(750,520,200,400);
         vvv.setBounds(850,500,200,400);
        eee.setBounds(850,520,200,400);
          vvv.setBounds(900,500,200,400);
        eee.setBounds(900,520,200,400);
        vv.setFont(new Font("Verdana",0,15));
       ee.setFont(new Font("Verdana",0,15));
          vvv.setFont(new Font("Verdana",1,15));
       eee.setFont(new Font("Verdana",1,15));
        
        addVertexInput();
        addHeader();
        addSearchInput();
        addDeleteinput();
        addModifyInput();
        addEdgeInput();
        addActionEdgeInput();
        addImportExportButtons();
        addDJbutton();
       // f.getContentPane().setBackground(Color.WHITE);
        //f.pack();
        f.add(l); 
        f.setLayout(null);
     f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
f.add(vv);
f.add(ee);
f.add(vvv);
f.add(eee);
        f.setVisible(true); 
        k.requestFocus();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       
                
    }
    
       protected void addHeader(){
         
         JLabel label; 
         label = new JLabel("Vertex Mania");
         label.setBounds(80,550,420,80);
         
         label.setVerticalAlignment(JLabel.TOP);
      label.setFont(new Font("Verdana", Font.ITALIC, 60));
      label.setPreferredSize(new Dimension(280, 150));
      label.setForeground(Color.black);
      label.setBackground(Color.WHITE);
      Border border = BorderFactory.createDashedBorder(Color.BLACK,5,1,0,false);
    //  BorderFactory.createD
     
      label.setBorder(border);
         f.add(label);
        
           
    }
       
       protected void addVertexInput(){

         xText = new JTextField("X",11); 
        yText= new JTextField("Y",11); 
        nameText= new JTextField("Vertex Name",11); 
    
        
           xText.setBounds(30,82,120,20);
           yText.setBounds(30,102,120,20);
           nameText.setBounds(30,60,120,20);
             b = new JButton("Add Vertex"); 
              b.addActionListener(this); 
            b.setBounds(30,122,120,20);
             f.add(b); 
            f.add(xText); 
              f.add(yText);
           f.add(nameText);   
    }
       protected void addSearchInput(){

    
            searchText =new JTextField("Vertex Name");
            searchText.setBounds(30,160,120,20);
       
            searchButton = new JButton("Search"); 
            searchButton.addActionListener(this); 
            searchButton.setBounds(30,200,120,20);
            f.add(searchButton); 
            f.add(searchText);            
    }
       protected void addDeleteinput(){

    
            deleteText =new JTextField("Vertex Name");
            deleteText.setBounds(30,220,120,20);
            deleteButton = new JButton("Delete"); 
            deleteButton.addActionListener(this); 
            deleteButton.setBounds(30,222,120,20);
            f.add(deleteButton); 
            //f.add(deleteText);            
    }
       protected void addModifyInput(){
         
            modifyText =new JTextField("Vertex Name");
            modifyText.setBounds(30,280,120,20);
       
            modifyButton = new JButton("Modify"); 
            modifyButton.addActionListener(this); 
            modifyButton.setBounds(30,242,120,20);
            f.add(modifyButton); 
           // f.add(modifyText);       
     }
       protected void addEdgeInput(){
        addEdgeFrom = new JTextField("From",11); 
        addEdgeTo= new JTextField("To",11); 
        addEdgeWeight = new JTextField("Weight",11); 
    
        
           addEdgeFrom.setBounds(230,62,120,20);
           addEdgeTo.setBounds(230,82,120,20);
           addEdgeWeight.setBounds(230,102,120,20);
             addEdgeButtton = new JButton("Add Edge"); 
              addEdgeButtton.addActionListener(this); 
            addEdgeButtton.setBounds(230,122,120,20);
             f.add(addEdgeButtton); 
            f.add(addEdgeFrom); 
              f.add(addEdgeTo);
           f.add(addEdgeWeight);
       }
       protected void addActionEdgeInput(){
           actionFrom = new JTextField("From",11); 
           actionTo= new JTextField("To",11); 
        
        
           actionFrom.setBounds(230,160,120,20);
           actionTo.setBounds(230,180,120,20);
           JButton searchEdgeButton,deleteEdgeButton,modifyEdgeButton;
             
               searchEdgeButton = new JButton("Search Edge"); 
               searchEdgeButton.addActionListener(this); 
               searchEdgeButton.setBounds(230,215,120,20);
               
               deleteEdgeButton = new JButton("Delete Edge"); 
               deleteEdgeButton.addActionListener(this); 
               deleteEdgeButton.setBounds(230,237,120,20);
                  
                 modifyEdgeButton = new JButton("Modify Edge"); 
               modifyEdgeButton.addActionListener(this); 
               modifyEdgeButton.setBounds(230,259,120,20);
              
                       
               
           
           f.add(modifyEdgeButton);
           f.add(deleteEdgeButton);
           f.add(searchEdgeButton);
            f.add(actionFrom); 
            f.add(actionTo); 
              
       }
    
   private void addImportExportButtons(){
               ImportGraphButton  = new JButton();
               ExportGraphButton = new JButton();
               
               ExportGraphButton = new JButton("Import Graph"); 
               ExportGraphButton.addActionListener(this); 
               ExportGraphButton.setBounds(30,400,120,20);
               ImportExportText= new JTextField("Import or Export From(Path)");
               ImportExportText.setBounds(30,460,370,25);
               ImportGraphButton = new JButton("Export Graph"); 
               ImportGraphButton.addActionListener(this); 
               ImportGraphButton.setBounds(30,428,120,20);
               
               f.add(ImportGraphButton);
               f.add(ExportGraphButton);
               f.add(ImportExportText);
   }
    private void addDJbutton(){
               JButton DJbutton  = new JButton();
               
        
               DJbutton = new JButton("PATH OPERATIONS"); 
              
               
               DJbutton.addActionListener(this); 
               DJbutton.setBounds(160,400,240,48);
             
              
               f.add(DJbutton);
               
   }
   
   
    @Override
     public void actionPerformed(ActionEvent e){
    
     String s = e.getActionCommand(); 
        if (s.equals("Add Vertex")) { 
            handleAddVertex();
        } 
        if(s.equals("Search")){
            handleSearchVertex();  
        }
        if(s.equals("Delete")){
          handleDeleteVertex();       
        }
         if(s.equals("Modify")){
          handleModifyVertex();       
        }
        if(s.equals("Add Edge")){
          handleAddEdge();  
        }
        if(s.equals("Search Edge")){
         
          this.handleSearchEdge();       
        }
         if(s.equals("Modify Edge")){
          this.handleModifyEdge();       
        }
          if(s.equals("Delete Edge")){
          this.handleDeleteEdge();       
        }
           if(s.equals("Import Graph")){
        this.handleImportGraph();
        }
            if(s.equals("Export Graph")){
         try {       
             this.handleExportGraph();
         } catch (FileNotFoundException ex) {
             Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
            if(s.equals("PATH OPERATIONS")){
                this.handleDj();
            }
            if(s.equals("Get Path")){
                
             
         try {
             if(myGraph.findVertex(froms.getText())==-1 || myGraph.findVertex(tos.getText())==-1){
                throw  new aException("Enter Correct Vertex Names");
             }
             
                myGraph.dj(froms.getText(), tos.getText());
                c.setText(myGraph.currentPathRequest);
             //k.ShowDJpathAnimated(froms.getText(), tos.getText());
             if(c2.isSelected()){
                  grid =1;
             }else{
                 grid=0;
             }
             if(c1.getSelectedIndex()==0){
                AnimateCircle l = new AnimateCircle("Circle");
                l.ShowDJpathAnimated(froms.getText(), tos.getText());
             }
             if(c1.getSelectedIndex()==1){
                AnimateSqaure l = new AnimateSqaure("Sqaure");
                l.ShowDJpathAnimated(froms.getText(), tos.getText());
               
             }
             if(c1.getSelectedIndex()==2){
                AnimateTriangle l = new AnimateTriangle("Triangle");
                l.ShowDJpathAnimated(froms.getText(), tos.getText());
               
             }
                if(c1.getSelectedIndex()==3){
                AnimateCross l = new AnimateCross("Cross");
                l.ShowDJpathAnimated(froms.getText(), tos.getText());
               
             }
                  if(c1.getSelectedIndex()==4){
                AnimatePlus l = new AnimatePlus("Plus");
                l.ShowDJpathAnimated(froms.getText(), tos.getText());
               
             }
                
         } catch (InterruptedException ex) {
             Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
         }catch(aException ss){
             ss.showMsg();
         }
            }
            if(s.equals("Stop Animation")){
                k.stopAnimation();
            }
    }
     
    JComboBox c1;
    JCheckBox c2;
     private void handleDj(){


          c = new JTextArea();
         JButton bt = new JButton("Get Path");
        String s1[] = { "Circle", "Sqaure", "Triangle", "Cross" ,"Plus"}; 
  
        // create checkbox 
        c2 = new JCheckBox("Enable Grid Duing Animation", true); 

        c1 = new JComboBox(s1); 
         froms = new JTextField();
         tos = new JTextField();
         c.setFocusable(false);
          bt.addActionListener(this); 
          JButton Xbutton = new JButton("Stop Animation");
          JScrollPane scroll = new JScrollPane(c);
          c.setText("\n\n\n\n\n\n\n\n\n\n\n\n");
           Xbutton.addActionListener(this); 
                //c.setSize(500,500);
              
                
         
Object[] message = {
    "From:",froms,
    "To:", tos,
    "Select Animation Style:",c1,
    c2,
    " ", bt,
    Xbutton,
    "Path",scroll,
      "      "," ", " "
};


int option = JOptionPane.showConfirmDialog(null, message, "FindPath ", JOptionPane.CLOSED_OPTION);
if (option == JOptionPane.OK_OPTION) {
    
          
   //   myGraph.modifyVertex(index,newNameText.getText(),newXtext.getText(),newYtext.getText());
      
    
} else {
  //l.setText("");
}


}
     
     private void handleImportGraph(){
          try {      
             TextInputOutputHandler.takeInput(ImportExportText.getText());
         } catch (IOException ex) {
             Logger.getLogger(ApplicationManager.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
      private void handleExportGraph() throws FileNotFoundException{
          TextInputOutputHandler.doExport(ImportExportText.getText());
     }
      
      
     private void handleModifyVertex(){
         int index = myGraph.findVertex(searchText.getText());
         
         JLabel note = new JLabel();
         note.setText("Leave Blank if you don't want to change that field");
             if(index>=0){ 
        newNameText = new JTextField();
        newXtext = new JTextField();
        newYtext = new JTextField();
        
Object[] message = {
  
    "new X:", newXtext,
    "new Y:", newYtext,
    "Note:",note
};

int option = JOptionPane.showConfirmDialog(null, message, "Modify ", JOptionPane.OK_CANCEL_OPTION);
if (option == JOptionPane.OK_OPTION) {
    
      myGraph.modifyVertex(index,"",newXtext.getText(),newYtext.getText());
      
    
} else {
  l.setText("Vertex " + searchText.getText() + " was not modified");
}   }  
else{
                 JOptionPane.showMessageDialog(f,
    "The vertex you want to modify  was not found :(",
    "Vertex not found",
    JOptionPane.ERROR_MESSAGE);
                  
             }
    }
     private void handleAddVertex(){
        
            l.setText("Message:"+ nameText.getText()+ " Adding to the graph with Xcord "+ xText.getText() + " ycord " + yText.getText()); 
             myGraph.addVertex(nameText.getText(),xText.getText(),yText.getText());
    }
     private void handleDeleteVertex(){
           
             int index = myGraph.findVertex(searchText.getText());
             if(index>=0){
                Object[] options = {"Yes, please",
                    "No way!"};
int n = JOptionPane.showOptionDialog(f,
    "Are you sure and want to delete " + searchText.getText(),
    "Your sure?",
    JOptionPane.YES_NO_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,     //do not use a custom Icon
    options,  //the titles of buttons
    options[0]); //default button title  

if(n==0){
   l.setText("Vertex "+ searchText.getText() + " was deleted");
    myGraph.deleteVertex(index);    
}
             }
             else{
                 JOptionPane.showMessageDialog(f,
    "The vertex you want to delete was not found :(",
    "Vertex not found",
    JOptionPane.ERROR_MESSAGE);
                  
             }
         
     }
     private void handleSearchVertex()
     {
         int index = myGraph.findVertex(searchText.getText());
         try{    
         if(index>=0){
                     myGraph.showVertexDetails(index);       
             }
             else{
                 throw new aException("The vertex you Searched for was not found :(");
             }
         }
             catch(aException s){
                 s.showMsg();
             }
           
     }

    private void handleAddEdge(){
       myGraph.addEdge(addEdgeFrom.getText(),addEdgeTo.getText(),addEdgeWeight.getText());
       l.setText("Message: New Edge was added with Weight " + addEdgeWeight.getText()); 
    }
    private void handleSearchEdge(){
       
        myGraph.showEdgeDetails(actionFrom.getText(),actionTo.getText());
        
    }

    private void handleModifyEdge() {
        try{
            int index1 = myGraph.findVertex(actionFrom.getText());
               int index = myGraph.findVertex(actionTo.getText());
               if(index ==-1 ||index1==-1){
                   throw new Exception();
               }
            JLabel note = new JLabel();
       note.setText("You are editing edgre from "+actionFrom.getText() + " to " + actionTo.getText());
       JTextField newWtext;
        newWtext = new JTextField();
        
     Object[] message = {
     "new Weight",newWtext,
     "Note:",note
     };int option = JOptionPane.showConfirmDialog(null, message, "Modify ", JOptionPane.OK_CANCEL_OPTION);
if (option == JOptionPane.OK_OPTION) {

    myGraph.modifyEdge(actionFrom.getText(),actionTo.getText(),newWtext.getText());
     System.out.println("dad");
              }
else{
    l.setText("Edge not Modified");
}
     
        }
        catch(Exception s){
            new aException("Invalid Names of vertex").showMsg();
        }
      


    }
    
    private void handleDeleteEdge() {
         
                Object[] options = {"Yes, please",
                    "No way!"};
int n = JOptionPane.showOptionDialog(f,
    "Are you sure and want to delete this Edge",
    "You sure?",
    JOptionPane.YES_NO_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,     //do not use a custom Icon
    options,  //the titles of buttons
    options[0]); //default button title  

if(n==0){
   l.setText("Edge Deleted from "+ actionFrom.getText()+ " to "+actionTo.getText());
  myGraph.deleteEdge(actionFrom.getText(),actionTo.getText());   
}
             
            
       
    }

   

     
     
}




class JavaApplication1 { 
    // JTextField 
     
    // default constructor 
    JavaApplication1() 
    { 
//        Takeinput myinput = new Takeinput();
    }  
  
    
    // main class 
    public static void main(String[] args) throws IOException 
    { try { 
//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

           // UIManager.setLookAndFeel(UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel")); 
        } 
        catch (Exception e) { 
            System.out.println("Look and Feel not set"); 
        } 
        ApplicationManager myinput = new ApplicationManager();
        
    } 
  
    // if the vutton is pressed 
   
} 