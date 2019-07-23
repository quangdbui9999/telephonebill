/*
 *  CSC-122 SP 2018 PROJECT:
 *  Programmer: Quang Bui
 *  Due Date: Thursday, March 22th, 2018
 * Description: The CheckCustomerPhoneNumber enumerated type include
 * 4 attribute: 
 * - validPhoneNumber (the phone number is valid), 
 * - areaCode (three digits numbers of phone number is not belong 
 * the states: New York, Delaware, Massachusetts, Virginia, Oregon
 * New Jersey, California, Texas, Washington, Pennsylvania, and Florida.
 * - digitNumber: check all characters in the customerPhoneNumber
 * instance variable are digits
 * lengthNumber: The length of customerPhoneNumber instance variable 
 * must equal to 10
 */

package project3;

/**
 * Programmed by: Quang Bui
 * Due Date: Thursday, March 22th, 2018
 * Description: The CheckCustomerPhoneNumber enumerated type include
 * 4 attribute: 
 * - validPhoneNumber (the phone number is valid), 
 * - areaCode (three digits numbers of phone number is not belong 
 * the states: New York, Delaware, Massachusetts, Virginia, Oregon
 * New Jersey, California, Texas, Washington, Pennsylvania, and Florida.
 * - digitNumber: check all characters in the customerPhoneNumber
 * instance variable are digits
 * lengthNumber: The length of customerPhoneNumber instance variable 
 * must = 10
 */
public enum CheckCustomerPhoneNumber {
    validPhoneNumber, areaCode, digitNumber, lengthNumber
}
