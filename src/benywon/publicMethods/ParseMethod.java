package benywon.publicMethods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by benywon on 2015/10/24.
 */
public class ParseMethod
{
    /**
     * 从一个string里面找到一个数字
     * @param str 输入string
     * @return 返回整形数字 要是是没有找到就是-1
     */
    public static int FindNumFromStr(String str)
    {
        String regEx="(\\d+)|(一)|(二|两)|(三)|(四)|(五)|(六)|(七)|(八)|(九)|(十)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        if(!matcher.find()){
            return  -1;
        }
        else
        {
            String out = matcher.group();
            if(matcher.group(1)!=null)
            {
                return Integer.parseInt(out);
            }
            else
            {
                for(int i=2;i<11;i++)
                {
                    if(matcher.group(i)!=null)
                    {
                        return i-1;
                    }
                }
                return -1;
            }
        }
    }
    /**
     * 从一个string里面找到两个数字
     * @param str 输入string
     * @param mode 是分隔符
     * @return 返回整形数字 要是是没有找到就是-1
     */
    public static int[] FindNumFromStr2(String str,String mode)
    {
        int base=1;
        if(str.contains("百"))
        {
            base=100;
        }
        else if(str.contains("千"))
        {
            base=1000;
        }
        else if(str.contains("万"))
        {
            base=10000;
        }
        int[] num2=new int[2];
        String regEx="(\\d+)"+mode+"(\\d+)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        if(!matcher.find()){
            return  null;
        }
        else
        {
            num2[0]=Integer.parseInt(matcher.group(1))*base;
            num2[1]=Integer.parseInt(matcher.group(2))*base;
            return  num2;
        }
    }
}
