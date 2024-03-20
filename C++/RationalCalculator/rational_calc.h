//
// Created by Stefan on 10/03/2024.
//

#ifndef RATIONALCALCULATOR_RATIONAL_CALC_H
#define RATIONALCALCULATOR_RATIONAL_CALC_H

#include <string>
#include <vector>
#include "Rational.h"

enum TokenType {
    Token_Number,
    Token_Operator,
    Token_eof
};

struct Token {
    TokenType type;
    std::string value;
    int precedence;
};

class TokenArray {
public:
    /* Constructors */
    explicit TokenArray(std::string &string);

    /* Accessors */
    Token getNextToken();

    Token peekNextToken();

    Token getToken(int index);

    bool consists(const Token &t);

    [[nodiscard]] int getCurrTokenIndex() const;

    int getSize();


    /* Modifiers */
    void removeTokens(int index1, int index2);

    void reset();

    void replaceRange(int startIdx, int endIdx, const Token &replacement);

    std::string toString();

    void setToken(int index, TokenType type, std::string value, int precedence);

private:
    std::vector<Token> tokens;
    int currentToken = 0;


};

///A function that finds the greatest common divisor
///@param a The firs number
///@param b The second number
///@return The gcd of the two arguments
int gcd(int a, int b);


///A function that finds the lowest common multiple
///@param a The firs number
///@param b The second number
///@return The gcd of the two arguments
int lcm(int a, int b);

///A function that solves a line
///@param array TokenArray with the equasion
///@return Token array containing the solution
TokenArray solveLine(TokenArray array);

///A function that solves the equasion inside the inner bracket
/// \param array The initial TokenArray
/// \return A new array with inner bracket solved
TokenArray solveInnerBrackets(TokenArray array);

///A function that solves all functions line sqrt and pow
/// \param array the TokenArray that needs to be simplified
/// \return A TokenArray without any sqrt and pow
TokenArray solve_functions(TokenArray array);

///A function that solves any equasion without brackets or functions
/// \param array The TokenArray with the equasion
/// \return The solution
TokenArray solve_precedence(TokenArray array);


/// \param array The TokenArray to be searched
/// \return The index of the highest priority operation in an equasion
int getHighestPriorityIndex(TokenArray array);


#endif //RATIONALCALCULATOR_RATIONAL_CALC_H
