package com.example.testkotlindemo



open class Test{
    var name :String="admin";
    var pwd :Int=123456;
}


class ChildTest: Test() {
    fun childMethodTest(){
        println("name:=====>"+name)
        println("pwd:=====>"+pwd)
    }
}

fun main() {
//    println("hello world")
//    test()
//    testApple()
//    forDemo()
//    breakDemo()
//    var childTest=ChildTest()
//    childTest.childMethodTest()

    var asd= B()
//    val qwe=asd as A
    asd.testA()
}

fun test(){
    val x:Int=1
    when(x){//替换了switch
        1-> println("******1")
        2-> println("=====2")
        else -> println("=====3")
    }
}

fun testApple(){//setOf设置set集合内容返回一个set集合。in判断该对象是否在该区域之内
    val items= setOf("apple","banana","kiwi");
    when{
        "orange" in items-> println("\nthis is orange")
        "banana" in items-> println("\nthis is banana")
    }
}

fun forDemo() {//for循环遍历集合1:类似for_each;2:类似for循环。index=0；index<list.size;index++
    val items = listOf("apple", "banana", "kiwi")
    for (item in items) {
        println(item)
    }

    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }
}

fun whileDemo() {//while和do..while的循环的使用例子
    println("----while 使用-----")
    var x = 5
    while (x > 0) {
        println( x--)
    }
    println("----do...while 使用-----")
    var y = 5
    do {
        println(y--)
    } while(y>0)
}

fun breakDemo() {
    for (i in 1..10) {//类似int i=0;i<list.size;i++
        if (i==3) continue  // i 为 3 时跳过当前循环，继续下一次循环
        println(i)
        if (i>5) break   // i 为 6 时 跳出循环
    }
}

open class A{
    open fun testA(){
        println("this is A")
    }
}
class B: A() {
    override fun testA(){
        println("this is B")
    }
    fun <T,R>Collection<T>.fold(initial:R,
                                combine:(acc:R,nextElement:T)->R):R{
        var accumulator:R=initial
        for (element:T in this){
            accumulator=combine(accumulator,element)
        }
        return accumulator
    }
}

