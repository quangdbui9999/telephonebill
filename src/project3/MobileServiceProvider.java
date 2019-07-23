/*
 *  CSC-122 SP 2018 PROJECT:
 *  Programmer: Quang Bui
 *  Due Date: Thursday, March 22th, 2018
 *  Description: The MobileServiceProvider class is an array of 
 * MonthlyBill objects. Each MonthlyBill object
 * is an element of the ArrayList<MonthlyBill>. The
 * MobileServiceProvider will be carry out all method is defined in the
 * MonthlyBill class.
 */

package project3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programmed by: Quang Bui
 * Due Date: Thursday, March 22th, 2018
 * Description: The MobileServiceProvider class is an array of 
 * MonthlyBill objects. Each MonthlyBill object
 * is an element of the ArrayList<MonthlyBill>. The
 * MobileServiceProvider will be carry out all method is defined in the
 * MonthlyBill class.
 */
public class MobileServiceProvider {
    private final ArrayList<MonthlyBill> listCustomer
            = new ArrayList<>();
    
    /**
     * Mutator: inputInformation()
     * Pre-condition: The input() method of the MonthlyBill class
     * must be defined.
     * Post-condition: This method will give users 2 choose
     * 1> Enter another customer. (enter another bill)
     * 0> Exit. (do not input the bill).
     * This method will notice the phonenumber, if the phone number that
     * users input is not the same with the phone number in ArrayList,
     * this bill will be add to the tail of ArrayList. Otherwise, 
     * if the phone number that users input is the same with the phone 
     * number in ArrayList, this bill will not be add to the tail 
     * of ArrayList and have problem to input another phone number.
     */
    public void inputInformation(){
        Scanner sc = new Scanner(System.in);
        int choose;
        
        do{
            System.out.println("==========================");
            System.out.println("1. Enter another customer.");
            System.out.println("0. Exit");
            System.out.println("==========================");

            do{
                System.out.print("Enter the number (0 or 1): ");
                choose = Integer.parseInt(sc.nextLine());

                if(choose > 1 || choose < 0){
                    System.err.println("Please choose 0 or 1.");
                }
            }while(choose > 1 || choose < 0);

            switch(choose){
                case 1:{
                    MonthlyBill customer = 
                            new MonthlyBill();
                    boolean check = true;
                    customer.input();
                    for(int i = 0; i < listCustomer.size(); i++){
                        if(customer.getCustomerPhoneNumber().equals
                        (listCustomer.get(i).getCustomerPhoneNumber())){
                            check = false;
                        }
                    }

                    if(check == false){
                        System.err.println("The phone number that you "
                                + "entered is the same number of another "
                                + "person.\nCan you enter another "
                                + "number, please?");
                    }
                    else{
                        this.listCustomer.add(customer);
                    }
                    break;
                }
                    
                case 0:{
                    break;
                }
            }
        }while(choose != 0);
    }
    
    /**
     * Accessor: toString()
     * @return result
     * Pre-condition: The toString() method of the MonthlyBill class
     * must be defined.
     * Post-condition: Output all the information of the bill in an array
     * (with 1 or 2 or many bills)
     */
    public String toString(){
        String result = "";
        result += "\n";
        for(int i = 0; i < listCustomer.size(); i++){
            result += listCustomer.get(i);
            result += "\n\n";
        }
        return result;
    }
}