package benywon.publicMethods;

import java.util.*;

/**
 * Created by benywon on 2015/11/24.
 */
public class StringUtils
{
    /**
     * caculate the itf per doc
     * if a word occurs frequently in a doc,then this words IC(w) shall be small
     * @param document
     * @return
     */
    public static Map<String,Double> getDocumentITF(String document)
    {
        Map<String,Double> map=new HashMap<String, Double>();
        Map<String,Integer> tfmap=getDocumentTF(document);
        for(Map.Entry<String, Integer> entry:tfmap.entrySet())
        {
            String word=entry.getKey();
            int number=entry.getValue();
            double itf=Math.log(1 + 1./number);
            map.put(word,itf);
        }
        return map;
    }

    public static List<String> getLeximaList(String in)
    {
        List<String> list=new ArrayList<String>();
        String[] strArray=in.split(" ");
        for(String str:strArray)
        {
            if(str.equals(""))
            {
                continue;
            }
            list.add(transferWord(str));
        }
        return list;
    }

    public static Map<String,Integer> getDocumentTF(String document)
    {
        Map<String,Integer> tfmap=new HashMap<String, Integer>();
        String[] strArray=document.split(" ");
        for(String string:strArray)
        {
            String word=transferWord(string);
            if(!tfmap.containsKey(word))
            {
                tfmap.put(word,1);
            }
            else
            {
                int number=tfmap.get(word);
                tfmap.put(word,number+1);
            }
        }
        return tfmap;
    }
    public static String transferWord(String word)
    {
        word=word.toLowerCase();
        word=stripComma(word);
        word=stripPeriod(word);
        word=stripnoise(word);
        word=stripQmark(word);
        return word;
    }
    private static String stripQmark(String word)
    {
        if(word.contains("?"))
        {
            return word.replaceAll("\\?","");
        }
        return word;
    }
    private static String stripComma (String word)
    {
        if(word.contains(","))
        {
            return word.replaceAll(",","");
        }
        return word;
    }
    private static String stripPeriod(String word)
    {
        if(word.contains("."))
        {
            return word.replaceAll("\\.","");
        }
        return word;
    }
    private static String stripnoise(String word)
    {
        if(word.contains("'s"))
        {
            return word.replaceAll("'s","");
        }
        return word;
    }
    public static Set<String> getLeximaFromString(String in)
    {
        Set<String> set=new HashSet<String>();
        String[] strArray=in.split(" ");
        for(String str:strArray)
        {
            if(str.equals(""))
            {
                continue;
            }
            set.add(transferWord(str));
        }
        return set;
    }

    /**
     * 从两个set中找到公共的集合
     * @param set1 应该是比较小的那个
     * @param set2 应该是比较大的那个
     * @return
     */
    public static Set<String> commonWordsSet(Set<String> set1,Set<String> set2)
    {
        Set<String> set=new HashSet<String>();
        for(String s1:set1)
        {
            if(set2.contains(s1))
            {
                set.add(s1);
            }
        }
        return set;
    }
    public static Set<String> wordsetInSet1ButNotInSet2(Set<String> set1,Set<String> set2)
    {
        Set<String> set=new HashSet<String>();
        for(String s1:set1)
        {
            if(!set2.contains(s1))
            {
                set.add(s1);
            }
        }
        return set;
    }
}
