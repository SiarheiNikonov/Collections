package ru.clevertec.customlist;

public class CustomLinkedList<E> implements CustomList<E> {
    private Node<E> firstNode;
    private Node<E> lastNode;
    private int mCurrentSize;
    private Integer maxSize;

    @Override
    public CustomIterator<E> getIterator() {
        return new CustomLinkedListIterator();
    }

    @Override
    public void setMaxSize(int size) {
        maxSize = size;
        if(maxSize < mCurrentSize) {
            lastNode = getNode(maxSize - 1);
            Node<E> node = lastNode.next;
            while(node != null) {
                Node<E> nextNode = node.next;
                node.previous = null;
                node.item = null;
                node.next = null;
                node = nextNode;
            }
            lastNode.next = null;
            mCurrentSize = maxSize;
        }
    }

    @Override
    public boolean add(E element) {
        if (maxSize != null && mCurrentSize >= maxSize) return false;

        if (mCurrentSize == 0) {
            firstNode = new Node<>(element, null, null);
            lastNode = firstNode;
            mCurrentSize++;
        } else {
            addLastNode(element);
        }
        return true;
    }

    @Override
    public boolean addAll(CustomList<? extends E> list) {
        if (maxSize != null && maxSize - mCurrentSize < list.size()) return false;

        CustomIterator<? extends E> iterator = list.getIterator();
        while (iterator.hasNext()) {
            if(mCurrentSize == 0) add(iterator.next());
            else {
                addLastNode(iterator.next());
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Object[] array) {
        if (maxSize != null && maxSize - mCurrentSize < array.length) return false;
        for (Object o : array) {
            if (mCurrentSize == 0) add((E) o);
            else {
                addLastNode((E) o);
            }
        }
        return true;
    }

    @Override
    public E set(int pos, E newElement) {
        if(pos >= mCurrentSize || pos < 0) return null;
        Node<E> node = getNode(pos);
        E item = node.item;
        node.item = newElement;
        return item;
    }

    @Override
    public E remove(int pos) {
        if(pos >= mCurrentSize || pos < 0) return null;
        Node<E> node = getNode(pos);
        E item = node.item;
        removeNode(node);

        return item;
    }

    @Override
    public void clear() {
        if(mCurrentSize == 0) return;
        while(firstNode.next != null) {
            Node<E> next = firstNode.next;
            firstNode.item = null;
            firstNode.next = null;
            firstNode = next;
        }
        firstNode = null;
        lastNode = null;
        mCurrentSize = 0;
    }

    @Override
    public int find(E element) {
        if(element == null) return -1;
        Node<E> node = firstNode;
        int pos = 0;
        while(!element.equals(node.item)) {
            pos++;
            node = node.next;
            if(node == null) return -1;
        }

        return pos;
    }

    @Override
    public E get(int pos) {
        if(pos >= mCurrentSize || pos < 0) return null;
        return getNode(pos).item;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[mCurrentSize];
        Node<E> node = firstNode;
        int position = 0;
        while(node != null) {
            array[position] = node.item;
            node = node.next;
            position++;
        }
        return array;
    }

    @Override
    public int size() {
        return mCurrentSize;
    }

    @Override
    public void trim() {
        if( mCurrentSize == 0) return;
        Node<E> node = firstNode;
        while(node != null) {
            Node<E> nextNode = node.next;
            if(node.item == null){
                removeNode(node);
            }
            node = nextNode;
        }
    }

    private void removeNode(Node<E> node) {
        Node<E> prev = node.previous;
        Node<E> next = node.next;
        if(prev != null) prev.next = next;
        if(next != null) next.previous = prev;
        if(node == firstNode) firstNode = next;
        if(node == lastNode) lastNode = prev;
        node.item = null;
        node.next = null;
        node.previous = null;
        mCurrentSize--;
    }

    private Node<E> getNode(int pos){
        Node<E> node;
        if( mCurrentSize/2 > pos + 1){
            node = firstNode;
            for(int i = 0; i< pos; i++){
                node = node.next;
            }
        } else {
            node = lastNode;
            for(int i = mCurrentSize-1; i > pos; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private void addLastNode(E element) {
        Node<E> node = new Node<>(element, lastNode, null);
        lastNode.next = node;
        lastNode = node;
        mCurrentSize++;
    }


    private static class Node<E> {
        E item;
        Node<E> previous;
        Node<E> next;


        public Node(E item, Node<E> previous, Node<E> next) {
            this.previous = previous;
            this.next = next;
            this.item = item;
        }
    }
    private class CustomLinkedListIterator implements CustomIterator<E> {

        Node<E> nextNode = firstNode;
        Node<E> prevNode;

        @Override
        public E next() {
            prevNode = nextNode;
            nextNode = nextNode.next;
            return prevNode.item;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public void remove() {
            removeNode(prevNode);
        }

        @Override
        public void addBefore(E element) {
            Node<E> node = new Node<>(element, prevNode, nextNode);
            add(node);
            prevNode = node;
        }

        @Override
        public void addAfter(E element) {
            Node<E> node = new Node<>(element, prevNode, nextNode);
            add(node);
            nextNode = node;
        }

        private void add(Node<E> node) {
            if(prevNode != null) prevNode.next = node;
            if(nextNode != null) nextNode.previous = node;
        }
    }
}
