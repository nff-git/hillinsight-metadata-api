package hillinsight.metadata.api.portal.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName Test
 * @Description TODO
 * @Author wcy
 * @Date 2021/5/21
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {


        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        LinkedList<Integer> integers1 = new LinkedList<>();

        Integer idOne = 6;
        Integer idTwo = 2;

        if(idOne > idTwo){
            //舍去 idOne 之后的数据 ，并且舍去 idTwo  之前的数据
            for (int i = idTwo-1; i < idOne; i++) {
                integers1.add(integers.get(i));
            }

        }else if(idOne < idTwo){
            //舍去 idOne 之前的数据 ，并且舍去 idTwo  之后的数据
            for (int i = idOne-1; i < idTwo; i++) {
                integers1.add(integers.get(i));
            }
        }
        //排序链表
        integers1.add(0,integers1.getLast());
        integers1.removeLast();
        for (int i = 0; i < integers1.size(); i++) {
            System.out.println(integers1.get(i));
        }







    }
}
