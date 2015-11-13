package benywon.publicMethods;

import java.io.*;
import java.util.*;

/**
 * Created by benywon on 2015/4/7.
 */
public class Filebases
{
    public static String readfile(String filepath)
    {
//        String str = null;
//        File file=new File(filepath);
//        Long filelength = file.length();     //获取文件长度
//        byte[] filecontent = new byte[filelength.intValue()];
//        try {
//            FileInputStream in = new FileInputStream(file);
//            in.read(filecontent);
//            in.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new String(filecontent);//返回文件内容,默认编码
        String charset="utf-8";
        File file = new File(filepath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取文件效率最快的方法就是一次全读进来，很多人用readline()之类的方法，可能需要反复访问文件，
     * 而且每次readline()都会调用编码转换，
     * 降低了速度，所以，在已知编码的情况下，按字节流方式先将文件都读入内存，再一次性编码转换是最快的方式
     * @param filepath
     * @param charset
     * @return
     */
    public static String readfile(String filepath,String charset)
    {
        File file = new File(filepath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void appendMethodA(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 写文件 每次一行
     * @param str 写入内容
     * @param filepath 写入文件位置
     * @param isadd 是否是追加
     * @return 写入结果
     */
    public static boolean Write2File(String str,String filepath,boolean isadd)
    {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filepath,isadd);

            fw.write(str+"\n");

            fw.close();
            fw.flush();
        } catch (IOException e1) {
            return false;
        }finally {
            {
                return true;
            }
        }
    }
    public static Object ReadObj(String filepath)
    {
        FileInputStream freader;
        Object obj = new Object();
        try {
            freader = new FileInputStream(filepath);
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);

            obj = (Object) objectInputStream.readObject();
            freader.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 将一个列表写入文件
     * @param list
     * @param filepath
     * @return
     */
    public static boolean WriteList2File(List list,String filepath)
    {
        String txt="";
        for(Object strobj:list)
        {
            String str=strobj.toString();
            txt+=str+"\r\n";
        }
        boolean allright=Write2File(txt,filepath,false);
        return allright;
    }
    /**
     * 将一个列表写入文件
     * @param list
     * @param filepath
     * @return
     */
    public static boolean WriteList2File(Set list,String filepath)
    {
        String txt="";
        for(Object strobj:list)
        {
            String str=strobj.toString();
            txt+=str+"\r\n";
        }
        boolean allright=Write2File(txt,filepath,false);
        return allright;
    }
    /**
     * 将一个对象写入文件
     * @param map
     * @param filepath
     */
    public static void WriteObj(Object map,String filepath)
    {
        try {
            FileOutputStream outStream = new FileOutputStream(filepath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
            objectOutputStream.writeObject(map);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从一个文件中得到列表
     * @param filepath
     * @return
     */
    public static List GetListFromFile(String filepath)
    {
        List<String> list=new ArrayList<String>();
        String txt=readfile(filepath);
        String[] li=txt.split("\r\n");
        list= Arrays.asList(li);
        return list;
    }

    public static void main(String[] args) {
        String file=Filebases.readfile("M:\\program\\cip\\DOCOMO\\program\\resource\\validcategory.txt");
        System.out.println(file);
    }
    public static Set GetSetFromFile(String filepath)
    {
        List<String> list=new ArrayList<String>();
        String txt=readfile(filepath);
        String[] li=txt.split("\r\n");
        list= Arrays.asList(li);
        return new HashSet<String>(list);
    }
}
