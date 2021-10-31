package com.wantoper.Services;

public class MyServices implements Serverices{
    public void dosomeServices(){
        System.out.println("dosomeService");
    }

    @Override
    public void add() {
        System.out.println("增加了一个用户");
    }

    @Override
    public void remove() {
        System.out.println("删除了一个用户");
    }

    @Override
    public void update() {
        System.out.println("修改了一个用户");
    }

    @Override
    public void select() {
        System.out.println("查找了一个用户");
    }
}
