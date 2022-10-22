#include <iostream>

int main() {
//    std::cout << "Hello, World!" << std::endl;
    //  point
    int a = 20;
    int *ip;
    ip = &a;
    std::cout << a << std::endl;
    std::cout << ip << std::endl;
    std::cout << *ip << std::endl;
    return 0;
}
