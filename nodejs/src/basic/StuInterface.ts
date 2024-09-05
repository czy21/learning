//描述带有属性的普通对象
interface Person {
    name: string
    age?: number
    readonly address?: string

    [propName: string]: any;//额外属性
}

//可索引的类型
export interface StringArray {
    [index: number]: string
}

//描述函数类型
export interface SearchFunc {
    (a: number, b: number): number;
}


export function of(p: Person): Person {
    return p
}


// 类类型
interface ClockInterface {
    currentTime: Date

    tick(): string;
}

class Clock implements ClockInterface {
    currentTime: Date;

    constructor(h: number, m: number) {
    }

    tick(): string {
        return "";
    }

}


interface ClockConstructor {
    new(hour: number, minute: number): ClockInterface;
}