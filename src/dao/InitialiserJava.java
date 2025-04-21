package dao;

import adt.*;
import entity.*;

public class InitialiserJava {
    private static DoublyListInterface<Company> companyList;
    private static DoublyListInterface<Job> jobList;
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

        companyList = new DoublyLinkedList<>();
        jobList = new DoublyLinkedList<>();
        applicantList = new DoublyLinkedList<>();
        skillList = new DoublyLinkedList<>();
        jobDesiredList = new DoublyLinkedList<>();
        scheduleList = new DoublyLinkedList<>();
        interviewList = new DoublyLinkedList<>();

        Company c1 = new Company("C1001", "TechCorp", "Selangor, Kuala Lumpur");
        Company c2 = new Company("C1002", "DataPro", "Johor");
        Company c3 = new Company("C1003", "Designify", "Penang");
        Company c4 = new Company("C1004", "BrandBoost", "Sabah");
        Company c5 = new Company("C1005", "SecureNet", "Kuala Lumpur, Johor");

        // TechCorp Jobs (Selangor, Kuala Lumpur)
        Job j1 = new Job("J1001", "Software Engineer", 3200.0, 12, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.2, "Programming:\nJava (Advanced)\nPython (Intermediate)"));
        Job j10 = new Job("J1010", "Software Engineer", 2600.0, 8, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.0, "Programming:\nJava (Intermediate)"));
        Job j11 = new Job("J1011", "AI Developer", 3200.0, 12, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.8, "Programming:\nPython (Advanced)"));
        Job j19 = new Job("J1019", "Software Engineer", 2200.0, 10, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.4, "Programming:\nJava (Intermediate)"));
        Job j27 = new Job("J1027", "Software Engineer", 2200.0, 12, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.0, "Programming:\nPython (Intermediate)"));
        Job j30 = new Job("J1030", "Software Engineer", 2600.0, 8, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.4, "Programming:\nJava (Intermediate)\nC++ (Beginner)"));
        Job j31 = new Job("J1031", "AI Developer", 3200.0, 12, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.8, "Programming:\nPython (Advanced)"));
        Job j40 = new Job("J1040", "Software Engineer", 2200.0, 10, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.0, "Programming:\nPython (Intermediate)"));
        Job j45 = new Job("J1045", "AI Developer", 3200.0, 12, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.8, "Programming:\nPython (Advanced)"));
        Job j51 = new Job("J1051", "Software Engineer", 3200.0, 12, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.8, "Programming:\nJava (Advanced)\nPython (Intermediate)"));
        Job j52 = new Job("J1052", "AI Developer", 3000.0, 10, "Active", "Selangor",
            new JobRequirement("Computer Science", 4.0, "Programming:\nPython (Advanced)\nC++ (Intermediate)"));
        Job j53 = new Job("J1053", "Data Analyst", 2600.0, 8, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.4, "Programming:\nPython (Intermediate)")); 
        Job j62 = new Job("J1062", "Data Analyst", 1800.0, 6, "Deactive", "Kuala Lumpur",
            new JobRequirement("Computer Science", 2.6, "Programming:\nPython (Intermediate)"));
        Job j63 = new Job("J1063", "Software Engineer", 2600.0, 8, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.4, "Programming:\nJava (Intermediate)\nC++ (Beginner)"));
        Job j71 = new Job("J1071", "Software Engineer", 2600.0, 10, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.2, "Programming:\nJava (Intermediate)\nPython (Beginner)"));
        Job j72 = new Job("J1072", "AI Developer", 3200.0, 12, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.8, "Programming:\nPython (Advanced)\nC++ (Intermediate)"));
        Job j73 = new Job("J1073", "Data Analyst", 1800.0, 6, "Deactive", "Kuala Lumpur",
            new JobRequirement("Computer Science", 2.4, "Programming:\nPython (Beginner)"));
        Job j82 = new Job("J1082", "Software Engineer", 3000.0, 12, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.6, "Programming:\nJava (Advanced)\nPython (Intermediate)"));
        Job j83 = new Job("J1083", "Data Analyst", 2200.0, 8, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.2, "Programming:\nPython (Intermediate)")); 
        Job j91 = new Job("J1091", "AI Developer", 3200.0, 12, "Active", "Selangor",
            new JobRequirement("Computer Science", 3.8, "Programming:\nPython (Advanced)\nC++ (Intermediate)"));
        Job j92 = new Job("J1092", "Data Analyst", 1800.0, 6, "Deactive", "Kuala Lumpur",
            new JobRequirement("Computer Science", 2.6, "Programming:\nPython (Beginner)")); 
        Job j93 = new Job("J1092", "Software Engineer", 1800.0, 6, "Deactive", "Kuala Lumpur",
            new JobRequirement("Computer Science", 2.6, "Programming:\nPython (Advanced)")); 

        // DataPro Jobs (Johor)
        Job j2 = new Job("J1002", "Software Engineer", 3200.0, 12, "Active", "Johor",
            new JobRequirement("Computer Science", 3.4, "Programming:\nC++ (Advanced)"));
        Job j12 = new Job("J1012", "Data Analyst", 1800.0, 6, "Deactive", "Johor",
            new JobRequirement("Computer Science", 2.4, "Programming:\nPython (Beginner)"));
        Job j20 = new Job("J1020", "Data Analyst", 1400.0, 6, "Deactive", "Johor",
            new JobRequirement("Computer Science", 2.2, "Programming:\nPython (Beginner)"));
        Job j28 = new Job("J1028", "Data Analyst", 1400.0, 10, "Deactive", "Johor",
            new JobRequirement("Computer Science", 2.2, "Programming:\nPython (Beginner)"));
        Job j32 = new Job("J1032", "Data Analyst", 1800.0, 6, "Deactive", "Johor",
            new JobRequirement("Computer Science", 2.6, "Programming:\nPython (Intermediate)"));
        Job j33 = new Job("J1033", "Software Engineer", 3000.0, 10, "Active", "Johor",
            new JobRequirement("Computer Science", 3.6, "Programming:\nC++ (Advanced)"));
        Job j41 = new Job("J1041", "Data Analyst", 1400.0, 6, "Deactive", "Johor",
            new JobRequirement("Computer Science", 2.2, "Programming:\nPython (Beginner)")); 
        Job j46 = new Job("J1046", "Software Engineer", 3000.0, 8, "Active", "Johor",
            new JobRequirement("Computer Science", 3.2, "Programming:\nC++ (Intermediate)"));
        Job j54 = new Job("J1054", "Software Engineer", 3000.0, 12, "Active", "Johor", 
            new JobRequirement("Computer Science", 3.6, "Programming:\nC++ (Advanced)"));
        Job j55 = new Job("J1055", "Software Engineer", 2200.0, 8, "Deactive", "Johor",
            new JobRequirement("Computer Science", 2.8, "Programming:\nJava (Beginner)"));
        Job j64 = new Job("J1064", "AI Developer", 3200.0, 12, "Active", "Johor",
            new JobRequirement("Computer Science", 3.8, "Programming:\nPython (Advanced)"));
        Job j65 = new Job("J1065", "Data Analyst", 1400.0, 6, "Deactive", "Johor",
            new JobRequirement("Computer Science", 2.2, "Programming:\nPython (Beginner)")); 
        Job j74 = new Job("J1074", "Software Engineer", 3000.0, 12, "Active", "Johor", 
            new JobRequirement("Computer Science", 3.6, "Programming:\nC++ (Advanced)"));
        Job j75 = new Job("J1075", "Software Engineer", 2200.0, 8, "Active", "Johor",
            new JobRequirement("Computer Science", 3.0, "Programming:\nJava (Intermediate)"));
        Job j84 = new Job("J1084", "Software Engineer", 3000.0, 10, "Active", "Johor", 
            new JobRequirement("Computer Science", 3.8, "Programming:\nC++ (Advanced)"));
        Job j85 = new Job("J1085", "AI Developer", 3200.0, 12, "Active", "Johor",
            new JobRequirement("Computer Science", 3.6, "Programming:\nPython (Advanced)"));
        Job j94 = new Job("J1094", "Software Engineer", 2200.0, 8, "Deactive", "Johor",
            new JobRequirement("Computer Science", 2.8, "Programming:\nC++ (Beginner)"));

        // Designify Jobs (Penang)
        Job j3 = new Job("J1003", "Data Analyst", 3200.0, 10, "Active", "Penang",
            new JobRequirement("Computer Science", 3.0, "Programming:\nPython (Advanced)"));
        Job j14 = new Job("J1014", "Graphic Designer", 2200.0, 8, "Active", "Penang",
            new JobRequirement("Multimedia", 3.2, "Design:\nPhotoshop (Intermediate)\nIllustrator (Advanced)"));
        Job j15 = new Job("J1015", "Graphic Designer", 2600.0, 12, "Active", "Penang",
            new JobRequirement("Multimedia", 3.0, "Design:\nIllustrator (Advanced)\nPhotoshop (Intermediate)"));
        Job j21 = new Job("J1021", "Graphic Designer", 1800.0, 8, "Active", "Penang",
            new JobRequirement("Multimedia", 2.6, "Design:\nIllustrator (Intermediate)"));
        Job j25 = new Job("J1025", "Graphic Designer", 2600.0, 8, "Active", "Penang",
            new JobRequirement("Multimedia", 3.2, "Design:\nIllustrator (Advanced)"));
        Job j34 = new Job("J1034", "Graphic Designer", 2200.0, 8, "Active", "Penang",
            new JobRequirement("Multimedia", 3.0, "Design:\nPhotoshop (Intermediate)"));
        Job j35 = new Job("J1035", "Graphic Designer", 2600.0, 12, "Active", "Penang",
            new JobRequirement("Multimedia", 3.2, "Design:\nIllustrator (Intermediate)"));
        Job j42 = new Job("J1042", "Graphic Designer", 1800.0, 8, "Active", "Penang",
            new JobRequirement("Multimedia", 2.8, "Design:\nIllustrator (Intermediate)"));
        Job j48 = new Job("J1048", "Data Analyst", 1800.0, 6, "Deactive", "Penang",
            new JobRequirement("Computer Science", 2.6, "Programming:\nPython (Beginner)"));
        Job j56 = new Job("J1056", "Graphic Designer", 1800.0, 6, "Active", "Penang",
            new JobRequirement("Multimedia", 3.0, "Design:\nIllustrator (Advanced)")); 
        Job j57 = new Job("J1057", "Graphic Designer", 2600.0, 10, "Active", "Penang", 
            new JobRequirement("Multimedia", 3.4, "Design:\nIllustrator (Advanced)")); 
        Job j66 = new Job("J1066", "Graphic Designer", 2600.0, 10, "Active", "Penang", 
            new JobRequirement("Multimedia", 3.4, "Design:\nIllustrator (Advanced)"));
        Job j67 = new Job("J1067", "Graphic Designer", 1800.0, 8, "Active", "Penang",
            new JobRequirement("Multimedia", 2.8, "Design:\nIllustrator (Intermediate)")); 
        Job j76 = new Job("J1076", "Graphic Designer", 1800.0, 6, "Active", "Penang",
            new JobRequirement("Multimedia", 3.2, "Design:\nPhotoshop (Advanced)")); 
        Job j77 = new Job("J1077", "Animator", 1400.0, 6, "Deactive", "Penang",
            new JobRequirement("Multimedia", 2.0, "Design:\nVideo Editing (Beginner)")); 
        Job j86 = new Job("J1086", "Graphic Designer", 1400.0, 6, "Deactive", "Penang",
            new JobRequirement("Multimedia", 2.6, "Design:\nIllustrator (Intermediate)"));
        Job j87 = new Job("J1087", "Graphic Designer", 2600.0, 10, "Active", "Penang", 
            new JobRequirement("Multimedia", 3.0, "Design:\nIllustrator (Advanced)")); 
        Job j95 = new Job("J1095", "Graphic Designer", 1800.0, 8, "Active", "Penang",
            new JobRequirement("Multimedia", 3.4, "Design:\nPhotoshop (Advanced)\nIllustrator (Intermediate)"));
        Job j96 = new Job("J1096", "Animator", 1400.0, 6, "Deactive", "Penang",
            new JobRequirement("Multimedia", 2.2, "Design:\nVideo Editing (Beginner)")); 

        // BrandBoost Jobs (Sabah) 
        Job j4 = new Job("J1004", "Graphic Designer", 3200.0, 6, "Active", "Sabah",
            new JobRequirement("Multimedia", 3.6, "Design:\nIllustrator (Advanced)\nPhotoshop (Intermediate)"));
        Job j6 = new Job("J1006", "Video Editor", 2600.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.8, "Design:\nVideo Editing (Advanced)"));
        Job j16 = new Job("J1016", "Video Editor", 1400.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.0, "Design:\nVideo Editing (Beginner)"));
        Job j17 = new Job("J1017", "Animator", 1800.0, 8, "Active", "Sabah",
            new JobRequirement("Multimedia", 2.8, "Design:\nVideo Editing (Intermediate)"));
        Job j23 = new Job("J1023", "Video Editor", 1000.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.4, "Design:\nVideo Editing (Beginner)"));
        Job j26 = new Job("J1026", "Animator", 1800.0, 6, "Active", "Sabah",
            new JobRequirement("Multimedia", 2.8, "Design:\nVideo Editing (Intermediate)"));
        Job j36 = new Job("J1036", "Video Editor", 1400.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.4, "Design:\nVideo Editing (Beginner)"));
        Job j37 = new Job("J1037", "Animator", 1800.0, 8, "Active", "Sabah",
            new JobRequirement("Multimedia", 2.8, "Design:\nVideo Editing (Intermediate)"));
        Job j44 = new Job("J1044", "Animator", 1000.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.0, "Design:\nVideo Editing (Beginner)"));
        Job j49 = new Job("J1049", "Video Editor", 1400.0, 8, "Active", "Sabah",
            new JobRequirement("Multimedia", 2.4, "Design:\nVideo Editing (Intermediate)")); 
        Job j58 = new Job("J1058", "Animator", 1400.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.4, "Design:\nVideo Editing (Beginner)")); 
        Job j59 = new Job("J1059", "Video Editor", 1800.0, 8, "Active", "Sabah",
            new JobRequirement("Multimedia", 2.8, "Design:\nVideo Editing (Intermediate)")); 
        Job j68 = new Job("J1068", "Video Editor", 1400.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.4, "Design:\nVideo Editing (Beginner)"));
        Job j69 = new Job("J1069", "Animator", 1800.0, 8, "Active", "Sabah",
            new JobRequirement("Multimedia", 2.8, "Design:\nVideo Editing (Intermediate)")); 
        Job j78 = new Job("J1078", "Video Editor", 1000.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.2, "Design:\nVideo Editing (Beginner)")); 
        Job j79 = new Job("J1079", "Animator", 1800.0, 8, "Active", "Sabah",
            new JobRequirement("Multimedia", 2.8, "Design:\nVideo Editing (Intermediate)")); 
        Job j88 = new Job("J1088", "Video Editor", 1400.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.4, "Design:\nVideo Editing (Beginner)")); 
        Job j89 = new Job("J1089", "Animator", 1800.0, 8, "Active", "Sabah",
            new JobRequirement("Multimedia", 3.0, "Design:\nVideo Editing (Intermediate)")); 
        Job j97 = new Job("J1097", "Video Editor", 1000.0, 6, "Deactive", "Sabah",
            new JobRequirement("Multimedia", 2.0, "Design:\nVideo Editing (Beginner)")); 
        Job j98 = new Job("J1098", "Animator", 1800.0, 8, "Active", "Sabah",
            new JobRequirement("Multimedia", 2.8, "Design:\nVideo Editing (Intermediate)")); 

        // SecureNet Jobs (Kuala Lumpur, Johor)
        Job j5 = new Job("J1005", "Graphic Designer", 3200.0, 8, "Active", "Kuala Lumpur",
            new JobRequirement("Multimedia", 3.8, "Design:\nPhotoshop (Advanced)"));
        Job j7 = new Job("J1007", "Animator", 2200.0, 12, "Active", "Johor",
            new JobRequirement("Multimedia", 3.2, "Design:\nVideo Editing (Intermediate)"));
        Job j8 = new Job("J1008", "AI Developer", 3200.0, 12, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 4.0, "Programming:\nPython (Advanced)"));
        Job j9 = new Job("J1009", "Software Engineer", 3000.0, 10, "Active", "Johor",
            new JobRequirement("Computer Science", 3.6, "Programming:\nJava (Advanced)"));
        Job j13 = new Job("J1013", "Software Engineer", 3000.0, 10, "Active", "Johor",
            new JobRequirement("Computer Science", 3.6, "Programming:\nC++ (Intermediate)"));
        Job j18 = new Job("J1018", "AI Developer", 3200.0, 12, "Active", "Johor",
            new JobRequirement("Computer Science", 4.0, "Programming:\nPython (Advanced)"));
        Job j22 = new Job("J1022", "Software Engineer", 3000.0, 12, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.8, "Programming:\nC++ (Advanced)"));
        Job j24 = new Job("J1024", "AI Developer", 3200.0, 10, "Active", "Johor",
            new JobRequirement("Computer Science", 3.6, "Programming:\nPython (Advanced)"));
        Job j29 = new Job("J1029", "Software Engineer", 3000.0, 8, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.4, "Programming:\nC++ (Intermediate)"));
        Job j38 = new Job("J1038", "AI Developer", 3200.0, 12, "Active", "Johor",
            new JobRequirement("Computer Science", 4.0, "Programming:\nPython (Advanced)"));
        Job j39 = new Job("J1039", "Software Engineer", 3000.0, 10, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.6, "Programming:\nJava (Advanced)"));
        Job j43 = new Job("J1043", "Graphic Designer", 2600.0, 12, "Active", "Johor",
            new JobRequirement("Multimedia", 3.4, "Design:\nIllustrator (Advanced)"));
        Job j47 = new Job("J1047", "Software Engineer", 2600.0, 10, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.4, "Programming:\nJava (Intermediate)"));
        Job j50 = new Job("J1050", "Software Engineer", 3000.0, 10, "Active", "Johor",  
            new JobRequirement("Computer Science", 3.6, "Programming:\nJava (Advanced)"));
        Job j60 = new Job("J1060", "Software Engineer", 2600.0, 8, "Active", "Johor",  
            new JobRequirement("Computer Science", 3.4, "Programming:\nPython (Intermediate)\nC++ (Beginner)"));
        Job j61 = new Job("J1061", "AI Developer", 2600.0, 8, "Active", "Johor",  
            new JobRequirement("Computer Science", 3.4, "Programming:\nPython (Intermediate)\nC++ (Beginner)"));
        Job j70 = new Job("J1070", "Software Engineer", 3000.0, 10, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 3.6, "Programming:\nJava (Advanced)")); 
        Job j80 = new Job("J1080", "AI Developer", 3200.0, 12, "Active", "Johor",
            new JobRequirement("Computer Science", 4.0, "Programming:\nPython (Advanced)")); 
        Job j81 = new Job("J1081", "Graphic Designer", 2600.0, 8, "Active", "Kuala Lumpur",
            new JobRequirement("Multimedia", 3.4, "Design:\nPhotoshop (Advanced)"));
        Job j90 = new Job("J1090", "Software Engineer", 2600.0, 8, "Active", "Johor",
            new JobRequirement("Computer Science", 3.4, "Programming:\nJava (Intermediate)\nPython (Beginner)"));
        Job j99 = new Job("J1099", "AI Developer", 3200.0, 12, "Active", "Kuala Lumpur",
            new JobRequirement("Computer Science", 4.0, "Programming:\nPython (Advanced)"));
        Job j100 = new Job("J1100", "Graphic Designer", 2600.0, 10, "Active", "Johor",
            new JobRequirement("Multimedia", 3.6, "Design:\nIllustrator (Advanced)")); 

        // TechCorp Jobs
        c1.addJob(j1);  
        c1.addJob(j10);  
        c1.addJob(j11);
        c1.addJob(j19);  
        c1.addJob(j27);
        c1.addJob(j30);
        c1.addJob(j31);
        c1.addJob(j40);
        c1.addJob(j45);
        c1.addJob(j51);
        c1.addJob(j52);
        c1.addJob(j53);
        c1.addJob(j62);
        c1.addJob(j63);
        c1.addJob(j71);
        c1.addJob(j72);
        c1.addJob(j73);
        c1.addJob(j82);
        c1.addJob(j91);
        c1.addJob(j92);
        c1.addJob(j93);

        // DataPro Jobs
        c2.addJob(j2);  
        c2.addJob(j12);  
        c2.addJob(j20);  
        c2.addJob(j28);
        c2.addJob(j32);
        c2.addJob(j33);
        c2.addJob(j41);
        c2.addJob(j46);
        c2.addJob(j54);
        c2.addJob(j55);
        c2.addJob(j64);
        c2.addJob(j65);
        c2.addJob(j74);
        c2.addJob(j75);
        c2.addJob(j84);
        c2.addJob(j85);
        c2.addJob(j94);

        // Designify Jobs
        c3.addJob(j3);  
        c3.addJob(j14);  
        c3.addJob(j15);  
        c3.addJob(j21);  
        c3.addJob(j25);
        c3.addJob(j34);
        c3.addJob(j35);
        c3.addJob(j42);
        c3.addJob(j48);
        c3.addJob(j56);
        c3.addJob(j57);
        c3.addJob(j66);
        c3.addJob(j76);
        c3.addJob(j77);
        c3.addJob(j86);
        c3.addJob(j87);
        c3.addJob(j95);
        c3.addJob(j96);

        // BrandBoost Jobs
        c4.addJob(j4); 
        c4.addJob(j6);  
        c4.addJob(j16);  
        c4.addJob(j17);  
        c4.addJob(j23);  
        c4.addJob(j26);
        c4.addJob(j36);
        c4.addJob(j37);
        c4.addJob(j44);
        c4.addJob(j49);
        c4.addJob(j58);
        c4.addJob(j59);
        c4.addJob(j68);
        c4.addJob(j69);
        c4.addJob(j78);
        c4.addJob(j79);
        c4.addJob(j88);
        c4.addJob(j89);
        c4.addJob(j97);
        c4.addJob(j98);

        // SecureNet Jobs
        c5.addJob(j5);  
        c5.addJob(j7);  
        c5.addJob(j8);  
        c5.addJob(j9);  
        c5.addJob(j13);  
        c5.addJob(j18);  
        c5.addJob(j22);  
        c5.addJob(j24);  
        c5.addJob(j29);
        c5.addJob(j38);
        c5.addJob(j39);
        c5.addJob(j43);
        c5.addJob(j47);
        c5.addJob(j50);
        c5.addJob(j51);
        c5.addJob(j52);
        c5.addJob(j60);
        c5.addJob(j61);
        c5.addJob(j70);
        c5.addJob(j80);
        c5.addJob(j81);
        c5.addJob(j90);
        c5.addJob(j99);
        c5.addJob(j100);
        
        // Add Jobs to the Job List (for separate retrieval)
        jobList.add(j1);
        jobList.add(j2);
        jobList.add(j3);
        jobList.add(j4);
        jobList.add(j5);
        jobList.add(j6);
        jobList.add(j7);
        jobList.add(j8);
        jobList.add(j9);
        jobList.add(j10);
        jobList.add(j11);
        jobList.add(j12);
        jobList.add(j13);
        jobList.add(j14);
        jobList.add(j15);
        jobList.add(j16);
        jobList.add(j17);
        jobList.add(j18);
        jobList.add(j19);
        jobList.add(j20);
        jobList.add(j21);
        jobList.add(j22);
        jobList.add(j23);
        jobList.add(j24);
        jobList.add(j25);
        jobList.add(j26);
        jobList.add(j27);
        jobList.add(j28);
        jobList.add(j29);
        jobList.add(j30);
        jobList.add(j31);
        jobList.add(j32);
        jobList.add(j33);
        jobList.add(j34);
        jobList.add(j35);
        jobList.add(j36);
        jobList.add(j37);
        jobList.add(j38);
        jobList.add(j39);
        jobList.add(j40);
        jobList.add(j41);
        jobList.add(j42);
        jobList.add(j43);
        jobList.add(j44);
        jobList.add(j45);
        jobList.add(j46);
        jobList.add(j47);
        jobList.add(j48);
        jobList.add(j49);
        jobList.add(j50);
        jobList.add(j51);
        jobList.add(j52);
        jobList.add(j53);
        jobList.add(j54);
        jobList.add(j55);
        jobList.add(j56);
        jobList.add(j57);
        jobList.add(j58);
        jobList.add(j59);
        jobList.add(j60);
        jobList.add(j61);
        jobList.add(j62);
        jobList.add(j63);
        jobList.add(j64);
        jobList.add(j65);
        jobList.add(j66);
        jobList.add(j67);
        jobList.add(j68);
        jobList.add(j69);
        jobList.add(j70);
        jobList.add(j71);
        jobList.add(j72);
        jobList.add(j73);
        jobList.add(j74);
        jobList.add(j75);
        jobList.add(j76);
        jobList.add(j77);
        jobList.add(j78);
        jobList.add(j79);
        jobList.add(j80);
        jobList.add(j81);
        jobList.add(j82);
        jobList.add(j83);
        jobList.add(j84);
        jobList.add(j85);
        jobList.add(j86);
        jobList.add(j87);
        jobList.add(j88);
        jobList.add(j89);
        jobList.add(j90);
        jobList.add(j91);
        jobList.add(j92);
        jobList.add(j93);
        jobList.add(j94);
        jobList.add(j95);
        jobList.add(j96);
        jobList.add(j97);
        jobList.add(j98);
        jobList.add(j99);
        jobList.add(j100);

        // Save Companies to the list
        companyList.add(c1);
        companyList.add(c2);
        companyList.add(c3);
        companyList.add(c4);
        companyList.add(c5);
        

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
        Interview i2 = new Interview("J1002", "S001", "IV002", "Data Analyst", "Fail", "2025-04-09");
        interviewList.add(i2);
        Interview i3 = new Interview("J1003", "S001", "IV003", "AI Developer", "Success", "2025-04-11");
        interviewList.add(i3);
        Interview i4 = new Interview("J1001", "S001", "IV004", "Software Engineer", "Fail", "2025-04-10");
        interviewList.add(i4);
        Interview i5 = new Interview("J1002", "S001", "IV005", "Data Analyst", "Success", "2025-04-09");
        interviewList.add(i5);
        Interview i6 = new Interview("J1003", "S001", "IV006", "AI Developer", "Fail", "2025-04-14");
        interviewList.add(i6);
        Interview i7 = new Interview("J1001", "S001", "IV007", "Software Engineer", "Success", "2025-04-13");
        interviewList.add(i7);
        Interview i8 = new Interview("J1002", "S001", "IV008", "Data Analyst", "Fail", "2025-04-15");
        interviewList.add(i8);

        // Schedule S002 - Computer Science 2
        Interview i9 = new Interview("J1003", "S002", "IV009", "AI Developer", "Success", "2025-04-12");
        interviewList.add(i9);
        Interview i10 = new Interview("J1001", "S002", "IV010", "Software Engineer", "Fail", "2025-04-16");
        interviewList.add(i10);
        Interview i11 = new Interview("J1002", "S002", "IV011", "Data Analyst", "Success", "2025-04-13");
        interviewList.add(i11);
        Interview i12 = new Interview("J1003", "S002", "IV012", "AI Developer", "Fail", "2025-04-11");
        interviewList.add(i12);
        Interview i13 = new Interview("J1001", "S002", "IV013", "Software Engineer", "Success", "2025-04-17");
        interviewList.add(i13);
        Interview i14 = new Interview("J1002", "S002", "IV014", "Data Analyst", "Fail", "2025-04-20");
        interviewList.add(i14);
        Interview i15 = new Interview("J1003", "S002", "IV015", "AI Developer", "Success", "2025-04-12");
        interviewList.add(i15);
        Interview i16 = new Interview("J1001", "S002", "IV016", "Software Engineer", "Fail", "2025-04-11");
        interviewList.add(i16);

        // Schedule S003 - Multimedia 1
        Interview i17 = new Interview("J1004", "S003", "IV017", "Graphic Designer", "Success", "2025-04-14");
        interviewList.add(i17);
        Interview i18 = new Interview("J1005", "S003", "IV018", "Video Editor", "Fail", "2025-04-12");
        interviewList.add(i18);
        Interview i19 = new Interview("J1006", "S003", "IV019", "Animator", "Success", "2025-04-13");
        interviewList.add(i19);
        Interview i20 = new Interview("J1004", "S003", "IV020", "Graphic Designer", "Fail", "2025-04-13");
        interviewList.add(i20);
        Interview i21 = new Interview("J1005", "S003", "IV021", "Video Editor", "Success", "2025-04-12");
        interviewList.add(i21);
        Interview i22 = new Interview("J1006", "S003", "IV022", "Animator", "Fail", "2025-04-20");
        interviewList.add(i22);
        Interview i23 = new Interview("J1004", "S003", "IV023", "Graphic Designer", "Fail", "2025-04-12");
        interviewList.add(i23);
        Interview i24 = new Interview("J1005", "S003", "IV024", "Video Editor", "Fail", "2025-04-15");
        interviewList.add(i24);

        // Schedule S004 - Multimedia 2
        Interview i25 = new Interview("J1006", "S004", "IV025", "Animator", "Success", "2025-04-13");
        interviewList.add(i25);
        Interview i26 = new Interview("J1004", "S004", "IV026", "Graphic Designer", "Fail", "2025-04-14");
        interviewList.add(i26);
        Interview i27 = new Interview("J1005", "S004", "IV027", "Video Editor", "Success", "2025-04-18");
        interviewList.add(i27);
        Interview i28 = new Interview("J1006", "S004", "IV028", "Animator", "Fail", "2025-04-20");
        interviewList.add(i28);
        Interview i29 = new Interview("J1004", "S004", "IV029", "Graphic Designer", "Success", "2025-04-13");
        interviewList.add(i29);
        Interview i30 = new Interview("J1005", "S004", "IV030", "Video Editor", "Fail", "2025-04-13");
        interviewList.add(i30);
        Interview i31 = new Interview("J1006", "S004", "IV031", "Animator", "Fail", "2025-04-16");
        interviewList.add(i31);
        Interview i32 = new Interview("J1004", "S004", "IV032", "Graphic Designer", "Fail", "2025-04-13");
        interviewList.add(i32);

        // APPLICANTS DATA
        DoublyLinkedList<Skill> skillList1 = new DoublyLinkedList<>();
        skillList1.add(s2); 
        skillList1.add(s6); 
        DoublyLinkedList<JobDesired> jobList1 = new DoublyLinkedList<>();
        jobList1.add(jd4);
        applicantList.add(new Applicant("A002", "Wong Ning Ning", "01670772704", "ningwong@gmail.com", "Selangor", 3.8, skillList1, jobList1));

        DoublyLinkedList<Skill> skillList2 = new DoublyLinkedList<>();
        skillList2.add(s6);
        DoublyLinkedList<JobDesired> jobList2 = new DoublyLinkedList<>();
        jobList2.add(jd4);
        applicantList.add(new Applicant("A003", "Ahmad Firdaus", "01276128047", "firdaus@gmail.com", "Johor", 3.9, skillList2, jobList2));

        DoublyLinkedList<Skill> skillList3 = new DoublyLinkedList<>();
        skillList3.add(s5);
        skillList3.add(s10);
        DoublyLinkedList<JobDesired> jobList3 = new DoublyLinkedList<>();
        jobList3.add(jd4);
        jobList3.add(jd6);
        applicantList.add(new Applicant("A004", "Tan Mei Ling", "01388699038", "meiling@gmail.com", "Selangor", 2.8, skillList3, jobList3));

        DoublyLinkedList<Skill> skillList4 = new DoublyLinkedList<>();
        skillList4.add(s5);
        skillList4.add(s7);
        skillList4.add(s1);
        DoublyLinkedList<JobDesired> jobList4 = new DoublyLinkedList<>();
        jobList4.add(jd1);
        applicantList.add(new Applicant("A005", "Syafiq Hassan", "01252722044", "syafiq@gmail.com", "Selangor", 3.7, skillList4, jobList4));

        DoublyLinkedList<Skill> skillList5 = new DoublyLinkedList<>();
        skillList5.add(s4);
        skillList5.add(s9);
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
        skillList7.add(s5);
        skillList7.add(s2);
        DoublyLinkedList<JobDesired> jobList7 = new DoublyLinkedList<>();
        jobList7.add(jd6);
        applicantList.add(new Applicant("A008", "Rajesh Kumar", "01142088959", "rajesh.kumar@gmail.com", "Johor", 2.7, skillList7, jobList7));

        DoublyLinkedList<Skill> skillList8 = new DoublyLinkedList<>();
        skillList8.add(s15);
        skillList8.add(s2);
        DoublyLinkedList<JobDesired> jobList8 = new DoublyLinkedList<>();
        jobList8.add(jd3);
        applicantList.add(new Applicant("A009", "Nurul Ain", "01431159253", "nurul@gmail.com", "Penang", 1.7, skillList8, jobList8));

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
        skillList10.add(s11);
        DoublyLinkedList<JobDesired> jobList10 = new DoublyLinkedList<>();
        jobList10.add(jd5);
        jobList10.add(jd4);
        applicantList.add(new Applicant("A011", "Chong Kai Lun", "01375237275", "ckl@gmail.com", "Penang", 4, skillList10, jobList10));

        DoublyLinkedList<Skill> skillList11 = new DoublyLinkedList<>();
        skillList11.add(s6);
        skillList11.add(s2);
        DoublyLinkedList<JobDesired> jobList11 = new DoublyLinkedList<>();
        jobList11.add(jd6);
        applicantList.add(new Applicant("A012", "Farah Nadia", "01464046749", "farah.nadia@gmail.com", "Penang", 3.3, skillList11, jobList11));

        DoublyLinkedList<Skill> skillList12 = new DoublyLinkedList<>();
        skillList12.add(s3);
        skillList12.add(s4);
        DoublyLinkedList<JobDesired> jobList12 = new DoublyLinkedList<>();
        jobList12.add(jd5);
        jobList12.add(jd4);
        applicantList.add(new Applicant("A013", "Ganesh Subramaniam", "01339903261", "ganesh.s@gmail.com", "Penang", 4, skillList12, jobList12));

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
        applicantList.add(new Applicant("A016", "Nabilah Ramli", "01998043964", "nabilah.ramli@gmail.com", "Sabah", 3.1, skillList15, jobList15));

        DoublyLinkedList<Skill> skillList16 = new DoublyLinkedList<>();
        skillList16.add(s6);
        skillList16.add(s4);
        skillList16.add(s3);
        DoublyLinkedList<JobDesired> jobList16 = new DoublyLinkedList<>();
        jobList16.add(jd1);
        applicantList.add(new Applicant("A017", "Amar Daniel", "01682479596", "amar.d@gmail.com", "Sabah", 2.8, skillList16, jobList16));

        DoublyLinkedList<Skill> skillList17 = new DoublyLinkedList<>();
        skillList17.add(s6);
        skillList17.add(s4);
        skillList17.add(s3);
        DoublyLinkedList<JobDesired> jobList17 = new DoublyLinkedList<>();
        jobList17.add(jd3);
        applicantList.add(new Applicant("A018", "Chong Mei Yen", "01388130563", "mei.yen@gmail.com", "Kuala Lumpur", 2, skillList17, jobList17));

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
        applicantList.add(new Applicant("A023", "Aisha Farzana", "01655599883", "aisha.f@gmail.com", "Johor", 3.2, sList23, jList23));

        DoublyLinkedList<Skill> sList24 = new DoublyLinkedList<>();
        sList24.add(s10); 
        DoublyLinkedList<JobDesired> jList24 = new DoublyLinkedList<>();
        jList24.add(jd4); 
        jList24.add(jd6); 
        applicantList.add(new Applicant("A024", "Ramesh Kumar", "01288776658", "ramesh.k@gmail.com", "Penang", 3.0, sList24, jList24));

        DoublyLinkedList<Skill> sList25 = new DoublyLinkedList<>();
        sList25.add(s2); 
        sList25.add(s5); 
        DoublyLinkedList<JobDesired> jList25 = new DoublyLinkedList<>();
        jList25.add(jd2); 
        applicantList.add(new Applicant("A025", "Tan Li Yin", "01322119881", "liyin.tan@gmail.com", "Sarawak", 3.6, sList25, jList25));

        DoublyLinkedList<Skill> sList26 = new DoublyLinkedList<>();
        sList26.add(s3); 
        DoublyLinkedList<JobDesired> jList26 = new DoublyLinkedList<>();
        jList26.add(jd1); 
        applicantList.add(new Applicant("A026", "Izzat Hakim", "01999331121", "izzat.hakim@gmail.com", "Selangor", 2.1, sList26, jList26));

        DoublyLinkedList<Skill> sList27 = new DoublyLinkedList<>();
        sList27.add(s18); 
        DoublyLinkedList<JobDesired> jList27 = new DoublyLinkedList<>();
        jList27.add(jd4); 
        applicantList.add(new Applicant("A027", "Sabrina Chew", "01855669902", "sabrina.c@gmail.com", "Johor", 3.7, sList27, jList27));

        DoublyLinkedList<Skill> sList28 = new DoublyLinkedList<>();
        sList28.add(s11); 
        DoublyLinkedList<JobDesired> jList28 = new DoublyLinkedList<>();
        jList28.add(jd3); 
        applicantList.add(new Applicant("A028", "Aditya Roy", "01477788557", "aditya.r@gmail.com", "Sabah", 1.9, sList28, jList28));

        DoublyLinkedList<Skill> sList29 = new DoublyLinkedList<>();
        sList29.add(s16); 
        DoublyLinkedList<JobDesired> jList29 = new DoublyLinkedList<>();
        jList29.add(jd6); 
        applicantList.add(new Applicant("A029", "Puteri Aisyah", "01299887763", "aisyah.p@gmail.com", "Kuala Lumpur", 2.8, sList29, jList29));

        DoublyLinkedList<Skill> sList30 = new DoublyLinkedList<>();
        sList30.add(s8);  
        DoublyLinkedList<JobDesired> jList30 = new DoublyLinkedList<>();
        jList30.add(jd1); 
        jList30.add(jd5); 
        applicantList.add(new Applicant("A030", "Daniel Chong", "01766554432", "daniel.ch@gmail.com", "Sarawak", 3.45, sList30, jList30));

        DoublyLinkedList<Skill> sList31 = new DoublyLinkedList<>();
        sList31.add(s1);
        sList31.add(s10);
        DoublyLinkedList<JobDesired> jList31 = new DoublyLinkedList<>();
        jList31.add(jd1);
        applicantList.add(new Applicant("A031", "Ng Wei Hong", "01355669878", "weihong.ng@gmail.com", "Penang", 2.4, sList31, jList31));

        DoublyLinkedList<Skill> sList32 = new DoublyLinkedList<>();
        sList32.add(s14);
        sList32.add(s18);
        DoublyLinkedList<JobDesired> jList32 = new DoublyLinkedList<>();
        jList32.add(jd6);
        applicantList.add(new Applicant("A032", "Samantha Kaur", "01488900232", "samantha.kaur@gmail.com", "Kuala Lumpur", 3.2, sList32, jList32));

        DoublyLinkedList<Skill> sList33 = new DoublyLinkedList<>();
        sList33.add(s5);
        DoublyLinkedList<JobDesired> jList33 = new DoublyLinkedList<>();
        jList33.add(jd2);
        jList33.add(jd5);
        applicantList.add(new Applicant("A033", "Ariff Azman", "01266789918", "ariff.azman@gmail.com", "Sarawak", 2.8, sList33, jList33));

        DoublyLinkedList<Skill> sList34 = new DoublyLinkedList<>();
        sList34.add(s7);
        sList34.add(s13);
        DoublyLinkedList<JobDesired> jList34 = new DoublyLinkedList<>();
        jList34.add(jd3);
        applicantList.add(new Applicant("A034", "Loh Jia Wen", "01199458232", "jiawen.loh@gmail.com", "Johor", 3.6, sList34, jList34));

        DoublyLinkedList<Skill> sList35 = new DoublyLinkedList<>();
        sList35.add(s4);
        sList35.add(s6);
        sList35.add(s15);
        DoublyLinkedList<JobDesired> jList35 = new DoublyLinkedList<>();
        jList35.add(jd5);
        applicantList.add(new Applicant("A035", "Izzat Firdaus", "01788412233", "izzat.firdaus@gmail.com", "Selangor", 3.0, sList35, jList35));

        DoublyLinkedList<Skill> sList36 = new DoublyLinkedList<>();
        sList36.add(s12);
        DoublyLinkedList<JobDesired> jList36 = new DoublyLinkedList<>();
        jList36.add(jd4);
        applicantList.add(new Applicant("A036", "Lim Hui Min", "01974563215", "huimin.lim@gmail.com", "Penang", 2.3, sList36, jList36));

        DoublyLinkedList<Skill> sList37 = new DoublyLinkedList<>();
        sList37.add(s2);
        sList37.add(s17);
        DoublyLinkedList<JobDesired> jList37 = new DoublyLinkedList<>();
        jList37.add(jd1);
        jList37.add(jd6);
        applicantList.add(new Applicant("A037", "Nadia Harith", "01487932106", "nadia.harith@gmail.com", "Kuala Lumpur", 3.9, sList37, jList37));

        DoublyLinkedList<Skill> sList38 = new DoublyLinkedList<>();
        sList38.add(s11);
        DoublyLinkedList<JobDesired> jList38 = new DoublyLinkedList<>();
        jList38.add(jd3);
        applicantList.add(new Applicant("A038", "Teoh Zhi Hao", "01239011238", "zhihao.teoh@gmail.com", "Selangor", 2.1, sList38, jList38));

        DoublyLinkedList<Skill> sList39 = new DoublyLinkedList<>();
        sList39.add(s9);
        sList39.add(s6);
        DoublyLinkedList<JobDesired> jList39 = new DoublyLinkedList<>();
        jList39.add(jd5);
        jList39.add(jd2);
        applicantList.add(new Applicant("A039", "Zarina Kamal", "01362297849", "zarina.kamal@gmail.com", "Sarawak", 3.3, sList39, jList39));

        DoublyLinkedList<Skill> sList40 = new DoublyLinkedList<>();
        sList40.add(s8);
        sList40.add(s16);
        DoublyLinkedList<JobDesired> jList40 = new DoublyLinkedList<>();
        jList40.add(jd4);
        applicantList.add(new Applicant("A040", "Chin Mei Yee", "01193476212", "mei.yee@gmail.com", "Johor", 3.7, sList40, jList40));

        DoublyLinkedList<Skill> sList41 = new DoublyLinkedList<>();
        sList41.add(s16);
        sList41.add(s15);
        sList41.add(s8);

        DoublyLinkedList<JobDesired> jList41 = new DoublyLinkedList<>();
        jList41.add(jd4);
        jList41.add(jd1);

        applicantList.add(new Applicant("A041", "Mrs. Kaitlyn Potter", "01718887270", "obailey@hotmail.com", "Selangor", 2.22, sList41, jList41));

        DoublyLinkedList<Skill> sList42 = new DoublyLinkedList<>();
        sList42.add(s15);
        sList42.add(s8);

        DoublyLinkedList<JobDesired> jList42 = new DoublyLinkedList<>();
        jList42.add(jd3);

        applicantList.add(new Applicant("A042", "Stacey Barnes", "01382880650", "elizabeth10@gmail.com", "Johor", 2.68, sList42, jList42));

        DoublyLinkedList<Skill> sList43 = new DoublyLinkedList<>();
        sList43.add(s11);
        sList43.add(s5);
        sList43.add(s16);

        DoublyLinkedList<JobDesired> jList43 = new DoublyLinkedList<>();
        jList43.add(jd5);

        applicantList.add(new Applicant("A043", "Jean Martinez", "01157681354", "sotoleah@yahoo.com", "Selangor", 3.86, sList43, jList43));

        DoublyLinkedList<Skill> sList44 = new DoublyLinkedList<>();
        sList44.add(s10);
        sList44.add(s9);

        DoublyLinkedList<JobDesired> jList44 = new DoublyLinkedList<>();
        jList44.add(jd3);
        jList44.add(jd5);

        applicantList.add(new Applicant("A044", "Patrick Gibson", "01450805504", "shannon74@gmail.com", "Penang", 2.56, sList44, jList44));

        DoublyLinkedList<Skill> sList45 = new DoublyLinkedList<>();
        sList45.add(s12);
        sList45.add(s9);
        sList45.add(s4);

        DoublyLinkedList<JobDesired> jList45 = new DoublyLinkedList<>();
        jList45.add(jd6);

        applicantList.add(new Applicant("A045", "Jose Hawkins", "01136580055", "gardnermiguel@hotmail.com", "Penang", 3.93, sList45, jList45));

        DoublyLinkedList<Skill> sList46 = new DoublyLinkedList<>();
        sList46.add(s6);
        sList46.add(s8);

        DoublyLinkedList<JobDesired> jList46 = new DoublyLinkedList<>();
        jList46.add(jd3);
        jList46.add(jd1);

        applicantList.add(new Applicant("A046", "Dillon Hunter", "01614922551", "joseph08@blanchard.com", "Sarawak", 3.73, sList46, jList46));

        DoublyLinkedList<Skill> sList47 = new DoublyLinkedList<>();
        sList47.add(s16);
        sList47.add(s14);

        DoublyLinkedList<JobDesired> jList47 = new DoublyLinkedList<>();
        jList47.add(jd5);

        applicantList.add(new Applicant("A047", "Stephanie Smith", "01462196481", "christopher@gmail.com", "Johor", 1.91, sList47, jList47));

        DoublyLinkedList<Skill> sList48 = new DoublyLinkedList<>();
        sList48.add(s18);
        sList48.add(s5);
        sList48.add(s9);

        DoublyLinkedList<JobDesired> jList48 = new DoublyLinkedList<>();
        jList48.add(jd5);

        applicantList.add(new Applicant("A048", "Brandi Zamora", "01398231469", "zvasquez@scott.info", "Sarawak", 1.96, sList48, jList48));

        DoublyLinkedList<Skill> sList49 = new DoublyLinkedList<>();
        sList49.add(s11);
        sList49.add(s12);
        sList49.add(s15);

        DoublyLinkedList<JobDesired> jList49 = new DoublyLinkedList<>();
        jList49.add(jd3);
        jList49.add(jd2);

        applicantList.add(new Applicant("A049", "Thomas Pittman", "01436740184", "villanue@gmail.com", "Sarawak", 3.45, sList49, jList49));

        DoublyLinkedList<Skill> sList50 = new DoublyLinkedList<>();
        sList50.add(s5);
        sList50.add(s17);

        DoublyLinkedList<JobDesired> jList50 = new DoublyLinkedList<>();
        jList50.add(jd2);

        applicantList.add(new Applicant("A050", "Cody Wilson", "01173167971", "alexis01@gmail.com", "Kuala Lumpur", 3.79, sList50, jList50));


        isInitialized = true;
    }
    public DoublyListInterface<Job> getJobList() { return jobList; }
    public DoublyListInterface<Company> getCompanyList() { return companyList; }
    public DoublyListInterface<Applicant> getApplicantList() { return applicantList; }
    public DoublyListInterface<Skill> getSkillList() { return skillList; }
    public DoublyListInterface<JobDesired> getJobDesiredList() { return jobDesiredList; }
    public DoublyListInterface<Schedule> getScheduleList() { return scheduleList; }
    public DoublyListInterface<Interview> getInterviewList() { return interviewList; }
} 

    


    
    
    
    

