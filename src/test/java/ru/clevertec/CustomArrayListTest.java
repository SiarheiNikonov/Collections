package ru.clevertec;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.customlist.CustomArrayList;
import ru.clevertec.customlist.CustomList;
import ru.clevertec.model.I;
import ru.clevertec.model.A;
import ru.clevertec.model.B;
import java.util.Arrays;

public class CustomArrayListTest {

    CustomList<I> testList;

    @BeforeEach
    void fillList() {
        testList = new CustomArrayList<>();
        addItems(testList);
    }

    @Test
    void testTrim() {
        int oldSize = testList.size();
        int nullItemsCount = 0;
        for (int i = 0; i < testList.size(); i++) {
            if (testList.get(i) == null) nullItemsCount++;
        }
        testList.trim();
        int newSize = testList.size();
        assert oldSize - nullItemsCount == newSize;
    }

    @Test
    void testAdd() {
        int oldSize = testList.size();
        int addedItemsCount = 0;
        for (int i = 0; i < getRandomInt(3,13); i++) addedItemsCount += addItems(testList);
        int newSize = testList.size();
        assert newSize == oldSize + addedItemsCount;
    }

    @Test
    void testToArray(){
        int listSize = testList.size();
        Object[] array = testList.toArray();
        boolean flag = true;
        for (Object o : array) {
            if (!(o instanceof I) && o != null) {
                flag = false;
            }
        }
        assert flag;
        assert listSize == array.length;
    }

    @Test
    void testAddAllChildList() {
        CustomList<B> newList = new CustomArrayList<>();
        int oldSize = testList.size();
        int addedItemsCount = 0;
        for (int i = 0; i < getRandomInt(5,50); i++) {
            addedItemsCount++;
            newList.add(new B());
        }
        testList.addAll(newList);
        assert testList.size() == oldSize + addedItemsCount;
    }

    @Test
    void testAddAllSupertypeList() {
        CustomList<I> newList = new CustomArrayList<>();
        int oldSize = testList.size();
        int addedItemsCount = 0;
        for (int i = 0; i < getRandomInt(3,13); i++) addedItemsCount += addItems(newList);
        testList.addAll(newList);
        assert testList.size() == oldSize + addedItemsCount;
    }

    @Test
    void testAddAllChildArray() {
        int addedItemsCount = getRandomInt(10,100);
        B[] array = new B[addedItemsCount];
        Arrays.fill(array, new B());
        int oldSize = testList.size();
        testList.addAll(array);
        assert oldSize + addedItemsCount == testList.size();
    }

    @Test
    void testAddAllSupertypeArray() {
        CustomList<I> newList = new CustomArrayList<>();
        int addedItemsCount = 0;
        for (int i = 0; i < getRandomInt(3,13); i++) addedItemsCount += addItems(newList);
        Object[] array = newList.toArray();
        int oldSize = testList.size();
        testList.addAll(array);
        assert oldSize + addedItemsCount == testList.size();
    }

    @Test
    void testSet(){
        CustomList<I> list = new CustomArrayList<>();
        list.add(null);
        list.add(null);
        list.set(0, new A());
        list.set(1, new B());
        assert list.get(0) instanceof A;
        assert list.get(1) instanceof B;
    }


    @Test
    void testRemove(){
        CustomList<I> list = new CustomArrayList<>();
        list.add(new A());
        list.add(new B());
        int oldSize = list.size();
        list.remove(0);
        assert list.size() == oldSize - 1;
        assert list.get(0) instanceof B;
    }

    @Test
    void testClear(){
        int oldSize = testList.size();
        testList.clear();
        assert testList.size() == 0 && oldSize != 0;
    }

    @Test
    void testFind(){
        CustomList<I> list = new CustomArrayList<>();
        I itemForSearch = new B();
        list.add(new A());
        list.add(itemForSearch);
        int position = list.find(itemForSearch);
        assert position == 1;
        list.set(1, new A());
        position = list.find(itemForSearch);
        assert position == -1;
    }

    @Test
    void testSetMaxSize(){
        while(testList.size() < 300) addItems(testList);
        int newSize = (int) Math.round(Math.random() * 100 + 100);
        Object[] array = Arrays.copyOf(testList.toArray(), newSize);
        testList.setMaxSize(newSize);

        assert newSize == testList.size();

        boolean flag = true;
        for(int i = 0; i < array.length; i++){
            if(array[i] != testList.get(i)) flag = false;
        }
        assert flag;
    }

    private int addItems(CustomList<I> list) {
        int itemsCount = getRandomInt(10,100);
        for (int i = 0; i < itemsCount; i++) {
            switch (getRandomInt(0,2)){
                case 0: {
                    list.add(new A());
                    break;
                }
                case 1: {
                    list.add(new B());
                    break;
                }
                case 2: {
                    list.add(null);
                }
            }
        }
        return itemsCount;
    }

    private int getRandomInt(int from, int to) {
        return (int) Math.round(Math.random() * (to-from)) + from;
    }
}
