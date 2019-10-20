#include <stdio.h>
void add(double n1, double n2, double *r) {
*r = n1 + n2;
}
void sub(double n1, double n2, double *r) {
*r = n1 - n2;
}
void mul(double n1, double n2, double *r) {
*r = n1 * n2;
}
void div(double n1, double n2, double *r) {
*r = n1 / n2;
}
int main(void) {
int op, ans;
double num1, num2, res;
printf("Simple calculator\n");
ans = 1;
while (ans == 1) {
op = 0;
printf("Which operation would you like to perform?\n");
while (op == 0) {
printf("Digit 1 for addition\n");
printf("Digit 2 for subtraction\n");
printf("Digit 3 for multiplication\n");
printf("Digit 4 for division\n");
scanf("%d", &op);
if (op < 1 || op > 4) {
printf("Please enter a valid input\n");
op = 0;
}
}
printf("Enter numbers of operation:\n");
scanf("%lf%lf", &num1, &num2);
if (op == 1) {
add(num1, num2, &res);
printf("Result is %lf\n", res);
}
else {
if (op == 2) {
sub(num1, num2, &res);
printf("Result is %lf\n", res);
}
else {
if (op == 3) {
mul(num1, num2, &res);
printf("Result is %lf\n", res);
}
else {
if (op == 4 && !(num2 == 0)) {
div(num1, num2, &res);
printf("Result is %lf\n", res);
}
else {
printf("Operation not valid\n");
}
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

