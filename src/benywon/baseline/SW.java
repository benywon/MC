package benywon.baseline;

import benywon.MCTest.MCStructure;
import benywon.publicMethods.Myconfig;

/** this is the baseline method sliding window
 * Created by benywon on 2015/11/24.
 */
public class SW
{
    public static void main(String[] args)
    {
        String mc500testfile= Myconfig.Getconfiginfo("MCtestDir")+Myconfig.Getconfiginfo("MC500testfilepath");
        MCStructure mcStructure=new MCStructure(mc500testfile);
        System.out.println("hsiud");
    }

}
