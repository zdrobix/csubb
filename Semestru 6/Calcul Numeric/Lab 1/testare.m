pkg load symbolic

#syms x a
# f = sqrt (a+ x)
#c_10 = coef_taylor(f, x, 10)

#R = pade_sym(exp(x), 2, 3)
#taylor(R, 'order', 2+3+1)
#taylor(exp(x), 'order', 2+3+1)
#pade_test(exp(x), 2, 3, a=-1, b=2)

# pade_sym(sin(x), 1, 1)
f = sqrt ( (1 + x/ 2) / (1+ 2* x) )
pade_test (f, 1, 1, a=0, b=3)
