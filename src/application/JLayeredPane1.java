package application;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class JLayeredPane1 extends JFrame{
      public JLayeredPane1(){
      	 super("JLayeredPane");
      	 /*由小到大定义组件深度数值，也就是Z-order layer的大小。
      	  */
      	 Integer[] layerConstants={
      	 	            JLayeredPane.DEFAULT_LAYER,JLayeredPane.PALETTE_LAYER,
      	 	            new Integer(101),JLayeredPane.MODAL_LAYER,new Integer(201),
      	 	            JLayeredPane.POPUP_LAYER,JLayeredPane.DRAG_LAYER
      	           };
      	 /*定义每个JLabel的颜色
      	  */
      	 Color[] colors={
      	 	             Color.red,Color.blue,Color.magenta,Color.cyan,Color.yellow,
      	 	             Color.green,Color.pink
      	 	            };
      	 Point position=new Point(10,10);
      	 JLabel[] label=new JLabel[7];
      	 JLayeredPane layeredPane=getLayeredPane();//取得窗口的Layered Pane
      	 
      	 for(int i=0;i<7;i++){
      	    label[i]=createLabel("第"+(i+1)+"层",colors[i],position);
      	    position.x=position.x+20;
      	    position.y=position.y+20;
      	    //将组件(JLabel)放入Layered Pane中并给予深度(Z-order layer)的数值。
      	    layeredPane.add(label[i],layerConstants[i]);
      	 }
      	 setSize(new Dimension(280,280));
      	 show();
      	 addWindowListener(
      	 	new WindowAdapter(){
      	 	   public void windowClosing(WindowEvent e){
      	 	      System.exit(0);
      	 	   }
      	    }
         );
      }
      public JLabel createLabel(String content,Color color,Point position){
      	 JLabel label=new JLabel(content,JLabel.CENTER);
      	 label.setVerticalAlignment(JLabel.TOP);
      	 label.setBackground(color);
      	 label.setForeground(Color.black);
      	 label.setOpaque(true);
      	 label.setBounds(position.x,position.y,100,100);
      	 return label;
      }
      public static void main(String[] args){
         new JLayeredPane1();
      }
}