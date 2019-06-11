package top.arron206.controller.ScoreSimulation;

public class RandInteger {
    //加载动态链接库
    static {
        System.loadLibrary("JavaNative");
    }

    //C++函数，返回一般地随机数
    public native static int uniformRand(int min, int max);

    //C++函数，返回正态分布的随机数
    public native static int normalRand(int min, int max, int E, double V);

}
