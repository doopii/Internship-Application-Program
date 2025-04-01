/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import adt.*;
import entity.*;

public class InitialiserJava {
    
    public ListInterface<Applicant> initializeApplicants() {
        ListInterface<Applicant> applicantList = new LinkedList<>();
        applicantList.add(new Applicant("A001", "Gary Tee","0128273661","gary@gmail.com", "Python"));
        applicantList.add(new Applicant("A002", "Wong Ning Ning","0139222345","ningwong@gmail.com", "SQL"));
        applicantList.add(new Applicant("A003", "Ahmad Firdaus","0198364895","firdaus@gmail.com", "Java"));
        applicantList.add(new Applicant("A004", "Tan Mei Ling","0139466678","meiling@gmail.com", "SQL"));
        applicantList.add(new Applicant("A005", "Syafiq Hassan","0120037452","syafiq@gmail.com", "Python"));
        applicantList.add(new Applicant("A006", "Lim Wei Jie","0193757112","weijie@gmail.com", "Java"));
        applicantList.add(new Applicant("A007", "Siti Aisyah","0188836781","aisyah@gmail.com", "Python"));
        applicantList.add(new Applicant("A008", "Rajesh Kumar","0103874613","rajesh.kumar@gmail.com", "Java"));
        applicantList.add(new Applicant("A009", "Nurul Ain","0178263312","nurul@gmail.com", "Python"));
        applicantList.add(new Applicant("A010", "Muhammad Hafiz","0192878233","hafizmuhammad@gmail.com", "SQL"));
        applicantList.add(new Applicant("A011", "Chong Kai Lun","0163321789","kailunchong@gmail.com", "SQL"));
        applicantList.add(new Applicant("A012", "Hafizah Zainal","0176623451","hafizah@gmail.com", "Python"));
        applicantList.add(new Applicant("A013", "Kelvin Tan","0147892334","kelvin@gmail.com", "Java"));
        applicantList.add(new Applicant("A014", "Yap Zhi Hui","0189904123","zhihui@gmail.com", "Python"));
        applicantList.add(new Applicant("A015", "Ariff Hakim","0134456778","ariff@gmail.com", "SQL"));
        applicantList.add(new Applicant("A016", "Goh Siew Lan","0126678899","siewlan@gmail.com", "Java"));
        applicantList.add(new Applicant("A017", "Faizal Rahman","0191234567","faizalrahman@gmail.com", "Java"));
        applicantList.add(new Applicant("A018", "Vijay Subramaniam","0175554443","vijay@gmail.com", "Python"));
        applicantList.add(new Applicant("A019", "Lee Wei Han","0118992345","weihan@gmail.com", "Java"));
        applicantList.add(new Applicant("A020", "Aida Roslan","0186654321","aida@gmail.com", "SQL"));
        applicantList.add(new Applicant("A021", "Kavitha Muniandy","0137782234","kavitha@gmail.com", "Python"));
        applicantList.add(new Applicant("A022", "Daniel Ng","0124456777","daniel@gmail.com", "Java"));
        applicantList.add(new Applicant("A023", "Zainab Abdullah","0193344556","zainab@gmail.com", "SQL"));
        applicantList.add(new Applicant("A024", "Haris Johari","0177788991","haris@gmail.com", "Python"));
        applicantList.add(new Applicant("A025", "Samantha Lau","0145566778","samantha@gmail.com", "Java"));
        applicantList.add(new Applicant("A026", "Farid Ismail","0189988776","farid@gmail.com", "SQL"));
        applicantList.add(new Applicant("A027", "Liyana Hassan","0165544332","liyana@gmail.com", "Python"));
        applicantList.add(new Applicant("A028", "Chin Wei Sheng","0136655442","weisheng@gmail.com", "Java"));
        applicantList.add(new Applicant("A029", "Shalini Raj","0197766554","shalini@gmail.com", "SQL"));
        applicantList.add(new Applicant("A030", "Hakim Zulkarnain","0123344556","hakim@gmail.com", "Python"));
        
        return applicantList;
    }

  public ListInterface<Interview> initializeInterviews() {
        ListInterface<Interview> interviewList = new LinkedList<>();
        interviewList.add(new Interview("J001", "S001", "I001", "Developer", "20-02-2025"));
        interviewList.add(new Interview("J002", "S002", "I002", "Tester", "20-02-2025"));
        interviewList.add(new Interview("J003", "S003", "I003", "Designer", "21-02-2025"));
        interviewList.add(new Interview("J003", "S004", "I004", "IT Support", "21-02-2025"));
        interviewList.add(new Interview("J004", "S005", "I005", "Developer", "22-02-2025"));
        interviewList.add(new Interview("J005", "S006", "I006", "Support specialist", "22-02-2025"));
        
        return interviewList;
    }
}
