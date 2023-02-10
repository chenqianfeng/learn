package com.example.streamlearn;

import java.util.stream.Stream;

/**
 * @program streamLearn
 * @ClassName Event
 * @description monad 设计模式实现    event流 -> eventData流
 * @create 2023-01-29 15:45
 **/
public class Event<T> {

    T data;

    public Event(T data){ this.data = data;}

    static class EventData {
        Integer id;
        String msg;
        EventData(Integer id, String msg){
            this.id = id;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "EventData{" +
                    "id=" + id +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

    static class Transforms{
        // 可插拔的转化方法，Integer转为EventData
        static EventData transform(Integer id){
            switch (id){
                case 0: return new EventData(id, "NEW");
                case 1: return new EventData(id, "RUNNABLE");
                case 2: return new EventData(id, "BLOCKED");
                case 3: return new EventData(id, "WAITING");
                case 4: return new EventData(id, "TIMED_WAITING");
                case 5: return new EventData(id, "TERMINATED");
                default: return new EventData(id, "ERROR");
            }
        }
    }

    // 函数式接口，A -> B
    @FunctionalInterface
    interface FN<A ,B> {
        B apply(A a);
    }

    // 方法参数为FN接口的实现，调用Event的构造函数将当前data通过传入的接口转化为指定的新的类型
    <B> Event<?> map(FN<T, B> f){
        return new Event<>(f.apply(this.data));
    }

    public static void main(String[] args) {
        Stream<Event<Integer>> s = Stream.of(
                new Event<>(1),
                new Event<>(2),
                new Event<>(3),
                new Event<>(4),
                new Event<>(5)
        );

        s.map(event-> event.map(Transforms::transform)).forEach(e-> System.out.println(e.data));
    }
}
