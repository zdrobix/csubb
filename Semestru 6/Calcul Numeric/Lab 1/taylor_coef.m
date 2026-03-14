function c=taylor_coef(f,n)
  if n<0
    c=sym(0);
    return;
  end
  c=subs(diff(f,n),0)/factorial(n);
