#include <stdio.h>
void askOperation(int *userOp) {
*userOp = 0;
printf("Which operation would you like to perform?\n");
while (*userOp == 0) {
printf("Digit 1 for addition\n");
printf("Digit 2 for subtraction\n");
printf("Digit 3 for multiplication\n");
printf("Digit 4 for division\n");
scanf("%d", &*userOp);
if (*userOp < 1 || *userOp > 4) {
printf("Please enter a valid input\n");
*userOp = 0;
}
}
}
void askNumbers(double *userNum1, double *userNum2) {
printf("Enter first number of operation: ");
scanf("%lf", &*userNum1);
printf("Enter second number of operation: ");
scanf("%lf", &*userNum2);
}
void askToContinue(int *ans) {
*ans = 2;
while (*ans == 2) {
printf("Enter 1 to continue or 0 to stop: ");
scanf("%d", &*ans);
if (!(*ans == 0) && !(*ans == 1)) {
printf("Please enter a valid *answer\n");
*ans = 2;
}
}
}
int main(void) {
int op, ans;
double num1, num2, res;
printf("Simple calculator\n");
ans = 1;
while (ans == 1) {
askOperation(&op);
askNumbers(&num2, &num1);
if (op == 1) {
res = num1 + num2;
printf("Result is %lf\n", res);
}
else {
if (op == 2) {
res = num1 - num2;
printf("Result is %lf\n", res);
}
else {
if (op == 3) {
res = num1 * num2;
printf("Result is %lf\n", res);
}
else {
if (op == 4) {
res = num1 / num2;
printf("Result is %lf\n", res);
}
else {
printf("Operation not valid\n");
}
}
}
}
askToContinue(&ans);
}
return 0;
}

