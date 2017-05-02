package auction1;
public class OurLinkedList<E> {
     OurNode<E> head, tail;
     int size = 0;
     
     public OurLinkedList(){}
     
    public void add(E e) {
         add(size,e);
     }
     
    public void addFirst(E e) {
      OurNode<E> newNode = new OurNode<E>(e); 
      newNode.next = head; 
      head = newNode; 
      size++; 

      if (tail == null) 
        tail = head;
    }
     
    public void addLast(E e) {
      OurNode<E> newNode = new OurNode<E>(e); 

      if (tail == null) {
        head = tail = newNode; 
      }
      else {
        tail.next = newNode; 
        tail = tail.next; 
      }

      size++; 
    }
     
    public void add(int index, E e) {
      if (index == 0) {
        addFirst(e);
      }
      else if (index >= size) {
        addLast(e);
      }
      else {
        OurNode<E> current = head;
        for (int i = 1; i < index; i++) {
          current = current.next;
        }
        OurNode<E> temp = current.next;
        current.next = new OurNode<E>(e);
        current.next.next = temp;
        size++;
      }
    }
     
    public E removeFirst() {
      if (size == 0) {
        return null;
      }
      else {
        OurNode<E> temp = head;
        head = head.next;
        size--;
        if (head == null) {
          tail = null;
        }
        return temp.element;
      }
    }
     
    public E removeLast() {
      if (size == 0) {
        return null;
      }
      else if (size == 1) {
        OurNode<E> temp = head;
        head = tail = null;
        size = 0;
        return temp.element;
      }
      else {
        OurNode<E> current = head;

        for (int i = 0; i < size - 2; i++) {
          current = current.next;
        }

        OurNode<E> temp = tail;
        tail = current;
        tail.next = null;
        size--;
        return temp.element;
      }
    }
     
    public E remove(int index) {
      if (index < 0 || index >= size) {
        return null;
      }
      else if (index == 0) {
        return removeFirst();
      }
      else if (index == size - 1) {
        return removeLast();
      }
      else {
        OurNode<E> previous = head;

        for (int i = 1; i < index; i++) {
          previous = previous.next;
        }

        OurNode<E> current = previous.next;
        previous.next = current.next;
        size--;
        return current.element;
      }
    }
     
    public boolean contains(E e) {
        OurNode<E> current = head;
        
        for(int i=0; i<size; i++) {
           if(current.element.equals(i)) {
               return true;
           }    
         current = current.next;
        }
        return false;
    }
     
    public E get(int index) {
      if(index < 0 || index > size-1) 
          return null;
        
      OurNode<E> temp = head;
      
      for(int i=0; i<index; i++) {
         temp = temp.next;
      }  
      return temp.element;             
    }
     
    public E getFirst() {
        if(size==0) {
          return null;
      }
      else 
          return head.element;
    }
     
    public E getLast(){
      if(size==0) {
          return null;
      }
      else 
          return tail.element;
    }
     
    public int indexOf(E e){
       OurNode<E> current = head;
       
       for(int i=0; i<size; i++) {
          if(current.element.equals(e)) {
                return i; 
          }
          current = current.next;
       }  
       return -1;
    }
     
    public int lastIndexOf(E e){
       OurNode<E> current = head;
       int lastIndex = -1;
       
       for(int i=0; i<size; i++) {
          if(current.element.equals(e)) {
             lastIndex = i;          
          }    
          current = current.next;
       }
       return lastIndex;
    }
     
    public E set(int index, E e){
       if(index < 0 || index > size-1)
         return null;
       
       OurNode<E> current = head;
       
       for (int i=0; i<index; i++) {
          current = current.next;         
       }    
       
       E temp = current.element;
       current.element = e;
       
       return temp;
    }
     
    public void clear(){
      head = tail = null;
      size = 0;
        System.out.println("List is now empty.");
    }
     
    public void printList() {
        OurNode<E> current = head;
        
        for(int i=0; i<size; i++) {
            System.out.print(current.element + " ");   
             current=current.next;
        }
        System.out.println();
    }
     
    public void reverse() {       
        for(int i=size; i>=0; i--) {
            System.out.print(get(i) + " ");
        }
    }
        
    public int getSize() {
        return size;
    }
}
