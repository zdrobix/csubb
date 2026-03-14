function x=mymin(myone)
%>>mymin(single(1))
%>>mymin(double(1))
x=myminsubnorm(myone)/myeps(myone);