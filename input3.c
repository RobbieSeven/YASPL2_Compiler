#include <stdio.h>
void add(double n1, double n2, double *r) {
*r = n1 + n2;
}
void mul(double n1, double n2, double *r) {
int i;
i = 0;
*r = 0;
while (i < n2) {
*r = *r + n1;
i = i + 1;
}
}
void div(double n1, double n2, int *r) {
if (n1 >= 0 && n2 > 0) {
*r = n1 / n2;
}
else {
*r = -1;
}
}
void exp(double n, int e, double *r) {
*r = 1;
while (e > 0) {
*r = *r * n;
e = e - 1;
}
}
void fib(int n, int *r) {
int f0, f1, i;
f0 = 0;
f1 = 1;
i = 1;
while (i < n) {
*r = f0 + f1;
f0 = f1;
f1 = *r;
i = i + 1;
}
}
int main(void) {
int iNum, iRes, op, ans;
double num1, num2, res;
printf("Simple calculator\n");
ans = 1;
while (ans == 1) {
op = 0;
printf("Which operation would you like to perform?\n");
while (op == 0) {
printf("Digit 1 for addition\n");
printf("Digit 2 for multiplication\n");
printf("Digit 3 for division on positive numbers\n");
printf("Digit 4 for exponentiation\n");
printf("Digit 5 for Fibonacci\n");
scanf("%d", &op);
if (op < 1 || op > 5) {
printf("Please enter a valid input\n");
op = 0;
}
}
if (op >= 1 && op <= 3) {
printf("Enter numbers of operation:\n");
scanf("%lf%lf", &num1, &num2);
if (op == 1) {
add(num1, num2, &res);
printf("Result is %lf\n", res);
}
else {
if (op == 2) {
mul(num1, num2, &res);
printf("Result is %lf\n", res);
}
else {
div(num1, num2, &iRes);
if (!(iRes == -1)) {
printf("Result is %d\n", iRes);
}
else {
printf("Operation not valid\n");
}
}
}
}
else {
if (op == 4) {
printf("Enter base:\n");
scanf("%lf", &num1);
printf("Enter exponent:\n");
scanf("%d", &iNum);
exp(num1, iNum, &res);
printf("Result is %lf\n", res);
}
else {
if (op == 5) {
printf("Enter number:\n");
scanf("%d", &iNum);
fib(iNum, &iRes);
printf("Result is %d\n", iRes);
}
else {
printf("Operation not valid\n");
}
}
}
ans = -1;
while (ans == -1) {
printf("Enter 1 to continue or 0 to stop: ");
scanf("%d", &ans);
if (!(ans == 0) && !(ans == 1)) {
printf("Please enter a valid answer\n");
ans = -1;
}
}
}
return 0;
}

