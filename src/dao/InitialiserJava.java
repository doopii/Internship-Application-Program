package dao;

import adt.*;
import entity.*;

public class InitialiserJava {

    private static DoublyListInterface<Applicant> applicantList;
    private static DoublyListInterface<Skill> skillList;
    private static DoublyListInterface<JobDesired> jobDesiredList;
    private static DoublyListInterface<Schedule> scheduleList;
    private static DoublyListInterface<Interview> interviewList;
    private static boolean isInitialized = false;

    public void initializeData() {
        if (isInitialized) {
            System.out.println(" ~~~ Data already initialized ~~~ ");
            return;
        }

        applicantList = new DoublyLinkedList<>();
        skillList = new DoublyLinkedList<>();
        jobDesiredList = new DoublyLinkedList<>();
        scheduleList = new DoublyLinkedList<>();
        interviewList = new DoublyLinkedList<>();

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
        
        // Schedule
        Schedule sched1 = new Schedule("S001", "2025-04-10 09:00AM", "Computer Science Interview Session 1");
        scheduleList.add(sched1);
        Schedule sched2 = new Schedule("S002", "2025-04-11 09:00AM", "Computer Science Interview Session 2");
        scheduleList.add(sched2);
        Schedule sched3 = new Schedule("S003", "2025-04-12 09:00AM", "Multimedia Interview Session 1");
        scheduleList.add(sched3);
        Schedule sched4 = new Schedule("S004", "2025-04-13 09:00AM", "Multimedia Interview Session 2");
        scheduleList.add(sched4);

        // Interview
        // Schedule S001 - Computer Science 1
        Interview i1 = new Interview("J1001", "S001", "IV001", "Software Engineer", "Success", "2025-04-10");
        interviewList.add(i1);
        Interview i2 = new Interview("J1002", "S001", "IV002", "Data Analyst", "Unsuccess", "2025-04-09");
        interviewList.add(i2);
        Interview i3 = new Interview("J1003", "S001", "IV003", "AI Developer", "Success", "2025-04-11");
        interviewList.add(i3);
        Interview i4 = new Interview("J1001", "S001", "IV004", "Software Engineer", "Unsuccess", "2025-04-10");
        interviewList.add(i4);
        Interview i5 = new Interview("J1002", "S001", "IV005", "Data Analyst", "Success", "2025-04-09");
        interviewList.add(i5);
        Interview i6 = new Interview("J1003", "S001", "IV006", "AI Developer", "Unsuccess", "2025-04-14");
        interviewList.add(i6);
        Interview i7 = new Interview("J1001", "S001", "IV007", "Software Engineer", "Success", "2025-04-13");
        interviewList.add(i7);
        Interview i8 = new Interview("J1002", "S001", "IV008", "Data Analyst", "Unsuccess", "2025-04-15");
        interviewList.add(i8);

        // Schedule S002 - Computer Science 2
        Interview i9 = new Interview("J1003", "S002", "IV009", "AI Developer", "Success", "2025-04-12");
        interviewList.add(i9);
        Interview i10 = new Interview("J1001", "S002", "IV010", "Software Engineer", "Unsuccess", "2025-04-16");
        interviewList.add(i10);
        Interview i11 = new Interview("J1002", "S002", "IV011", "Data Analyst", "Success", "2025-04-13");
        interviewList.add(i11);
        Interview i12 = new Interview("J1003", "S002", "IV012", "AI Developer", "Unsuccess", "2025-04-11");
        interviewList.add(i12);
        Interview i13 = new Interview("J1001", "S002", "IV013", "Software Engineer", "Success", "2025-04-17");
        interviewList.add(i13);
        Interview i14 = new Interview("J1002", "S002", "IV014", "Data Analyst", "Unsuccess", "2025-04-20");
        interviewList.add(i14);
        Interview i15 = new Interview("J1003", "S002", "IV015", "AI Developer", "Success", "2025-04-12");
        interviewList.add(i15);
        Interview i16 = new Interview("J1001", "S002", "IV016", "Software Engineer", "Unsuccess", "2025-04-11");
        interviewList.add(i16);

        // Schedule S003 - Multimedia 1
        Interview i17 = new Interview("J1004", "S003", "IV017", "Graphic Designer", "Success", "2025-04-14");
        interviewList.add(i17);
        Interview i18 = new Interview("J1005", "S003", "IV018", "Video Editor", "Unsuccess", "2025-04-12");
        interviewList.add(i18);
        Interview i19 = new Interview("J1006", "S003", "IV019", "Animator", "Success", "2025-04-13");
        interviewList.add(i19);
        Interview i20 = new Interview("J1004", "S003", "IV020", "Graphic Designer", "Unsuccess", "2025-04-13");
        interviewList.add(i20);
        Interview i21 = new Interview("J1005", "S003", "IV021", "Video Editor", "Success", "2025-04-12");
        interviewList.add(i21);
        Interview i22 = new Interview("J1006", "S003", "IV022", "Animator", "Unsuccess", "2025-04-20");
        interviewList.add(i22);
        Interview i23 = new Interview("J1004", "S003", "IV023", "Graphic Designer", "Success", "2025-04-12");
        interviewList.add(i23);
        Interview i24 = new Interview("J1005", "S003", "IV024", "Video Editor", "Unsuccess", "2025-04-15");
        interviewList.add(i24);

        // Schedule S004 - Multimedia 2
        Interview i25 = new Interview("J1006", "S004", "IV025", "Animator", "Success", "2025-04-13");
        interviewList.add(i25);
        Interview i26 = new Interview("J1004", "S004", "IV026", "Graphic Designer", "Unsuccess", "2025-04-14");
        interviewList.add(i26);
        Interview i27 = new Interview("J1005", "S004", "IV027", "Video Editor", "Success", "2025-04-18");
        interviewList.add(i27);
        Interview i28 = new Interview("J1006", "S004", "IV028", "Animator", "Unsuccess", "2025-04-20");
        interviewList.add(i28);
        Interview i29 = new Interview("J1004", "S004", "IV029", "Graphic Designer", "Success", "2025-04-13");
        interviewList.add(i29);
        Interview i30 = new Interview("J1005", "S004", "IV030", "Video Editor", "Unsuccess", "2025-04-13");
        interviewList.add(i30);
        Interview i31 = new Interview("J1006", "S004", "IV031", "Animator", "Success", "2025-04-16");
        interviewList.add(i31);
        Interview i32 = new Interview("J1004", "S004", "IV032", "Graphic Designer", "Unsuccess", "2025-04-13");
        interviewList.add(i32);

        DoublyLinkedList<Skill> skillList20 = new DoublyLinkedList<>();
        skillList20.add(s3); 
        skillList20.add(s4); 
        DoublyLinkedList<JobDesired> jobList20 = new DoublyLinkedList<>();
        jobList20.add(jd6);
        jobList20.add(jd2);
        applicantList.add(new Applicant("A001", "Kavitha Devi", "01137468493", "kavitha.devi@gmail.com", "Selangor", 3.4, skillList20, jobList20));

        DoublyLinkedList<Skill> skillList1 = new DoublyLinkedList<>();
        skillList1.add(s2); 
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

        
        DoublyLinkedList<Skill> sList21 = new DoublyLinkedList<>();
        sList21.add(s6);  
        sList21.add(s12); 
        DoublyLinkedList<JobDesired> jList21 = new DoublyLinkedList<>();
        jList21.add(jd1); 
        applicantList.add(new Applicant("A021", "Nur Iman", "01712345678", "nur.iman@gmail.com", "Selangor", 4.0, sList21, jList21));

        DoublyLinkedList<Skill> sList22 = new DoublyLinkedList<>();
        sList22.add(s13); 
        DoublyLinkedList<JobDesired> jList22 = new DoublyLinkedList<>();
        jList22.add(jd3); 
        applicantList.add(new Applicant("A022", "Lee Min Ho", "01987654321", "lee.minho@gmail.com", "Kuala Lumpur", 2.4, sList22, jList22));

        DoublyLinkedList<Skill> sList23 = new DoublyLinkedList<>();
        sList23.add(s9);  
        DoublyLinkedList<JobDesired> jList23 = new DoublyLinkedList<>();
        jList23.add(jd5); 
        applicantList.add(new Applicant("A023", "Aisha Farzana", "0165559988", "aisha.f@gmail.com", "Johor", 3.2, sList23, jList23));

        DoublyLinkedList<Skill> sList24 = new DoublyLinkedList<>();
        sList24.add(s10); 
        DoublyLinkedList<JobDesired> jList24 = new DoublyLinkedList<>();
        jList24.add(jd4); 
        jList24.add(jd6); 
        applicantList.add(new Applicant("A024", "Ramesh Kumar", "0128877665", "ramesh.k@gmail.com", "Penang", 3.0, sList24, jList24));

        DoublyLinkedList<Skill> sList25 = new DoublyLinkedList<>();
        sList25.add(s2); 
        sList25.add(s5); 
        DoublyLinkedList<JobDesired> jList25 = new DoublyLinkedList<>();
        jList25.add(jd2); 
        applicantList.add(new Applicant("A025", "Tan Li Yin", "0132211988", "liyin.tan@gmail.com", "Sarawak", 3.6, sList25, jList25));

        DoublyLinkedList<Skill> sList26 = new DoublyLinkedList<>();
        sList26.add(s3); 
        DoublyLinkedList<JobDesired> jList26 = new DoublyLinkedList<>();
        jList26.add(jd1); 
        applicantList.add(new Applicant("A026", "Izzat Hakim", "0199933112", "izzat.hakim@gmail.com", "Selangor", 2.1, sList26, jList26));

        DoublyLinkedList<Skill> sList27 = new DoublyLinkedList<>();
        sList27.add(s18); 
        DoublyLinkedList<JobDesired> jList27 = new DoublyLinkedList<>();
        jList27.add(jd4); 
        applicantList.add(new Applicant("A027", "Sabrina Chew", "0185566990", "sabrina.c@gmail.com", "Johor", 3.7, sList27, jList27));

        DoublyLinkedList<Skill> sList28 = new DoublyLinkedList<>();
        sList28.add(s11); 
        DoublyLinkedList<JobDesired> jList28 = new DoublyLinkedList<>();
        jList28.add(jd3); 
        applicantList.add(new Applicant("A028", "Aditya Roy", "0147778855", "aditya.r@gmail.com", "Sabah", 1.9, sList28, jList28));

        DoublyLinkedList<Skill> sList29 = new DoublyLinkedList<>();
        sList29.add(s16); 
        DoublyLinkedList<JobDesired> jList29 = new DoublyLinkedList<>();
        jList29.add(jd6); 
        applicantList.add(new Applicant("A029", "Puteri Aisyah", "0129988776", "aisyah.p@gmail.com", "Kuala Lumpur", 2.8, sList29, jList29));

        DoublyLinkedList<Skill> sList30 = new DoublyLinkedList<>();
        sList30.add(s8);  
        DoublyLinkedList<JobDesired> jList30 = new DoublyLinkedList<>();
        jList30.add(jd1); 
        jList30.add(jd5); 
        applicantList.add(new Applicant("A030", "Daniel Chong", "0176655443", "daniel.ch@gmail.com", "Sarawak", 3.45, sList30, jList30));


        isInitialized = true;
    }
    public DoublyListInterface<Applicant> getApplicantList() { return applicantList; }
    public DoublyListInterface<Skill> getSkillList() { return skillList; }
    public DoublyListInterface<JobDesired> getJobDesiredList() { return jobDesiredList; }
    public DoublyListInterface<Schedule> getScheduleList() { return scheduleList; }
    public DoublyListInterface<Interview> getInterviewList() { return interviewList; }
} 

    


    
    
    
    

