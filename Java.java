import java.util.*;

/* Donor Class */

class Donor {

    String name;
    String blood;
    String location;
    String contact;
    String status;

    Donor(String name,String blood,String location,String contact,String status){
        this.name=name;
        this.blood=blood;
        this.location=location;
        this.contact=contact;
        this.status=status;
    }

    void display(){
        System.out.println(name+" | "+blood+" | "+location+" | "+contact+" | "+status);
    }
}

/* Node for Linked List */

class Node{

    Donor data;
    Node next;

    Node(Donor data){
        this.data=data;
        this.next=null;
    }
}

/* Linked List for Donor Storage */

class DonorLinkedList{

    Node head;

    void addDonor(Donor donor){

        Node newNode=new Node(donor);

        if(head==null){
            head=newNode;
            return;
        }

        Node temp=head;

        while(temp.next!=null){
            temp=temp.next;
        }

        temp.next=newNode;
    }

    void removeDonor(String contact){

        if(head==null) return;

        if(head.data.contact.equals(contact)){
            head=head.next;
            return;
        }

        Node temp=head;

        while(temp.next!=null && !temp.next.data.contact.equals(contact)){
            temp=temp.next;
        }

        if(temp.next!=null){
            temp.next=temp.next.next;
        }
    }

    void displayDonors(){

        Node temp=head;

        while(temp!=null){
            temp.data.display();
            temp=temp.next;
        }
    }

    Donor searchByContact(String contact){

        Node temp=head;

        while(temp!=null){

            if(temp.data.contact.equals(contact)){
                return temp.data;
            }

            temp=temp.next;
        }

        return null;
    }
}

/* Stack for Undo Delete */

class UndoStack{

    Stack<Donor> stack=new Stack<>();

    void pushDeleted(Donor donor){
        stack.push(donor);
    }

    Donor undo(){

        if(stack.isEmpty()){
            System.out.println("Nothing to undo");
            return null;
        }

        return stack.pop();
    }
}

/* Queue for Emergency Requests */

class EmergencyQueue{

    Queue<String> queue=new LinkedList<>();

    void addRequest(String blood){

        queue.add(blood);
        System.out.println("Emergency request added for: "+blood);
    }

    void processRequest(){

        if(queue.isEmpty()){
            System.out.println("No emergency requests");
            return;
        }

        String req=queue.remove();
        System.out.println("Processing request for: "+req);
    }

    void showQueue(){
        System.out.println("Current Queue: "+queue);
    }
}

/* Main Class */

public class BloodDonorDSA {

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);

        DonorLinkedList donorList=new DonorLinkedList();
        UndoStack undoStack=new UndoStack();
        EmergencyQueue emergencyQueue=new EmergencyQueue();

        HashMap<String,Donor> donorHash=new HashMap<>();

        while(true){

            System.out.println("\n--- Blood Donor Management ---");
            System.out.println("1.Register Donor");
            System.out.println("2.Display Donors");
            System.out.println("3.Search Donor");
            System.out.println("4.Remove Donor");
            System.out.println("5.Undo Delete");
            System.out.println("6.Add Emergency Request");
            System.out.println("7.Process Emergency Request");
            System.out.println("8.Exit");

            int choice=sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:

                    System.out.print("Name: ");
                    String name=sc.nextLine();

                    System.out.print("Blood Group: ");
                    String blood=sc.nextLine();

                    System.out.print("Location: ");
                    String location=sc.nextLine();

                    System.out.print("Contact: ");
                    String contact=sc.nextLine();

                    System.out.print("Status (Available/Not Available): ");
                    String status=sc.nextLine();

                    Donor donor=new Donor(name,blood,location,contact,status);

                    donorList.addDonor(donor);
                    donorHash.put(contact,donor);

                    System.out.println("Donor Registered Successfully");

                    break;

                case 2:

                    donorList.displayDonors();

                    break;

                case 3:

                    System.out.print("Enter Contact: ");
                    String c=sc.nextLine();

                    Donor d=donorHash.get(c);

                    if(d!=null)
                        d.display();
                    else
                        System.out.println("Donor Not Found");

                    break;

                case 4:

                    System.out.print("Enter Contact to Remove: ");
                    String r=sc.nextLine();

                    Donor removed=donorHash.get(r);

                    if(removed!=null){

                        undoStack.pushDeleted(removed);
                        donorList.removeDonor(r);
                        donorHash.remove(r);

                        System.out.println("Donor Removed");
                    }
                    else
                        System.out.println("Donor Not Found");

                    break;

                case 5:

                    Donor undo=undoStack.undo();

                    if(undo!=null){

                        donorList.addDonor(undo);
                        donorHash.put(undo.contact,undo);

                        System.out.println("Undo Successful");
                    }

                    break;

                case 6:

                    System.out.print("Enter Blood Group Request: ");
                    String req=sc.nextLine();

                    emergencyQueue.addRequest(req);

                    break;

                case 7:

                    emergencyQueue.processRequest();

                    break;

                case 8:

                    System.exit(0);

                default:

                    System.out.println("Invalid Choice");

            }

        }

    }

}