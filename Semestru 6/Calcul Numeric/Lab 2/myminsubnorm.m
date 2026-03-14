function x=myminsubnorm(myone)
%>>myminsubnorm(single(1))
%>>myminsubnorm(double(1))
 x=myone;
 while x/2>0
    x=x/2;
 end