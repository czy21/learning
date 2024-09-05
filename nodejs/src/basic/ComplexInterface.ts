//类静态部分与实例部分的区别
interface ClockConstructor {
    new(hour: number, minute: number): ClockInterface;
}

interface ClockInterface {
    sum(): void;
}

function createClock(ctor: ClockConstructor, hour: number, minute: number): ClockInterface {
    return new ctor(hour, minute);
}

class DigitalClock implements ClockInterface {
    h: number;
    m: number;

    constructor(h: number, m: number) {
        this.h = h
        this.m = m
    }

    sum(): void {
        console.log(this.h + this.m);
    }
}

let digital = createClock(DigitalClock, 12, 17);
digital.sum()

//混合类型
interface Counter {
    (start: number): string;

    interval: number;

    reset(): void;
}

function getCounter(): Counter {
    let counter: Counter;
    counter = <Counter>function (start: number) {
        console.log(start)
    }
    counter.interval = 123;
    counter.reset = function () {
    };
    return counter;
}

let c = getCounter();
c(10);
c.reset();
c.interval = 5.0;