/*
 *  CSC-122 SP 2018 PROJECT:
 *  Programmer: Quang Bui
 *  Due Date: Thursday, March 22th, 2018
 * Description: The MonthlyBill class include the information 
 * of the customer: phone number, kind of plan that customer will use, 
 * the additional line, the minutes used, the amounts of data used, and 
 * some static final variable is declared with the values are not change 
 * to serves the MonthlyBill class. The MonthlyBill 
 * include 3 plans:  
 * Premier Family Plan, Deluxe Family Plan, and Standard Family Plan. 
 * Each plan has the bill month, additional line, shared family minutes 
 * of talk time, and shared data is different. 
 * The MonthlyBill class also carry out to compute the cost 
 * of minutes and data used that exceed the plan allotment depend on the 
 * Plan (data and minutes). $0.4 for per minutes, and $15.00 per 500 MB.
 */

package project3;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Programmed by: Quang Bui
 * Due Date: Thursday, March 22th, 2018
 * Description: The MonthlyBill class include the information 
 * of the customer: phone number, kind of plan that customer will use, 
 * the additional line, the minutes used, the amounts of data used, and 
 * some static final variable is declared with the values are not change 
 * to serves the MonthlyBill class. The MonthlyBill 
 * include 3 plans:  
 * Premier Family Plan, Deluxe Family Plan, and Standard Family Plan. 
 * Each plan has the bill month, additional line, shared family minutes 
 * of talk time, and shared data is different. 
 * The MonthlyBill class also carry out to compute the cost 
 * of minutes and data used that exceed the plan allotment depend on the 
 * Plan (data and minutes). $0.4 for per minutes, and $15.00 per 500 MB.
 */
public class MonthlyBill {
    /* customerPhoneNumber variable's length must be 10, have the
    area code appropriated with some states, and all character is
    digital number */
    private String customerPhoneNumber;
    // kindPlan include Premier, Deluxe, Standard;
    private FamilyPlan kindPlan;
    // checkPhone include validPhoneNumber, areaCode, 
    // digitNumber, lengthNumber
    private CheckCustomerPhoneNumber checkPhone;
    // additionalLine: Standard and Deluxe (0 - 3), Premier (0 - 4)
    private int additionalLine;
    private int minutesUsed; // must be > 0
    private double dataUsed; // must be > 0
    private static final double costLine = 10.0d;
    private static final int minutesPremier = 1200;
    private static final int minutesDeluxe = 800;
    private static final int minutesStandard = 500;
    private static final double datalimitPremier = 10.0d;
    private static final double datalimitDeluxe = 4.0d;
    private static final double datalimitStandard = 2.0d;
    /* periodOfNotice is the distance between pulish the bill and 
    the due date to pay the bill */
    private static final int periodOfNotice = 20;
    /* If the minutes used is exceed to litmit, each minues is 
    computed $0.40 */
    private static final double allotmentCostMinutes = 0.40d;
    /* If the data used is exceed to litmit, each data is 
    computed 500 MB / $15.00 */
    private static final double exceedData = 500;
    private static final double billedCostData = 15.0d;
    // 1 GB = 1024 MB
    private static final int changeGBToMB = 1024;
    private static final double perMonthPremier = 149.0d;
    private static final double perMonthDeluxe = 129.0d;
    private static final double perMonthStandard = 99.0d;
    
    /**
     * D E F A U L T   C O N S T R U C T O R
     * Pre-condition: the customerPhoneNumber, kindPlan, checkPhone,
     * additionalLine, minutesUsed. and dataUsed variable must be 
     * declared and they are placed inside the class MonthlyBill
     * Post-condition: Create the valid for some variables: 
     * customerPhoneNumber, kindPlan, checkPhone,
     * additionalLine, minutesUsed and dataUsed
     */
    public MonthlyBill(){
        customerPhoneNumber = "6091234321";
        kindPlan = FamilyPlan.Standard;
        checkPhone = CheckCustomerPhoneNumber.validPhoneNumber;
        additionalLine = 3;
        minutesUsed = 502;
        dataUsed = 3.00;
    }
    
    /**
     * Mutator: input()
     * Pre-condition: the checkPhoneNumber() method must be defined to
     * check the condition for customerPhoneNumber variable. The
     * displayTypeOfPlan() method must be defined to input the
     * additionalLine variable
     * This method should import java.util.Scanner;
     * Post: The input() method carries out to input the values for
     * customerPhoneNumber, kindPlan, checkPhone,
     * additionalLine, minutesUsed and dataUsed variables. In this 
     * method: customerPhoneNumber must be checked by the 
     * checkPhoneNumber() method, and the minutesUsed and 
     * dataUsed variables must be > 0.
     */
    public void input(){
        Scanner cin = new Scanner(System.in);
        int choose = 0;
        
        do{
            System.out.print("Enter phone number"
                    + " (the length equal to 10): ");
            customerPhoneNumber = cin.nextLine();
            
            if(this.checkPhoneNumber() != checkPhone.validPhoneNumber){
                this.displayProblemPhoneNumber();
            }
        }while(this.checkPhoneNumber() != checkPhone.validPhoneNumber);
        
        do{
            this.displayTypeOfPlan();
            System.out.print("Please choose kind of plan to use: ");
            choose = Integer.parseInt(cin.nextLine());
            
            if(choose > 3 || choose < 1){
                System.err.println("Family Plan is invalid.");
                System.err.println("Can you check and "
                        + "enter again, please?");
            }
        }while(choose > 3 || choose < 1);
        
        switch(choose){
            case 1:{
                kindPlan = FamilyPlan.Premier;
                this.inputAdditionalLine(4);
                break;
            }
            case 2:{
                kindPlan = FamilyPlan.Deluxe;
                this.inputAdditionalLine(3);
                break;
            }
            
            case 3:{
                kindPlan = FamilyPlan.Standard;
                this.inputAdditionalLine(3);
                break;
            }
        }

        do{
            System.out.print("Enter the minutes used: ");
            minutesUsed = Integer.parseInt(cin.nextLine());
            if(minutesUsed <= 0){
                System.err.println("The minutes used is invalid.");
                System.err.println("Can you check and "
                        + "enter again, please?");
            }
        }while(minutesUsed <= 0);
        
        do{
            System.out.print("Enter the data used (GB): ");
            dataUsed = Double.parseDouble(cin.nextLine());
            
            if(dataUsed <= 0){
                System.err.println("The data used is invalid.");
                System.err.println("Can you check and "
                        + "enter again, please?");
            }
        }while(dataUsed <= 0);
    }
    
    /**
     * Mutator: inputAdditionalLine(int numberPhones)
     * @param numberPhones 
     * Pre-condition: The additionalLine variable must be declared in
     * this class and exist.
     * Post-condition: The inputAdditionalLine(int numberPhones) method
     * just input the value for additionalLine variable. The
     * numberPhones variable is passed as a parameter to determine
     * the max number of phone of each plan.
     */
    private void inputAdditionalLine(int numberPhones){
        Scanner cin = new Scanner(System.in);
        do{
            System.out.print("Enter additional line "
                    + "(0 - " + numberPhones + "): ");
            additionalLine = Integer.parseInt(cin.nextLine());
            
            if(additionalLine > numberPhones || additionalLine < 0){
                System.err.println("The additional Line is invalid.");
                System.err.println("Can you check and "
                        + "enter again, please?");
            }
        }while(additionalLine > numberPhones || additionalLine < 0);
    }
    
    /**
     * Accessor: calculateCostLine()
     * @return CostLine
     * Pre-condition: The additionalLine variable must be declared in
     * this class and exist. The calculateCostLine() is a static
     * final variable in this class and initialize with 10.0d
     * Post-condition: The calculateCostLine method will compute
     * the cost of line (addition lines) by additionalLine * costLine;
     */
    private double calculateCostLine(){
        return additionalLine * costLine;
    }
    
    /**
     * Accessor: calculateMonthlyBills()
     * @return MonthlyBills
     * Pre-condition: The billExceedData(), billExceedMinutes(),
     * calculateCostLine(), and calculateRate() must be defined
     * Post-condition: The calculateMonthlyBills() method will computed
     * the money that each plan must pay per month by
     * MonthlyBills = monthis.billExceedData() + this.billExceedMinutes()
     *         + this.calculateCostLine() + this.calculateRate()
     * and return the money that each plan must pay for service.
     */
    private double calculateMonthlyBills(){
        return (this.billExceedData() + this.billExceedMinutes()
                + this.calculateCostLine() + this.calculateRate());
    }
    
    /**
     * Accessor displayInformation()
     * @return result
     * Pre-condition: The kindPlan, additionalLine, minutesUsed, and
     * dataUsed variables must be initialized. The handlePhoneNumber(),
     * displayArea(), calculateMonthlyBills(), calculateRate(),
     * calculateCostLine(), billExceedMinutes(), and billExceedData()
     * methods must be defined.
     * This method should import java.text.DecimalFormat;
     * Post-condition: the displayInformation() show all the information
     * of the bill (do not have the date publish and due date to 
     * pay the bill)
     */
    private String displayInformation(){
        DecimalFormat fmt = new DecimalFormat("0.00");
        String result = "";
        
        result += "\nCustomer ID: " + handlePhoneNumber();
        result += "\tArea: " + displayArea();
        result += "\t\tTotal Due: $" + 
                fmt.format(this.calculateMonthlyBills());
        result += "\nPlan: " + kindPlan;
        result += "\t\t\tRate: $" + fmt.format(this.calculateRate());
        result += "\nAdditional Lines: " + additionalLine;
        result += "\t$ " + fmt.format(calculateCostLine());
        result += "\n\nShared Minutes Used: \t" + minutesUsed + " minutes";
        result += "\tCost: $" + fmt.format(billExceedMinutes());
        result += "\nShared Data Used: \t" + dataUsed + " GB";
        result += "\t\tCost: $" + fmt.format(billExceedData()) + "\n\n";
        
        return result;
    }
    
    /**
     * Accessor: exceedCostEachMB()
     * @return the cost of each MB
     * Pre-condition: The static final variables: billedCostData and
     * exceedData must be defined.
     * Post-condition: The method exceedCostEachMB() will compute and 
     * return the cost of each MB by billedCostData / exceedData;
     */
    private double exceedCostEachMB(){
        return billedCostData / exceedData;
    }
    
    /**
     * Accessor: billExceedData()
     * @return money
     * Pre-condition: the static final variables: datalimitPremier,
     * datalimitDeluxe, changeGBToMB, and datalimitStandard must 
     * be defined. The dataUsed and kindPlan must be defined. 
     * The exceedCostEachMB() method must be defined.
     * Post-condition: This method will compute the money when the
     * data of each plan use exceed the data limit depend on each
     * plan. The data limit of each plan is: Premier is 10.0 GB,
     * Deluxe is 4.0 GB, and Standard is 2.0 GB.
     * If the data used is exceed:
     * - The first time, this method will get the data limit by
     * (datause - datalimit)* changeGBToMB = exceedMB
     * - After that, they use the result of exceedCostEachMB() multiply
     * with exceedMB and assign to money
     * If the dataUse is not exceed the data limit, the money is zeo
     */
    private double billExceedData(){
        double money = 0.0;
        double exceedMB = 0.0;
        switch(kindPlan){
            case Premier:{
                if(dataUsed <= datalimitPremier){
                    money = 0.0;
                }else if(dataUsed > datalimitPremier){
                    exceedMB = (dataUsed - datalimitPremier)
                            * changeGBToMB;
                    money = exceedMB * exceedCostEachMB();
                }
                break;
            }
            case Deluxe:{
                if(dataUsed <= datalimitDeluxe){
                    money = 0.0;
                }else if(dataUsed > datalimitDeluxe){
                    exceedMB = (dataUsed - datalimitDeluxe)
                            * changeGBToMB;
                    money = exceedMB * exceedCostEachMB();
                }
                break;
            }
            case Standard:{
                if(dataUsed <= datalimitStandard){
                    money = 0.0;
                }else if(dataUsed > datalimitStandard){
                    exceedMB = (dataUsed - datalimitStandard)
                            * changeGBToMB;
                    money = exceedMB * exceedCostEachMB();
                }
                break;
            }
        }
        
        return money;
    }
    
    /**
     * Accessor: billExceedMinutes
     * @return money
     * Pre-condition: The static final variable: minutesPremier,
     * minutesDeluxe, and minutesStandard allotmentCostMinutes must 
     * be defined with value depend on the plan. The minutesUsed
     * variable must be defined.
     * Post-condition: This method will compute the exceed minutes, with
     * each minute is $0.4. If the minutes used is not exceed the
     * minutes limit(depend on the plan) the money will be 0.
     * If the minutes used is exceed the minutes limit, each minute 
     * exceed will be computed $0.4.
     */
    private double billExceedMinutes(){
        double money = 0.0;
        
        switch(kindPlan){
            case Premier:{
                if(minutesUsed <= minutesPremier){
                    money = 0.0d;
                }else if(minutesUsed > minutesPremier){
                    money = (double)(minutesUsed - minutesPremier)
                            * allotmentCostMinutes;
                }
                break;
            }
            case Deluxe:{
                if(minutesUsed <= minutesDeluxe){
                    money = 0.0d;
                }else if(minutesUsed > minutesDeluxe){
                    money = (double)(minutesUsed - minutesDeluxe)
                            * allotmentCostMinutes;
                }
                break;
            }
            case Standard:{
                if(minutesUsed <= minutesStandard){
                    money = 0.0d;
                }else if(minutesUsed > minutesStandard){
                    money = (minutesUsed - minutesStandard)
                            * allotmentCostMinutes;
                }
                break;
            }
        }
        
        return money;
    }
    
    /**
     * Mutator: displayTypeOfPlan()
     * Pre-condition: none
     * Post-condition: display type of plan
     */
    private void displayTypeOfPlan(){
        System.out.println("==============================="
                + "================");
        System.out.println("|\t1. Premier Family Plan.     "
                + "          |");
        System.out.println("|\t2. Deluxe Family Plan.    "
                + "            |");
        System.out.println("|\t3. Standard Family Plan.  "
                + "            |");
        System.out.println("===================================="
                + "===========");
    }
    
    /**
     * Accessor: calculateRate()
     * @return money
     * Pre-condition: The static final variables: perMonthPremier,
     * perMonthDeluxe, and perMonthStandard must be defined.
     * Post-condition: This metho will computer the rate of each plan
     * Deluxe: $129.00
     * Premier: $149.00
     * Standard: $99.00
     */
    private double calculateRate(){
        double money = 0.0;
        
        switch(kindPlan){
            case Premier:{
                money = perMonthPremier;
                break;
            }
            case Deluxe:{
                money = perMonthDeluxe;
                break;
            }
            case Standard:{
                money = perMonthStandard;
                break;
            }
        }
        
        return money;
    }
    
    /**
     * Accessor: toString()
     * @return result
     * Pre-condition: The begin(), dueDate(), displayInformation(),
     * and end() methods must be defined.
     * Post-condition: This method just output all information
     * of the bill.
     */
    public String toString(){
        String result = "";
        
        result += begin();
        result += dueDate();
        result += displayInformation();
        result += end();
        
        return result;
    }
    
    /**
     * Accessor: begin()
     * @return result
     * Pre-condition: none
     * Post-condition: this method output the title of the bill
     */
    private String begin(){
        String result = "";
        result += "***********************************************"
                + "************************\n";
        result += "\t\t\tDisKonnect Mobile Service\n\n";
        return result;
    }
    
    /**
     * Accessor: end()
     * @return result
     * Pre-condition: none
     * Post-condition: this method just output the conclusion
     * of the bill.
     */
    private String end(){
        String result = "";
        result += "Please pay full amount by the due date.\n";
        result += "***********************************************"
                + "************************\n";
        return result;
    }
    
    /**
     * Accessor: dueDate()
     * @return result
     * Pre-condition: The users should 
     * import java.text.SimpleDateFormat; import java.util.Calendar;
     * to get the date. The static final variable: periodOfNotice
     * must be defined to 20.
     * Post-condition: this method will get the date (present to run
     * the application) and return the due date to pay the bill
     * (after 20 days from the date the programmers run application)
     */
    private String dueDate(){
        String result = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        // getInstance() method: returns a Calendar object whose calendar 
        // fields have been initialized with the current date and time
        Calendar dateNow = Calendar.getInstance();
        //System.out.println("Date now: " 
        //        + dateFormat.format(dateNow.getTime()));
        dateNow.add(Calendar.DATE, periodOfNotice);
        result += "DUE: "
                + dateFormat.format(dateNow.getTime());
        result += "\n";
        return result;
    }
    
    /**
     * Mutator: displayProblemPhoneNumber()
     * Pre-condition: The methods: checkPhoneNumber() must be defined.
     * Post-condition: this method will base on the checkPhoneNumber()
     * method to output all information about the problem when users
     * input the phone number. If users input phone number is invalid,
     * these problems will output.
     */
    private void displayProblemPhoneNumber(){
        checkPhone = this.checkPhoneNumber();
        switch (checkPhone) {
            case lengthNumber:
                System.err.println("The phone number that you entered is "
                        + "not a phone number because its length is not"
                        + " equal 10.");
                break;
            case digitNumber:
                System.err.println("The phone number that you entered is "
                        + "not a phone number. \nMaybe it contains "
                        + "alphabetic, or symbol characters");
                break;
            case areaCode:
                System.err.println("The phone number that you entered "
                        + "have been not supported. \n We just support"
                        + "  for some state: New York, New Jersey, "
                        + "California, Texas, \nWashington, Pennsylvania,"
                        + "\nOregon, Florida, Delaware, "
                        + "Massachusetts, and Virginia.");
                break;
            default:
                break;
        }
    }
    
    /**
     * Accessor: checkPhoneNumber
     * @return checkPhone (enumerated type: instant variable)
     * Pre-condition: the lookUpAreaCode(), checkDigitPhoneNumber(),
     * and checkPhoneLength() must be defined.
     * Post-condition: This method just get the problems about the 
     * phone number: length, digit, and area code. If one of the three
     * methods: lookUpAreaCode(), checkDigitPhoneNumber(),
     * and checkPhoneLength() is return false, they will be assigned
     * to checkPhone enumerated type with respectively.
     */
    private CheckCustomerPhoneNumber checkPhoneNumber(){
        if(this.lookUpAreaCode() == false){
            checkPhone = CheckCustomerPhoneNumber.areaCode;
        }
        if(this.checkDigitPhoneNumber() == true &&
                this.checkPhoneLength() == true &&
                this.lookUpAreaCode() == true){
            checkPhone = CheckCustomerPhoneNumber.validPhoneNumber;
        }
        if(this.checkPhoneLength() == false){
            checkPhone = CheckCustomerPhoneNumber.lengthNumber;
        }
        if(this.checkDigitPhoneNumber() == false){
            checkPhone = CheckCustomerPhoneNumber.digitNumber;
        }
        return checkPhone;
    }
    
    /**
     * Accessor: checkPhoneLength
     * @return true if length() == 10 
     * and false if length() is not equal 10
     * Pre-condition: customerPhoneNumber variable must be initialize
     * Post-condition: this method will return true if length() == 10 
     * and false if length() is not equal 10
     */
    private boolean checkPhoneLength(){
        return (customerPhoneNumber.length() == 10);
    }
    
    /**
     * Accessor: checkDigitPhoneNumber()
     * @return true if customerPhoneNumber variable is all characters
     * and false if customerPhoneNumber variable is not all characters
     * Pre-condition: the users should 
     * import java.util.regex.Matcher; import java.util.regex.Pattern;
     * Post-condition: this method will
     * return true if customerPhoneNumber variable is all characters
     * and false if customerPhoneNumber variable is not all characters
     */
    private boolean checkDigitPhoneNumber(){
        boolean flag = false;
        Pattern model = Pattern.compile("\\d*");
        Matcher fit = model.matcher(this.customerPhoneNumber);
        if(fit.matches()){
            flag = true; // is phone number
        }else{
            flag = false; // is not phone number
        }
        return flag;
    }
    
    /**
     * Accessor: handlePhoneNumber()
     * @return result
     * Pre-condition: The checkPhoneNumber() method must be defined.
     * The customerPhoneNumber variable must be initialized.
     * Post-condition: this method will separate the String
     * customerPhoneNumber to 3 parts by substring() method is
     * defined in the String class. 
     * Part 1: Area code - customerPhoneNumber.substring(0, 3);
     * Part 2: customerPhoneNumber.substring(3, 6);
     * Part 3: customerPhoneNumber.substring(6, 10);
     * and after that connect 3 parts by the format of
     * a phone number
     */
    private String handlePhoneNumber(){
        String result = "";
        String sec1, sec2, sec3;
        if(this.checkPhoneNumber() != checkPhone.validPhoneNumber){
            result = "";
        }else{
            sec1 = customerPhoneNumber.substring(0, 3);
            sec2 = customerPhoneNumber.substring(3, 6);
            sec3 = customerPhoneNumber.substring(6, 10);
            result += "(" + sec1 + ")-" + sec2 + "-" + sec3;
        }
        
        return result;
    }
    
    /**
     * Accessor: getCustomerPhoneNumber()
     * @return customerPhoneNumber
     * Pre-condition: none
     * Post-condition:
     * @return a copy of the MonthlyBill's customerPhoneNumber
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber; 
    }
    
    /**
     * Mutator: 
     * @param inCustomerPhoneNumber 
     * Pre-condition: The checkPhoneNumber() method must be defined.
     * Post-condition: change the value of customerPhoneNumber if
     * checkPhoneNumber() method is returned true. Otherwise, the
     * value of customerPhoneNumber is not change
     */
    public void setCustomerPhoneNumber(String inCustomerPhoneNumber) {
        if(this.checkPhoneNumber() == checkPhone.validPhoneNumber)
            customerPhoneNumber = inCustomerPhoneNumber;
    }

    /**
     * Accessor: getKindPlan()
     * @return kindPlan
     * Pre-condition: none
     * Post-condition: 
     * @return a copy of the MonthlyBill's kindPlan
     */
    public FamilyPlan getKindPlan() {
        return kindPlan;
    }

    /**
     * Mutator: 
     * @param inKindPlan 
     * Pre-condition: none
     * Post-condition: change the value of kindPlan
     */
    public void setKindPlan(FamilyPlan inKindPlan) {
        kindPlan = inKindPlan;
    }

    /**
     * Accessor: getAdditionalLine()
     * @return additionalLine
     * Pre-condition: none
     * Post-condition:
     * @return a copy of the MonthlyBill's additionalLine
     */
    public int getAdditionalLine() {
        return additionalLine;
    }

    /**
     * Mutator: 
     * @param inAdditionalLine 
     * Pre-condition: the kindPlan variable must be initialized one
     * of three plan: Premier, Standard, and deluxe. The inAdditionalLine
     * must be in the range [0 - 4] for Premier plan and [0 - 3] for
     * Standard and Deluxe plan.
     * Post-condition: change the value of additionalLine if kindPlan is 
     * Premier and inAdditionalLine in range [0 - 4] or if kindPlan is 
     * Deluxe or Standard and inAdditionalLine in range [0 - 3].
     * Otherwise, the additionalLine = 0.
     */
    public void setAdditionalLine(int inAdditionalLine) {
        if(0 <= inAdditionalLine && inAdditionalLine <= 4
                && kindPlan == FamilyPlan.Premier){
            additionalLine = inAdditionalLine;
        }else if(0 <= inAdditionalLine && inAdditionalLine <= 3
                && (kindPlan == FamilyPlan.Deluxe
                || kindPlan == FamilyPlan.Standard)){
            additionalLine = inAdditionalLine;
        }else{
            additionalLine = 0;
        }
    }

    /**
     * Accessor: getMinutesUsed()
     * @return minutesUsed
     * Pre-condition: none
     * Post-condition:
     * @return a copy of the MonthlyBill's minutesUsed
     */
    public int getMinutesUsed() {
        return minutesUsed;
    }

    /**
     * Mutator: 
     * @param inMinutesUsed 
     * Pre-condition: inMinutesUsed must be > 0
     * Post-condition: change the value of minutesUsed if 
     * inMinutesUsed > 0; otherwise the minutesUsed = 0
     */
    public void setMinutesUsed(int inMinutesUsed) {
        if(inMinutesUsed > 0)
            minutesUsed = inMinutesUsed;
        else
            minutesUsed = 0;
    }

    /**
     * Accessor: getDataUsed()
     * @return dataUsed
     * Pre-condition: none
     * Post-condition:
     * @return a copy of the MonthlyBill's dataUsed
     */
    public double getDataUsed() {
        return dataUsed;
    }

    /**
     * Mutator: 
     * @param inDataUsed 
     * Pre-condition: inDataUsed must be > 0
     * Post-condition: change the value of dataUsed if inDataUsed > 0,
     * otherwise the dataUsed is 0
     */
    public void setDataUsed(double inDataUsed) {
        if(inDataUsed > 0)
            dataUsed = inDataUsed;
        else
            dataUsed = 0;
    }
    
    /**
     * Accessor: displayArea()
     * @return result
     * Pre-condition: The arrayPhoneNumber() method must be defined.
     * Post-condition: This method will scan the arrayPhoneNumber from 0
     * to (arrayPhoneNumber.length - 1) to find the element (3 first
     * digit of customerPhoneNumber variable). If threeDitgit 
     * variable is matched
     * with 3 first digit of customerPhoneNumber variable, it will be
     * assigned (element = i) with i is a position and scan from 0
     * to (length - 1). After that, the element will be compared with
     * each element in arrayPhoneNumber and get the name of State.
     */
    private String displayArea(){
        String[] arrayPhoneNumber = createArrayPhone();
        String result = "";
        int element = 0; // element to scan in arrayPhoneNumber
        String threeDitgit = customerPhoneNumber.substring(0, 3);
        for(int i = 0; i < arrayPhoneNumber.length; i++){
            if(threeDitgit.equals(arrayPhoneNumber[i])){
                element = i;
            }
        }
        
        if(element >= 0 && element <= 10){
            result += "New York";
        }else if(element >= 11 && element <= 16){
            result += "New Jersey";
        }else if(element >= 17 && element <= 42){
            result += "California";
        }else if(element >= 43 && element <= 62){
            result += "Texas";
        }else if(element >= 63 && element <= 67){
            result += "Washington";
        }else if(element >= 68 && element <= 74){
            result += "Pennsylvania";
        }else if(element >= 75 && element <= 76){
            result += "Oregon";
        }else if(element >= 77 && element <= 91){
            result += "Florida";
        }else if(element == 92){
            result += "Delaware";
        }else if(element >= 93 && element <= 97){
            result += "Massachusetts";
        }else if(element >= 98 
                && element <= arrayPhoneNumber.length - 1){
            result += "Virginia";
        }
        
        return result;
    }
    
    /**
     * Accessor: createArrayPhone()
     * @return an arrayPhoneNumber[] 
     * Pre-condition: user uses the initializer list to create an array
     * Post-condition: this method will return an arrayPhoneNumber[]
     * with each element in this array is the area of each state: 
     * New York, New Jersey, California, Texas, Washington, Pennsylvania,
     * Oregon, Florida, Delaware, Massachusetts, and Virginia
     */
    private String[] createArrayPhone(){
        String[] arrayPhoneNumber = {"212", "315", "516", "518", "585", 
            "607", "631", "716", "718", "845", "914", "201", "609", 
            "732", "856", "908", "973", "209", "213", "310", "323",
            "408", "415", "510", "530", "559", "562", "619", "626",
            "650", "661", "707", "714", "760", "805", "818", "831",
            "858", "909", "916", "925", "949", "951", "210", "214", 
            "254", "281", "325", "361", "409", "432", "512", "713",
            "806", "817", "830", "903", "915", "936", "940", "956",
            "972" ,"979", "206", "253", "360", "425", "509", "215",
            "412", "570", "610", "717", "724", "814", "503", "541",
            "239", "305", "321", "352", "386", "407", "561", "727",
            "772", "813", "850", "863", "904", "941", "954", "302",
            "413", "508", "617", "781", "978", "276", "434", "540",
            "703", "757", "804"};
        return arrayPhoneNumber;
    }
    
    /**
     * Accessor: lookUpAreaCode()
     * @return flag
     * Pre-condition: The arrayPhoneNumber() method must be defined.
     * Post-condition: this method will return false if 3 first digits
     * of the customerPhoneNumber (in array) variable is not match 
     * with the the customerPhoneNumber variable. Otherwise, this method
     * will return true if 3 first digits
     * of the customerPhoneNumber (in array) variable is matched 
     * with the the customerPhoneNumber variable.
     */
    private boolean lookUpAreaCode(){
        String[] arrayPhoneNumber = createArrayPhone();
        boolean flag = false;
        
        if(this.checkPhoneLength() == false){
            flag = false;
        }else{
            for(String threeFirstDigit : arrayPhoneNumber)
            {
                if(threeFirstDigit.equals(
                        customerPhoneNumber.substring(0, 3)))
                {
                    flag = true;
                }
            }
        }
        
        return flag;
    }

    /**
     * Accessor: getCheckPhone()
     * @return checkPhone
     * Pre-condition: none
     * Post-condition: 
     * return a copy of the MonthlyBill's checkPhone
     */
    public CheckCustomerPhoneNumber getCheckPhone() {
        return checkPhone;
    }

    /**
     * Mutator: setCheckPhone(CheckCustomerPhoneNumber inCheckPhone)
     * @param inCheckPhone 
     * Pre-condition: inCheckPhone must be type of CheckCustomerPhoneNumber
     * Post-condition: assigned inCheckPhone to checkPhone
     */
    public void setCheckPhone(CheckCustomerPhoneNumber inCheckPhone) {
        checkPhone = inCheckPhone;
    }
}