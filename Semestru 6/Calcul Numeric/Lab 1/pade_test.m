function pade_test(f,m,k,a=-1,b=1)
  x=symvar(f);
  fh=function_handle(f);
  R=pade_sym(f,m,k)
  Rh=function_handle(R);
  T=taylor(f,x,0,'order',m+k+1)
  Th=function_handle(T);
  clf;hold on;grid on;
  fplot(fh,[a,b],'-g','linewidth',2);
  fplot(Rh,[a,b],'-.b','linewidth',1.5);
  fplot(Th,[a,b],'--r','linewidth',1.5);
  h=legend({'f','Pade','Taylor'},'location','northeastoutside');
  set(h,'fontsize',15);
endfunction
