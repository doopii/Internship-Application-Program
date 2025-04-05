package utility;

import adt.DoublyListInterface;
import entity.Applicant;

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

}
