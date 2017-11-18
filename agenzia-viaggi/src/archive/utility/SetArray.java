/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;
import java.util.*;

public class SetArray<E> implements Set<E>{
    private E[] array;
    private int size, modCount=0;

    public SetArray(){
        array = (E[]) new Object[0];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }
    private int find(Object c){
        int i = 0;
        while(i<size && !(c==null ? array[i] == null : c.equals(array[i])))
            i++;
        return (i < size ? i : -1);
    }
    public boolean contains(Object o) {
        return find(o) >= 0;
    }

    public Iterator<E> iterator() {
        final SetArray col = this;

        return new Iterator<E>() {
            private int cursor = 0;
            private int lastRet = -1;
            private int expectedModCount = modCount;

            private void checkForModification(){
                if (expectedModCount != modCount)
                    throw new ConcurrentModificationException();
            }
            public boolean hasNext(){
                checkForModification();
                return cursor<size;
            }
            public E next(){
                if (!hasNext())
                    throw new NoSuchElementException();
                lastRet = cursor;
                return array[cursor++];
            }
            public void remove(){
                checkForModification();
                if (lastRet == -1)
                    throw new IllegalStateException();
                col.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            }
        };
    }

    public E[] toArray() {
        E[] a=(E[]) new Object[0];
        int n=0;
        for(int i=0;i<array.length;i++){
            if(array[i]!=null){
                n=a.length;
                a=Arrays.copyOf(a,n+1);
                a[n]=array[i];
            }
        }
        return a;
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //ADD senza ripetizione
    public boolean add(E e) {
        if (contains(e)) return false;
        if(array.length==size)
            array = Arrays.copyOf(array,(3*(size+1))/2);
        array[size] = e;
        size++;
        modCount++;
        return true;
    }
    private void remove(int i){
        modCount++;
        array[i] = array[size-1];
        size--;
    }

    public boolean remove(Object o) {
        int i = find(o);
        if (i < 0) return false;
        remove(i);
        return true;
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
