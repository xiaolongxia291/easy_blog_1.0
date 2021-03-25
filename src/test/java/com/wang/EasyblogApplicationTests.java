package com.wang;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;

@SpringBootTest
class EasyblogApplicationTests {

    @Test
    public void test(){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<10;++i){
            list.add(i);
        }
        Iterator<Integer> it=list.iterator();
        while(it.hasNext()){
            Integer i=it.next();
            System.out.println(i);
            if(i==8)it.remove();
        }
        for(Integer i:list){
            System.out.println("list:"+i);
        }
    }
}
