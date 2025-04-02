package dao;

import adt.*;
import entity.*;
import java.util.Iterator;

/**
 *
 * @author yijia
 */
public class DataInitializer {

    // Keep DAO instances for backward compatibility
    //private final CompanyDAO companyDAO = new CompanyDAO();
    //private final JobDAO jobDAO = new JobDAO();
    //private final SkillDAO skillDAO = new SkillDAO();
    
    // Static collections to hold the hardcoded data
    private static DoublyListInterface<Company> companyList;
    private static DoublyListInterface<Job> jobList;
//    private static ListInterface<Skill> skillList;
    
    // Flag to check if data is already initialized
    private static boolean isInitialized = false;


    public void initializeData() {
        if (isInitialized) {
            System.out.println(" ~~~ Data already initialized ~~~ ");
            return;
        }

        companyList = new DoublyLinkedList<>();
        jobList = new DoublyLinkedList<>();
//        skillList = new LinkedList<>();

        // Create Companies
        Company c1 = new Company("C1001", "TechCorp", "New York");
        Company c2 = new Company("C1002", "DataPro", "San Francisco");
        Company c3 = new Company("C1003", "Designify", "Remote");
        Company c4 = new Company("C1004", "BrandBoost", "Los Angeles");
        Company c5 = new Company("C1005", "SecureNet", "Washington D.C.");

        // Create Jobs with JobRequirements
        Job j1 = new Job("J1001", "Software Engineer", 1500, 6, "Inactive",
                new JobRequirement("J1001", "Computer Science", 3.2, "S101, S104"));
        Job j2 = new Job("J1002", "Data Analyst", 1200, 3, "Active",
                new JobRequirement("J1002", "Data Science", 3.5, "S102, S105"));
        Job j3 = new Job("J1003", "UX Designer", 1000, 4, "Active",
                new JobRequirement("J1003", "Graphic Design", 3.0, "S106, S110"));
        Job j4 = new Job("J1004", "Marketing Manager", 1800, 6, "Active",
                new JobRequirement("J1004", "Marketing", 3.3, "S102, S107"));
        Job j5 = new Job("J1005", "Cybersecurity Specialist", 2000, 6, "Active",
                new JobRequirement("J1005", "Cybersecurity", 3.7, "S103, S105"));
        Job j6 = new Job("J1006", "Backend Developer", 1600, 6, "Active",
                new JobRequirement("J1006", "Software Engineering", 3.1, "S101, S104"));
        Job j7 = new Job("J1007", "Cloud Engineer", 1700, 6, "Active",
                new JobRequirement("J1007", "Cloud Computing", 3.4, "S104, S105"));
        Job j8 = new Job("J1008", "AI Researcher", 2200, 6, "Active",
                new JobRequirement("J1008", "Artificial Intelligence", 3.8, "S103, S105"));

        // Assign Jobs to Companies
        c1.addJob(j1);
        c1.addJob(j6);
        c2.addJob(j2);
        c3.addJob(j3);
        c4.addJob(j4);
        c5.addJob(j5);
        c5.addJob(j7);
        c5.addJob(j8);

        // Add Jobs to the Job List (for separate retrieval)
        jobList.add(j1);
        jobList.add(j2);
        jobList.add(j3);
        jobList.add(j4);
        jobList.add(j5);
        jobList.add(j6);
        jobList.add(j7);
        jobList.add(j8);

        // Save Companies to the list
        companyList.add(c1);
        companyList.add(c2);
        companyList.add(c3);
        companyList.add(c4);
        companyList.add(c5);

        // Initialize Skills
//        initializeSkills();
        
        // Set flag to indicate data is initialized
        isInitialized = true;

        System.out.println("\n ~~~ All entity data has been initialized. ~~~");
        
        // For backward compatibility, also save to files
        //companyDAO.saveToFile(companyList);
        //jobDAO.saveToFile(jobList);
        //skillDAO.saveToFile(skillList);
    }

//    private void initializeSkills() {
//        skillList.add(new Skill("S101", "Language Skill", "Fluent in English and Chinese"));
//        skillList.add(new Skill("S102", "Language Skill", "Multilingual (e.g., Malay, Korean, Japanese, Hindi)"));
//        skillList.add(new Skill("S103", "Language Skill", "Strong communication skills in multiple languages"));
//        skillList.add(new Skill("S104", "Programming Skill", "Basic programming skills (e.g., HTML, CSS, Python)"));
//        skillList.add(new Skill("S105", "Programming Skill", "Professional programming skills (e.g., C#, Java, Python)"));
//        skillList.add(new Skill("S106", "Creative & Media Skill", "Writing skills (e.g., content writing, copywriting)"));
//        skillList.add(new Skill("S107", "Creative & Media Skill", "Basic video editing skills (e.g., CapCut, iMovie)"));
//        skillList.add(new Skill("S108", "Creative & Media Skill", "Professional video editing (e.g., Adobe Premiere)"));
//        skillList.add(new Skill("S109", "Creative & Media Skill", "Photography skills (e.g., portrait, event photography)"));
//        skillList.add(new Skill("S110", "Creative & Media Skill", "Graphic design skills"));
//
//        System.out.println(" ~~~ Skills initialized successfully. ~~~");
//    }
    
    public DoublyListInterface<Company> getCompanyList() {
        if (!isInitialized) {
            initializeData();
        }
        return companyList;
    }

    public DoublyListInterface<Job> getJobList() {
        if (!isInitialized) {
            initializeData();
        }
        return jobList;
    }

//    public ListInterface<Skill> getSkillList() {
//        if (!isInitialized) {
//            initializeData();
//        }
//        return skillList;
//    }

//    public void displaySkills(ListInterface<Skill> skillList) {
//        // Create an iterator to traverse the skill list
//        Iterator<Skill> iterator = skillList.getIterator();
//
//        System.out.println(" === All Available Skills: ===");
//
//        // Iterate through the list and display each skill's details
//        while (iterator.hasNext()) {
//            Skill skill = iterator.next();
//            System.out.println("Skill ID: " + skill.getId());
//            System.out.println("Category: " + skill.getCategory());
//            System.out.println("Description: " + skill.getDescription());
//            System.out.println("------------------------------");
//        }
//    }

    public static void main(String[] args) {
        DataInitializer initializer = new DataInitializer();
        initializer.initializeData();
//        initializer.displaySkills(initializer.getSkillList());
    }
    
    /*public void initializeData() {
           // Initialize Company Data with Jobs Inside
           DoublyListInterface<Company> companyList = new DoublyLinkedList<>();
           DoublyListInterface<Job> jobList = new DoublyLinkedList<>();
           ListInterface<Skill> skillList = new LinkedList<>();

           // Create Companies
           Company c1 = new Company("C1001", "TechCorp", "New York");
           Company c2 = new Company("C1002", "DataPro", "San Francisco");
           Company c3 = new Company("C1003", "Designify", "Remote");
           Company c4 = new Company("C1004", "BrandBoost", "Los Angeles");
           Company c5 = new Company("C1005", "SecureNet", "Washington D.C.");

           // Create Jobs with JobRequirements
           Job j1 = new Job("J1001", "Software Engineer", 1500, 6, "Inactive",
                   new JobRequirement("J1001", "Computer Science", 3.2, "S101, S104"));
           Job j2 = new Job("J1002", "Data Analyst", 1200, 3, "Active",
                   new JobRequirement("J1002", "Data Science", 3.5, "S102, S105"));
           Job j3 = new Job("J1003", "UX Designer", 1000, 4, "Active",
                   new JobRequirement("J1003", "Graphic Design", 3.0, "S106, S110"));
           Job j4 = new Job("J1004", "Marketing Manager", 1800, 6, "Active",
                   new JobRequirement("J1004", "Marketing", 3.3, "S102, S107"));
           Job j5 = new Job("J1005", "Cybersecurity Specialist", 2000, 6, "Active",
                   new JobRequirement("J1005", "Cybersecurity", 3.7, "S103, S105"));
           Job j6 = new Job("J1006", "Backend Developer", 1600, 6, "Active",
                   new JobRequirement("J1006", "Software Engineering", 3.1, "S101, S104"));
           Job j7 = new Job("J1007", "Cloud Engineer", 1700, 6, "Active",
                   new JobRequirement("J1007", "Cloud Computing", 3.4, "S104, S105"));
           Job j8 = new Job("J1008", "AI Researcher", 2200, 6, "Active",
                   new JobRequirement("J1008", "Artificial Intelligence", 3.8, "S103, S105"));

           // Assign Jobs to Companies
           c1.addJob(j1);
           c1.addJob(j6);
           c2.addJob(j2);
           c3.addJob(j3);
           c4.addJob(j4);
           c5.addJob(j5);
           c5.addJob(j7);
           c5.addJob(j8);

           // Add Jobs to the Job List (for separate retrieval)
           jobList.add(j1);
           jobList.add(j2);
           jobList.add(j3);
           jobList.add(j4);
           jobList.add(j5);
           jobList.add(j6);
           jobList.add(j7);
           jobList.add(j8);

           // Save Companies
           companyList.add(c1);
           companyList.add(c2);
           companyList.add(c3);
           companyList.add(c4);
           companyList.add(c5);
           companyDAO.saveToFile(companyList);

           // Save Job List Separately
           jobDAO.saveToFile(jobList);

           // Initialize and Save Skills
           initializeSkills(skillList);
           skillDAO.saveToFile(skillList);

           System.out.println("\n ~~~ All entity data has been initialized and saved. ~~~");
       }

       private void initializeSkills(ListInterface<Skill> skillList) {
           skillList.add(new Skill("S101", "Language Skill", "Fluent in English and Chinese"));
           skillList.add(new Skill("S102", "Language Skill", "Multilingual (e.g., Malay, Korean, Japanese, Hindi)"));
           skillList.add(new Skill("S103", "Language Skill", "Strong communication skills in multiple languages"));
           skillList.add(new Skill("S104", "Programming Skill", "Basic programming skills (e.g., HTML, CSS, Python)"));
           skillList.add(new Skill("S105", "Programming Skill", "Professional programming skills (e.g., C#, Java, Python)"));
           skillList.add(new Skill("S106", "Creative & Media Skill", "Writing skills (e.g., content writing, copywriting)"));
           skillList.add(new Skill("S107", "Creative & Media Skill", "Basic video editing skills (e.g., CapCut, iMovie)"));
           skillList.add(new Skill("S108", "Creative & Media Skill", "Professional video editing (e.g., Adobe Premiere)"));
           skillList.add(new Skill("S109", "Creative & Media Skill", "Photography skills (e.g., portrait, event photography)"));
           skillList.add(new Skill("S110", "Creative & Media Skill", "Graphic design skills"));

           System.out.println(" ~~~ Skills initialized successfully. ~~~");
       }

       public void displaySkills(ListInterface<Skill> skillList) {
           // Create an iterator to traverse the skill list
           Iterator<Skill> iterator = skillList.getIterator();

           System.out.println("All Available Skills:");

           // Iterate through the list and display each skill's details
           while (iterator.hasNext()) {
               Skill skill = iterator.next();
               System.out.println("Skill ID: " + skill.getId());
               System.out.println("Category: " + skill.getCategory());
               System.out.println("Description: " + skill.getDescription());
               System.out.println("------------------------------");
           }
       }

       public static void main(String[] args) {
           DataInitializer initializer = new DataInitializer();
           initializer.initializeData();
           ListInterface<Skill> skillList = new LinkedList<>();
           initializer.initializeSkills(skillList);
           initializer.displaySkills(skillList);
       }
   }*/
}