package gb.hw.lesson7;

import gb.hw.lesson7.interfaces.AfterSuite;
import gb.hw.lesson7.interfaces.BeforeSuit;
import gb.hw.lesson7.interfaces.Test;

public class App01 {
    private App01 app01;
    @BeforeSuit
    public void init(){
        System.out.println("Test started");
    }

    @Test(value = 2)
    public void sum(int a, int b){
        System.out.println(a + b);
    }
    @Test(value = 1)
    public void dif(int a,int b){
        System.out.println(a - b);
    }

    @Test(value = 1)
    public void multi(int a, int b) {
        System.out.println(a * b);
    }

    @AfterSuite
    public void end(){
        System.out.println("Test complete");
    }

}
