import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class DataManager {
    private static FileWriter file;
    JSONObject jo;
    Map passjo;
    JSONObject appjo;

    DataManager() throws Exception {
        Object obj = new JSONParser().parse(new FileReader("src/data/ProgramData.json"));
        jo = (JSONObject) obj;
        Object PassObj = new JSONParser().parse(new FileReader("src/data/PasswordData.json"));
        passjo = (Map) PassObj;
        Object AppObj = new JSONParser().parse(new FileReader("src/data/AppData.json"));
        appjo = (JSONObject) AppObj;
    }

    public String getFeedback(String username) throws FileNotFoundException
    {
        Map students = (Map) jo.get("students");
        JSONObject ob = (JSONObject) students.get(username);
        String feedback = (String) ob.get("StudentFeedback");
        return feedback;
    }

    public String teacherGetStudentFeedback(int id, String subject) throws FileNotFoundException
    {
        JSONArray names = (JSONArray) appjo.get("students");
        String Key = (String) names.get(id);

        Map students = (Map) jo.get("students");
        JSONObject ob = (JSONObject) students.get(Key);
        JSONArray grades = (JSONArray) ob.get("grades");
        String feedback = "";
        for (int i = 0; i < grades.size(); i++)
        {
            JSONObject subjectOb = (JSONObject) grades.get(i);
            try {
                if(subjectOb.get(subject) != null)
                {
                    feedback = (String)subjectOb.get("Feedback");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("List: " + grades);
        return feedback;
    }
    public void ChangeStudentFeedback(int id, String subject, int grade, String Feedback) throws FileNotFoundException {

        JSONArray names = (JSONArray) appjo.get("students");
        String Key = (String) names.get(id);

        Map students = (Map) jo.get("students");
        JSONObject ob = (JSONObject) students.get(Key);
        JSONArray grades = (JSONArray) ob.get("grades");
        for (int i = 0; i < grades.size(); i++)
        {
            JSONObject subjectOb = (JSONObject) grades.get(i);
            try {
                if(subjectOb.get(subject) != null)
                {
                    subjectOb.put(subject,grade);
                    subjectOb.put("Feedback",Feedback);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        PrintWriter pw = new PrintWriter("src/data/ProgramData.json");
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
    int returnStudentSize()
    {
        Map students = (Map) jo.get("students");
        return students.size();
    }

    public void changeStudentPolls(String Title, String Caption) throws FileNotFoundException {

        ((JSONObject)appjo.get("Poll")).put("question",Title);
        ((JSONObject)appjo.get("Poll")).put("caption",Caption);

        PrintWriter pw = new PrintWriter("src/data/AppData.json");
        pw.write(appjo.toJSONString());
        pw.flush();
        pw.close();
    }

    void changeFeedback(String username, String Feedback) throws FileNotFoundException {
        Map students = (Map) jo.get("students");
        JSONObject ob = (JSONObject) students.get(username);
        ob.put("StudentFeedback",Feedback);
        PrintWriter pw = new PrintWriter("src/data/ProgramData.json");
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
    void pollAgree(String username) throws FileNotFoundException {
        Map students = (Map) jo.get("students");
        JSONObject ob = (JSONObject) students.get(username);
        ob.put("Poll","1");
        PrintWriter pw = new PrintWriter("src/data/ProgramData.json");
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
    void pollDisgree(String username) throws FileNotFoundException
    {
        Map students = (Map) jo.get("students");
        JSONObject ob = (JSONObject) students.get(username);
        ob.put("Poll","0");
        PrintWriter pw = new PrintWriter("src/data/ProgramData.json");
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
    String[] getPolls()
    {
        String[] Output = new String[2];
        Output[0] = (String) ((JSONObject)appjo.get("Poll")).get("question");
        Output[1] = (String) ((JSONObject)appjo.get("Poll")).get("caption");
       return Output;
    }
    String[][] getAssignments(String username)
    {
        ArrayList<ArrayList<String>> Unit_Grade = new ArrayList<ArrayList<String>>();
        Map students = (Map)jo.get("students");
        JSONObject ob = (JSONObject) students.get(username);
        JSONArray grades = (JSONArray) ob.get("grades");
        Iterator itr = grades.iterator();
        while (itr.hasNext())
        {
            Iterator<Map.Entry> itr1;
            itr1 = ((Map) itr.next()).entrySet().iterator();
            ArrayList<String> x = new ArrayList<String>();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                if(!pair.getKey().equals("Feedback"))
                {
                    x.add(0,(String) pair.getKey());
                }
                if(isNumeric(String.valueOf(pair.getValue()))) {
                    x.add(1,String.valueOf(pair.getValue()));
                }
                else
                {
                    x.add(String.valueOf(pair.getValue()));
                }

            }
            Unit_Grade.add(x);
        }

        String[][] stringArray = Unit_Grade.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return stringArray;
    }
    String getStudentName(int id)
    {
        JSONArray names = (JSONArray) appjo.get("students");
        String Key = (String) names.get(id);
        return Key;
    }
    String getStudentFeedback(int id)
    {
        JSONArray names = (JSONArray) appjo.get("students");
        String Key = (String) names.get(id);

        ArrayList<ArrayList<String>> Unit_Grade = new ArrayList<ArrayList<String>>();
        Map students = (Map)jo.get("students");
        JSONObject ob = (JSONObject) students.get(Key);
        String feedback = (String)ob.get("StudentFeedback");

        return feedback;
    }

    String[][] getStudentAssignment(int id)
    {
        JSONArray names = (JSONArray) appjo.get("students");
        String Key = (String) names.get(id);

        ArrayList<ArrayList<String>> Unit_Grade = new ArrayList<ArrayList<String>>();
        Map students = (Map)jo.get("students");
        JSONObject ob = (JSONObject) students.get(Key);
        JSONArray grades = (JSONArray) ob.get("grades");
        Iterator itr = grades.iterator();
        while (itr.hasNext())
        {
            Iterator<Map.Entry> itr1;
            itr1 = ((Map) itr.next()).entrySet().iterator();
            ArrayList<String> x = new ArrayList<String>();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                if(!pair.getKey().equals("Feedback"))
                {
                    x.add(0,(String) pair.getKey());
                }
                if(isNumeric(String.valueOf(pair.getValue()))) {
                    x.add(1,String.valueOf(pair.getValue()));
                }
                else
                {
                    x.add(String.valueOf(pair.getValue()));
                }

            }
            Unit_Grade.add(x);
        }

        String[][] stringArray = Unit_Grade.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return stringArray;
    }
    String[] getPollResults()
    {
        JSONArray names = (JSONArray) appjo.get("students");
        int agreeSum = 0;
        int disagreeSum = 0;
        String title = (String) ((JSONObject)appjo.get("Poll")).get("question");
        String caption = (String) ((JSONObject)appjo.get("Poll")).get("caption");
        Map students = (Map)jo.get("students");

        for (int i = 0; i < names.size(); i++)
        {
            String Key = (String) names.get(i);
            //Change the id from index to string to get result.
            JSONObject ob = (JSONObject) students.get(Key);
            int Pollresponse =  Integer.valueOf((String) ob.get("Poll"));

            if(Pollresponse == 1)
            {
                agreeSum += 1;
            }
            if(Pollresponse == 0)
            {
                disagreeSum += 1;
            }
        }

        String[] output = {title,caption,String.valueOf(agreeSum),String.valueOf(disagreeSum),String.valueOf(names.size())};
        return output;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;

    }
    public int ReturnPassword(String username, String Password)
    {
        JSONObject studentbody = ((JSONObject) passjo.get("student"));
        JSONObject teacherbody = ((JSONObject) passjo.get("teacher"));

        boolean validStudent = studentbody.containsKey(username);
        boolean validTeacher = teacherbody.containsKey(username);
        if(validStudent) {
            JSONObject studentob = ((JSONObject) studentbody.get(username));
            if (studentob.get("password").equals(Password)) {
                return 1; // success for a student
            }
            else {
                return 3; // invalid password
            }
        }
        else if(validTeacher) {
            JSONObject teacherob = ((JSONObject) teacherbody.get(username));
            if (teacherob.get("password").equals(Password)) {
                return 2; // success for a teacher
            }
            else {
                return 3; // invalid password
            }
        }
        else{
            return 0; // invalid username
        }
    }
}