package benywon.MCTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by benywon on 2015/10/24.
 */
public class Question implements Serializable
{
    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public boolean isMultiple()
    {
        return IsMultiple;
    }

    public void setIsMultiple(boolean isMultiple)
    {
        IsMultiple = isMultiple;
    }

    public List<Answer> getAnswer()
    {
        return answer;
    }

    public void setAnswer(List<Answer> answer)
    {
        this.answer = answer;
    }

    private String question;

    public int getQuestionID()
    {
        return questionID;
    }

    public void setQuestionID(int questionID)
    {
        this.questionID = questionID;
    }

    private int questionID;
    private boolean IsMultiple;//
    private List<Answer> answer=new ArrayList<Answer>();
    public void setPredicAnswer(int id)
    {
        for(Answer answer:this.answer)
        {
            if(answer.getId()==id)
            {
                answer.setPredictResult(true);
                break;
            }
        }
    }
    public void setthisquestion(List<String> questionone,int questionID)
    {
        this.setQuestionID(questionID);
        String q=questionone.get(0);
        String qtype=q.split(":")[0];
        if(qtype.equals("multiple"))
        {
            setIsMultiple(true);
        }
        else
        {
            setIsMultiple(false);
        }
        setQuestion(q.split(":")[1]);
        for (int i = 1; i <questionone.size() ; i++)
        {
            Answer answer=new Answer(questionone.get(i),i);
            this.answer.add(answer);
        }
    }
}
