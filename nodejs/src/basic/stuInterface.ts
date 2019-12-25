//描述带有属性的普通对象
interface Person {
    name: string
    age?: number
    readonly address?: string

    [propName: string]: any;//额外属性
}

//描述函数类型
export interface SearchFunc {
    (a: number, b: number): number;
}

export function of(p: Person): Person {
    return p
}
