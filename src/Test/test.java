package Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by benywon on 2015/10/28.
 */
public class test
{
    public static void main(String[] args)
    {
        String str="2015-10-3116:30";
        String regEx="(\\d{4}-\\d{2}-\\d{2}).{3,6}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        if(matcher.find())
        {
            String dstr = matcher.group(1);
            System.out.println(dstr);
        }

        System.out.println(1.0/20);

    }
}
