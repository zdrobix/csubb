function c = coef_taylor(f,x,n, a = 0)

  df_n = diff(f, x, n)

  df_n_a = subs(df_n, x, a)

  c = df_n_a / factorial(n)
