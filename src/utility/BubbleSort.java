package utility;

import adt.DoublyListInterface;
import entity.Applicant;
import entity.Interview;
import entity.Schedule;

public class BubbleSort {

    public static void bubbleSort(DoublyListInterface<Applicant> list, String by) {
        int n = list.getNumberOfEntries();
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= n - i; j++) {
                Applicant a1 = list.getEntry(j);
                Applicant a2 = list.getEntry(j + 1);
                boolean shouldSwap = false;

                switch (by) {
                    case "id":
                        shouldSwap = a1.getApplicantID().compareTo(a2.getApplicantID()) > 0;
                        break;
                    case "name":
                        shouldSwap = a1.getApplicantName().compareToIgnoreCase(a2.getApplicantName()) > 0;
                        break;
                    case "cgpa":
                        shouldSwap = a1.getCgpa() > a2.getCgpa(); // ascending
                        break;
                }

                if (shouldSwap) {
                    list.swap(j, j + 1);
                }
            }
        }
    }
    
        public static void sortInterviewByDate(DoublyListInterface<Interview> interviewList) {
        int n = interviewList.getNumberOfEntries();
        boolean sorted;

        for (int pass = 1; pass < n; pass++) {
            sorted = true;
            for (int i = 1; i < n - pass; i++) {
                Interview first = interviewList.getEntry(i);
                Interview second = interviewList.getEntry(i + 1);

                if (first.getInterviewDate().compareTo(second.getInterviewDate()) > 0) {
                    interviewList.replace(i, second);
                    interviewList.replace(i + 1, first);
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    } 
    
    public static void sortScheduleByTime(DoublyListInterface<Schedule> scheduleList) {
        int n = scheduleList.getNumberOfEntries();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Schedule s1 = scheduleList.getEntry(j);
                Schedule s2 = scheduleList.getEntry(j + 1);
                if (s1.getScheduleTime().compareTo(s2.getScheduleTime()) > 0) {
                    scheduleList.replace(j, s2);
                    scheduleList.replace(j + 1, s1);
                }
            }
        }
    }

}
