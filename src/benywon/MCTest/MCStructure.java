package benywon.MCTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benywon on 2015/10/24.
 */
public class MCStructure implements Serializable
{
    public int MC_id;//表征是mctest500 或者是mc200
    public String Purpose;//是test 还是train 还是dev
    //**************************************//

    public List<Document> documents=new ArrayList<Document>();

    public MCStructure(List<String> list)
    {
        for(String str:list)
        {
            Document document = new Document();
            String[] div = str.split("\t");
            int number=Integer.parseInt(div[0].split("\\.")[2]);
            document.setDocID(number);
            document.setDocument(div[2]);
            //from now to the end of line these fields are q&a so we do it separately
            int len=div.length;
            List<String> questionAanswer=new ArrayList<String>();
            for(int i=3;i<len;i++)
            {
                questionAanswer.add(div[i]);
            }
            document.setquestionAasnwer(questionAanswer);
            this.documents.add(document);
        }

    }

    public void setallAnswers(List<String> answers)
    {
        int id=0;
        for(String onedoc:answers)
        {
            Document doc=this.documents.get(id);
            id++;
            String[] fouranswer=onedoc.split("\t");
            for (int i = 0; i < fouranswer.length; i++)
            {
                int ans=returnint(fouranswer[i]);
                doc.getQuestions().get(i).getAnswer().get(ans).setIsAnswer(true);
            }
        }
    }

    public static void main(String[] args)
    {
        String ii="D";
        System.out.println(ii.compareTo("A"));
    }
    private int returnint(String in)
    {
       return in.compareTo("A");
    }
}
