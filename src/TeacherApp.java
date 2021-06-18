
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author TheSaddestBread
 */
public class TeacherApp extends MainApp {
    int studentIndex = 0;
    int currentID = 0;
    int maxIndex;
    public TeacherApp(String username) throws Exception {
        super(username);
        maxIndex = studentdata.returnStudentSize();
    }

    void changeIndex(int increment)
    {
        studentIndex += increment;
        if(studentIndex >= maxIndex)
        {
            studentIndex = 0;
        }
        if (studentIndex < 0)
        {
            studentIndex = maxIndex - 1;
        }
    }

    void updateText(int id)
    {
        switch(id)
        {
            case 1:
                updateStudentDataText();
                break;
            case 2:
                updateStudentFeedback();
                break;
            case 3:
                updateStudentPolls();
                break;
        }
    }


    void InputField() throws FileNotFoundException
    {
        //Subject, then the grade, then the feedback.
        InputApp Input = new InputApp();
        switch (currentID)
        {
            case 1:
                Input.GradeChange();
                String Feedback = studentdata.teacherGetStudentFeedback(studentIndex,Input.subject);
                Input.GetFeedback(Feedback);
                studentdata.ChangeStudentFeedback(studentIndex,Input.subject,Input.grade,Input.feedback);
                updateText(currentID);
                break;
            case 3:
                String[] pastcaptions = studentdata.getPolls();
                String[] newcaptions = Input.InputTitleCaption(pastcaptions[0],pastcaptions[1]);
                studentdata.changeStudentPolls(newcaptions[0],newcaptions[1]);
                updateText(currentID);
                break;

        }

    }

    @Override
    void HighlightButton(int id, String title) throws FileNotFoundException {
        super.HighlightButton(id, title);
        performActions(id);
        currentID = id;
        updateText(id);
    }

    public void AssignmentActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        // TODO add your handling code here:
        int id = 1;
        String title = "Grades";
        HighlightButton(id , title);

    }


    public void StudentPollActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        // TODO add your handling code here:
        int id = 3;
        String title = "Student Polls";
        HighlightButton(id , title);

    }

    public void FeedbackActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        // TODO add your handling code here:
        int id = 2;
        String title = "Feedback";
        HighlightButton(id , title);
    }

    public void DisagreeActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        // TODO add your handling code here:
        changeIndex(1);
        updateText(currentID);
    }

    public void AgreeActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        // TODO add your handling code here:
        changeIndex(-1);
        updateText(currentID);
    }


    public void SubmitActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        // TODO add your handling code here:
        InputField();
    }



    public void ScheduleActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
        // TODO add your handling code here:
        int id = 4;
        String title = "Schedule";
        HighlightButton(id , title);
    }
    void updateStudentFeedback()
    {
        String Display = studentdata.getStudentFeedback(studentIndex);
        if (Display.equalsIgnoreCase(""))
        {
            Display = "None...";
        }
        jTextArea2.setText(Display);

        TitleText.setText("Student: " + studentdata.getStudentName(studentIndex));
    }
    void updateStudentPolls()
    {
        String[] Display = studentdata.getPollResults();
        TitleText.setText("Question: " + Display[0]);
        String output = Display[1] + "\nStudents that agree: " + Display[2] + "\nStudents that disagree: " + Display[3] + "\nTotal Students: " + Display[4];
        jTextArea2.setText(output);

    }
    void updateStudentDataText()
    {
        String[][] Display = studentdata.getStudentAssignment(studentIndex);
        String output = "";
        double averagesum = 0;

        int index = 0;
        for (int y = 0; y < Display.length; y++)
        {
            for (int x = 0; x < Display[y].length; x++)
            {
                switch (x)
                {
                    case 0:
                        output += Display[y][x] + ": ";
                        break;
                    case 1:
                        output += Display[y][x] + "\n";
                        break;
                    case 2:
                        String feedback = "none";
                        if(!Display[y][x].equalsIgnoreCase(""))
                        {
                            feedback = Display[y][x];
                        }
                        output += "Feedback: " + feedback + "\n\n";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + x);
                }
                if(x == 1)
                {
                    averagesum += Double.valueOf(Display[y][x]);
                    index++;
                }
            }
        }
        int average = (int) Math.round(averagesum/index);
        output += "Average: " + average;
        jTextArea2.setText(output);

        TitleText.setText("Student: " + studentdata.getStudentName(studentIndex));
    }

    void performActions(int id) throws FileNotFoundException {
        LargeBody.setVisible(false);
        jTextArea1.setVisible(false);
        TitleText.setVisible(true);
        Agree.setVisible(true);
        Disagree.setVisible(true);
        gradesText.setVisible(false);
        jTextArea2.setVisible(true);
        jScrollPane2.setVisible(true);

        Submit.setText("Edit");
        Agree.setText("<-");
        Disagree.setText("->");

        switch(id)
        {
            case 1:
                updateStudentDataText();
                Submit.setVisible(true);

                break;
            case 2:
                TitleText.setText("All Student Feedback");
                Agree.setVisible(true);
                Disagree.setVisible(true);
                Submit.setVisible(false);

                updateStudentFeedback();
                break;
            case 3:
                TitleText.setText("Student Polls");
                Agree.setVisible(false);
                Disagree.setVisible(false);
                Submit.setVisible(true);

                break;
        }
    }
}
