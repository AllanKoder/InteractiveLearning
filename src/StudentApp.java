import java.io.FileNotFoundException;
/**
 *
 * @author TheSaddestBread
 */
public class StudentApp extends MainApp {
    public StudentApp(String username) throws Exception {
        super(username);
    }

    @Override
    void HighlightButton(int id, String title) throws FileNotFoundException {
        super.HighlightButton(id, title);
        performActions(id);
    }

    void performActions(int id) throws FileNotFoundException {
        switch(id)
        {
            case 1:
                LargeBody.setVisible(false);
                jTextArea1.setVisible(false);
                TitleText.setVisible(true);
                Agree.setVisible(false);
                Disagree.setVisible(false);
                gradesText.setVisible(false);
                Submit.setVisible(false);
                jTextArea2.setVisible(true);
                jScrollPane2.setVisible(true);

                String[][] Display = studentdata.getAssignments(Username);
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
                TitleText.setText("Feedback and Grades");
                jTextArea2.setText(output);
                break;
            case 2:
                LargeBody.setVisible(true);
                jTextArea1.setVisible(true);
                TitleText.setVisible(true);
                Agree.setVisible(false);
                Disagree.setVisible(false);
                gradesText.setVisible(false);
                Submit.setVisible(true);
                jTextArea2.setVisible(false);
                jScrollPane2.setVisible(false);
                Submit.setText("Submit");
                jTextArea1.setText(studentdata.getFeedback(Username));
                TitleText.setText("Submit Questions/Feedback to your Teacher");

                break;
            case 3:
                LargeBody.setVisible(false);
                jTextArea1.setVisible(false);
                TitleText.setVisible(true);
                Agree.setVisible(true);
                Disagree.setVisible(true);
                gradesText.setVisible(true);
                Submit.setVisible(false);
                jTextArea2.setVisible(false);
                jScrollPane2.setVisible(false);
                Agree.setText("Agree");
                Disagree.setText("Disagree");
                String[] PollsOutput = studentdata.getPolls();
                TitleText.setText(PollsOutput[0]);
                gradesText.setText(PollsOutput[1]);
                break;
        }
    }
}
