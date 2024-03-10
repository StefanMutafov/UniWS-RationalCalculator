//
// Created by Stefan on 10/03/2024.
//

#include "functions.h"

int gcd(int a, int b) {
    if (a < 0)
        a *= -1;
    if (b < 0)
        b *= -1;
    if (a == b)
        return a;
    if (a == 0)
        return b;
    if (b == 0)
        return a;
    if (a > b)
        return gcd(a - b, b);
    return gcd(a, b - a);
}

int lcm(int a, int b) {
    return (a * b) / gcd(a, b);
}
