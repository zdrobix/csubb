function T=taylor_draw(f,a,b,n)
  clf; hold on; grid on;
  fh=function_handle(f);
  fplot(fh,[a,b]);
  L={'f'};
  for i=1:n
    T=taylor(f,'order',i+1);
    Th=function_handle(T);
    fplot(Th,[a,b]);
    L{end+1}=['T' num2str(i)];
  endfor
  legend(L,'location','northeastoutside');
endfunction