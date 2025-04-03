/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import adt.*;
import entity.*;

public class InitialiserJava {
    
    private static DoublyListInterface<Applicant> applicantList;
    private static DoublyListInterface<Skill> skillList;
    private static DoublyListInterface<JobDesired> jobDesiredList;
    
    private static boolean isInitialized = false;

    public void initializeData() {
        if (isInitialized) {
                System.out.println(" ~~~ Data already initialized ~~~ ");
                return;
            }

        applicantList = new DoublyLinkedList<>();
        skillList = new DoublyLinkedList<>();
        jobDesiredList = new DoublyLinkedList<>();

         
    
   } 
    
//    DoublyListInterface<Skill> skillList = initializeSkills();  
//    DoublyListInterface<JobDesired> jobDesiredList = initializeJobDesired();  
//    
//    public DoublyListInterface<Applicant> initializeApplicants() {
//        DoublyListInterface<Applicant> applicantList = new DoublyLinkedList<>();
//        applicantList.add(new Applicant("A001", "Gary Tee","0128273661","gary@gmail.com", 
//                new Skill(),
//                new JobDesired()));
//        
//        
//        applicantList.add(new Applicant("A002", "Wong Ning Ning","0139222345","ningwong@gmail.com", ""));
//        applicantList.add(new Applicant("A003", "Ahmad Firdaus","0198364895","firdaus@gmail.com", ""));
//        applicantList.add(new Applicant("A004", "Tan Mei Ling","0139466678","meiling@gmail.com", ""));
//        applicantList.add(new Applicant("A005", "Syafiq Hassan","0120037452","syafiq@gmail.com", ""));
//        applicantList.add(new Applicant("A006", "Lim Wei Jie","0193757112","weijie@gmail.com", ""));
//        applicantList.add(new Applicant("A007", "Siti Aisyah","0188836781","aisyah@gmail.com", ""));
//        applicantList.add(new Applicant("A008", "Rajesh Kumar","0103874613","rajesh.kumar@gmail.com", ""));
//        applicantList.add(new Applicant("A009", "Nurul Ain","0178263312","nurul@gmail.com", ""));
//        applicantList.add(new Applicant("A010", "Muhammad Hafiz","0192878233","hafizmuhammad@gmail.com", ""));
//
//        
//        return applicantList;
//    }
//    
//    public DoublyListInterface<Skill> initializeSkills() {
//        DoublyListInterface<Skill> skillList = new DoublyLinkedList<>();
//        // Programming category
//        skillList.add(new Skill("Programming", "Java", "Advanced", 3.7));
//        skillList.add(new Skill("Programming", "Python", "Intermediate", 3.4));
//        skillList.add(new Skill("Programming", "C++", "Beginner", 3.5));
//
//        // Design category
//        skillList.add(new Skill("Design", "Photoshop", "Advanced", 3.8));
//        skillList.add(new Skill("Design", "Video Editing", "Intermediate", 3.3));
//        skillList.add(new Skill("Design", "Illustrator", "Beginner", 3.2));
//        
//        return skillList;
//    }
//    
//    public DoublyListInterface<JobDesired> initializeJobDesired() {
//        DoublyListInterface<JobDesired> jobDesiredList = new DoublyLinkedList<>();
//        jobDesiredList.add(new JobDesired("IT", "Software Engineer"));
//        jobDesiredList.add(new JobDesired("IT", "Data Analyst"));
//        jobDesiredList.add(new JobDesired("Multimedia", "Graphic Designer"));
//        jobDesiredList.add(new JobDesired("Multimedia", "Video Editor"));
//  
//        return jobDesiredList;
//    }
//    
//    
//    
//    public DoublyListInterface<Interview> initializeInterviews() {
//        DoublyListInterface<Interview> interviewList = new DoublyLinkedList<>();
//        interviewList.add(new Interview("J001", "S001", "I001", "Developer", "20-02-2025"));
//        interviewList.add(new Interview("J002", "S002", "I002", "Tester", "20-02-2025"));
//        interviewList.add(new Interview("J003", "S003", "I003", "Designer", "21-02-2025"));
//        interviewList.add(new Interview("J003", "S004", "I004", "IT Support", "21-02-2025"));
//        interviewList.add(new Interview("J004", "S005", "I005", "Developer", "22-02-2025"));
//        interviewList.add(new Interview("J005", "S006", "I006", "Support specialist", "22-02-2025"));
//        
//        return interviewList;
//    }
    
    
    
    
}
