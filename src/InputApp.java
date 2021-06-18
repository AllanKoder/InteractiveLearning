import javax.swing.*;

public class InputApp {
    public JFrame f1;
    public JFrame f2;
    public JFrame inputSubject;
    public int grade;
    public String feedback;
    public String subject;
    public void GradeChange() {
        inputSubject=new JFrame();
        subject=JOptionPane.showInputDialog(inputSubject,"Enter Subject (Case Sensitive)");
        grade = getInt(100);
    }
    public void GetFeedback(String InitialValue)
    {
        f2=new JFrame();
        feedback=JOptionPane.showInputDialog(f1,"Enter Feedback",InitialValue);
    }
    public String[] InputTitleCaption(String title, String caption)
    {
        String tit = JOptionPane.showInputDialog(inputSubject,"Enter New Poll Question", title);
        String cap = JOptionPane.showInputDialog(inputSubject,"Enter New Poll Caption", caption);
        String[] text = new String[]{tit,cap};
        return text;
    }
    Integer getInt(int max)
    {
        f1=new JFrame();
        String gradeText=JOptionPane.showInputDialog(f1,"Enter Grade (0-" + max + ")");
        int IntGrade = 0;
        try {
            IntGrade = Integer.valueOf(gradeText);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            getInt(max);
        }
        if(IntGrade < 0 || IntGrade > max)
        {
            getInt(max);
        }
        return IntGrade;
    }

}