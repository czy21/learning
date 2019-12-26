//类静态部分与实例部分的区别
interface ClockConstructor {
    new(hour: number, minute: number): ClockInterface;
}

interface ClockInterface {
    tick(): void;
}

function createClock(ctor: ClockConstructor, hour: number, minute: number): ClockInterface {
    return new ctor(hour, minute);
}

class DigitalClock implements ClockInterface {
    constructor(h: number, m: number) {
    }

    tick(): void {
        console.log("beep beep");
    }
}

class AnalogClock implements ClockInterface {
    constructor(h: number, m: number) {
    }

    tick(): void {
        console.log("tick tock");
    }
}

let digital = createClock(DigitalClock, 12, 17);
let analog = createClock(AnalogClock, 7, 32);
//

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

class Control {
    private state: any;
}

interface SelectableControl extends Control {
    select(): void;
}

class Button extends Control implements SelectableControl {
    select() {
    }
}

class TextBox extends Control {
    select() {
    }
}

// 错误：“Image”类型缺少“state”属性。
class Imagee extends Control implements SelectableControl {
    select() {
    }
}
