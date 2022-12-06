#include <iostream>
#include <fstream>

#include <windows.h>
#include <winuser.h>
#include <ctime>

#include <vector>

using namespace std;

const int pole_rows = 50;
const int pole_cols = 200;

const char GROUND_CH = '=';
const int GROUND = pole_rows * 4/5;

char field[pole_rows][pole_cols];

const char EMPTY_CH = 0;
const char BORDER_CH = '&';


enum COLORS {
    BLACK = 0,
    BLUE = FOREGROUND_BLUE,
    CYAN = FOREGROUND_BLUE | FOREGROUND_GREEN,
    GREEN = FOREGROUND_GREEN,
    RED = FOREGROUND_RED,
    BROWN = FOREGROUND_RED | FOREGROUND_GREEN,
    PURPLE = FOREGROUND_RED | FOREGROUND_BLUE,
    LIGHT_GREY =  FOREGROUND_RED | FOREGROUND_BLUE | FOREGROUND_GREEN,

    GREY = 0 | FOREGROUND_INTENSITY,
    LIGHT_BLUE = FOREGROUND_BLUE | FOREGROUND_INTENSITY,
    LIGHT_CYAN = FOREGROUND_BLUE | FOREGROUND_GREEN | FOREGROUND_INTENSITY,
    LIGHT_GREEN = FOREGROUND_GREEN | FOREGROUND_INTENSITY,
    LIGHT_RED = FOREGROUND_RED | FOREGROUND_INTENSITY,
    YELLOW = FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_INTENSITY,
    PINK = FOREGROUND_RED | FOREGROUND_BLUE | FOREGROUND_INTENSITY,
    WHITE =  FOREGROUND_RED | FOREGROUND_BLUE | FOREGROUND_GREEN | FOREGROUND_INTENSITY
};

const COLORS BACKGROUND_COLOR = WHITE;

HANDLE hConsoleOutput = ::GetStdHandle(STD_OUTPUT_HANDLE);
COORD screen_buf = {pole_rows , pole_cols};
CHAR_INFO blank_screen[(pole_rows) * (pole_cols)] = {0};



void setFontSize(int FontSize){
    CONSOLE_FONT_INFOEX info = {0};
    info.cbSize       = sizeof(info);
    info.dwFontSize.X = FontSize; // leave X as zero
    info.dwFontSize.Y = FontSize; // leave X as zero
    info.FontWeight   = FW_NORMAL;
    wcscpy(info.FaceName, L"Terminal");
    SetCurrentConsoleFontEx(GetStdHandle(STD_OUTPUT_HANDLE), NULL, &info);
}




void draw_char(char ch, int y, int x, COLORS foreground_color, COLORS background_color) {
    CHAR_INFO ch_info;
    ch_info.Char.AsciiChar = ch;
    ch_info.Attributes = foreground_color | (background_color << 4);

    COORD buf_size = {1, 1};
    COORD buf_coord = {0, 0};
    SMALL_RECT screen_pos = {x, y, x+1, y+1};
    ::WriteConsoleOutput(hConsoleOutput, &ch_info, buf_size, buf_coord, &screen_pos);

}

void clear_screen() {
    COORD buf_coord = {0, 0};
    SMALL_RECT screen_pos = {0, 0, screen_buf.X, screen_buf.Y};
    ::WriteConsoleOutput(hConsoleOutput, blank_screen, screen_buf, buf_coord, &screen_pos);
}

void set_background(COLORS bgr) {
    for (int i = 0; i < screen_buf.X; i++) {
        for (int j = 0; j < screen_buf.Y; j++) {
             draw_char(' ', i, j, bgr, bgr);
        }
    }

}


void init(){

    SMALL_RECT rect = {0, 0, screen_buf.Y, screen_buf.X};

    ::SetConsoleScreenBufferSize(hConsoleOutput, screen_buf);
    ::SetConsoleWindowInfo(hConsoleOutput, TRUE, &rect);
    ::SetConsoleTitle("FIGURES...");

    setFontSize(8);
}

void print_field() {
    ///system("cls");
    for (int r = 0; r < pole_rows; r++) {
            cout << endl;
    }


    for (int r = 0; r < pole_rows; r++) {
        for (int c = 0; c < pole_cols; c++) {
            cout << field[r][c];
        }
        cout << endl;
    }

    return;
}

inline void change_char(char ch, int r, int c,
                        COLORS txt = BLACK,
                        COLORS bgr = BACKGROUND_COLOR) {

    draw_char(ch, r, c, txt, bgr);
    field[r][c] = ch;

    return;
}

void set_border() {
    for (int r = 0; r < pole_rows; r++) {
        change_char(BORDER_CH, r, 0, BLACK, YELLOW);
        change_char(BORDER_CH, r, pole_cols-1, BLACK, YELLOW);
    }

    for (int c = 0; c < pole_cols; c++) {
        change_char(BORDER_CH, 0, c, BLACK, YELLOW);
        change_char(BORDER_CH, pole_rows-1, c, BLACK, YELLOW);
    }

    return;
}

const int PLAYER_ROWS = 18;
const int PLAYER_COLS = 14;

struct TBullet {
    int row;
    int col;
    char ch = '*';
    int health = 1;

    void print();
    void move();
};

void TBullet::print() {
    change_char(ch, row, col, BLUE, BACKGROUND_COLOR);
}

void TBullet::move() {
    change_char(0, row, col, BACKGROUND_COLOR, BACKGROUND_COLOR);
    col++;

    if (field[row][col] != 0) {
        health--;
    }

    if (col >= pole_cols-2) {
        health = 0;
    }
    return;
}


struct TPlayer {
    int row;
    int col;

    char img[PLAYER_ROWS][PLAYER_COLS];
    vector<TBullet> bullets;
    int health = 1000;

    void print();
    void print_bullets();
    void shoot();
    void move();
};

void TPlayer::print_bullets() {
    int sz = bullets.size();
    for (int i = 0; i <sz; i++) {
        bullets[i].print();
    }
}

void TPlayer::shoot() {
    TBullet newBullet;

    newBullet.row = row + PLAYER_ROWS/2;
    newBullet.col = col + PLAYER_COLS;

    bullets.push_back(newBullet);

    return;
}

void TPlayer::move() {
    /// change position (key control)
    if (GetAsyncKeyState('W')) {row--;}
    else { row++; }
    ///if (GetAsyncKeyState('S')) {row++;}
    if (GetAsyncKeyState('A')) {col--;}
    if (GetAsyncKeyState('D')) {col++;}
    if (GetAsyncKeyState(VK_SPACE)) {shoot();}


    /// validate the new positioni
    if (row < 1)                    { row = 1; }
    if (row > GROUND - PLAYER_ROWS) { row = GROUND - PLAYER_ROWS; }
    if (col < 1)                    { col = 1; }

    /// check for collision
    for (int r = 0; r < PLAYER_ROWS; r++) {
        health -= (field[row+r][col + PLAYER_COLS] == '#');
        health -= (field[row+r][col - 1] == '#');
    }

    for (int c = 0; c < PLAYER_COLS; c++) {
       health -= (field[row-1][col+c] == '#');
       health -= (field[row+PLAYER_ROWS][col+c] == '#');
       break; /// single damage only
    }


    /// bullets
    int sz = bullets.size();
    for (int i = 0; i < sz; i++) {
        bullets[i].move();
    }

    for (int i = 0; i < sz; i++) {
        if (bullets[i].health <= 0) {
            change_char(0, bullets[i].row, bullets[i].col, BACKGROUND_COLOR, BACKGROUND_COLOR);
            bullets.erase(bullets.begin() + i);
            i--;
            sz--;
        }
    }
    return;
}


void TPlayer::print() {
    COLORS color;
    for (int r = 0; r < PLAYER_ROWS; r++) {
        for (int c = 0; c < PLAYER_COLS; c++) {
            switch (img[r][c]) {
                case 'R' : color = RED; break;
                case 'B' : color = BLACK; break;
                case 'P' : color = BROWN; break;
                case 'L' : color = LIGHT_BLUE; break;
                case 'U' : color = BLUE; break;
                case 'Y' : color = YELLOW; break;

                default: color = BACKGROUND_COLOR;
            }
            change_char(img[r][c], row + r, col + c, color, color);
        }
    }

    return;
}

void add_ground() {
    for (int c = 1; c < pole_cols-1; c++) {
        change_char(GROUND_CH, GROUND, c, GREEN, BACKGROUND_COLOR);
    }
}

void load_map() {
    int max_stones = 20;

    int br = 0;
    int rand_r, rand_c, rand_l;

    while(br < max_stones) {
        rand_l = 1 + rand() % 3;
        rand_r = 1 + rand() % (GROUND-1);
        rand_c = PLAYER_COLS + 1 + rand() % (pole_cols-2-rand_l-PLAYER_COLS);


        if (field[rand_r][rand_c] == 0) {
            for (int i = 0; i < rand_l; i++) {
                change_char('#', rand_r, rand_c + i, GREEN, GREEN);
            }
            br++;
        }
    }
}


void new_stone(int max_stones) {

    int br = 0;
    while (br < max_stones) {
        int rand_r, rand_c, rand_l;

        rand_l = 1 + rand() % 3;
        rand_r = 1 + rand() % (GROUND-2);
        rand_c = 3*pole_cols/4 + rand() % (pole_cols/4 - 2 - rand_l);

        if (field[rand_r][rand_c] == 0) {
            for (int i = 0; i < rand_l; i++) {
                change_char('#', rand_r, rand_c + i, GREEN, GREEN);
            }
            br++;
        }
    }
}

void clean() {
    COLORS color;
    /// field
    for (int r = 1; r < pole_rows-1; r++) {
        for (int c = 1; c < pole_cols-2; c++) {
            /// move all stone one position left
            if (((field[r][c] == 0)   && (field[r][c+1] == '#')) ||
                ((field[r][c] == '#') && (field[r][c+1] == 0))) {

                    switch (field[r][c+1]) {
                        case 'R' : color = RED; break;
                        case 'B' : color = BLACK; break;
                        case 'P' : color = BROWN; break;
                        case 'L' : color = LIGHT_BLUE; break;
                        case 'U' : color = BLUE; break;
                        case 'Y' : color = YELLOW; break;
                        case '#' : color = GREEN; break;

                        default: color = BACKGROUND_COLOR;
                    }
                    change_char(field[r][c+1], r, c, color, color);
            }
            else if ((field[r][c] != 0) && (field[r][c] != '#') && (field[r][c] != GROUND_CH)){
                /// delete the hero from the field
                change_char(0, r, c, BACKGROUND_COLOR, BACKGROUND_COLOR);
            }
        }
    }
    return;
}

void print_score(int value, int row, int col) {

    int pos = col + 10;

    while (value != 0) {
        draw_char((value % 10) + '0', row, pos, RED, BACKGROUND_COLOR);
        pos--;
        value = value / 10;
    }
    draw_char(' ', row, pos, BACKGROUND_COLOR, BACKGROUND_COLOR);

    return;
}

void print_string(string str, int row, int col) {
    int len = str.length();

    for (int i = 0; i < len; i++) {
        draw_char(str[i], row, col + i, RED, BACKGROUND_COLOR);
    }

    return;
}

const int WIN_SCORE = 1000000;

int main() {

    /// initialization
    int score = 0;
    srand(time(0));


    /// console + map
    init();
    set_background(BACKGROUND_COLOR);
    set_border();
    add_ground();
    load_map();


    /// Mario
    TPlayer hero;

    hero.row = GROUND - PLAYER_ROWS;
    hero.col = 1;

    ifstream file_in;
    file_in.open("mario.txt");
    /// TODO: error handling

    for (int r = 0; r < PLAYER_ROWS; r++) {
        for (int c = 0; c < PLAYER_COLS; c++) {
            file_in >> hero.img[r][c];

            /// transparent pixels
            if (hero.img[r][c] == '.') {
                hero.img[r][c] = 0;
            }
        }
    }
    file_in.close();

    hero.print();
    ///print_field();


    int tmp_r = 0, tmp_c = 0;
    while ((hero.health > 0) && (score < WIN_SCORE)) {

        clean();

        hero.move();

        hero.print();
        hero.print_bullets();

        if (score % 20 == 0) {
            new_stone(rand()%10);
        }

        print_string("Score: ", pole_rows, 3);
        print_score(score, pole_rows, 10);

        print_string("HEALTH: ", pole_rows, 25);
        print_score(hero.health, pole_rows, 33);

        Sleep(30);
        score++;
    }

    if (hero.health > 0) {
        print_string("WIN", pole_rows/2, pole_cols/2 - 2);
    }
    else {
        print_string("GAME OVER", pole_rows/2, pole_cols/2 - 5);
    }

    Sleep(30000);

    return 0;
}
