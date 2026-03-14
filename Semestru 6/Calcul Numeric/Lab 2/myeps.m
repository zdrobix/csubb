function x=myeps(myone)
%>>myeps(single(1))
%>>myeps(double(1))
x=myone;
while x/2+1>1
   x=x/2;
endwhile