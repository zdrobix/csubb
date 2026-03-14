function  pol_test(n=15)
clf; hold on;
pkg load statistics;
a=poly(1:n);
 for i=1:100
   ap=a+normrnd(0,10^-5,1,n+1);
   r=roots(ap);
   plot(real(r),imag(r),'.k');
 endfor
 plot(1:n,zeros(1,n),'or');
 xlim([0 n+1]);
endfunction
