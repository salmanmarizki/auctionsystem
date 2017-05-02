package auction1;
public class OurNode <E>{
    E element;
   OurNode<E> next;
   
   public OurNode() {
       element = null;
       next = null;
   }   
   
   public OurNode(E item) {
       element = item;
       next = null;
   }
}
