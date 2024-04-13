cmake_minimum_required(VERSION 3.22)
project(RationalNums_Calculator)

set(CMAKE_CXX_STANDARD 17)

add_executable(RationalNums_Calculator main.cpp
        rational_calc.cpp
        Rational.cpp)
