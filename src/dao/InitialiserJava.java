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

        // Programming
        Skill s1 = new Skill("Programming", "Java", "Beginner");
        skillList.add(s1);
        Skill s2 = new Skill("Programming", "Java", "Intermediate");
        skillList.add(s2);
        Skill s3 = new Skill("Programming", "Java", "Advanced");
        skillList.add(s3);

        Skill s4 = new Skill("Programming", "Python", "Beginner");
        skillList.add(s4);
        Skill s5 = new Skill("Programming", "Python", "Intermediate");
        skillList.add(s5);
        Skill s6 = new Skill("Programming", "Python", "Advanced");
        skillList.add(s6);

        Skill s7 = new Skill("Programming", "C++", "Beginner");
        skillList.add(s7);
        Skill s8 = new Skill("Programming", "C++", "Intermediate");
        skillList.add(s8);
        Skill s9 = new Skill("Programming", "C++", "Advanced");
        skillList.add(s9);

        // Design
        Skill s10 = new Skill("Design", "Photoshop", "Beginner");
        skillList.add(s10);
        Skill s11 = new Skill("Design", "Photoshop", "Intermediate");
        skillList.add(s11);
        Skill s12 = new Skill("Design", "Photoshop", "Advanced");
        skillList.add(s12);

        Skill s13 = new Skill("Design", "Video Editing", "Beginner");
        skillList.add(s13);
        Skill s14 = new Skill("Design", "Video Editing", "Intermediate");
        skillList.add(s14);
        Skill s15 = new Skill("Design", "Video Editing", "Advanced");
        skillList.add(s15);

        Skill s16 = new Skill("Design", "Illustrator", "Beginner");
        skillList.add(s16);
        Skill s17 = new Skill("Design", "Illustrator", "Intermediate");
        skillList.add(s17);
        Skill s18 = new Skill("Design", "Illustrator", "Advanced");
        skillList.add(s18);


        // Category: Computer Science
        JobDesired jd1 = new JobDesired("Computer Science", "Software Engineer");
        JobDesired jd2 = new JobDesired("Computer Science", "Data Analyst");
        JobDesired jd5 = new JobDesired("Computer Science", "AI Developer");

        // Category: Multimedia
        JobDesired jd3 = new JobDesired("Multimedia", "Graphic Designer");
        JobDesired jd4 = new JobDesired("Multimedia", "Video Editor");
        JobDesired jd6 = new JobDesired("Multimedia", "Animator");
        
        jobDesiredList.add(jd1);
        jobDesiredList.add(jd2);
        jobDesiredList.add(jd3);
        jobDesiredList.add(jd4);
        jobDesiredList.add(jd5);
        jobDesiredList.add(jd6);

        DoublyLinkedList<Skill> skillList20 = new DoublyLinkedList<>();
        skillList20.add(s2);
        skillList20.add(s4);
        skillList20.add(s3);
        DoublyLinkedList<JobDesired> jobList20 = new DoublyLinkedList<>();
        jobList20.add(jd6);
        jobList20.add(jd2);
        applicantList.add(new Applicant("A001", "Kavitha Devi", "01137468493", "kavitha.devi@gmail.com", "Selangor", 3.4, skillList20, jobList20));
        
        DoublyLinkedList<Skill> skillList1 = new DoublyLinkedList<>();
        skillList1.add(s2);
        skillList1.add(s1);
        skillList1.add(s6);
        DoublyLinkedList<JobDesired> jobList1 = new DoublyLinkedList<>();
        jobList1.add(jd4);
        applicantList.add(new Applicant("A002", "Wong Ning Ning", "01670772704", "ningwong@gmail.com", "Selangor", 3.8, skillList1, jobList1));

        DoublyLinkedList<Skill> skillList2 = new DoublyLinkedList<>();
        skillList2.add(s5);
        skillList2.add(s6);
        DoublyLinkedList<JobDesired> jobList2 = new DoublyLinkedList<>();
        jobList2.add(jd4);
        applicantList.add(new Applicant("A003", "Ahmad Firdaus", "01276128047", "firdaus@gmail.com", "Johor", 3.9, skillList2, jobList2));

        DoublyLinkedList<Skill> skillList3 = new DoublyLinkedList<>();
        skillList3.add(s5);
        skillList3.add(s3);
        DoublyLinkedList<JobDesired> jobList3 = new DoublyLinkedList<>();
        jobList3.add(jd4);
        jobList3.add(jd6);
        applicantList.add(new Applicant("A004", "Tan Mei Ling", "01388699038", "meiling@gmail.com", "Selangor", 3.8, skillList3, jobList3));

        DoublyLinkedList<Skill> skillList4 = new DoublyLinkedList<>();
        skillList4.add(s5);
        skillList4.add(s4);
        skillList4.add(s1);
        DoublyLinkedList<JobDesired> jobList4 = new DoublyLinkedList<>();
        jobList4.add(jd1);
        applicantList.add(new Applicant("A005", "Syafiq Hassan", "01252722044", "syafiq@gmail.com", "Selangor", 3.7, skillList4, jobList4));

        DoublyLinkedList<Skill> skillList5 = new DoublyLinkedList<>();
        skillList5.add(s4);
        skillList5.add(s2);
        DoublyLinkedList<JobDesired> jobList5 = new DoublyLinkedList<>();
        jobList5.add(jd3);
        jobList5.add(jd2);
        applicantList.add(new Applicant("A006", "Lim Wei Jie", "01481707334", "weijie@gmail.com", "Johor", 3.6, skillList5, jobList5));

        DoublyLinkedList<Skill> skillList6 = new DoublyLinkedList<>();
        skillList6.add(s5);
        skillList6.add(s3);
        DoublyLinkedList<JobDesired> jobList6 = new DoublyLinkedList<>();
        jobList6.add(jd6);
        applicantList.add(new Applicant("A007", "Siti Aisyah", "01378255314", "aisyah@gmail.com", "Johor", 3.1, skillList6, jobList6));

        DoublyLinkedList<Skill> skillList7 = new DoublyLinkedList<>();
        skillList7.add(s6);
        skillList7.add(s5);
        skillList7.add(s2);
        DoublyLinkedList<JobDesired> jobList7 = new DoublyLinkedList<>();
        jobList7.add(jd6);
        applicantList.add(new Applicant("A008", "Rajesh Kumar", "01142088959", "rajesh.kumar@gmail.com", "Johor", 3.7, skillList7, jobList7));

        DoublyLinkedList<Skill> skillList8 = new DoublyLinkedList<>();
        skillList8.add(s6);
        skillList8.add(s2);
        DoublyLinkedList<JobDesired> jobList8 = new DoublyLinkedList<>();
        jobList8.add(jd3);
        applicantList.add(new Applicant("A009", "Nurul Ain", "01431159253", "nurul@gmail.com", "Penang", 3.8, skillList8, jobList8));

        DoublyLinkedList<Skill> skillList9 = new DoublyLinkedList<>();
        skillList9.add(s3);
        skillList9.add(s1);
        skillList9.add(s4);
        DoublyLinkedList<JobDesired> jobList9 = new DoublyLinkedList<>();
        jobList9.add(jd5);
        jobList9.add(jd3);
        applicantList.add(new Applicant("A010", "Muhammad Hafiz", "01330425166", "hafizmuhammad@gmail.com", "Penang", 3.1, skillList9, jobList9));

        DoublyLinkedList<Skill> skillList10 = new DoublyLinkedList<>();
        skillList10.add(s5);
        skillList10.add(s2);
        skillList10.add(s3);
        DoublyLinkedList<JobDesired> jobList10 = new DoublyLinkedList<>();
        jobList10.add(jd5);
        jobList10.add(jd4);
        applicantList.add(new Applicant("A011", "Chong Kai Lun", "01375237275", "ckl@gmail.com", "Penang", 3.5, skillList10, jobList10));

        DoublyLinkedList<Skill> skillList11 = new DoublyLinkedList<>();
        skillList11.add(s6);
        skillList11.add(s2);
        DoublyLinkedList<JobDesired> jobList11 = new DoublyLinkedList<>();
        jobList11.add(jd6);
        applicantList.add(new Applicant("A012", "Farah Nadia", "01464046749", "farah.nadia@gmail.com", "Penang", 3.8, skillList11, jobList11));

        DoublyLinkedList<Skill> skillList12 = new DoublyLinkedList<>();
        skillList12.add(s3);
        skillList12.add(s4);
        DoublyLinkedList<JobDesired> jobList12 = new DoublyLinkedList<>();
        jobList12.add(jd5);
        jobList12.add(jd4);
        applicantList.add(new Applicant("A013", "Ganesh Subramaniam", "01339903261", "ganesh.s@gmail.com", "Penang", 3.9, skillList12, jobList12));

        DoublyLinkedList<Skill> skillList13 = new DoublyLinkedList<>();
        skillList13.add(s3);
        skillList13.add(s5);
        skillList13.add(s1);
        DoublyLinkedList<JobDesired> jobList13 = new DoublyLinkedList<>();
        jobList13.add(jd6);
        jobList13.add(jd4);
        applicantList.add(new Applicant("A014", "Zulaikha Ismail", "01650445978", "zulaikha.i@gmail.com", "Sabah", 3.5, skillList13, jobList13));

        DoublyLinkedList<Skill> skillList14 = new DoublyLinkedList<>();
        skillList14.add(s3);
        skillList14.add(s4);
        skillList14.add(s2);
        DoublyLinkedList<JobDesired> jobList14 = new DoublyLinkedList<>();
        jobList14.add(jd2);
        applicantList.add(new Applicant("A015", "Foo Jian Hao", "01941469290", "foo.jh@gmail.com", "Sabah", 3.3, skillList14, jobList14));

        DoublyLinkedList<Skill> skillList15 = new DoublyLinkedList<>();
        skillList15.add(s6);
        skillList15.add(s5);
        skillList15.add(s3);
        DoublyLinkedList<JobDesired> jobList15 = new DoublyLinkedList<>();
        jobList15.add(jd2);
        applicantList.add(new Applicant("A016", "Nabilah Ramli", "01998043964", "nabilah.ramli@gmail.com", "Sabah", 3.4, skillList15, jobList15));

        DoublyLinkedList<Skill> skillList16 = new DoublyLinkedList<>();
        skillList16.add(s6);
        skillList16.add(s4);
        skillList16.add(s3);
        DoublyLinkedList<JobDesired> jobList16 = new DoublyLinkedList<>();
        jobList16.add(jd1);
        applicantList.add(new Applicant("A017", "Amar Daniel", "01682479596", "amar.d@gmail.com", "Sabah", 3.8, skillList16, jobList16));

        DoublyLinkedList<Skill> skillList17 = new DoublyLinkedList<>();
        skillList17.add(s6);
        skillList17.add(s4);
        skillList17.add(s3);
        DoublyLinkedList<JobDesired> jobList17 = new DoublyLinkedList<>();
        jobList17.add(jd3);
        applicantList.add(new Applicant("A018", "Chong Mei Yen", "01388130563", "mei.yen@gmail.com", "Kuala Lumpur", 3.7, skillList17, jobList17));

        DoublyLinkedList<Skill> skillList18 = new DoublyLinkedList<>();
        skillList18.add(s3);
        skillList18.add(s1);
        DoublyLinkedList<JobDesired> jobList18 = new DoublyLinkedList<>();
        jobList18.add(jd6);
        jobList18.add(jd5);
        applicantList.add(new Applicant("A019", "Ravi Chandran", "01471487421", "ravi.ch@gmail.com", "Kuala Lumpur", 3.1, skillList18, jobList18));

        DoublyLinkedList<Skill> skillList19 = new DoublyLinkedList<>();
        skillList19.add(s4);
        skillList19.add(s6);
        skillList19.add(s5);
        DoublyLinkedList<JobDesired> jobList19 = new DoublyLinkedList<>();
        jobList19.add(jd6);
        applicantList.add(new Applicant("A020", "Ainul Mardhiah", "01347167655", "ainul.m@gmail.com", "Kuala Lumpur", 3.4, skillList19, jobList19));

        

        isInitialized = true;
    }
    public DoublyListInterface<Applicant> getApplicantList() { return applicantList; }
    public DoublyListInterface<Skill> getSkillList() { return skillList; }
    public DoublyListInterface<JobDesired> getJobDesiredList() { return jobDesiredList; }
} 

    

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
    
    
    
    

