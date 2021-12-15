package gb.hw.lesson7;

import gb.hw.lesson7.interfaces.AfterSuite;
import gb.hw.lesson7.interfaces.BeforeSuit;
import gb.hw.lesson7.interfaces.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class TestStarter {
    public static void main(String[] args) {
        TestStarter startTest = new TestStarter();
        App01 app01 = new App01();

        startTest.start(App01.class);
        //startTest.start(app01);
        // startTest.start("gb.hw.lesson7.App01");

    }

//--------------------------METHODS-----------------------------//
    public void start(Object ob){
        Class clazz = ob.getClass();
        work(clazz);
    }

    public void start(String s){
        try {
        Class clazz = Class.forName(s);
        work(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start(Class clazz){
            work(clazz);
    }

    public void work(Class clazz){
        Constructor cons = null;
        try {
            cons = clazz.getConstructor();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Object obj = null;
        try {
            obj = cons.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Method[] methods = clazz.getDeclaredMethods();
        int before=0, after=0;
        Set<Integer> hashSet = new HashSet<>();
        //Checking methods
        for (Method o : methods) {
            if(o.getAnnotation(BeforeSuit.class) != null) {
                before++;
            }
            if(o.getAnnotation(AfterSuite.class)!=null){
                after++;
            }
            if(o.getAnnotation(Test.class)!=null){
                hashSet.add(o.getAnnotation(Test.class).value());
            }
        }
        if (before!=1 || after!=1){
            throw new RuntimeException("Error!!!");
        }
        else {
            List<Integer> indexes = new ArrayList<>(hashSet);
            Collections.sort(indexes);
            try {
                    for (Method o : methods) {
                        if (o.getAnnotation(BeforeSuit.class) != null) {
                            o.invoke(obj);
                        }
                    }
                    for (int i =0; i<hashSet.size();i++) {
                        for (Method o : methods) {
                            if(o.getAnnotation(Test.class) != null && o.getAnnotation(Test.class).value() == (int) indexes.get(i)){
                                o.invoke(obj,3,2);
                            }
                        }
                    }
                    for (Method o : methods) {
                        if (o.getAnnotation(AfterSuite.class) != null) {
                            o.invoke(obj);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
        }

    }

}
