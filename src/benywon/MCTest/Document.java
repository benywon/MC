package benywon.MCTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benywon on 2015/10/24.
 */
public class Document implements Serializable
{
    private static final long serialVersionUID = -5809382578272943999L;
    private int docID;
    private String document;
    private List<Question> questions=new ArrayList<Question>();

    //*****************************************************//


    public String getDocument()
    {
        return document;
    }

    public void setDocument(String document)
    {
        this.document = document;
    }

    public List<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }

    public int getDocID()
    {
        return docID;
    }

    public void setDocID(int docID)
    {
        this.docID = docID;
    }
    public void setonequestion(List<String> q)
    {
        Question question=new Question();
        int size=this.questions.size();
        question.setQuestionID(size);
        String questionstr=q.get(0);
        //multiple: What color were the most ducklings?

    }


    public void setquestionAasnwer(List<String> questionAanswer)
    {
        int len=questionAanswer.size();
        for (int i = 0,j=0; i <len; i+=5,j++)
        {
            Question question=new Question();
            List<String> questionone=questionAanswer.subList(i,i+5);
            question.setthisquestion(questionone,j);
            this.questions.add(question);
        }
    }
}
