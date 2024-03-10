//
// Created by Stefan on 10/03/2024.
//

#ifndef RATIONALCALCULATOR_RATIONAL_H
#define RATIONALCALCULATOR_RATIONAL_H


#include <string>

class Rational {
public:
    /* Constructors */
    Rational();
    Rational(int num, int den);
    explicit Rational(int num);
    explicit Rational(std::string str);

    /* Accessors */
    int getNum() const;
    int getDen() const;
    std::string str() const;

    bool is_pos() const;
    bool is_neg() const;
    bool is_inf() const;
    bool is_nan() const;

    /* Modifiers */
    void set(int num, int den);
    void str(std::string s);

    /* Operators */
    Rational add(Rational b) const;
    Rational sub(Rational b) const;
    Rational mul(Rational b) const;
    Rational div(Rational b) const;

private:
    int num_;
    int den_;
};





#endif //RATIONALCALCULATOR_RATIONAL_H
