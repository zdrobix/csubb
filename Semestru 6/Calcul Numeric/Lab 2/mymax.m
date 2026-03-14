function x=mymax(myone)
%>>mymax(single(1))
%>>mymax(double(1))
 x=2*myone-myeps(myone);
 while 4*x>2*x
  x=2*x;
 end
 