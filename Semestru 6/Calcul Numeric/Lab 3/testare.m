A = [10 7 8 7; 7 5 6 5; 8 6 10 9; 7 5 9 10]
b = [32 23 33 31]'

 # backslash rezolva sisteme liniare
y = A \ b

 # perturbare : +- 0.1
bp = [32.1 22.9 33.1 30.9]'
# e prost conditionata. adica o mica perturbare produce schimbari mari
yp = A \ bp

cond(A)

#function d=errel(x,xp,p=2)
#d=norm(x-xp,p)/norm(x,p);

er_rel_input = errel(b, bp)
er_rel_output = errel(y, yp)
er_rel_output / er_rel_input

 for n = 10:15
   c =  1./(1:n); # = [1/1, 1/2, .... 1/n]
   cond(vander(c))
 endfor


 # poltest -> prost conditionata pentru val > 5

 condpol(poly(1:15), 1)
 condpol(poly(1:15), 11)


 # demonstratie numar de conditionare
 # fie xi radacina simpla, nenula a polinomului
 # p = x^n + a_1* x^n-1 ...
 # 1. prima def -> cond_1_xi(a) = || (a_j * xi'aj ) / (xi(a) )_j = 1..n ||_1
# = suma:j=1..n de |a_j * xi'aj / xi(a)|


