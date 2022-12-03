package com.gougou.strategy;

/**
 * @author fangxu
 * on date:2022/1/4
 */
public class StrategyPattern
{
    public static void main(String[] args)
    {
        Context context = new Context();
        Strategy strategyA = new ConcreteStrategyA();
        context.setStrategy(strategyA);
        context.algorithm();
        System.out.println("-----------------");
        Strategy strategyB = new ConcreteStrategyB();
        context.setStrategy(strategyB);
        context.algorithm();
    }
}
//抽象策略类
interface Strategy
{
    public void algorithm();    //策略方法
}
//具体策略类A
class ConcreteStrategyA implements Strategy
{
    public void algorithm()
    {
        System.out.println("具体策略A的策略方法被访问！");
    }
}
//具体策略类B
class ConcreteStrategyB implements Strategy
{
    public void algorithm()
    {
        System.out.println("具体策略B的策略方法被访问！");
    }
}
//环境类
class Context
{
    private Strategy strategy;
    public Strategy getStrategy()
    {
        return strategy;
    }
    public void setStrategy(Strategy strategy)
    {
        this.strategy=strategy;
    }
    public void algorithm()
    {
        strategy.algorithm();
    }
}
