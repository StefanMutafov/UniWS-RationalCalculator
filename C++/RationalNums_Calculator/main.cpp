#include <iostream>
#include <fstream>
#include <sstream>
#include "Rational.h"
#include "rational_calc.h"
#include <unistd.h>

int main(int argc, char *argv[]) {

    if (argc == 2) {
        std::string fileName = argv[1];
        std::ifstream file(fileName);
        std::ofstream fout(fileName.replace(fileName.length() - 4, 4, ".out"));
        std::string cLine{};
        int lineNumber = 0;
        if (file) {
            while (!file.eof()) {
                std::getline(file, cLine);
                lineNumber++;

                try {
                    TokenArray cEquasion{cLine};
                    if (cEquasion.peekNextToken().precedence != -1) {
                        cEquasion = solveLine(cEquasion);
                    }
                    fout << cEquasion.toString() << "\n";
                }
                catch (std::invalid_argument &e) {
                    fout << "Syntax error!" << "\n";
                    std::cerr << "There was an unexpected symbol in your equations at line " << lineNumber << " : "
                              << cLine << std::endl;
                    // return EXIT_FAILURE;
                }

            }

        } else {
            std::cerr << "Error reading file " << fileName << '\n';
            return EXIT_FAILURE;
        }

    } else {
        std::cerr << "Incorrect number of arguments" << '\n';
        return EXIT_FAILURE;
    }
    return 0;
}
