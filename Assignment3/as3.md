Your task for this assignment is in three parts.

- (Part 1) You are to **implement the *run* method** of a class called *Benchmark*. Please see the skeleton class that I created in the repository. The API of this class is as follows:

```java
public class Benchmark<T> {
  public Benchmark (Function<T,Void> f)
  public double run(T t, int m)
}
```

The generic type *T* is that of the input to the function *f* which you will pass in to the constructor. The return type of your function *f* is *Void* (which means you will have to return *null* to close the lambda that you will write in your application).

// T是函数f的输入。 f的返回类型是void（ return 关闭）

Function *f* is the function whose timing you want to measure. For example, you might create a function which sorts an array with *n* elements.  // f是你想要测量时间的函数

The first parameter to the *run* function is the parameter that will **in turn be passed** to function *f*. In the example mentioned above, this parameter will be *n*, an *Integer* and the number of elements to be sorted. 

The second parameter to the *run* function (*m)* is the number of times the function *f* will be called.

The return value from *run* is the average number of milliseconds taken for each run of function *f*.





- (Part 2) Implement two or more classes from: ***SelectionSort**,* ***InsertionSort**,* and ***ShellSort***. Each will define a method *sort* which takes as its input an *array<Comparable>[ ]* of some length (we referred to this as *n* above). See the stubs that I created in the repository. Use the implementations in the book (and lecture slides) or use your own implementation.



- (Part 3) Measure the running times of these two (or more) different sorts, and with at least **three different initial array ordering** situations: **random**, **ordered**, **partially-ordered** and **reverse-ordered**. I suggest that your arrays to be sorted are of type *Integer*. Use the **doubling method** for choosing *n* and test for **at least five values of *n**. Draw any conclusions from your observations regarding order of growth.*



As usual, the submission will be your entire project (*clean, i.e. without the target and project folders).* I will provide stubs and unit tests in the repository.

Report on your observations and show screen shots of (some of) the runs and also the unit tests.

Further notes: you should probably use the *System.nanos* method to get the clock time. This isn't guaranteed to be accurate which is one of the reason you should run the experiment several times for each value of *n*. Also, for each invocation of *run*, run the given function *f* 10 times to get the system "warmed up" before you start the timing proper.

The *Sort* interface takes care of copying the array when the *sort(array)* signature is called. It returns a new array as a result. The original array is unchanged. Therefore, you do not need to worry about the insertion-based sorts getting quicker because of the arrays getting more sorted (they don't).

If you need clarification, ask on Slack